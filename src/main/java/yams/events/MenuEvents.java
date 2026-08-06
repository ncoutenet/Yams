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
    private YamControl _myControler;
    
    public MenuEvents(YamControl yc){
        this._myControler = yc;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("confirmNouveau")){
            this._myControler.confirmQuit(false);
        }
        if(ae.getActionCommand().equals("nouvellePartie")){
            this._myControler.nouvellePartie();
        }
        if(ae.getActionCommand().equals("nouveau")){
            this._myControler.nouveau();
        }
        if(ae.getActionCommand().equals("recommencer")){
            this._myControler.recommencer();
        }
        if (ae.getActionCommand().equals("annuler")){
            this._myControler.annuler();
        }
        if(ae.getActionCommand().equals("openHightScores")){
            this._myControler.openHightScores();
        }
        if(ae.getActionCommand().equals("prefs")){
            this._myControler.showPrefs();
        }
        if (ae.getActionCommand().equals("confirmQuit")){
            this._myControler.confirmQuit(true);
        }
        if(ae.getActionCommand().equals("quitter")){
            this._myControler.quitter();
        }
        if(ae.getActionCommand().equals("help")){
            this._myControler.affichHelp();
        }
        if(ae.getActionCommand().equals("regles")){
            this._myControler.affichageRegles();
        }
        if (ae.getActionCommand().equals("aperçuRegles")){
            this._myControler.apercuRegle();
        }
    }
    
}
