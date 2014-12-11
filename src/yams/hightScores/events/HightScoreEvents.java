/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.hightScores.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */

/*
 * Cette classe écoute les actions des boutons de la fenêtre des meilleurs scores
 */
public class HightScoreEvents implements ActionListener{
    private YamControl _myControler;
    
    public HightScoreEvents(YamControl yc){
        this._myControler = yc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(" commande reçue: " + ae.getActionCommand());
        if(ae.getActionCommand().equals("closeHightScores")){
            _myControler.closeHightScores();
        }
        if(ae.getActionCommand().equals("resetHightScores")){
            _myControler.confirmResetHightScores();
        }
        if(ae.getActionCommand().equals("resetAllScores")){
            _myControler.resetHightScores(true);
        }
        if(ae.getActionCommand().equals("resetOneScore")){
            _myControler.resetHightScores(false);
        }
        if(ae.getActionCommand().equals("resetNoScore")){
            _myControler.cancelResetHightScore();
        }
    }
    
}
