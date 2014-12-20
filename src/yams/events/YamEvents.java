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
    private YamControl _myControler;
    
    public YamEvents(YamControl yc){
        _myControler = yc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(" commande reçue: " + ae.getActionCommand());
        if(ae.getActionCommand().equals("commencer")){
            this._myControler.commencer();
        }
        if(ae.getActionCommand().equals("lancer")){
            this._myControler.lancer();
        }
        if(ae.getActionCommand().equals("finTour")){
            this._myControler.finTour(false);
        }
        if(ae.getActionCommand().equals("validerFinTour")){
            this._myControler.validationScore();
        }
        if(ae.getActionCommand().equals("annulerFinTour")){
            this._myControler.annulerFinTour();
        }
        if (ae.getActionCommand().equals("confScore")){
            this._myControler.confScores();
        }
        if(ae.getActionCommand().equals("changePrefs")){
            this._myControler.changePrefs();
        }
        if(ae.getActionCommand().equals("confirmFinTour")){
            this._myControler.finTour();
        }
        if(ae.getActionCommand().equals("cancelFinTour")){
            this._myControler.closeConfirmWindow();
        }
    }
    
}
