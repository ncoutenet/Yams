/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yams.hightScores.views.HightScoreVue;

/**
 *
 * @author Nicolas
 */
public class ComboBoxEvents implements ActionListener{
    private HightScoreVue _view;
    
    public ComboBoxEvents(HightScoreVue v){
        this._view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this._view.selectMode();
    }
    
}
