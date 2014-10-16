/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import yams.control.YamControl;
import yams.events.YamEvents;
import yams.hightScores.events.ComboBoxEvents;
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
        this._scoresLibres = new ArrayList<Score>();
        this._scoresMontants = new ArrayList<Score>();
        this._scoresDescendants = new ArrayList<Score>();
        this._myControler = c;
        this._modelScore = new ModeleTableHightScore();
        this._modelRow = new ModelRowHeader();
        this._rowHeader = new JTable(this._modelRow);
        this._rowHeader.setFocusable(false);
        this._rowHeader.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn colHead = this._rowHeader.getColumnModel().getColumn(0);
        colHead.setPreferredWidth(20);
        this._tableScore = new JTable(this._modelScore);
        this._tableScore.setFocusable(false);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
        this._tableScore.setFont(font);
        this._tableScore.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn colScore = this._tableScore.getColumnModel().getColumn(1);
        colScore.setPreferredWidth(50);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        //initialisation du menu déroulant
        Object[] modes = new Object[3];
        modes[0] = new String("Libre");
        modes[1] = new String("Montant");
        modes[2] = new String("Descendant");
        
        //instanciation du menu
        this._cbModeJeu = new JComboBox(modes);
        this._cbModeJeu.addActionListener(new ComboBoxEvents(this));
        JLabel labModes = new JLabel("Mode de jeu: ");
        JPanel panMod = new JPanel(new FlowLayout());
        panMod.add(labModes);
        panMod.add(this._cbModeJeu);
        pan.add(panMod, BorderLayout.NORTH);
        
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(this._tableScore);
        jsp.setRowHeaderView(this._rowHeader);
        Dimension d = jsp.getPreferredSize();
        d.height = this._rowHeader.getPreferredSize().height+23;
        jsp.setPreferredSize(d);
        TableColumn colName = this._tableScore.getColumnModel().getColumn(0);
        colName.setPreferredWidth(jsp.getPreferredSize().width-73);
        Dimension dh = this._rowHeader.getPreferredScrollableViewportSize();
        dh.width = this._rowHeader.getPreferredSize().width;
        this._rowHeader.setPreferredScrollableViewportSize(dh);
        pan.add(jsp, BorderLayout.CENTER);
        
        this._btnRetour = new JButton("Retour");
        this._btnRetour.addActionListener(new YamEvents(this._myControler));
        this._btnRetour.setActionCommand("closeHightScores");
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.add(this._btnRetour);
        pan.add(panBtn, BorderLayout.SOUTH);
        
        this._scoresLibres = this._myControler.loadHightScores(0);
        this._scoresMontants = this._myControler.loadHightScores(1);
        this._scoresDescendants = this._myControler.loadHightScores(2);
        
        this.changeScores(0);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    /*
     * Mise à jour du tableau
     */
    public void changeScores(int mode){
        this._modelScore.delScores();
        switch(mode){
            case 0:
                for(int i=0; i<this._scoresLibres.size(); i++){
                    this._modelScore.addScore(this._scoresLibres.get(i));
                }
                break;
            case 1:
                for(int i=0; i<this._scoresMontants.size(); i++){
                    this._modelScore.addScore(this._scoresMontants.get(i));
                }
                break;
            case 2:
                for(int i=0; i<this._scoresDescendants.size(); i++){
                    this._modelScore.addScore(this._scoresDescendants.get(i));
                }
                break;
            default:
                break; //n'arrivera pas
        }
        this._tableScore.updateUI();
    }
    
    public void selectMode(){
        if(this._cbModeJeu.getSelectedItem().equals("Libre")){
            this.changeScores(0);
        }else if(this._cbModeJeu.getSelectedItem().equals("Montant")){
            this.changeScores(1);
        }else if(this._cbModeJeu.getSelectedItem().equals("Descendant")){
            this.changeScores(2);
        }
    }
    
    public void close(){
        this.dispose();
    }
    
    public void setScores(List<Score> scores, int mode){
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public void addScoreLibre(Score s){
        if(this._scoresLibres.size() < 10){
            this._scoresLibres.add(s);
            this.sortScores(this._scoresLibres);
            this._myControler.saveHightScores(this._scoresLibres, 0);
            this.changeScores(0);
        }
        else{
            Score oldScore = this._scoresLibres.get(this._scoresLibres.size()-1);
            if(s.getScore() > oldScore.getScore()){
                this._scoresLibres.remove(this._scoresLibres.size()-1);
                this._scoresLibres.add(s);
                this.sortScores(this._scoresLibres);
                this._myControler.saveHightScores(this._scoresLibres, 0);
            }
        }
    }
    
    public void addScoreMontant(Score s){
        if(this._scoresMontants.size() < 10){
            this._scoresMontants.add(s);
            this.sortScores(this._scoresMontants);
            this._myControler.saveHightScores(this._scoresMontants, 1);
        }
        else{
            Score oldScore = this._scoresMontants.get(this._scoresMontants.size()-1);
            if(s.getScore() > oldScore.getScore()){
                this._scoresMontants.remove(this._scoresMontants.size()-1);
                this._scoresMontants.add(s);
                this.sortScores(this._scoresMontants);
                this._myControler.saveHightScores(this._scoresMontants, 1);
            }
        }
    }
    
    public void addScoreDescendant(Score s){
        if(this._scoresDescendants.size() < 10){
            this._scoresDescendants.add(s);
            this.sortScores(this._scoresDescendants);
            this._myControler.saveHightScores(this._scoresDescendants, 2);
        }
        else{
            Score oldScore = this._scoresDescendants.get(this._scoresDescendants.size()-1);
            if(s.getScore() > oldScore.getScore()){
                this._scoresDescendants.remove(this._scoresDescendants.size()-1);
                this._scoresDescendants.add(s);
                this.sortScores(this._scoresDescendants);
                this._myControler.saveHightScores(this._scoresDescendants, 2);
            }
        }
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
    
    private void sortScores(List<Score> scores){
        for(int i=scores.size(); i>0; i--){
            for(int j=0; j<i-1; j++){
                if(scores.get(j).getScore() > scores.get(j+1).getScore()){
                    Score tmp = scores.get(j);
                    scores.set(j, scores.get(j+1));
                    scores.set(j+1, tmp);
                }
            }
        }
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
