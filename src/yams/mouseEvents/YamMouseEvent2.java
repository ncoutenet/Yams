/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.mouseEvents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.JeuVue;

/**
 *
 * @author nicolas
 */
public class YamMouseEvent2 implements MouseListener{
    private JeuVue _myView;
    
    public YamMouseEvent2(JeuVue vue){
        this._myView = vue;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this._myView.majSelDes(1);
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
