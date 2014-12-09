/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.model;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.*;
import yams.control.YamControl;
import yams.pojos.Joueur;
import yams.views.JeuVue;

/**
 *
 * @author nicolas
 */
// TODO ajuster les index des scores des brelan, petite suite, grande suite et chance

/*
 * Classe gérant le jeu
 */
public class YamModele {
    private int _joueur;
    private int _nbJoueur;
    private YamControl _myControler;
    
    public YamModele(int nbJoueurs, YamControl yc){
        //initialisation des variables locales
        this._nbJoueur = nbJoueurs;
        this._joueur = 0;
        this._myControler = yc;
    }
    
    /*
     * Lancement des dés
     */
    public int[] lancer(){
         int[] resultat = new int[5];
         Random r = new Random();
        
        for(int i = 0; i < 5; i++)
        {
            resultat[i] = 1 + r.nextInt(6);
        }
         return resultat;
    }
    
    /*
     * Joue un son lors du lancer
     */
    public void playSoundDe(){
        if(this._myControler.isSound()){
            AudioClip clip;

            clip = Applet.newAudioClip(getClass().getResource("/resources/sons/dé_roulant.wav"));
            clip.play();
        }
    }
    
    /*
     * Joue un son lors de la fin de la partie
     */
    public void playSoundFin(){
        if(this._myControler.isSound()){
            AudioClip clip;

            clip = Applet.newAudioClip(getClass().getResource("/resources/sons/applaudissements.wav"));
            clip.play();
        }
    }
    
    /*
     * change le joueur en train de jouer
     */
    public void changerJoueur(){
        this._joueur = (this._joueur + 1) % this._nbJoueur;
    }
    
    /*
     * Trie les joueurs par score
     */
    public Joueur[] sortJoueurs(Joueur[] joueurs){
        List<Joueur> liste = new ArrayList<Joueur>();
        Joueur[] listeTriee = new Joueur[this._myControler.getNbJoueurs()];
        int min;
        int index = 0;
        
        liste.addAll(Arrays.asList(joueurs));
        
        for(int i = 0; i < joueurs.length; i++){
            min = 0;
            for(int j = 1; j < liste.size(); j++){
                if(liste.get(min).getScore(16) > liste.get(j).getScore(16)){
                    min = j;
                }
            }
            listeTriee[i] = liste.remove(min);
        }
        
        return listeTriee;
    }
    
    /*
     * Calcule le score des 1
     */
    public int calc1(int[] des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 1){
                score += des[i];
            }
        }
        scores[tour][0] = false;
        jeu.setScore(tour, 0, score);
        if(score == 0){
            jeu.majColorTab(tour, 1, -1);
        }
        else jeu.majColorTab(tour, 1, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des 2
     */
    public int calc2(int[] des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 2){
                score += des[i];
            }
        }
        scores[tour][1] = false;
        jeu.setScore(tour, 1, score);
        if(score == 0){
            jeu.majColorTab(tour, 2, -1);
        }
        else jeu.majColorTab(tour, 2, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des 3
     */
    public int calc3(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 3){
                score += des[i];
            }
        }
        scores[tour][2] = false;
        jeu.setScore(tour, 2, score);
        if(score == 0){
            jeu.majColorTab(tour, 3, -1);
        }
        else jeu.majColorTab(tour, 3, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des 4
     */
    public int calc4(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 4){
                score += des[i];
            }
        }
        scores[tour][3] = false;
        jeu.setScore(tour, 3, score);
        if(score == 0){
            jeu.majColorTab(tour, 4, -1);
        }
        else jeu.majColorTab(tour, 4, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des 5
     */
    public int calc5(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 5){
                score += des[i];
            }
        }
        scores[tour][4] = false;
        jeu.setScore(tour, 4, score);
        if(score == 0){
            jeu.majColorTab(tour, 5, -1);
        }
        else jeu.majColorTab(tour, 5, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des 6
     */
    public int calc6(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            if(des[i] == 6){
                score += des[i];
            }
        }
        scores[tour][5] = false;
        jeu.setScore(tour, 5, score);
        if(score == 0){
            jeu.majColorTab(tour, 6, -1);
        }
        else jeu.majColorTab(tour, 6, 1);
        
        return score;
    }
    
    /*
     * Calcule le score des +
     */
    public int calcPlus(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            score += des[i];
        }
        scores[tour][6] = false;
        jeu.setScore(tour, 9, score);
        jeu.majColorTab(tour, 10, 1);
        
        return score;
    }
    
    /*
     * Calcule le score du brelan
     */
    public int calcBrelan(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        boolean brelan = false;
        List<Integer> listDes = new ArrayList<Integer>(5);
        
        for(int i=0; i<5; i++){
            Integer val = new Integer(des[i]);
            listDes.add(val);
        }
        Collections.sort(listDes);
        if((listDes.get(0).equals(listDes.get(1)) && listDes.get(1).equals(listDes.get(2))) || (listDes.get(1).equals(listDes.get(2)) && listDes.get(2).equals(listDes.get(3))) || (listDes.get(2).equals(listDes.get(3)) && listDes.get(3).equals(listDes.get(4)))){
            brelan = true;
        }
        if(brelan){
            score = 10;
            jeu.majColorTab(tour, 10, 1);
        }
        else{
            jeu.majColorTab(tour, 10, -1);
        }
        scores[tour][6] = false;
        jeu.setScore(tour, 9, score);
        
        return score;
    }
    
    /*
     * Calcule le score des -
     */
    public int calcMinus(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        
        for(int i = 0; i < 5; i++){
            score += des[i];
        }
        scores[tour][7] = false;
        jeu.setScore(tour, 10, score);
        jeu.majColorTab(tour, 11, 1);
        
        return score;
    }
    
    /*
     * Calcul le score de la petite suite
     */
    public int calcLittleSuite(int[]des, boolean[][]scores, int tour, JeuVue jeu){
        int score = 0;
        boolean littleSuite = true;
        List<Integer> listDes = new ArrayList<Integer>(5);
        
        for(int i=0; i<5; i++){
            Integer val = new Integer(des[i]);
            listDes.add(val);
        }
        Collections.sort(listDes);
        if(listDes.get(0).equals(new Integer(1))){
            for(int i=0; i<4; i++){
                int de1 = listDes.get(i);
                int de2 = listDes.get(i+1) - 1;
                if(de1 != de2){
                    littleSuite = false;
                }
            }
        }
        else{
            littleSuite = false;
        }
        
        if(littleSuite){
            score = 15;
            jeu.majColorTab(tour, 11, 1);
        }
        else{
            jeu.majColorTab(tour, 11, -1);
        }
        scores[tour][7] = false;
        jeu.setScore(tour, 11, score);
        
        return score;
    }
    
    /*
     * Calcule le score de la grande suite
     */
    public int calcBigSuite(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        boolean bigSuite = true;
        List<Integer> listDes = new ArrayList(5);
        
        for(int i = 0; i < 5; i++){
            Integer val = new Integer(des[i]);
            listDes.add(val);
        }
        Collections.sort(listDes);
        if(listDes.get(0).equals(new Integer(2))){
            for(int i = 0; i < 4; i++){
                int de1 = listDes.get(i);
                int de2 = listDes.get(i+1) - 1;
                if(de1 != de2){
                    bigSuite = false;
                }
            }
        }
        else{
            bigSuite = false;
        }
        if(bigSuite){
            score = 25;
            jeu.majColorTab(tour, 13, 1);
        }
        else {
            jeu.majColorTab(tour, 13, -1);
        }
        scores[tour][8] = false;
        jeu.setScore(tour, 12, score);
        
        return score;
    }
    
    /*
     * Calcule le score de la suite
     */
    public int calcSuite(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
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
            jeu.majColorTab(tour, 13, 1);
        }
        else {
            jeu.majColorTab(tour, 13, -1);
        }
        scores[tour][8] = false;
        jeu.setScore(tour, 12, score);
        
        return score;
    }
    
    /*
     * Calcule le score du full
     */
    public int calcFull(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
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
            jeu.majColorTab(tour, 14, 1);
        }
        else {
            jeu.majColorTab(tour, 14, -1);
        }
        scores[tour][9] = false;
        jeu.setScore(tour, 13, score);
        
        return score;
    }
    
    /*
     * Calcule le score du carré
     */
    public int calcCarre(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
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
            jeu.majColorTab(tour, 15, 1);
        }
        else{
            jeu.majColorTab(tour, 15, -1);
        }
        scores[tour][10] = false;
        jeu.setScore(tour, 14, score);
        
        return score;
    }
    
    /*
     * Calcule le score du yam's
     */
    public int calcYam(int[]des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
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
            jeu.majColorTab(tour, 16, 1);
        }
        else{
            jeu.majColorTab(tour, 16, -1);
        }
        scores[tour][11] = false;
        jeu.setScore(tour, 15, score);
        
        return score;
    }
    
    /*
     * Calcule la chance
     */
    public int calcChance(int[] des, boolean[][] scores, int tour, JeuVue jeu){
        int score = 0;
        for(int i=0; i<5; i++){
            score += des[i];
        }
        
        return score;
    }
    
    /*
     * gère la fin de partie
     */
    public boolean finPartie(boolean[][] scores, boolean soundPref){
        boolean result = true;
        for(int i = 0; i < this._nbJoueur; i++){
            for(int j = 0; j < 12; j++){
                if(scores[i][j]){
                    return false;
                }
            }
        }
        
        if(soundPref){
            this.playSoundFin();
        }
        
        return result;
    }
    
    /*
     * Retourne le code du joueur à qui c'est de jouer
     */
    public int getTour(){
        return this._joueur;
    }
    
    /*
     * met à jour le nombre de lancés restants
     */
    public int majNbLances(int nb){
        int result;
        
        if(nb == 0){
            result = 3;
        }
        else result = nb - 1;
        
        return result;
    }
}
