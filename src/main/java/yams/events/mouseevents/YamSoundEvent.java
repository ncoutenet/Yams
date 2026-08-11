/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events.mouseevents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class YamSoundEvent implements MouseListener{
    private YamControl myControler;
    
    public YamSoundEvent(YamControl control){
        this.myControler = control;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.myControler.majSoundOnClic();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // non utilisé
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // non utilisé
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // non utilisé
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // non utilisé
    }
    
}
