/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events;

import com.sun.glass.ui.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class GameWindowStateListener implements WindowStateListener{
    private final YamControl myControler;

    public GameWindowStateListener(YamControl yc){
        myControler = yc;
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        myControler.resizeDices(e.getNewState() != Window.NORMAL);
    }
    
}
