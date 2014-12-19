/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.control;

import java.util.ArrayList;
import java.util.List;
import yams.Yams;
import yams.folder.DataFolder;
import yams.hightScores.pojos.Score;
import yams.hightScores.views.HightScoreVue;
import yams.hightScores.views.ResetHightScoresVue;
import yams.model.YamModele;
import yams.pojos.Joueur;
import yams.regles.ReglesVue;
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
    private ConnexionVue _connexion;
    private YamModele _modele;
    private JeuVue _jeu;
    private FinPartieVue _finPartie;
    private ConfirmQuitVue _confirmQuit;
    private InfoScoreVue _confScores;
    private HightScoreVue _HightScore;
    private ResetHightScoresVue _resetHightScores;
    private PreferencesVue _preferences;
    private DataFolder _data;
    private FinTourVue _finTour;
    private ConfirmScoreVue _confirmFinTour;
    
    private String _actualWindow;
    private String[] _nomsJoueurs;
    private int _nbJoueurs;
    private boolean[][] _scoresValides;
    private int _tour;
    private int _mode;
    private List<Boolean> _listPrefs;
    
    public YamControl(){
        this._data = new DataFolder(this);
        this._data.createDataFolder();
        this._data.createNewBDDFiles();
        this._listPrefs = _data.loadPrefs();
        
        this._connexion = new ConnexionVue(this, this._listPrefs.get(Yams.PREFSOUND));
        this._connexion.affichage(true);
        this._HightScore = new HightScoreVue(this);
        this._confirmFinTour = new ConfirmScoreVue(this, this._jeu);
        this._resetHightScores = new ResetHightScoresVue(this, this._HightScore);
    }
    
    /*
     * Initialise les joueurs
     */
    public void setNomsJoueurs(){
        _connexion.setJoueurs();
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
        this._actualWindow = actualWindow;
    }
    
    /*
     * Retourne les préférences de son
     */
    public boolean isSound() {
        return this._listPrefs.get(Yams.PREFSOUND);
    }
    
    /*
     * Affiche les préférences
     */
    public void showPrefs(){
        this._preferences = new PreferencesVue(this, _listPrefs);
        if(this._actualWindow.equals("Jeu")){
            this._preferences.enableGroup(Yams.PREFSELECT, false);
            this._preferences.enableGroup(Yams.PREFRULES, false);
        }
    }
    
    /*
     * Récupère les préférences modifiées
     */
    public void changePrefs(){
        this._preferences.setPrefs();
        this._listPrefs = this._preferences.getPrefs();
        this._preferences.dispose();
        this.majSound();
    }
    
    /*
     * Retourne les coups restants
     */
    private String getCoupsRestants(int joueur){
        List<String> coups = new ArrayList<String>();
        String result = new String();
        int cpt = 0;
        int nbCoups;
        
        if(this.getPrefs().get(Yams.PREFRULES)){
            nbCoups = 12;
        }
        else{
            nbCoups = 13;
        }
        
        for(int i = 0; i < nbCoups; i++){
            String coup = new String();
            switch(i){
                case 0:
                    coup = "1";
                    break;
                case 1:
                    coup = "2";
                    break;
                case 2:
                    coup = "3";
                    break;
                case 3:
                    coup = "4";
                    break;
                case 4:
                    coup = "5";
                    break;
                case 5:
                    coup = "6";
                    break;
                case 6:
                    if(this.getPrefs().get(Yams.PREFRULES)){
                        coup = "+";
                    }
                    else{
                        coup = "brelan";
                    }
                    break;
                case 7:
                    if(this.getPrefs().get(Yams.PREFRULES)){
                        coup = "-";
                    }
                    else{
                        coup = "petite suite";
                    }
                    break;
                case 8:
                    if(this.getPrefs().get(Yams.PREFRULES)){
                        coup = "suite";
                    }
                    else{
                        coup = "grande suite";
                    }
                    break;
                case 9:
                    coup = "full";
                    break;
                case 10:
                    coup = "carré";
                    break;
                case 11:
                    coup = "yam's";
                    break;
                case 12:
                    if(!this.getPrefs().get(Yams.PREFRULES)){
                        coup = "chance";
                    }
                    break;
                default:
                    break;
            }
            if(this._scoresValides[joueur][i]){
                coups.add(coup);
            }
        }
        
        while(cpt < coups.size()){
            result += coups.get(cpt);
            if(cpt != coups.size()-1){
                result += " ";
            }
            cpt++;
        }
        
        return result;
    }
    
    /*
     * Retourne les scores validés du joueur en train de jouer sous forme de tableau de booléens
     */
    public boolean[] getScoresValides(){
        boolean[] scores;
        if(this.getPrefs().get(Yams.PREFRULES)){
            scores = new boolean[12];
            System.arraycopy(_scoresValides[_tour], 0, scores, 0, 12);
        }
        else{
            scores = new boolean[13];
            System.arraycopy(_scoresValides[_tour], 0, scores, 0, 13);
        }
        
        return scores;
    }
    
    /*
     * fonction d'initialisation de la partie
     */
    public void commencer(){
        _mode = _connexion.getModeJeu();
        _nomsJoueurs = _connexion.getNomsJoueurs();
        _nbJoueurs = _connexion.getNbJoueurs();
        _modele = new YamModele(_nbJoueurs, this);
        if(this.getPrefs().get(Yams.PREFRULES)){
            this._scoresValides = new boolean[_nbJoueurs][12];
            for(int i = 0; i < _nbJoueurs; i++){
                for(int j = 0; j < 12; j++){
                    _scoresValides[i][j] = true;
                }
            }
        }
        else{
            this._scoresValides = new boolean[_nbJoueurs][13];
            for(int i = 0; i < _nbJoueurs; i++){
                for(int j = 0; j < 13; j++){
                    _scoresValides[i][j] = true;
                }
            }
        }
        
        _tour = _modele.getTour();
        
        //initialisation et mise à jour des vues
        _jeu = new JeuVue(_nbJoueurs, _nomsJoueurs, _tour, this, this._mode, this._listPrefs.get(Yams.PREFSOUND));
        _jeu.initDes();
        _connexion.affichage(false);
        _jeu.affichage(true);
        _jeu.majCoupsRestants(this.getCoupsRestants(this._tour));
    }
    
    /*
     * fonction permettant de verifier si le joueur peux relancer des dés
     */
    public void checkDes(){
        boolean[] select = this._jeu.getSelectedDes();
        if(this._jeu.getLancesRestants() < 3){
            if(this._listPrefs.get(Yams.PREFSELECT)){
                if(select[0] && select[1] && select[2] && select[3] && select[4]){
                    this._jeu.setEnabledLancer(false);
                }
                else{
                    this._jeu.setEnabledLancer(true);
                }
            }
            else{
                if(!select[0] && !select[1] && !select[2] && !select[3] && !select[4]){
                    this._jeu.setEnabledLancer(false);
                }
                else{
                    this._jeu.setEnabledLancer(true);
                }
            }
        }
    }
    
    /*
     * fonction de lancement des dés
     */
    public void lancer(){
        int[]des;
        int lancesRestants = _jeu.getLancesRestants();
        
        if(this._listPrefs.get(Yams.PREFSOUND)){
            _modele.playSoundDe();
        }
        
        des = _modele.lancer();
        lancesRestants = _modele.majNbLances(lancesRestants);
        _jeu.setNbLancers(lancesRestants);
        boolean[] selDes = this._jeu.getSelectedDes();
        for(int i = 0; i < 5; i++){
            if(lancesRestants == 2){
                _jeu.setValDe(i, des[i], false);
            }
            else if(selDes[i] != this._listPrefs.get(Yams.PREFSELECT)){
                _jeu.setValDe(i, des[i], false);
            }
        }
        _jeu.setEnabledFinTour(true);
        _jeu.setTotalPoints(false);
        if(lancesRestants == 0){
            _jeu.setEnabledLancer(false);
            this.finTour(true);
        }
        if(!this.getPrefs().get(Yams.PREFSELECT)){
            this.checkDes();
        }
    }
    
    /*
     * nouvelle partie (après abandon)
     */
    public void nouveau(){
        _confirmQuit.activation(false);
        _jeu.affichage(false);
        _connexion.affichage(true);
    }
    
    /*
     * nouvelle partie
     */
    public void nouvellePartie(){
        _finPartie.affichage(false);
        _finPartie.dispose();
        _jeu.affichage(false);
        _connexion.affichage(true);
    }
    
    /*
     * Nouvelle partie avec les mêmes joueurs que précédement
     */
    public void recommencer(){
        if(this._finPartie != null){
            this._finPartie.affichage(false);
        }
        _jeu.affichage(false);
        this.commencer();
    }
    
    /*
     * Prend en compte le clic sur l'image du son
     */
    public void majSoundOnClic(){
        this._listPrefs.set(Yams.PREFSOUND, !this._listPrefs.get(Yams.PREFSOUND));
        this.majSound();
    }
    
    /*
     * mise à jour de la préférence de son
     */
    public void majSound(){
        this._connexion.majSound(this._listPrefs.get(Yams.PREFSOUND));
        if(this._jeu != null){
            this._jeu.majSound(this._listPrefs.get(Yams.PREFSOUND));
        }
    }
    
    /*
     * fermeture du programme
     */
    public void quitter(){
        this._data.savePrefs(this._listPrefs);
        System.exit(0);
    }
    
    /*
     * gestion de la fin du tour
     */
    public void finTour(){
        _jeu.setTour(_tour);
        this._confirmFinTour.setVisible(false); //ferme l'éventuelle fenêtre de confirmation ouverte
        if(_mode == 0){
            _finTour = new FinTourVue(_scoresValides, _tour, this, false, this._jeu);
            _finTour.setAffichage(true);
        }
        else if(_mode == 1){
            int i = 0;
            while(!_scoresValides[_tour][i]){
                i++;
            }
            finTourMontantDescendant(_tour, i);
        }
        else if(_mode == 2){
            int i;
            if(this.getPrefs().get(Yams.PREFRULES)){
                i = 11;
            }
            else{
                i = 12;
            }
            while(!_scoresValides[_tour][i]){
                i--;
            }
            finTourMontantDescendant(_tour, i);
        }
        else{
            System.err.println("[ERREUR] mode de jeu faux");
            System.exit(1);
        }
    }
    
    private void finTourMontantDescendant(int joueur, int index){
        String strScore = new String();
        int[] des = this._jeu.getDes();
        int score = 0;
        
        switch(index){
            case 0:
                score = this._modele.calc1(des, this._scoresValides, this._tour, this._jeu);
                strScore = "1";
                break;
            case 1:
                score = this._modele.calc2(des, this._scoresValides, this._tour, this._jeu);
                strScore = "2";
                break;
            case 2:
                score = this._modele.calc3(des, this._scoresValides, this._tour, this._jeu);
                strScore = "3";
                break;
            case 3:
                score = this._modele.calc4(des, this._scoresValides, this._tour, this._jeu);
                strScore = "4";
                break;
            case 4:
                score = this._modele.calc5(des, this._scoresValides, this._tour, this._jeu);
                strScore = "5";
                break;
            case 5:
                score = this._modele.calc6(des, this._scoresValides, this._tour, this._jeu);
                strScore = "6";
                break;
            case 6:
                if(this.getPrefs().get(Yams.PREFRULES)){
                    score = this._modele.calcPlus(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "+";
                }
                else{
                    score = this._modele.calcBrelan(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "brelan";
                }
                break;
            case 7:
                if(this.getPrefs().get(Yams.PREFRULES)){
                    score = this._modele.calcMinus(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "-";
                }
                else{
                    score = this._modele.calcLittleSuite(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "petite suite";
                }
                break;
            case 8:
                if(this.getPrefs().get(Yams.PREFRULES)){
                    score = this._modele.calcSuite(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "suite";
                }
                else{
                    score = this._modele.calcBigSuite(des, this._scoresValides, this._tour, this._jeu);
                    strScore = "grande suite";
                }
                break;
            case 9:
                score = this._modele.calcFull(des, this._scoresValides, this._tour, this._jeu);
                strScore = "full";
                break;
            case 10:
                score = this._modele.calcCarre(des, this._scoresValides, this._tour, this._jeu);
                strScore = "carré";
                break;
            case 11:
                score = this._modele.calcYam(des, this._scoresValides, this._tour, this._jeu);
                strScore = "yam's";
                break;
            case 12:
                score = this._modele.calcChance(des, this._scoresValides, this._tour, this._jeu);
                strScore = "chance";
                break;
            default: //n'arrive jamais
                break;
        }
        
        //affichage de la fenetre d'information
        this._confScores = new InfoScoreVue(score, strScore, _jeu, this);
        this._confScores.activation(true);
    }
    
    /*
     * gestion de la fin du tour en montante/descendante
     */
    public void confScores(){
        this._confScores.activation(false);
        
        if(this._modele.finPartie(this._scoresValides, this._listPrefs.get(Yams.PREFSOUND))){
            Joueur[] joueurs = new Joueur[this._nbJoueurs];
            int max = 0;
            
            for(int i = 0; i < this._nbJoueurs; i++){
                joueurs[i] = this._jeu.getJoueur(i);
            }
            System.out.println("tri joueur");
            joueurs = this._modele.sortJoueurs(joueurs);
            
            this._finPartie = new FinPartieVue(this, this._jeu, joueurs);
            this._finPartie.affichage(true);
        }
        else{
            this.tourSuivant();
            this._jeu.setTotalPoints(true);
        }
    }
    
    /*
     * Retourne le tableau des préférences
     */
    public List<Boolean> getPrefs(){
        return this._listPrefs;
    }
    
    /*
     * Retourne le nombre de joueurs
     */
    public int getNbJoueurs(){
        return this._nbJoueurs;
    }
    
    /*
     * gestion de la fin de partie
     */
    public void finTour(boolean fin){
        _jeu.setTour(_tour);
        if(_mode == 0){
            _finTour = new FinTourVue(_scoresValides, _tour, this, fin, this._jeu);
            _finTour.setAffichage(true);
        }
        else{
            if(!fin){
                this._confirmFinTour.setVisible(true);
            }
            else{
                this.finTour();
            }
        }
    }
    
    /*
     * enregistrement des scores choisis
     */
    public void validationScore(){
        String choix;
        choix = this._finTour.getChoix();
        
        int[] des = this._jeu.getDes();
        
        if(choix.equals("1")){
            this._modele.calc1(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("2")){
           this._modele.calc2(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("3")){
            this._modele.calc3(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("4")){
            this._modele.calc4(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("5")){
            this._modele.calc5(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("6")){
            this._modele.calc6(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("+")){
            this._modele.calcPlus(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("-")){
            this._modele.calcMinus(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("suite")){
            this._modele.calcSuite(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("full")){
            this._modele.calcFull(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("carré")){
            this._modele.calcCarre(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("yam's")){
            this._modele.calcYam(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("brelan")){
            this._modele.calcBrelan(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("petite suite")){
            this._modele.calcLittleSuite(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("grande suite")){
            this._modele.calcBigSuite(des, this._scoresValides, this._tour, this._jeu);
        }
        else if(choix.equals("chance")){
            this._modele.calcChance(des, this._scoresValides, this._tour, this._jeu);
        }
        
        this._finTour.setAffichage(false);
        this._jeu.setTotalPoints(true);
        if(this._modele.finPartie(this._scoresValides, this._listPrefs.get(Yams.PREFSOUND))){
            Joueur[] joueurs = new Joueur[this._nbJoueurs];
            int max = 0;
            
            for(int i = 0; i < this._nbJoueurs; i++){
                joueurs[i] = this._jeu.getJoueur(i);
            }
            
            joueurs = this._modele.sortJoueurs(joueurs);
            this._finPartie = new FinPartieVue(this, this._jeu, joueurs);
            this._finPartie.affichage(true);
        }
        else{
            this.tourSuivant();
        }
    }
    
    /*
     * ferme la fenêtre de fin du tour
     */
    public void annulerFinTour(){
        this._finTour.setAffichage(false);
    }
    
    /*
     * préparation du tour suivant
     */
    private void tourSuivant(){
        this._jeu.setEnabledFinTour(false);
        this._jeu.setEnabledLancer(true);
        this._jeu.initDes();
        this._jeu.initScoreTour();
        this._jeu.setNbLancers(3);
        this._modele.changerJoueur();
        this._tour = this._modele.getTour();
        this._jeu.setTour(this._tour);
        this._jeu.majCoupsRestants(this.getCoupsRestants(this._tour));
    }
    
    /*
     * affichage des règles
     */
    public void affichageRegles() {
            ReglesVue rav = new ReglesVue(_mode, this);
    }
    
    /*
     * gestion de l'annulation de l'abandon de la partie
     */
    public void annuler(){
        this._confirmQuit.activation(false);
    }
    
    /*
     * gestion de la confirmation de l'abandon
     */
    public void confirmQuit(boolean quit){
        this._confirmQuit = new ConfirmQuitVue(quit, this._jeu, this);
        this._confirmQuit.activation(true);
    }
    
    /*
     * affichage des règles lors du choix du mode
     */
    public void apercuRegle(){
        int mode = this._connexion.getModeJeu();
        ReglesVue rv = new ReglesVue(mode, this);
    }
    
    /*
     * Ouvre la fenêtre des hight scores
     */
    public void openHightScores(){
        this._HightScore.toFront();
        if(!this._actualWindow.equals("FinPartie")){
            this._HightScore.setMode(this._mode);
        }
        this._HightScore.setVisible(true);
    }
    
    /*
     * Sauve les scores
     */
    public void saveHightScores(List<Score> scores, int mode){
        this._data.saveScores(scores, mode);
    }
    
    /*
     * Charge les scores
     */
    public List<Score> loadHightScores(int mode){
        return this._data.loadScores(mode);
    }
    
    /*
     * Ajoute un score
     */
    public void addAScore(Score s){
        this._HightScore.addScore(s, this._mode);
    }
    
    /**
     * Met en relief les score de la dernière partie
     */
    public void selectScores(List<Score> scores){
        this._HightScore.setMode(this._mode);
        this._HightScore.selectScores(scores, this._mode);
    }
    
    /*
     * ferme la fenêtre des hight scores
     */
    public void closeHightScores(){
        this._HightScore.setVisible(false);
    }
    
    /*
     * Ouvre la fenêtre de confirmation de l'effacement des scores
     */
    public void confirmResetHightScores(){
        this._resetHightScores.setVisible(true);
    }
    
    /*
     * Efface les meilleurs scores
     */
    public void resetHightScores(boolean all){
        this._resetHightScores.dispose();
        
        if(all){
            this._HightScore.setScores(new ArrayList<Score>(), Yams.MODELIBRE);
            this._data.saveScores(new ArrayList<Score>(), Yams.MODELIBRE);
            
            this._HightScore.setScores(new ArrayList<Score>(), Yams.MODEMONTANT);
            this._data.saveScores(new ArrayList<Score>(), Yams.MODEMONTANT);
            
            this._HightScore.setScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
            this._data.saveScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
        }
        else{
            switch(this._HightScore.getModeJeu()){
                case Yams.MODELIBRE:
                    this._HightScore.setScores(new ArrayList<Score>(), Yams.MODELIBRE);
                    this._data.saveScores(new ArrayList<Score>(), Yams.MODELIBRE);
                    break;
                case Yams.MODEMONTANT:
                    this._HightScore.setScores(new ArrayList<Score>(), Yams.MODEMONTANT);
                    
                    this._HightScore.changeScores(Yams.MODEMONTANT);
                    break;
                case Yams.MODEDESCENDANT:
                    this._HightScore.setScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
                    this._data.saveScores(new ArrayList<Score>(), Yams.MODEDESCENDANT);
                    break;
                default:
                    System.err.println("Mode de jeu inexistant");
                    break;
            }
        }
        
        this._HightScore.changeScores(this._HightScore.getModeJeu());
    }
    
    /*
     * ferme la fenêtre de confirmation de l'effacement des scores
     */
    public void cancelResetHightScore(){
        this._resetHightScores.dispose();
    }
    
    /*
     * ferme la fenêtre de confirmation de fin du tour en montante/descendante
     */
    public void closeConfirmWindow(){
        this._confirmFinTour.setVisible(false);
    }
    
    /**
     * redimentionne les dés
     */
    public void resizeDices(boolean big){
        this._jeu.redimDices(big);
    }
}