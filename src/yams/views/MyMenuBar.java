/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import yams.control.YamControl;
import yams.events.YamEvents;

/**
 *
 * @author nicolas
 */
public class MyMenuBar extends JMenuBar{
    private YamControl _myControler;
    private String _myType; //"jeu" pour une partie en cours, "connexion" pour la page d'initialisation
    
    public MyMenuBar(YamControl yc, String type){
        this._myControler = yc;
        this._myType = type;
        
        //menu jeu
        JMenu mJeu = new JMenu("Jeu");
        JMenu mPartie = new JMenu("Partie");
        JMenuItem miNouveau = new JMenuItem("Nouveau");
        miNouveau.addActionListener(new YamEvents(this._myControler));
        miNouveau.setActionCommand("confirmNouveau");
        mPartie.add(miNouveau);
        // TODO ajouter l'option "recommencer"
        mJeu.add(mPartie);
        if(this._myType.equals("connexion")){
            mPartie.setEnabled(false);
        }
        JMenuItem miScores = new JMenuItem("Scores");
        miScores.addActionListener(new YamEvents(this._myControler));
        miScores.setActionCommand("openHightScores");
        mJeu.add(miScores);
        mJeu.addSeparator();
        JMenuItem miQuitter = new JMenuItem("Quitter");
        miQuitter.addActionListener(new YamEvents(this._myControler));
        if(this._myType.equals("jeu")){
            miQuitter.setActionCommand("confirmQuit");
        }
        else{
            miQuitter.setActionCommand("quitter");
        }
        mJeu.add(miQuitter);
        this.add(mJeu);
        
        //menu aide
        JMenu mAide = new JMenu("Aide");
        JMenuItem miRegles = new JMenuItem("Règles");
        miRegles.addActionListener(new YamEvents(this._myControler));
        if(this._myType.equals("connexion")){
            miRegles.setActionCommand("aperçuRegles");
        }
        else{
            miRegles.setActionCommand("regles");
        }
        mAide.add(miRegles);
        this.add(mAide);
    }
}
