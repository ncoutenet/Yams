/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hight_scores.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yams.hight_scores.views.HightScoreVue;

/**
 *
 * @author Nicolas
 */
public class ComboBoxEvents implements ActionListener{
    private HightScoreVue view;
    
    public ComboBoxEvents(HightScoreVue v){
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.view.selectMode();
    }
    
}
