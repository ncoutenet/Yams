/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    
    private JComboBox<Object> _cbModeJeu;
    private JButton _btnRetour;
    private List<Score> _scoresLibres;
    private List<Score> _scoresMontants;
    private List<Score> _scoresDescendants;
    
    public HightScoreVue(YamControl c){
        super("Hight Scores");
        //this._scores = new ArrayList<Score>();
        this._myControler = c;
        this._modelScore = new ModeleTableHightScore();
        this._modelRow = new ModelRowHeader();
        this._rowHeader = new JTable(this._modelRow);
        this._rowHeader.setFocusable(false);
        this._tableScore = new JTable(this._modelScore);
        this._tableScore.setFocusable(false);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        // TODO afficher les scores par mode de jeu
        //initialisation du menu déroulant
        Object[] modes = new Object[3];
        modes[0] = new String("Libre");
        modes[1] = new String("Montant");
        modes[2] = new String("Descendant");
        
        //instanciation du menu
        this._cbModeJeu = new JComboBox(modes);
        JLabel labModes = new JLabel("Mode de jeu: ");
        JPanel panMod = new JPanel(new FlowLayout());
        panMod.add(labModes);
        panMod.add(this._cbModeJeu);
        pan.add(panMod, BorderLayout.NORTH);
        
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(this._tableScore);
        jsp.setRowHeaderView(this._rowHeader);
        pan.add(jsp, BorderLayout.CENTER);
        
        this._btnRetour = new JButton("Retour");
        this._btnRetour.addActionListener(new YamEvents(this._myControler));
        this._btnRetour.setActionCommand("closeHightScores");
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.add(this._btnRetour);
        pan.add(panBtn, BorderLayout.SOUTH);
        
        // TODO enregistrer les parties pour les afficher plus tard
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.toFront();
        this.setVisible(true);
    }
    
    public void close(){
        this.dispose();
    }
    
    public void setScores(List<Score> scores, int mode){
        //this._scores = scores;
    }
    
    public void addAScore(Score s, int mode){
        /*if(this._scores.size() < 10){
            this._scores.add(s);
            this.sortScores();
            // TODO mettre à jour le tableau
        }
        else{
            Score oldScore = this._scores.get(this._scores.size()-1);
            if(s.getScore() > oldScore.getScore()){
                this._scores.remove(this._scores.size()-1);
                this._scores.add(s);
                this.sortScores();
                // TODO mettre à jour le tableau
            }
        }*/
    }
    
    public List<Score> getScores(int mode){
        switch(mode){
            case 0:
                return this._scoresLibres;
            case 1:
                return this._scoresMontants;
            case 2:
                return this._scoresDescendants;
            default:
                return null; //n'arrivera pas
        }
    }
    
    private void sortScores(){
       /* List<Score> scores = new ArrayList<Score>(this._scores.size());
        
        for(int i=0; i<this._scores.size(); i++){
            Score newScore = this._scores.get(i);
            int index = 0;
            boolean stopped = false;
            while((index < scores.size()) && (!stopped)){
                Score oldScore = scores.get(index);
                if(oldScore.getScore() > newScore.getScore()){
                    index++;
                }else{
                    stopped = true;
                }
            }
            if(stopped){
                scores.add(index, newScore);
            }else{
                scores.add(newScore);
            }
        }
        this._scores = scores;*/
    }
    
    /*
     * Retourne le code du mode de jeu
     */
    public int getModeJeu(){
        if(this._cbModeJeu.getSelectedItem().getClass().equals(String.class)){
            if(this._cbModeJeu.getSelectedItem().equals("Libre")){
                return 0;
            }
            else if(this._cbModeJeu.getSelectedItem().equals("Montant")){
                return 1;
            }
            else if(this._cbModeJeu.getSelectedItem().equals("Descendant")){
                return 2;
            }
        }
        
        return -1;
    }
}
