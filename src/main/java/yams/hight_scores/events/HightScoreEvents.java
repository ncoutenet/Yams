/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.hight_scores.events;

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
    private YamControl myControler;

    public HightScoreEvents(YamControl yc){
        this.myControler = yc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("closeHightScores")){
            this.myControler.closeHightScores();
        }
        if(ae.getActionCommand().equals("resetHightScores")){
            this.myControler.confirmResetHightScores();
        }
        if(ae.getActionCommand().equals("resetAllScores")){
            this.myControler.resetHightScores(true);
        }
        if(ae.getActionCommand().equals("resetOneScore")){
            this.myControler.resetHightScores(false);
        }
        if(ae.getActionCommand().equals("resetNoScore")){
            this.myControler.cancelResetHightScore();
        }
        if(ae.getActionCommand().equals("exportHightScores")){
            this.myControler.exportHightScores();
        }
    }
    
}
