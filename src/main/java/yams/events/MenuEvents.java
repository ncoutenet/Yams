/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class MenuEvents implements ActionListener{
    private YamControl myControler;
    
    public MenuEvents(YamControl yc){
        this.myControler = yc;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("confirmNouveau")){
            this.myControler.confirmQuit(false);
        }
        if(ae.getActionCommand().equals("nouvellePartie")){
            this.myControler.nouvellePartie();
        }
        if(ae.getActionCommand().equals("nouveau")){
            this.myControler.nouveau();
        }
        if(ae.getActionCommand().equals("recommencer")){
            this.myControler.recommencer();
        }
        if (ae.getActionCommand().equals("annuler")){
            this.myControler.annuler();
        }
        if(ae.getActionCommand().equals("openHightScores")){
            this.myControler.openHightScores();
        }
        if(ae.getActionCommand().equals("prefs")){
            this.myControler.showPrefs();
        }
        if (ae.getActionCommand().equals("confirmQuit")){
            this.myControler.confirmQuit(true);
        }
        if(ae.getActionCommand().equals("quitter")){
            this.myControler.quitter();
        }
        if(ae.getActionCommand().equals("help")){
            this.myControler.affichHelp();
        }
        if(ae.getActionCommand().equals("regles")){
            this.myControler.affichageRegles();
        }
        if (ae.getActionCommand().equals("aperçuRegles")){
            this.myControler.apercuRegle();
        }
    }
    
}
