/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author nicolas
 */

public class YamControl {
    private ConnectionVue _connection;
    private YamModele _modele;
    private JeuVue _jeu;
    private FinTourVue _finTour;
    private FinPartieVue _finPartie;
    
    private String[] _nomsJoueurs;
    private int _nbJoueurs;
    private boolean[][] _scoresValides;
    private int _tour;
    
    public YamControl(){
        _connection = new ConnectionVue(this);
        _connection.affichage(true);
    }
    
    public void setNomsJoueurs(){
        _connection.setJoueurs();
    }
    
    public void commencer(){
        _nomsJoueurs = _connection.getNomsJoueurs();
        _nbJoueurs = _connection.getNbJoueurs();
        _modele = new YamModele(_nbJoueurs);
        _scoresValides = new boolean[_nbJoueurs][12];
        for(int i = 0; i < _nbJoueurs; i++){
            for(int j = 0; j < 12; j++){
                _scoresValides[i][j] = true;
            }
        }
        _tour = _modele.getTour();
        
        //initialisation et mise à jour des vues
        _jeu = new JeuVue(_nbJoueurs, _nomsJoueurs, _tour, this);
        _connection.affichage(false);
        _jeu.affichage(true);
    }
    //problème ici
    public void lancer(){
        int[]des = new int[5];
        int lancesRestants = _jeu.getLancesRestants();
        System.out.println("lancés restants : " + lancesRestants);
        
        des = _modele.lancer();
        lancesRestants = _modele.majNbLances(lancesRestants);
        System.out.println("lancés restants : " + lancesRestants);
        _jeu.setNbLancers(lancesRestants);
        for(int i = 0; i < 5; i++){
            _jeu.setValDe(i, des[i]);
        }
        _jeu.setEnabledFinTour(true);
        _jeu.setEnabledDes(true);
        if(lancesRestants == 0){
            _jeu.setEnabledLancer(false);
            _jeu.setEnabledDes(false);
        }
    }
    
    public void nouveau(){
        _jeu.affichage(false);
        _connection.affichage(true);
    }
    
    public void recommencer(){
        _finPartie.affichage(false);
        _jeu.affichage(false);
        _connection.affichage(true);
    }
    
    public void quitter(){
        System.exit(0);
    }
    
    public void finTour(){
        _jeu.setTour(_tour);
        _jeu.setEnabledDes(false);
        _finTour = new FinTourVue(_scoresValides, _tour, this);
        _finTour.setAffichage(true);
    }
    
    public void majSelectDes(){
        this._jeu.majSelDes();
    }
    
    public void validationScore(){
        String choix = new String();
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
        }
        else if(choix.equals("2")){
           for(int i = 0; i < 5; i++){
               if(des[i] == 2){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][1] = false;
           this._jeu.setScore(this._tour, 1, score); 
        }
        else if(choix.equals("3")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 3){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][2] = false;
           this._jeu.setScore(this._tour, 2, score);
        }
        else if(choix.equals("4")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 4){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][3] = false;
           this._jeu.setScore(this._tour, 3, score);
        }
        else if(choix.equals("5")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 5){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][4] = false;
           this._jeu.setScore(this._tour, 4, score);
        }
        else if(choix.equals("6")){
            for(int i = 0; i < 5; i++){
               if(des[i] == 6){
                   score += des[i];
               }
           }
           this._scoresValides[this._tour][5] = false;
           this._jeu.setScore(this._tour, 5, score);
        }
        else if(choix.equals("+")){
            for(int i = 0; i < 5; i++){
                   score += des[i];
           }
           this._scoresValides[this._tour][6] = false;
           this._jeu.setScore(this._tour, 9, score);
        }
        else if(choix.equals("-")){
            
            for(int i = 0; i < 5; i++){
                   score += des[i];
           }
           this._scoresValides[this._tour][7] = false;
           this._jeu.setScore(this._tour, 10, score);
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
            }
           this._scoresValides[this._tour][9] = false;
            this._jeu.setScore(this._tour, 13, score);
        }
        else if(choix.equals("carré")){
            
           for(int i = 0; i < 5; i++){
               
           }
           score = 40;
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
            }
           this._scoresValides[this._tour][11] = false;
           this._jeu.setScore(this._tour, 15, score);
        }
        this._finTour.setAffichage(false);
        if(this._modele.finPartie(this._scoresValides)){
            Joueur[] joueurs = new Joueur[this._nbJoueurs];
            int max = 0;
            
            for(int i = 0; i < this._nbJoueurs; i++){
                joueurs[i] = this._jeu.getJoueur(i);
            }
            
            for(int i = 1; i < this._nbJoueurs; i++){
                if(joueurs[max].getScore(16) < joueurs[i].getScore(16)){
                    max = i;
                }
            }
            this._finPartie = new FinPartieVue(this, joueurs[max]);
            this._finPartie.affichage(true);
        }
        else{
            this.tourSuivant();
        }
    }
    
    private void tourSuivant(){
        this._jeu.setEnabledFinTour(false);
        this._jeu.setEnabledLancer(true);
        this._jeu.initDes();
        this._jeu.setNbLancers(3);
        this._modele.changerJoueur();
        this._tour = this._modele.getTour();
        this._jeu.setTour(this._tour);
    }
}