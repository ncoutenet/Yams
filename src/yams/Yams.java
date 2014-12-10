/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import yams.control.YamControl;

/**
 *
 * @author nicolas
 */

// TODO permettre l'export des scores
// TODO ajouter le mode sec

/*
 * Classe principale permettant le lancement du programme
 */
public class Yams {
    //constantes de sélection des préférences
    public static final int PREFSOUND = 0;
    public static final int PREFSELECT = 1;
    public static final int PREFRULES = 2;
    
    //constantes de sélection du mode de jeu
    public static final int MODELIBRE = 0;
    public static final int MODEMONTANT = 1;
    public static final int MODEDESCENDANT = 2;

    public static void main(String[] args) {
        YamControl contoleur = new YamControl();
    }
}
