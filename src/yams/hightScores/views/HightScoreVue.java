/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableColumn;
import yams.Yams;
import yams.control.YamControl;
import yams.hightScores.events.ComboBoxEvents;
import yams.hightScores.events.HightScoreEvents;
import yams.hightScores.pojos.Score;
import yams.hightScores.table.ModelRowHeader;
import yams.hightScores.table.ModeleTableHightScore;
import yams.table.ColorTab;

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
    private ColorTab _gestionnaire;
    
    private JComboBox _cbModeJeu;
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
        this._gestionnaire = new ColorTab(10, 3);
        this._tableScore.setDefaultRenderer(Object.class, this._gestionnaire);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
        this._tableScore.setFont(font);
        this._tableScore.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn colScore = this._tableScore.getColumnModel().getColumn(1);
        colScore.setPreferredWidth(50);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        //initialisation du menu déroulant
        Object[] modes = new Object[3];
        modes[Yams.MODELIBRE] = new String("Libre");
        modes[Yams.MODEMONTANT] = new String("Montant");
        modes[Yams.MODEDESCENDANT] = new String("Descendant");
        
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
        d.height = this._rowHeader.getPreferredSize().height+20;
        jsp.setPreferredSize(d);
        this._tableScore.getColumnModel().getColumn(0).setPreferredWidth(jsp.getPreferredSize().width-180); //ajustement de la colonne du pseudo
        this._tableScore.getColumnModel().getColumn(2).setPreferredWidth(this._tableScore.getColumnModel().getColumn(2).getPreferredWidth()+30); //ajustement de la colonne de la date
        for(int i=0; i<3; i++){
            this._tableScore.getColumnModel().getColumn(i).setResizable(false);
        }
        Dimension dh = this._rowHeader.getPreferredScrollableViewportSize();
        dh.width = this._rowHeader.getPreferredSize().width;
        this._rowHeader.setPreferredScrollableViewportSize(dh);
        pan.add(jsp, BorderLayout.CENTER);
        
        JButton btnReset = new JButton("Reset...");
        btnReset.addActionListener(new HightScoreEvents(this._myControler));
        btnReset.setActionCommand("resetHightScores");
        
        this._btnRetour = new JButton("Retour");
        this._btnRetour.addActionListener(new HightScoreEvents(this._myControler));
        this._btnRetour.setActionCommand("closeHightScores");
        Box panBtn = Box.createHorizontalBox();
        panBtn.add(btnReset);
        panBtn.add(Box.createHorizontalGlue());
        panBtn.add(this._btnRetour);
        pan.add(panBtn, BorderLayout.SOUTH);
        
        this._scoresLibres = this._myControler.loadHightScores(Yams.MODELIBRE);
        this._scoresMontants = this._myControler.loadHightScores(Yams.MODEMONTANT);
        this._scoresDescendants = this._myControler.loadHightScores(Yams.MODEDESCENDANT);
        
        this.changeScores(Yams.MODELIBRE);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Mise à jour du tableau
     */
    public final void changeScores(int mode){
        int nbCol = 3;
        
        this._modelScore.delScores();
        this._gestionnaire.clear();
        
        switch(mode){
            case Yams.MODELIBRE:
                for(int i=0; i<this._scoresLibres.size(); i++){
                    this._modelScore.addScore(this._scoresLibres.get(i));
                    if(i%2 == 0){
                        for(int col=0; col<nbCol; col++){
                            this._gestionnaire.setCouleurs(i, col, ColorTab.BLEU);
                        }
                    }
                }
                break;
            case Yams.MODEMONTANT:
                for(int i=0; i<this._scoresMontants.size(); i++){
                    this._modelScore.addScore(this._scoresMontants.get(i));
                    if(i%2 == 0){
                        for(int col=0; col<nbCol; col++){
                            this._gestionnaire.setCouleurs(i, col, ColorTab.BLEU);
                        }
                    }
                }
                break;
            case Yams.MODEDESCENDANT:
                for(int i=0; i<this._scoresDescendants.size(); i++){
                    this._modelScore.addScore(this._scoresDescendants.get(i));
                    if(i%2 == 0){
                        for(int col=0; col<nbCol; col++){
                            this._gestionnaire.setCouleurs(i, col, ColorTab.BLEU);
                        }
                    }
                }
                break;
            default:
                System.err.println("Mode de jeu inconnu");
                break; //n'arrivera pas
        }
        this._tableScore.updateUI();
    }
    
    /**
     * Met en relief les parties en paramètre
     */
    public void selectScores(List<Score> newScores, int mode){
        List<Score> scores;
        int nbCol = 3;
        
        switch(mode){
            case Yams.MODELIBRE:
                scores = this._scoresLibres;
                break;
            case Yams.MODEMONTANT:
                scores = this._scoresMontants;
                break;
            case Yams.MODEDESCENDANT:
                scores = this._scoresDescendants;
                break;
            default:
                return;
        }
        for(int i=0; i<newScores.size(); i++){
            int j = 0;
            while((j<scores.size()) && (!this.isEquals(newScores.get(i), scores.get(j)))){
                j++;
            }
            if(j<scores.size()){
                for(int col=0; col<nbCol; col++){
                    this._gestionnaire.setCouleurs(j, col, ColorTab.VERT);
                }
            }
        }
    }
    
    /**
     * Retourne vrai si les scores en paramètre sont égaux, faux sinon
     */
    private boolean isEquals(Score s1, Score s2){
        if((s1.getName().equals(s2.getName())) && (s1.getScore() == s2.getScore()) && (s1.getDate().equals(s2.getDate()))){
            return true;
        }
        else{
            return false;
        }
    }
    
    /*
     * Permet d'afficher les score du mode de jeu choisis par l'utilisateur
     */
    public void selectMode(){
        if(this._cbModeJeu.getSelectedItem().equals("Libre")){
            this.changeScores(Yams.MODELIBRE);
        }else if(this._cbModeJeu.getSelectedItem().equals("Montant")){
            this.changeScores(Yams.MODEMONTANT);
        }else if(this._cbModeJeu.getSelectedItem().equals("Descendant")){
            this.changeScores(Yams.MODEDESCENDANT);
        }
    }
    
    /*
     * Met le menu déroulant à jour
     */
    public void setMode(int mode){
        switch(mode){
            case Yams.MODELIBRE:
                this._cbModeJeu.setSelectedIndex(Yams.MODELIBRE);
                break;
            case Yams.MODEMONTANT:
                this._cbModeJeu.setSelectedIndex(Yams.MODEMONTANT);
                break;
            case Yams.MODEDESCENDANT:
                this._cbModeJeu.setSelectedIndex(Yams.MODEDESCENDANT);
                break;
                default:
                    System.err.println("Mauvais mode de jeu");
                    break; //n'arrivera pas
        }
    }
    
    /*
     * ferme la fenêtre
     */
    public void close(){
        this.dispose();
    }
    
    public void setScores(List<Score> scores, int mode){
        switch(mode){
            case Yams.MODELIBRE:
                this._scoresLibres = scores;
                break;
            case Yams.MODEMONTANT:
                this._scoresMontants = scores;
                break;
            case Yams.MODEDESCENDANT:
                this._scoresDescendants = scores;
                break;
            default:
                System.err.println("Mode de jeu inexistant");
                break;
        }
    }
    
    /*
     * Ajoute un score
     */
    public void addScore(Score s, int mode){
        switch(mode){
            case Yams.MODELIBRE:
                if(this._scoresLibres.size() < 10){
                    this._scoresLibres.add(s);
                    this.sortScores(this._scoresLibres);
                    this._myControler.saveHightScores(this._scoresLibres, Yams.MODELIBRE);
                }
                else{
                    Score oldScore = this._scoresLibres.get(this._scoresLibres.size()-1);
                    if(s.getScore() > oldScore.getScore()){
                        this._scoresLibres.remove(this._scoresLibres.size()-1);
                        this._scoresLibres.add(s);
                        this.sortScores(this._scoresLibres);
                        this._myControler.saveHightScores(this._scoresLibres, Yams.MODELIBRE);
                    }
                }
                this.changeScores(Yams.MODELIBRE);
                break;
            case Yams.MODEMONTANT:
                if(this._scoresMontants.size() < 10){
                    this._scoresMontants.add(s);
                    this.sortScores(this._scoresMontants);
                    this._myControler.saveHightScores(this._scoresMontants, Yams.MODEMONTANT);
                }
                else{
                    Score oldScore = this._scoresMontants.get(this._scoresMontants.size()-1);
                    if(s.getScore() > oldScore.getScore()){
                        this._scoresMontants.remove(this._scoresMontants.size()-1);
                        this._scoresMontants.add(s);
                        this.sortScores(this._scoresMontants);
                        this._myControler.saveHightScores(this._scoresMontants, Yams.MODEMONTANT);
                    }
                }
                this.changeScores(Yams.MODEMONTANT);
                break;
            case Yams.MODEDESCENDANT:
                if(this._scoresDescendants.size() < 10){
                    this._scoresDescendants.add(s);
                    this.sortScores(this._scoresDescendants);
                    this._myControler.saveHightScores(this._scoresDescendants, Yams.MODEDESCENDANT);
                }
                else{
                    Score oldScore = this._scoresDescendants.get(this._scoresDescendants.size()-1);
                    if(s.getScore() > oldScore.getScore()){
                        this._scoresDescendants.remove(this._scoresDescendants.size()-1);
                        this._scoresDescendants.add(s);
                        this.sortScores(this._scoresDescendants);
                        this._myControler.saveHightScores(this._scoresDescendants, Yams.MODEDESCENDANT);
                    }
                }
                this.changeScores(Yams.MODEDESCENDANT);
                break;
            default:
                break; //n'arrivera pas
        }
    }
    
    /*
     * retourne la liste des scores du mode de jeu choisis
     */
    public List<Score> getScores(int mode){
        switch(mode){
            case Yams.MODELIBRE:
                return this._scoresLibres;
            case Yams.MODEMONTANT:
                return this._scoresMontants;
            case Yams.MODEDESCENDANT:
                return this._scoresDescendants;
            default:
                return null; //n'arrivera pas
        }
    }
    
    /*
     * Trie les scores
     */
    private void sortScores(List<Score> scores){
        for(int i=scores.size(); i>0; i--){
            for(int j=0; j<i-1; j++){
                if(scores.get(j).getScore() < scores.get(j+1).getScore()){
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
                return Yams.MODELIBRE;
            }
            else if(this._cbModeJeu.getSelectedItem().equals("Montant")){
                return Yams.MODEMONTANT;
            }
            else if(this._cbModeJeu.getSelectedItem().equals("Descendant")){
                return Yams.MODEDESCENDANT;
            }
        }
        
        return -1;
    }
}
