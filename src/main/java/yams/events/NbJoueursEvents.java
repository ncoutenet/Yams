/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.logging.Logger;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */

/*
 * Classe gérant les modifications du nombre de joueurs
 */
public class NbJoueursEvents implements ChangeListener{
    private static final Logger LOGGER = Logger.getLogger(NbJoueursEvents.class.getName());
    private YamControl myControler;

    public NbJoueursEvents(YamControl yc){
        myControler = yc;
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        LOGGER.info(" Nombre de joueurs modifié");
        myControler.setNomsJoueurs();
    }
    
}
