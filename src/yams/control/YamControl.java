/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.control;

import yams.pojos.Joueur;
import yams.model.YamModele;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import yams.hightScores.views.HightScoreVue;
import yams.views.ConfirmQuitVue;
import yams.views.ConnectionVue;
import yams.views.FinPartieVue;
import yams.views.FinTourVue;
import yams.views.InfoScoreVue;
import yams.views.JeuVue;

import yams.regles.ReglesVue;

/**
 *
 * @author nicolas
 */

/*
 * Classe controleur, elle gère le bon fonctionnement du jeu
 */
public class YamControl {
    private ConnectionVue _connection;
    private YamModele _modele;
    
    private Preferences _prefs;
    private JeuVue _jeu;
    private FinTourVue _finTour;
    private FinPartieVue _finPartie;
    private ConfirmQuitVue _confirmQuit;
    private InfoScoreVue _confScores;
    private HightScoreVue _HightScore;
    
    private String[] _nomsJoueurs;
    private int _nbJoueurs;
    private boolean[][] _scoresValides;
    private int _tour;
    private int _mode;
    
    private boolean _sound;
    
    public YamControl(){
        _prefs = Preferences.userNodeForPackage(YamControl.class);
        _sound = _prefs.getBoolean("sound", true);
        _connection = new ConnectionVue(this, this._sound);
        _connection.affichage(true);
    }
    
    /*
     * Initialise les joueurs
     */
    public void setNomsJoueurs(){
        _connection.setJoueurs();
    }
    
    /*
     * Retourne les préférences de son
     */
    public boolean isSound() {
        return _sound;
    }
    
    
    /*
     * Retourne les coups restants
     */
    private String getCoupsRestants(int joueur){
        List<String> coups = new ArrayList<String>();
        String result = new String();
        int cpt = 0;
        
        for(int i = 0; i < 12; i++){
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
                    coup = "+";
                    break;
                case 7:
                    coup = "-";
                    break;
                case 8:
                    coup = "suite";
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
        boolean[] scores = new boolean[12];
        
        System.arraycopy(_scoresValides[_tour], 0, scores, 0, 12);
        
        return scores;
    }
    
    /*
     * fonction d'initialisation de la partie
     */
    public void commencer(){
        _mode = _connection.getModeJeu();
        _nomsJoueurs = _connection.getNomsJoueurs();
        _nbJoueurs = _connection.getNbJoueurs();
        _modele = new YamModele(_nbJoueurs, this);
        _scoresValides = new boolean[_nbJoueurs][12];
        for(int i = 0; i < _nbJoueurs; i++){
            for(int j = 0; j < 12; j++){
                _scoresValides[i][j] = true;
            }
        }
        _tour = _modele.getTour();
        
        //initialisation et mise à jour des vues
        _jeu = new JeuVue(_nbJoueurs, _nomsJoueurs, _tour, this, this._mode, this._sound);
        _connection.affichage(false);
        _jeu.affichage(true);
        _jeu.majCoupsRestants(this.getCoupsRestants(this._tour));
    }
    
    public void lancer(){
        int[]des;
        int lancesRestants = _jeu.getLancesRestants();
        
        if(_sound){
            _modele.playSoundDe();
        }
        
        des = _modele.lancer();
        lancesRestants = _modele.majNbLances(lancesRestants);
        _jeu.setNbLancers(lancesRestants);
        for(int i = 0; i < 5; i++){
            _jeu.setValDe(i, des[i]);
        }
        _jeu.setEnabledFinTour(true);
        _jeu.setTotalPoints(false);
        if(lancesRestants == 0){
            _jeu.setEnabledLancer(false);
            this.finTour(true);
        }
    }
    
    /*
     * nouvelle partie (après abandon)
     */
    public void nouveau(){
        _confirmQuit.activation(false);
        _jeu.affichage(false);
        _connection.affichage(true);
    }
    
    /*
     * nouvelle partie
     */
    public void nouvellePartie(){
        _finPartie.affichage(false);
        _jeu.affichage(false);
        _connection.affichage(true);
    }
    
    /*
     * Nouvelle partie avec les mêmes joueurs que précédement
     */
    public void recommencer(){
        _finPartie.affichage(false);
        _jeu.affichage(false);
        this.commencer();
    }
    
    /*
     * mise à jour de la préférenc de son
     */
    public void majSound(){
        this._sound = !this._sound;
        this._prefs.putBoolean("sound", (boolean)this._sound);
        this._connection.majSound(this._sound);
        this._jeu.majSound(this._sound);
    }
    
    /*
     * fermeture du programme
     */
    public void quitter(){
        System.exit(0);
    }
    
    /*
     * gestion de la fin du tour
     */
    public void finTour(){
        _jeu.setTour(_tour);
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
            int i = 11;
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
        List<Integer> listDes;
        switch(index){
            case 0:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 1){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][0] = false;
                this._jeu.setScore(this._tour, 0, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 1, -1);
                }
                else this._jeu.majColorTab(this._tour, 1, 1);
                strScore = "1";
                break;
            case 1:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 2){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][1] = false;
                this._jeu.setScore(this._tour, 1, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 2, -1);
                }
                else this._jeu.majColorTab(this._tour, 2, 1);
                strScore = "2";
                break;
            case 2:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 3){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][2] = false;
                this._jeu.setScore(this._tour, 2, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 3, -1);
                }
                else this._jeu.majColorTab(this._tour, 3, 1);
                strScore = "3";
                break;
            case 3:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 4){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][3] = false;
                this._jeu.setScore(this._tour, 3, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 4, -1);
                }
                else this._jeu.majColorTab(this._tour, 4, 1);
                strScore = "4";
                break;
            case 4:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 5){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][4] = false;
                this._jeu.setScore(this._tour, 4, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 5, -1);
                }
                else this._jeu.majColorTab(this._tour, 5, 1);
                strScore = "5";
                break;
            case 5:
                for(int i = 0; i < 5; i++){
                    if(des[i] == 6){
                        score += des[i];
                    }
                }
                this._scoresValides[this._tour][5] = false;
                this._jeu.setScore(this._tour, 5, score);
                if(score == 0){
                    this._jeu.majColorTab(this._tour, 6, -1);
                }
                else this._jeu.majColorTab(this._tour, 6, 1);
                strScore = "6";
                break;
            case 6:
                for(int i = 0; i < 5; i++){
                    score += des[i];
                }
                this._scoresValides[this._tour][6] = false;
                this._jeu.setScore(this._tour, 9, score);
                this._jeu.majColorTab(this._tour, 10, 1);
                strScore = "+";
                break;
            case 7:
                for(int i = 0; i < 5; i++){
                   score += des[i];
                }
                this._scoresValides[this._tour][7] = false;
                this._jeu.setScore(this._tour, 10, score);
                this._jeu.majColorTab(this._tour, 11, 1);
                strScore = "-";
                break;
            case 8:
                boolean suite = true;
                listDes = new ArrayList(5);
                for(int i = 0; i < 5; i++){
                    Integer val = new Integer(des[i]);
                    listDes.add(val);
                }
                Collections.sort(listDes);
                for(int i = 0; i < 4; i++){
                    int de1 = listDes.get(i);
                    int de2 = listDes.get(i+1) - 1;
                    if(de1 != de2){
                        suite = false;
                    }
                }
                if(suite){
                    score = 20;
                    this._jeu.majColorTab(this._tour, 13, 1);
                }
                else {
                    this._jeu.majColorTab(this._tour, 13, -1);
                }
                this._scoresValides[this._tour][8] = false;
                this._jeu.setScore(this._tour, 12, score);
                strScore = "suite";
                break;
            case 9:
                boolean full = false;
                listDes = new ArrayList(5);
                for(int i = 0; i < 5; i++){
                    Integer val = new Integer(des[i]);
                    listDes.add(val);
                }
                Collections.sort(listDes);
                if(listDes.get(0).equals(listDes.get(1)) && listDes.get(0).equals(listDes.get(2)) && (listDes.get(0) != listDes.get(3)) && listDes.get(3).equals(listDes.get(4))){
                    full = true;
                }
                else if(listDes.get(0).equals(listDes.get(1)) && (listDes.get(0) != listDes.get(2)) && listDes.get(2).equals(listDes.get(3)) && listDes.get(3).equals(listDes.get(4))){
                    full = true;
                }
                if(full){
                    score = 30;
                    this._jeu.majColorTab(this._tour, 14, 1);
                }
                else {
                    this._jeu.majColorTab(this._tour, 14, -1);
                }
                this._scoresValides[this._tour][9] = false;
                this._jeu.setScore(this._tour, 13, score);
                strScore = "full";
                break;
            case 10:
                boolean carre = false;
                listDes = new ArrayList<Integer>(5);
                for(int i = 0; i < 5; i++){
                    Integer val = new Integer(des[i]);
                    listDes.add(val);
                }
                Collections.sort(listDes);

                if((listDes.get(0).equals(listDes.get(1)) && listDes.get(1).equals(listDes.get(2)) && listDes.get(2).equals(listDes.get(3))) || (listDes.get(1).equals(listDes.get(2)) && listDes.get(2).equals(listDes.get(3)) && listDes.get(3).equals(listDes.get(4)))){
                    carre = true;
                }

                if(carre){
                    score = 40;
                    this._jeu.majColorTab(this._tour, 15, 1);
                }
                else{
                    this._jeu.majColorTab(this._tour, 15, -1);
                }
                this._scoresValides[this._tour][10] = false;
                this._jeu.setScore(this._tour, 14, score);
                strScore = "carré";
                break;
            case 11:
                boolean yam = true;
                int i = 0;
                while((i < 4) && (yam)){
                    if(des[i] != des[i+1]){
                        yam = false;
                    }
                    i++;
                }
                if(yam){
                    score = 50;
                    this._jeu.majColorTab(this._tour, 16, 1);
                }
                else{
                    this._jeu.majColorTab(this._tour, 16, -1);
                }
                this._scoresValides[this._tour][11] = false;
                this._jeu.setScore(this._tour, 15, score);
                strScore = "yam's";
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
    // FIXME avec plusieurs joueurs la fenêtre de fin de partie ne s'affiche pas
    public void confScores(){
        this._confScores.activation(false);
        
        if(this._modele.finPartie(this._scoresValides, this._sound)){
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
            this._jeu.setTotalPoints(true);
        }
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
    private void finTour(boolean fin){
        _jeu.setTour(_tour);
        if(_mode == 0){
            _finTour = new FinTourVue(_scoresValides, _tour, this, fin, this._jeu);
            _finTour.setAffichage(true);
        }
        else{
            this.finTour();
        }
    }
    
    /*
     * enregistrement des scores choisis
     */
    public void validationScore(){
        String choix;
        choix = this._finTour.getChoix();
        
        int[] des = this._jeu.getDes();
        int score = 0;
        
        if(choix.equals("1")){
           for(int i = 0; i < 5; i++){
               if(des[i] == 1){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][0] = false;
           this._jeu.setScore(this._tour, 0, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 1, -1);
                }
                else this._jeu.majColorTab(this._tour, 1, 1);
        }
        else if(choix.equals("2")){
           for(int i = 0; i < 5; i++){
               if(des[i] == 2){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][1] = false;
           this._jeu.setScore(this._tour, 1, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 2, -1);
                }
                else this._jeu.majColorTab(this._tour, 2, 1);
        }
        else if(choix.equals("3")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 3){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][2] = false;
           this._jeu.setScore(this._tour, 2, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 3, -1);
                }
                else this._jeu.majColorTab(this._tour, 3, 1);
        }
        else if(choix.equals("4")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 4){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][3] = false;
           this._jeu.setScore(this._tour, 3, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 4, -1);
                }
                else this._jeu.majColorTab(this._tour, 4, 1);
        }
        else if(choix.equals("5")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 5){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][4] = false;
           this._jeu.setScore(this._tour, 4, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 5, -1);
                }
                else this._jeu.majColorTab(this._tour, 5, 1);
        }
        else if(choix.equals("6")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 6){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][5] = false;
           this._jeu.setScore(this._tour, 5, score);
           if(score == 0){
                    this._jeu.majColorTab(this._tour, 6, -1);
                }
                else this._jeu.majColorTab(this._tour, 6, 1);
        }
        else if(choix.equals("+")){
            for(int i = 0; i < 5; i++){
                   score += des[i];
           }
           this._scoresValides[this._tour][6] = false;
           this._jeu.setScore(this._tour, 9, score);
           this._jeu.majColorTab(this._tour, 10, 1);
        }
        else if(choix.equals("-")){
            
            for(int i = 0; i < 5; i++){
                   score += des[i];
           }
           this._scoresValides[this._tour][7] = false;
           this._jeu.setScore(this._tour, 10, score);
           this._jeu.majColorTab(this._tour, 11, 1);
        }
        else if(choix.equals("suite")){
            boolean suite = true;
            List<Integer> listDes = new ArrayList(5);
            for(int i = 0; i < 5; i++){
                Integer val = new Integer(des[i]);
                listDes.add(val);
            }
            Collections.sort(listDes);
            for(int i = 0; i < 4; i++){
                int de1 = listDes.get(i);
                int de2 = listDes.get(i+1) - 1;
                if(de1 != de2){
                    suite = false;
                }
            }
            if(suite){
                score = 20;
                this._jeu.majColorTab(this._tour, 13, 1);
            }
            else{
                this._jeu.majColorTab(this._tour, 13, -1);
            }
           this._scoresValides[this._tour][8] = false;
            this._jeu.setScore(this._tour, 12, score);
        }
        else if(choix.equals("full")){
            boolean full = false;
            List<Integer> listDes = new ArrayList(5);
            for(int i = 0; i < 5; i++){
                Integer val = new Integer(des[i]);
                listDes.add(val);
            }
            Collections.sort(listDes);
            if(listDes.get(0).equals(listDes.get(1)) && listDes.get(0).equals(listDes.get(2)) && (listDes.get(0) != listDes.get(3)) && listDes.get(3).equals(listDes.get(4))){
                full = true;
            }
            else if(listDes.get(0).equals(listDes.get(1)) && (listDes.get(0) != listDes.get(2)) && listDes.get(2).equals(listDes.get(3)) && listDes.get(3).equals(listDes.get(4))){
                full = true;
            }
            if(full){
                score = 30;
                this._jeu.majColorTab(this._tour, 14, 1);
            }
            else{
                this._jeu.majColorTab(this._tour, 14, -1);
            }
           this._scoresValides[this._tour][9] = false;
            this._jeu.setScore(this._tour, 13, score);
        }
        else if(choix.equals("carré")){
            boolean carre = false;
            List<Integer> listDes = new ArrayList<Integer>(5);
            for(int i = 0; i < 5; i++){
                Integer val = new Integer(des[i]);
                listDes.add(val);
            }
            Collections.sort(listDes);
            
            if((listDes.get(0).equals(listDes.get(1)) && listDes.get(1).equals(listDes.get(2)) && listDes.get(2).equals(listDes.get(3))) || (listDes.get(1).equals(listDes.get(2)) && listDes.get(2).equals(listDes.get(3)) && listDes.get(3).equals(listDes.get(4)))){
                carre = true;
            }
            
            if(carre){
                score = 40;
                this._jeu.majColorTab(this._tour, 15, 1);
            }
            else{
                this._jeu.majColorTab(this._tour, 15, -1);
            }
            this._scoresValides[this._tour][10] = false;
            this._jeu.setScore(this._tour, 14, score);
        }
        else if(choix.equals("yam's")){
            boolean yam = true;
            int i = 0;
            while((i < 4) && (yam)){
               if(des[i] != des[i+1]){
                   yam = false;
               }
               i++;
           }
            if(yam){
                score = 50;
                this._jeu.majColorTab(this._tour, 16, 1);
            }
            else{
                this._jeu.majColorTab(this._tour, 16, -1);
            }
           this._scoresValides[this._tour][11] = false;
           this._jeu.setScore(this._tour, 15, score);
        }
        this._finTour.setAffichage(false);
        this._jeu.setTotalPoints(true);
        if(this._modele.finPartie(this._scoresValides, this._sound)){
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
//        this._finTour.dispose();
    }
    
    /*
     * préparation du tour suivant
     */
    private void tourSuivant(){
        this._jeu.setEnabledFinTour(false);
        this._jeu.setEnabledLancer(true);
        this._jeu.initDes();
        this._jeu.setNbLancers(3);
        this._modele.changerJoueur();
        this._tour = this._modele.getTour();
        this._jeu.setTour(this._tour);
        this._jeu.majCoupsRestants(this.getCoupsRestants(this._tour));
    }
    
    /*
     * gestion de l'affichage des règles
     */
    public void affichageRegles() {
            ReglesVue rav = new ReglesVue(_mode);
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
     * gestion des règles lors du choix du mode
     */
    public void apercuRegle(){
        int mode = this._connection.getModeJeu();
        ReglesVue rv = new ReglesVue(mode);
    }
    
    /*
     * Ouvre la fenêtre des hight scores
     */
    public void openHightScores(){
        this._HightScore = new HightScoreVue(this);
    }
    
    /*
     * ferme la fenêtre des hight scores
     */
    public void closeHightScores(){
        this._HightScore.close();
    }
}