/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.util.Random;

/**
 *
 * @author nicolas
 */
public class YamModele {
    private int _joueur;
    private int _nbJoueur;
    
    public YamModele(int nbJoueurs){
        //initialisation des variables locales
        this._nbJoueur = nbJoueurs;
        this._joueur = 0;
    }
    
    public int[] premierlancer(){
        int[] lancer = new int[5];
        Random r = new Random();
        
        for(int i = 0; i < 5; i++)
        {
            lancer[i] = 1 + r.nextInt(6);
        }
        
        return lancer;
    }
    
    public int[] lancer(){
         int[] resultat = new int[5];
         Random r = new Random();
        
        for(int i = 0; i < 5; i++)
        {
            resultat[i] = 1 + r.nextInt(6);
        }
         return resultat;
    }
    
    public void changerJoueur(){
        this._joueur = (this._joueur + 1) % this._nbJoueur;
    }
    
    public boolean finPartie(boolean[][] scores){
        boolean result = true;
        for(int i = 0; i < this._nbJoueur; i++){
            for(int j = 0; j < 12; j++){
                if(scores[i][j]){
                    return false;
                }
            }
        }
        return result;
    }
    
    public int getTour(){
        return this._joueur;
    }
    
    public int majNbLances(int nb){
        int result;
        
        if(nb == 0){
            result = 3;
        }
        else result = nb - 1;
        
        return result;
    }
}
