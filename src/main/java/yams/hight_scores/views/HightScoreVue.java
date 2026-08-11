/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hight_scores.views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableColumn;
import yams.ModeJeu;
import yams.control.YamControl;
import yams.hight_scores.events.ComboBoxEvents;
import yams.hight_scores.events.HightScoreEvents;
import yams.hight_scores.pojos.Score;
import yams.hight_scores.table.ModelRowHeader;
import yams.hight_scores.table.ModeleTableHightScore;
import yams.table.ColorTab;

/**
 *
 * @author Nicolas
 */
public class HightScoreVue extends JFrame{
    private static final Logger LOGGER = Logger.getLogger(HightScoreVue.class.getName());
    private transient YamControl myControler;
    private JTable tableScore;
    private ModeleTableHightScore modelScore;
    private JTable rowHeader;
    private ModelRowHeader modelRow;
    private ColorTab gestionnaire;

    private JComboBox<ModeJeu> cbModeJeu;
    private JButton btnRetour;
    
    private List<Score> scoresLibres;
    private List<Score> scoresMontants;
    private List<Score> scoresDescendants;
    
    public HightScoreVue(YamControl c){
        super("Hight Scores");
        super.setResizable(false);
        this.scoresLibres = new ArrayList<Score>();
        this.scoresMontants = new ArrayList<Score>();
        this.scoresDescendants = new ArrayList<Score>();
        this.myControler = c;
        this.modelScore = new ModeleTableHightScore();
        this.modelRow = new ModelRowHeader();
        this.rowHeader = new JTable(this.modelRow);
        this.rowHeader.setFocusable(false);
        this.rowHeader.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn colHead = this.rowHeader.getColumnModel().getColumn(0);
        colHead.setPreferredWidth(20);
        this.tableScore = new JTable(this.modelScore);
        this.tableScore.setFocusable(false);
        this.gestionnaire = new ColorTab(10, 3);
        this.tableScore.setDefaultRenderer(Object.class, this.gestionnaire);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
        this.tableScore.setFont(font);
        this.tableScore.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn colScore = this.tableScore.getColumnModel().getColumn(1);
        colScore.setPreferredWidth(50);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        //instanciation du menu déroulant
        this.cbModeJeu = new JComboBox<>(ModeJeu.values());
        this.cbModeJeu.addActionListener(new ComboBoxEvents(this));
        JLabel labModes = new JLabel("Mode de jeu: ");
        JPanel panMod = new JPanel(new FlowLayout());
        panMod.add(labModes);
        panMod.add(this.cbModeJeu);
        pan.add(panMod, BorderLayout.NORTH);
        
        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(this.tableScore);
        jsp.setRowHeaderView(this.rowHeader);
        Dimension d = jsp.getPreferredSize();
        d.height = this.rowHeader.getPreferredSize().height+20;
        jsp.setPreferredSize(d);
        this.tableScore.getColumnModel().getColumn(0).setPreferredWidth(jsp.getPreferredSize().width-180); //ajustement de la colonne du pseudo
        this.tableScore.getColumnModel().getColumn(2).setPreferredWidth(this.tableScore.getColumnModel().getColumn(2).getPreferredWidth()+30); //ajustement de la colonne de la date
        for(int i=0; i<3; i++){
            this.tableScore.getColumnModel().getColumn(i).setResizable(false);
        }
        Dimension dh = this.rowHeader.getPreferredScrollableViewportSize();
        dh.width = this.rowHeader.getPreferredSize().width;
        this.rowHeader.setPreferredScrollableViewportSize(dh);
        pan.add(jsp, BorderLayout.CENTER);
        
        JButton btnReset = new JButton("Reset...");
        btnReset.addActionListener(new HightScoreEvents(this.myControler));
        btnReset.setActionCommand("resetHightScores");
        
        JButton btnExport = new JButton("Exporter...");
        btnExport.addActionListener(new HightScoreEvents(this.myControler));
        btnExport.setActionCommand("exportHightScores");
        
        this.btnRetour = new JButton("Retour");
        this.btnRetour.addActionListener(new HightScoreEvents(this.myControler));
        this.btnRetour.setActionCommand("closeHightScores");
        Box panBtn = Box.createHorizontalBox();
        panBtn.add(btnReset);
        panBtn.add(btnExport);
        panBtn.add(Box.createHorizontalGlue());
        panBtn.add(this.btnRetour);
        pan.add(panBtn, BorderLayout.SOUTH);
        
        this.scoresLibres = this.myControler.loadHightScores(ModeJeu.LIBRE);
        this.scoresMontants = this.myControler.loadHightScores(ModeJeu.MONTANT);
        this.scoresDescendants = this.myControler.loadHightScores(ModeJeu.DESCENDANT);

        this.changeScores(ModeJeu.LIBRE);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Mise à jour du tableau
     */
    public final void changeScores(ModeJeu mode){
        int nbCol = 3;

        this.modelScore.delScores();
        this.gestionnaire.clear();

        List<Score> scores = this.getScores(mode);
        for(int i=0; i<scores.size(); i++){
            this.modelScore.addScore(scores.get(i));
            if(i%2 == 0){
                for(int col=0; col<nbCol; col++){
                    this.gestionnaire.setCouleurs(i, col, ColorTab.BLEU);
                }
            }
        }
        this.tableScore.updateUI();
    }
    
    /**
     * Met en relief les parties en paramètre
     */
    public void selectScores(List<Score> newScores, ModeJeu mode){
        List<Score> scores = this.getScores(mode);
        int nbCol = 3;

        for(int i=0; i<newScores.size(); i++){
            int j = 0;
            while((j<scores.size()) && (!this.isEquals(newScores.get(i), scores.get(j)))){
                j++;
            }
            if(j<scores.size()){
                for(int col=0; col<nbCol; col++){
                    this.gestionnaire.setCouleurs(j, col, ColorTab.VERT);
                }
            }
        }
    }
    
    /**
     * Retourne vrai si les scores en paramètre sont égaux, faux sinon
     */
    private boolean isEquals(Score s1, Score s2){
        return (s1.getName().equals(s2.getName())) && (s1.getScore() == s2.getScore()) && (s1.getDate().equals(s2.getDate()));
    }
    
    /*
     * Permet d'afficher les score du mode de jeu choisis par l'utilisateur
     */
    public void selectMode(){
        this.changeScores((ModeJeu) this.cbModeJeu.getSelectedItem());
    }

    /*
     * Met le menu déroulant à jour
     */
    public void setMode(ModeJeu mode){
        this.cbModeJeu.setSelectedItem(mode);
    }
    
    /*
     * ferme la fenêtre
     */
    public void close(){
        this.dispose();
    }
    
    public void setScores(List<Score> scores, ModeJeu mode){
        switch(mode){
            case LIBRE:
                this.scoresLibres = scores;
                break;
            case MONTANT:
                this.scoresMontants = scores;
                break;
            case DESCENDANT:
                this.scoresDescendants = scores;
                break;
            default:
                LOGGER.warning("Mode de jeu inexistant");
                break;
        }
    }

    /*
     * Ajoute un score
     */
    public void addScore(Score s, ModeJeu mode){
        List<Score> scores = this.getScores(mode);
        addScoreToList(scores, s, mode);
        this.changeScores(mode);
    }

    private void addScoreToList(List<Score> scores, Score s, ModeJeu mode){
        if(scores.size() < 10){
            scores.add(s);
            this.sortScores(scores);
            this.myControler.saveHightScores(scores, mode);
        }
        else{
            Score oldScore = scores.get(scores.size()-1);
            if(s.getScore() > oldScore.getScore()){
                scores.remove(scores.size()-1);
                scores.add(s);
                this.sortScores(scores);
                this.myControler.saveHightScores(scores, mode);
            }
        }
    }
    
    /*
     * retourne la liste des scores du mode de jeu choisis
     */
    public List<Score> getScores(ModeJeu mode){
        switch(mode){
            case LIBRE:
                return this.scoresLibres;
            case MONTANT:
                return this.scoresMontants;
            case DESCENDANT:
                return this.scoresDescendants;
            default:
                return new ArrayList<>(); //n'arrivera pas
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
    public ModeJeu getModeJeu(){
        return (ModeJeu) this.cbModeJeu.getSelectedItem();
    }
}
