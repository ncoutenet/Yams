/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.pojos;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import yams.control.YamControl;
import yams.events.MenuEvents;

/**
 *
 * @author nicolas
 */
public class MyMenuBar extends JMenuBar{
    private transient YamControl myControler;
    private String myType; //"jeu" pour une partie en cours, "connexion" pour la page d'initialisation, "finPartie" pour la fenêtre de fin de partie
    
    public MyMenuBar(YamControl yc, String type){
        this.myControler = yc;
        this.myType = type;
        
        //menu jeu
        JMenu mJeu = new JMenu("Jeu");
        JMenu mPartie = new JMenu("Partie");
        JMenuItem miNouveau = new JMenuItem("Nouveau");
        miNouveau.addActionListener(new MenuEvents(this.myControler));
        if(this.myType.equals("jeu")){
            miNouveau.setActionCommand("confirmNouveau");
        }
        else if(this.myType.equals("finPartie")){
            miNouveau.setActionCommand("nouvellePartie");
        }
        mPartie.add(miNouveau);
        JMenuItem miRecommencer = new JMenuItem("Recommencer");
        miRecommencer.addActionListener(new MenuEvents(this.myControler));
        miRecommencer.setActionCommand("recommencer");
        mPartie.add(miRecommencer);
        mJeu.add(mPartie);
        if(this.myType.equals("connexion")){
            mPartie.setEnabled(false);
        }
        JMenuItem miScores = new JMenuItem("Scores");
        miScores.addActionListener(new MenuEvents(this.myControler));
        miScores.setActionCommand("openHightScores");
        mJeu.add(miScores);
        JMenuItem miPrefs = new JMenuItem("Préférences");
        miPrefs.addActionListener(new MenuEvents(this.myControler));
        miPrefs.setActionCommand("prefs");
        mJeu.add(miPrefs);
        mJeu.addSeparator();
        JMenuItem miQuitter = new JMenuItem("Quitter");
        miQuitter.addActionListener(new MenuEvents(this.myControler));
        if(this.myType.equals("jeu")){
            miQuitter.setActionCommand("confirmQuit");
        }
        else{
            miQuitter.setActionCommand("quitter");
        }
        mJeu.add(miQuitter);
        this.add(mJeu);
        
        //menu aide
        JMenu mAide = new JMenu("Aide");
        JMenuItem miTuto = new JMenuItem("Mode d'emploi");
        miTuto.addActionListener(new MenuEvents(this.myControler));
        miTuto.setActionCommand("help");
        mAide.add(miTuto);
        JMenuItem miRegles = new JMenuItem("Règles");
        miRegles.addActionListener(new MenuEvents(this.myControler));
        if(this.myType.equals("connexion")){
            miRegles.setActionCommand("aperçuRegles");
        }
        else{
            miRegles.setActionCommand("regles");
        }
        mAide.add(miRegles);
        this.add(mAide);
    }
}
