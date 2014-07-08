/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.views;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import yams.control.YamControl;
import yams.events.YamEvents;
import yams.hightScores.pojos.Score;
import yams.hightScores.table.ModelRowHeader;
import yams.hightScores.table.ModeleTableHightScore;

/**
 *
 * @author Nicolas
 */
public class HightScoreVue extends JFrame{
    private YamControl _myControler;
    private JTable _tableScore;
    private ModeleTableHightScore _modelScore;
    private JTable _rowHeader;
    private ModelRowHeader _modelRow;
    
    private JButton _btnRetour;
    
    public HightScoreVue(YamControl c){
        super("Hight Scores");
        this._myControler = c;
        this._modelScore = new ModeleTableHightScore();
        this._modelRow = new ModelRowHeader();
        this._rowHeader = new JTable(this._modelRow);
        this._rowHeader.setFocusable(false);
        this._tableScore = new JTable(this._modelScore);
        this._tableScore.setFocusable(false);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(this._tableScore);
        jsp.setRowHeaderView(this._rowHeader);
        pan.add(jsp, BorderLayout.NORTH);
        
        this._btnRetour = new JButton("Retour");
        this._btnRetour.addActionListener(new YamEvents(this._myControler));
        this._btnRetour.setActionCommand("closeHightScores");
        
        // TODO enregistrer les parties pour les afficher plus tard
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.toFront();
        this.setVisible(true);
    }
    
    public void close(){
        this.dispose();
    }
    
    private void setScores(){
        Score score;
    }
}
