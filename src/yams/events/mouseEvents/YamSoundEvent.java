/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events.mouseEvents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.views.ConnexionVue;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class YamSoundEvent implements MouseListener{
    private YamControl _myControler;
    
    public YamSoundEvent(YamControl control){
        this._myControler = control;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this._myControler.majSound();
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}
