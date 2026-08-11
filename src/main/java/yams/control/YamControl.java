/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.control;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import yams.Yams;
import yams.aide.AideVue;
import yams.events.AnimationLancerListener;
import yams.folder.DataFolder;
import yams.hight_scores.pojos.Score;
import yams.hight_scores.views.HightScoreVue;
import yams.hight_scores.views.ResetHightScoresVue;
import yams.model.ItemName;
import static yams.model.ItemName.SUITE;
import static yams.model.ItemName.PETITE_SUITE;
import static yams.model.ItemName.CHANCE;
import static yams.model.ItemName.CARRE;
import static yams.model.ItemName.YAM;
import static yams.model.ItemName.GRANDE_SUITE;
import static yams.model.ItemName.BRELAN;
import yams.model.YamModele;
import yams.pojos.Joueur;
import yams.aide.ReglesVue;
import yams.folder.MyFileFilter;
import yams.views.*;

/**
 *
 * @author nicolas
 */

/*
 * Fonctionnement
 * 
 * Lorsque le joueur choisis le nombre de joueur la fonction setNomJoueur est appelée pour prendre en compte les nouveaux joueurs.
 * Quand l'utilisateur valide le lancement de la partie en cliquant sur "Commencer" la fonction commencer est appelée.
 * La fonction commencer masque la fenêtre de paramétrage de la partie et affiche celle de jeu.
 * Quand un joueur lance les dés, la fonction lancer est appelée
 * cette fonction calcule la nouvelle valeur de chaque dés et demande la mise à jour des dés effectivement relancés.
 * Quand un joueur sélectionne un dé les fonctions majSelDes(i) et checkDes sont appelées
 * majSelDes(i) met à jour le dé i (elle appartien à la fenêtre de jeu)
 * checkDes verifie si des dés peuvent être lancés et bloque ou non le bouton de lancer.
 * A la fin du tour la fonction finTour(b) est appelée
 * si b est vrai, on ne peut pas annuler la fin du tour et on doit choisir quel est le score à modifier.
 * La validation de ce choix appelle la fonction validationScore qui va calculer si un score peut être inscrit dans la case choisie
 * Si oui, la case est coloriée en vert est le score est mis à jour; si non la case est coloriée en rouge et ne peut plus être choisie (cela correspond à rayer un score).
 * Lorsque la partie est terminée la fonction validationScore affiche la fenêtre de fin de partie.
 * Cette fenête permet de voir les scores, modifier les préférences, quitter le jeu, relancer la partie précédente, ou en commencer une nouvelle.
 */

/*
 * Classe controleur, elle gère le bon fonctionnement du jeu
 */
public class YamControl {
    private static final Logger LOGGER = Logger.getLogger(YamControl.class.getName());
    private ConnexionVue connexion;
    private YamModele modele;
    private JeuVue jeu;
    private FinPartieVue finPartie;
    private ConfirmQuitVue confirmQuit;
    private InfoScoreVue confScores;
    private HightScoreVue hightScore;
    private ResetHightScoresVue resetHightScores;
    private PreferencesVue preferences;
    private DataFolder data;
    private FinTourVue finTour;
    private ConfirmScoreVue confirmFinTour;
    
    private String actualWindow;
    private int nbJoueurs;
    private boolean[][] scoresValides;
    private int tour;
    private int mode;
    private List<Boolean> listPrefs;
    
    public YamControl(){
        this.data = new DataFolder();
        this.data.createDataFolder();
        this.data.createNewBDDFiles();
        this.listPrefs = data.loadPrefs();
        
        this.connexion = new ConnexionVue(this, this.listPrefs.get(Yams.PREFSOUND));
        this.connexion.affichage(true);
        this.hightScore = new HightScoreVue(this);
        this.confirmFinTour = new ConfirmScoreVue(this, this.jeu);
        this.resetHightScores = new ResetHightScoresVue(this, this.hightScore);
    }
    
    /*
     * Initialise les joueurs
     */
    public void setNomsJoueurs(){
        connexion.setJoueurs();
    }
    
    /*
     * Verification de la longueur du pseudo
     */
    public boolean checkNomJoueur(String name){
        return (name.length() < 9);
    }
    
    /*
     * Sauvegarde la derniere fenêtre ouverte
     */
    public void setActualWindow(String actualWindow) {
        this.actualWindow = actualWindow;
    }
    
    /*
     * Retourne les préférences de son
     */
    public boolean isSound() {
        return this.listPrefs.get(Yams.PREFSOUND);
    }
    
    /*
     * Affiche les préférences
     */
    public void showPrefs(){
        this.preferences = new PreferencesVue(this, listPrefs);
        if(this.actualWindow.equals("Jeu")){
            this.preferences.enableGroup(Yams.PREFSELECT, false);
            this.preferences.enableGroup(Yams.PREFRULES, false);
        }
    }
    
    /*
     * Récupère les préférences modifiées
     */
    public void changePrefs(){
        this.preferences.setPrefs();
        this.listPrefs = this.preferences.getPrefs();
        this.preferences.dispose();
        this.majSound();
    }
    
    /*
     * Retourne les coups restants
     */
    private String getCoupsRestants(int joueur){
        List<String> coups = new ArrayList<String>();
        boolean rules = this.getPrefs().get(Yams.PREFRULES);
        int nbCoups = rules ? 12 : 13;

        for(int i = 0; i < nbCoups; i++){
            String coup = ItemName.nomCoup(i, rules);
            if(this.scoresValides[joueur][i]){
                coups.add(coup);
            }
        }

        return String.join(" ", coups);
    }
    
    /*
     * Retourne les scores validés du joueur en train de jouer sous forme de tableau de booléens
     */
    public boolean[] getScoresValides(){
        boolean[] scores;
        boolean rules = this.getPrefs().get(Yams.PREFRULES);
        if(rules){
            scores = new boolean[12];
            System.arraycopy(scoresValides[tour], 0, scores, 0, 12);
        }
        else{
            scores = new boolean[13];
            System.arraycopy(scoresValides[tour], 0, scores, 0, 13);
        }
        
        return scores;
    }
    
    /*
     * fonction d'initialisation de la partie
     */
    public void commencer(){
        mode = connexion.getModeJeu();
        String[] nomsJoueurs = connexion.getNomsJoueurs();
        nbJoueurs = connexion.getNbJoueurs();
        modele = new YamModele(nbJoueurs, this);
        boolean rules = this.getPrefs().get(Yams.PREFRULES);
        if(rules){
            this.scoresValides = new boolean[nbJoueurs][12];
            for(int i = 0; i < nbJoueurs; i++){
                for(int j = 0; j < 12; j++){
                    scoresValides[i][j] = true;
                }
            }
        }
        else{
            this.scoresValides = new boolean[nbJoueurs][13];
            for(int i = 0; i < nbJoueurs; i++){
                for(int j = 0; j < 13; j++){
                    scoresValides[i][j] = true;
                }
            }
        }
        
        tour = modele.getTour();
        
        //initialisation et mise à jour des vues
        jeu = new JeuVue(nbJoueurs, nomsJoueurs, tour, this, this.mode, this.listPrefs.get(Yams.PREFSOUND));
        jeu.initDes();
        connexion.affichage(false);
        jeu.affichage(true);
        jeu.majCoupsRestants(this.getCoupsRestants(this.tour));
    }
    
    /*
     * fonction permettant de verifier si le joueur peux relancer des dés
     */
    public void checkDes(){
        boolean[] select = this.jeu.getSelectedDes();
        if(this.jeu.getLancesRestants() < 3){
            boolean garde = this.listPrefs.get(Yams.PREFSELECT);
            if(garde){
                this.jeu.setEnabledLancer(!(select[0] && select[1] && select[2] && select[3] && select[4]));
            }
            else{
                this.jeu.setEnabledLancer(!(!select[0] && !select[1] && !select[2] && !select[3] && !select[4]));
            }
        }
    }
    
    /*
     * fonction de lancement des dés
     */
    public void lancer(){
        int[] des;
        int lancesRestants = jeu.getLancesRestants();
        boolean sound = this.listPrefs.get(Yams.PREFSOUND);

        if(sound){
            modele.playSoundDe();
        }

        des = modele.lancer();
        lancesRestants = modele.majNbLances(lancesRestants);
        boolean[] selDes = this.jeu.getSelectedDes();
        boolean[] aAnimer = new boolean[5];
        boolean garde = this.listPrefs.get(Yams.PREFSELECT);
        for(int i = 0; i < 5; i++){
            aAnimer[i] = (lancesRestants == 2) || (selDes[i] != garde);
        }

        jeu.setEnabledLancer(false);
        jeu.setAnimationEnCours(true);
        Timer timer = new Timer(AnimationLancerListener.INTERVAL_MS, new AnimationLancerListener(jeu, this, aAnimer, des, lancesRestants));
        timer.start();
    }

    /*
     * appelée en fin d'animation de lancer : fixe l'état final des dés et poursuit la logique de jeu
     */
    public void finLancer(int lancesRestants){
        jeu.setAnimationEnCours(false);
        jeu.setNbLancers(lancesRestants);
        jeu.setEnabledFinTour(true);
        jeu.setTotalPoints(false);
        if(lancesRestants == 0){
            this.finTour(true);
        }
        else{
            jeu.setEnabledLancer(true);
        }
        boolean garde = this.getPrefs().get(Yams.PREFSELECT);
        if(!garde){
            this.checkDes();
        }
    }
    
    /*
     * nouvelle partie (après abandon)
     */
    public void nouveau(){
        confirmQuit.activation(false);
        jeu.affichage(false);
        connexion.affichage(true);
    }
    
    /*
     * nouvelle partie
     */
    public void nouvellePartie(){
        finPartie.affichage(false);
        finPartie.dispose();
        jeu.affichage(false);
        connexion.affichage(true);
    }
    
    /*
     * Nouvelle partie avec les mêmes joueurs que précédement
     */
    public void recommencer(){
        if(this.finPartie != null){
            this.finPartie.affichage(false);
        }
        jeu.affichage(false);
        this.commencer();
    }
    
    /*
     * Prend en compte le clic sur l'image du son
     */
    public void majSoundOnClic(){
        boolean sound = this.listPrefs.get(Yams.PREFSOUND);
        this.listPrefs.set(Yams.PREFSOUND, !sound);
        this.majSound();
    }

    /*
     * mise à jour de la préférence de son
     */
    public void majSound(){
        boolean sound = this.listPrefs.get(Yams.PREFSOUND);
        this.connexion.majSound(sound);
        if(this.jeu != null){
            this.jeu.majSound(sound);
        }
    }
    
    /*
     * fermeture du programme
     */
    public void quitter(){
        this.data.savePrefs(this.listPrefs);
        System.exit(0);
    }
    
    /*
     * gestion de la fin du tour
     */
    private int indexCoupMontant(){
        int i = 0;
        while(!scoresValides[tour][i]){
            i++;
        }
        return i;
    }

    private int indexCoupDescendant(){
        boolean rules = this.getPrefs().get(Yams.PREFRULES);
        int i = rules ? 11 : 12;
        while(!scoresValides[tour][i]){
            i--;
        }
        return i;
    }

    public void finTour(){
        jeu.setTour(tour);
        this.confirmFinTour.setVisible(false); //ferme l'éventuelle fenêtre de confirmation ouverte
        if(mode == 0){
            finTour = new FinTourVue(scoresValides, tour, this, false, this.jeu);
            finTour.setAffichage(true);
        }
        else if(mode == 1){
            finTourMontantDescendant(indexCoupMontant());
        }
        else if(mode == 2){
            finTourMontantDescendant(indexCoupDescendant());
        }
        else{
            LOGGER.warning("[ERREUR] mode de jeu faux");
            System.exit(1);
        }
    }
    
    private void finTourMontantDescendant(int index){
        String strScore = "";
        int[] des = this.jeu.getDes();
        int score = 0;
        boolean rules = this.getPrefs().get(Yams.PREFRULES);

        switch(index){
            case 0:
                score = this.modele.calc1(des, this.scoresValides, this.tour, this.jeu);
                strScore = "1";
                break;
            case 1:
                score = this.modele.calc2(des, this.scoresValides, this.tour, this.jeu);
                strScore = "2";
                break;
            case 2:
                score = this.modele.calc3(des, this.scoresValides, this.tour, this.jeu);
                strScore = "3";
                break;
            case 3:
                score = this.modele.calc4(des, this.scoresValides, this.tour, this.jeu);
                strScore = "4";
                break;
            case 4:
                score = this.modele.calc5(des, this.scoresValides, this.tour, this.jeu);
                strScore = "5";
                break;
            case 5:
                score = this.modele.calc6(des, this.scoresValides, this.tour, this.jeu);
                strScore = "6";
                break;
            case 6:
                if(rules){
                    score = this.modele.calcPlus(des, this.scoresValides, this.tour, this.jeu);
                    strScore = "+";
                }
                else{
                    score = this.modele.calcBrelan(des, this.scoresValides, this.tour, this.jeu);
                    strScore = BRELAN;
                }
                break;
            case 7:
                if(rules){
                    score = this.modele.calcMinus(des, this.scoresValides, this.tour, this.jeu);
                    strScore = "-";
                }
                else{
                    score = this.modele.calcLittleSuite(des, this.scoresValides, this.tour, this.jeu);
                    strScore = PETITE_SUITE;
                }
                break;
            case 8:
                if(rules){
                    score = this.modele.calcSuite(des, this.scoresValides, this.tour, this.jeu);
                    strScore = SUITE;
                }
                else{
                    score = this.modele.calcBigSuite(des, this.scoresValides, this.tour, this.jeu);
                    strScore = GRANDE_SUITE;
                }
                break;
            case 9:
                score = this.modele.calcFull(des, this.scoresValides, this.tour, this.jeu);
                strScore = "full";
                break;
            case 10:
                score = this.modele.calcCarre(des, this.scoresValides, this.tour, this.jeu);
                strScore = CARRE;
                break;
            case 11:
                score = this.modele.calcYam(des, this.scoresValides, this.tour, this.jeu);
                strScore = YAM;
                break;
            case 12:
                score = this.modele.calcChance(des, this.scoresValides, this.tour, this.jeu);
                strScore = CHANCE;
                break;
            default: //n'arrive jamais
                break;
        }
        
        //affichage de la fenetre d'information
        this.confScores = new InfoScoreVue(score, strScore, jeu, this);
        this.confScores.activation(true);
    }
    
    /*
     * gestion de la fin du tour en montante/descendante
     */
    public void confScores(){
        this.confScores.activation(false);
        
        if(this.modele.finPartie(this.scoresValides, this.listPrefs.get(Yams.PREFSOUND))){
            Joueur[] joueurs = new Joueur[this.nbJoueurs];

            for(int i = 0; i < this.nbJoueurs; i++){
                joueurs[i] = this.jeu.getJoueur(i);
            }
            joueurs = this.modele.sortJoueurs(joueurs);
            
            this.finPartie = new FinPartieVue(this, this.jeu, joueurs);
            this.finPartie.affichage(true);
        }
        else{
            this.tourSuivant();
            this.jeu.setTotalPoints(true);
        }
    }
    
    /*
     * Retourne le tableau des préférences
     */
    public List<Boolean> getPrefs(){
        return this.listPrefs;
    }
    
    /*
     * Retourne le nombre de joueurs
     */
    public int getNbJoueurs(){
        return this.nbJoueurs;
    }
    
    /*
     * gestion de la fin de partie
     */
    public void finTour(boolean fin){
        jeu.setTour(tour);
        if(mode == 0){
            finTour = new FinTourVue(scoresValides, tour, this, fin, this.jeu);
            finTour.setAffichage(true);
        }
        else{
            if(!fin){
                this.confirmFinTour.setVisible(true);
            }
            else{
                this.finTour();
            }
        }
    }
    
    /*
     * enregistrement des scores choisis
     */
    private void appliquerChoix(String choix, int[] des){
        switch(choix){
            case "1":
                this.modele.calc1(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "2":
                this.modele.calc2(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "3":
                this.modele.calc3(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "4":
                this.modele.calc4(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "5":
                this.modele.calc5(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "6":
                this.modele.calc6(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "+":
                this.modele.calcPlus(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "-":
                this.modele.calcMinus(des, this.scoresValides, this.tour, this.jeu);
                break;
            case SUITE:
                this.modele.calcSuite(des, this.scoresValides, this.tour, this.jeu);
                break;
            case "full":
                this.modele.calcFull(des, this.scoresValides, this.tour, this.jeu);
                break;
            case CARRE:
                this.modele.calcCarre(des, this.scoresValides, this.tour, this.jeu);
                break;
            case YAM:
                this.modele.calcYam(des, this.scoresValides, this.tour, this.jeu);
                break;
            case BRELAN:
                this.modele.calcBrelan(des, this.scoresValides, this.tour, this.jeu);
                break;
            case PETITE_SUITE:
                this.modele.calcLittleSuite(des, this.scoresValides, this.tour, this.jeu);
                break;
            case GRANDE_SUITE:
                this.modele.calcBigSuite(des, this.scoresValides, this.tour, this.jeu);
                break;
            case CHANCE:
                this.modele.calcChance(des, this.scoresValides, this.tour, this.jeu);
                break;
            default:
                break;
        }
    }

    public void validationScore(){
        String choix = this.finTour.getChoix();

        int[] des = this.jeu.getDes();

        this.appliquerChoix(choix, des);

        this.finTour.setAffichage(false);
        this.jeu.setTotalPoints(true);
        if(this.modele.finPartie(this.scoresValides, this.listPrefs.get(Yams.PREFSOUND))){
            Joueur[] joueurs = new Joueur[this.nbJoueurs];

            for(int i = 0; i < this.nbJoueurs; i++){
                joueurs[i] = this.jeu.getJoueur(i);
            }

            joueurs = this.modele.sortJoueurs(joueurs);
            this.finPartie = new FinPartieVue(this, this.jeu, joueurs);
            this.finPartie.affichage(true);
        }
        else{
            this.tourSuivant();
        }
    }
    
    /*
     * ferme la fenêtre de fin du tour
     */
    public void annulerFinTour(){
        this.finTour.setAffichage(false);
    }
    
    /*
     * préparation du tour suivant
     */
    private void tourSuivant(){
        this.jeu.setEnabledFinTour(false);
        this.jeu.setEnabledLancer(true);
        this.jeu.initDes();
        this.jeu.initScoreTour();
        this.jeu.setNbLancers(3);
        this.modele.changerJoueur();
        this.tour = this.modele.getTour();
        this.jeu.setTour(this.tour);
        this.jeu.majCoupsRestants(this.getCoupsRestants(this.tour));
    }
    
    /*
     * affichage des règles
     */
    public void affichageRegles() {
            new ReglesVue(mode, this);
    }
    
    /*
     * gestion de l'annulation de l'abandon de la partie
     */
    public void annuler(){
        this.confirmQuit.activation(false);
    }
    
    /*
     * gestion de la confirmation de l'abandon
     */
    public void confirmQuit(boolean quit){
        this.confirmQuit = new ConfirmQuitVue(quit, this.jeu, this);
        this.confirmQuit.activation(true);
    }
    
    /*
     * affichage des règles lors du choix du mode
     */
    public void apercuRegle(){
        int modeJeu = this.connexion.getModeJeu();
        new ReglesVue(modeJeu, this);
    }
    
    /**
     * affichage du mode d'emploi
     */
    public void affichHelp(){
        new AideVue();
    }
    
    /*
     * Ouvre la fenêtre des hight scores
     */
    public void openHightScores(){
        this.hightScore.toFront();
        if(!this.actualWindow.equals("FinPartie")){
            this.hightScore.setMode(this.mode);
        }
        this.hightScore.setVisible(true);
    }
    
    /*
     * Sauve les scores
     */
    public void saveHightScores(List<Score> scores, int modeJeu){
        this.data.saveScores(scores, modeJeu);
    }

    /*
     * Charge les scores
     */
    public List<Score> loadHightScores(int modeJeu){
        return this.data.loadScores(modeJeu);
    }
    
    /*
     * Ajoute un score
     */
    public void addAScore(Score s){
        this.hightScore.addScore(s, this.mode);
    }
    
    /**
     * Met en relief les score de la dernière partie
     */
    public void selectScores(List<Score> scores){
        this.hightScore.setMode(this.mode);
        this.hightScore.selectScores(scores, this.mode);
    }
    
    /*
     * ferme la fenêtre des hight scores
     */
    public void closeHightScores(){
        this.hightScore.setVisible(false);
    }
    
    /*
     * Ouvre la fenêtre de confirmation de l'effacement des scores
     */
    public void confirmResetHightScores(){
        this.resetHightScores.setVisible(true);
    }
    
    /*
     * Efface les meilleurs scores
     */
    public void resetHightScores(boolean all){
        this.resetHightScores.dispose();
        
        if(all){
            this.hightScore.setScores(new ArrayList<Score>(), Yams.MODELIBRE);
            this.data.saveScores(new ArrayList<Score>(), Yams.MODELIBRE);
            
            this.hightScore.setScores(new ArrayList<Score>(), Yams.MODEMONTANT);
            this.data.saveScores(new ArrayList<Score>(), Yams.MODEMONTANT);
            
            this.hightScore.setScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
            this.data.saveScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
        }
        else{
            switch(this.hightScore.getModeJeu()){
                case Yams.MODELIBRE:
                    this.hightScore.setScores(new ArrayList<Score>(), Yams.MODELIBRE);
                    this.data.saveScores(new ArrayList<Score>(), Yams.MODELIBRE);
                    break;
                case Yams.MODEMONTANT:
                    this.hightScore.setScores(new ArrayList<Score>(), Yams.MODEMONTANT);
                    
                    this.hightScore.changeScores(Yams.MODEMONTANT);
                    break;
                case Yams.MODEDESCENDANT:
                    this.hightScore.setScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
                    this.data.saveScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
                    break;
                default:
                    LOGGER.warning("Mode de jeu inexistant");
                    break;
            }
        }
        
        this.hightScore.changeScores(this.hightScore.getModeJeu());
    }
    
    /*
     * ferme la fenêtre de confirmation de l'effacement des scores
     */
    public void cancelResetHightScore(){
        this.resetHightScores.dispose();
    }
    
    /*
     * ferme la fenêtre de confirmation de fin du tour en montante/descendante
     */
    public void closeConfirmWindow(){
        this.confirmFinTour.setVisible(false);
    }
    
    /**
     * redimentionne les dés
     */
    public void resizeDices(boolean big){
        this.jeu.redimDices(big);
    }
    
    public void exportHightScores(){
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export des meilleurs scores");
        jfc.setAcceptAllFileFilterUsed(false);
        jfc.addChoosableFileFilter(new MyFileFilter("*.xml", "xml"));
        jfc.setFileFilter(new MyFileFilter("*.csv", "csv"));
        int returnVal = jfc.showSaveDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this.data.exportScores(jfc.getFileFilter().getDescription(), jfc.getSelectedFile().getAbsolutePath(), this.hightScore.getScores(Yams.MODELIBRE), this.hightScore.getScores(Yams.MODEMONTANT), this.hightScore.getScores(Yams.MODEDESCENDANT));
        }
    }
}