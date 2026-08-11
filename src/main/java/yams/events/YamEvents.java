/*
 * To change this template, choose Tools | Templates
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

/*
 * Classe gérant les clics sur les boutons
 */
public class YamEvents implements ActionListener{
    private YamControl myControler;

    public YamEvents(YamControl yc){
        myControler = yc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("commencer")){
            this.myControler.commencer();
        }
        if(ae.getActionCommand().equals("lancer")){
            this.myControler.lancer();
        }
        if(ae.getActionCommand().equals("finTour")){
            this.myControler.finTour(false);
        }
        if(ae.getActionCommand().equals("validerFinTour")){
            this.myControler.validationScore();
        }
        if(ae.getActionCommand().equals("annulerFinTour")){
            this.myControler.annulerFinTour();
        }
        if (ae.getActionCommand().equals("confScore")){
            this.myControler.confScores();
        }
        if(ae.getActionCommand().equals("changePrefs")){
            this.myControler.changePrefs();
        }
        if(ae.getActionCommand().equals("confirmFinTour")){
            this.myControler.finTour();
        }
        if(ae.getActionCommand().equals("cancelFinTour")){
            this.myControler.closeConfirmWindow();
        }
    }
    
}
