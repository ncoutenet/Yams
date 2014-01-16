/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.mouseEvents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.ConnectionVue;

/**
 *
 * @author nicolas
 */
public class YamSoundConnectionEvent implements MouseListener{
    private ConnectionVue _view;
    
    public YamSoundConnectionEvent(ConnectionVue view){
        this._view = view;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this._view.majSound(false);
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
