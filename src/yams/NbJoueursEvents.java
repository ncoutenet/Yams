/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nicolas
 */
public class NbJoueursEvents implements ChangeListener{
    private YamControl _myControler;
    
    public NbJoueursEvents(YamControl yc){
        _myControler = yc;
    }
    
    @Override
    public void stateChanged(ChangeEvent ce) {
        System.out.println(" Nombre de joueurs modifié");
        _myControler.setNomsJoueurs();
    }
    
}
