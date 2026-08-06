/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events.mouseEvents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.views.JeuVue;

/**
 *
 * @author nicolas
 */
public class YamMouseEvent5 implements MouseListener{
    private JeuVue _myView;
    
    public YamMouseEvent5(JeuVue vue){
        this._myView = vue;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this._myView.majSelDes(4);
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
