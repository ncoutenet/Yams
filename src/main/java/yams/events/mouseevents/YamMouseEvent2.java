/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events.mouseevents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import yams.views.JeuVue;

/**
 *
 * @author nicolas
 */
public class YamMouseEvent2 implements MouseListener{
    private JeuVue myView;
    
    public YamMouseEvent2(JeuVue vue){
        this.myView = vue;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this.myView.majSelDes(1);
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
