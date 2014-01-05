/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            _myControler.commencer();
        }
        if(ae.getActionCommand().equals("lancer")){
            _myControler.lancer();
        }
        if(ae.getActionCommand().equals("nouveau")){
            _myControler.nouveau();
        }
        if(ae.getActionCommand().equals("quitter")){
            _myControler.quitter();
        }
        if(ae.getActionCommand().equals("finTour")){
            _myControler.finTour();
        }
        if(ae.getActionCommand().equals("validerFinTour")){
            _myControler.validationScore();
        }
        if(ae.getActionCommand().equals("annulerFinTour")){
            _myControler.annulerFinTour();
        }
        if(ae.getActionCommand().equals("nouvellePartie")){
            _myControler.nouvellePartie();
        }
        if(ae.getActionCommand().equals("recommencer")){
            _myControler.recommencer();
        }
        if(ae.getActionCommand().equals("regles")){
                _myControler.affichageRegles();
        }
        if (ae.getActionCommand().equals("annuler")){
            _myControler.annuler();
        }
        if(ae.getActionCommand().equals("confirmNouveau")){
            _myControler.confirmQuit(false);
        }
        if (ae.getActionCommand().equals("confirmQuit")){
            _myControler.confirmQuit(true);
        }
        if (ae.getActionCommand().equals("confScore")){
            _myControler.confScores();
        }
        if (ae.getActionCommand().equals("aperçuRegles")){
            _myControler.apercuRegle();
        }
    }
    
}
