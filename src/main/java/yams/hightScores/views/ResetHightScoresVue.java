/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.hightScores.views;

import java.awt.*;
import javax.swing.*;
import yams.control.YamControl;
import yams.hightScores.events.HightScoreEvents;

/**
 *
 * @author nicolas
 */
public class ResetHightScoresVue extends JDialog{
    private YamControl _myControler;
    
    public ResetHightScoresVue(YamControl yc, HightScoreVue parent){
        super(parent, true);
        this._myControler = yc;
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("Remise à zéro des meilleurs scores");
        
        JLabel labQuestion = new JLabel("Quels scores souhaitez-vous effacer?");
        JLabel labAdvertasing = new JLabel();
        labAdvertasing.setFont(new Font(labAdvertasing.getFont().getFamily(), Font.BOLD, labAdvertasing.getFont().getSize()));
        labAdvertasing.setForeground(Color.red);
        labAdvertasing.setHorizontalAlignment(SwingConstants.CENTER);
        labAdvertasing.setText("Attention, cette opération est irréversible");
        
        JButton btnAll = new JButton("Tous");
        JButton btnActu = new JButton("Uniquement ceux du mode actuel");
        JButton btnCancel = new JButton("Aucun");
        
        btnAll.addActionListener(new HightScoreEvents(this._myControler));
        btnAll.setActionCommand("resetAllScores");
        btnActu.addActionListener(new HightScoreEvents(this._myControler));
        btnActu.setActionCommand("resetOneScore");
        btnCancel.addActionListener(new HightScoreEvents(this._myControler));
        btnCancel.setActionCommand("resetNoScore");
        
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        
        Box boxLabels = Box.createVerticalBox();
        boxLabels.add(labQuestion);
        boxLabels.add(labAdvertasing);
        panel.add(boxLabels, BorderLayout.CENTER);
        
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.add(btnAll);
        panBtn.add(btnActu);
        panBtn.add(btnCancel);
        panel.add(panBtn, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(this.getParent());
    }
}
