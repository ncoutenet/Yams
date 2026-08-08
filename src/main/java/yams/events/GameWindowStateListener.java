/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events;

import com.sun.glass.ui.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.logging.Logger;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class GameWindowStateListener implements WindowStateListener{
    private static final Logger LOGGER = Logger.getLogger(GameWindowStateListener.class.getName());
    private YamControl myControler;

    public GameWindowStateListener(YamControl yc){
        this.myControler = yc;
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        if(e.getNewState() == Window.NORMAL){
            this.myControler.resizeDices(false);
            LOGGER.info("normal");
        }
        else{
            this.myControler.resizeDices(true);
            LOGGER.info("miximisé");
        }
    }
    
}
