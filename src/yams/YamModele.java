/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.Random;

/**
 *
 * @author nicolas
 */

/*
 * Classe gérant le jeu
 */
public class YamModele {
    private int _joueur;
    private int _nbJoueur;
    
    public YamModele(int nbJoueurs){
        //initialisation des variables locales
        this._nbJoueur = nbJoueurs;
        this._joueur = 0;
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
    public void playSound(){
        AudioClip clip;
        
        clip = Applet.newAudioClip(getClass().getResource("sons/dé_roulant.wav"));
        clip.play();
    }
    
    /*
     * change le joueur en train de jouer
     */
    public void changerJoueur(){
        this._joueur = (this._joueur + 1) % this._nbJoueur;
    }
    
    /*
     * gère la fin de partie
     */
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
