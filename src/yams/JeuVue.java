/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import yams.table.ModeleTableScore;

/**
 *
 * @author nicolas
 */
public class JeuVue extends JFrame {
    private Icon[] _des;
    private JCheckBox[] _selectionDes;
    private JLabel _aQui;
    private JLabel[] _labDes;
    private JLabel _nbLancers;
    private JButton _btnFinTour;
    private JButton _btnLancer;
    private JLabel _labCoutsRestants;
    
    private int[] _valDes;
    private boolean[] _selDes;
    private String[] _nomsJoueurs;
    private int _tour;
    private int _lancesRestants;
    
    private JTable _tableau;
    private ModeleTableScore _tabModel;
    private YamControl _myControler;
    
    public JeuVue(int nbJoueurs, String[] noms, int tour, YamControl yc){
        super("Jeu du Yam's");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        _myControler = yc;
        
        //initialisation des images des dés
        this._des = new Icon[7];
        this._des[0] = new ImageIcon(getClass().getResource("images/dés/indef.gif"));
        this._des[1] = new ImageIcon(getClass().getResource("images/dés/1.gif"));
        this._des[2] = new ImageIcon(getClass().getResource("images/dés/2.gif"));
        this._des[3] = new ImageIcon(getClass().getResource("images/dés/3.gif"));
        this._des[4] = new ImageIcon(getClass().getResource("images/dés/4.gif"));
        this._des[5] = new ImageIcon(getClass().getResource("images/dés/5.gif"));
        this._des[6] = new ImageIcon(getClass().getResource("images/dés/6.gif"));
        this._labDes = new JLabel[5];
        for(int i = 0; i < 5; i++)
            this._labDes[i] = new JLabel();
        
        //initialisation du tableau des scores
        this._tabModel = new ModeleTableScore(nbJoueurs, this);
        this.setJoueurs(noms);
        this._tableau = new JTable(_tabModel);
        this._tableau.setName("Tableau des scores");
        
        //initialisation du tour, des valeurs et des sélections des dés
        this._tour = tour;
        this._valDes = new int[5];
        this._selDes = new boolean[5];
        this._selectionDes = new JCheckBox[5];
        this._aQui = new JLabel();
        for(int i = 0; i < 5; i++){
            _selectionDes[i] = new JCheckBox();
            _selectionDes[i].addActionListener(new YamEvents(_myControler));
            _selectionDes[i].setActionCommand("selDé");
            _selDes[i] = false;
            _valDes[i] = 0;
        }
        this.setAQui(this._tour);
        this.refreshDes();
        
        //initialisation des variables locales restantes
        this._lancesRestants = 3;
        
        //fabrication de la fenêtre
        JPanel panJeu = new JPanel(new BorderLayout());
        panJeu.add(_aQui, BorderLayout.NORTH);
        
        JPanel panDes = new JPanel(new GridLayout(5, 1, 0, 5));
        for(int i = 0; i < 5; i++)
        {
            JPanel panel = new JPanel(new FlowLayout());
            panel.add(this._selectionDes[i]);
            panel.add(this._labDes[i]);
            panDes.add(panel);
        }
        this.setEnabledDes(false);
        panJeu.add(panDes, BorderLayout.CENTER);
        
        //emplacement des dés
        JPanel panLancement = new JPanel(new GridLayout(2, 1));
        _btnLancer = new JButton("Lancer");
        _btnLancer.addActionListener(new YamEvents(_myControler));
        _btnLancer.setActionCommand("lancer");
        this._nbLancers = new JLabel();
        this.setNbLancers(3);
        _btnFinTour = new JButton("Fin du Tour");
        _btnFinTour.addActionListener(new YamEvents(_myControler));
        _btnFinTour.setActionCommand("finTour");
        this.setEnabledFinTour(false);
        JPanel panBtnLancement = new JPanel(new FlowLayout());
        panBtnLancement.add(_btnLancer);
        panBtnLancement.add(this._btnFinTour);
        panLancement.add(panBtnLancement);
        panLancement.add(this._nbLancers);
        panJeu.add(panLancement, BorderLayout.SOUTH);
        
        //barre des menus
        JPanel panBtnBar = new JPanel(new FlowLayout());
        JButton btnNouveau = new JButton("Nouveau");
        btnNouveau.addActionListener(new YamEvents(_myControler));
        btnNouveau.setActionCommand("nouveau");
        panBtnBar.add(btnNouveau);
        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(new YamEvents(_myControler));
        btnQuitter.setActionCommand("quitter");
        panBtnBar.add(btnQuitter);
        
        //label des couts restants
        this._labCoutsRestants = new JLabel();
        
        //assemblage des éléments de la fenêtre
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.add(panBtnBar, BorderLayout.NORTH);
        pan.add(panJeu, BorderLayout.WEST);
        JScrollPane spScores = new JScrollPane(this._tableau);
        spScores.setPreferredSize(this._tableau.getPreferredSize());
        pan.add(spScores, BorderLayout.CENTER);
        pan.add(this._labCoutsRestants, BorderLayout.SOUTH);
        
        this.pack();
    }
    
    public void affichage(boolean enable){
        this.setVisible(enable);
    }
    
    public Joueur getJoueur(int index){
        return this._tabModel.getJoueur(index);
    }
    
    public void majSelDes(){
        for(int i = 0; i < 5; i++){
            if(this._selectionDes[i].isSelected()){
                this._selDes[i] = true; 
            }
            else {
                this._selDes[i] = false;
            }
        }
    }
    
    public void majCoupsRestants(String coups){
        String texte = new String("Coups restants: ");
        texte += coups;
        this._labCoutsRestants.setText(texte);
    }
    
    public void setEnabledDes(boolean enable){
        for(int i = 0; i < 5; i++){
            this._selectionDes[i].setEnabled(enable);
        }
    }
    
    private void majDes(int index){
        this._labDes[index].setIcon(this._des[this._valDes[index]]);
    }
    
    public void setValDe(int index, int val){
        if(!this._selDes[index]){
            this._valDes[index] = val;
            this.majDes(index);
        }
    }
    
    public int[] getDes(){
        return this._valDes;
    }
    
    public void setEnabledFinTour(boolean enable){
        this._btnFinTour.setEnabled(enable);
    }
    
    public void setEnabledLancer(boolean enable){
        this._btnLancer.setEnabled(enable);
    }
    
    private void setAQui(int index){
        String tour = new String("Tour de: ");
        String nom = new String(this._nomsJoueurs[index]);
            tour += nom;
        this._aQui.setText(tour);
    }
    
    public void setNbLancers(int nb){
        String lancer = new String("Reste ");
        
        lancer += String.valueOf(nb);
        lancer += " lancés";
        this._nbLancers.setText(lancer);
        this._lancesRestants = nb;
    }
    
    public void refreshDes(){
        for(int i = 0; i < 5; i++){
            _labDes[i].setIcon(_des[_valDes[i]]);
        }
    }
    
    public void initDes(){
        for(int i = 0; i < 5; i++){
            if(this._selectionDes[i].isSelected()){
                this._selectionDes[i].setSelected(false);
            }
        }
        this.majSelDes();
        for(int i = 0; i < 5; i++){
            this.setValDe(i, 0);
        }
    }
    
    public void set_nb_Lances(){
        int nb = this.getLancesRestants();
        String lances = new String("Reste ");
        lances += String.valueOf(nb);
        lances += " lancers.";
        
        this._nbLancers.setText(lances);
    }
    
    public int getLancesRestants(){
        return this._lancesRestants;
    }
    
    public void setJoueurs(String[] joueurs){
        this._nomsJoueurs = new String[joueurs.length];
        for(int i = 0; i < joueurs.length; i++){
            Joueur j = new Joueur(joueurs[i]);
            this._tabModel.addJoueur(j);
            this._nomsJoueurs[i] = joueurs[i];
        }
    }
    
    public void setScore(int joueur, int index, int score){
        this._tabModel.setScoreJoueur(joueur, index, score);
        this._tableau.updateUI();
    }
    
    public boolean[] getSelectedDes(){
        return this._selDes;
    }
    
    public void setTour(int tour){
        this._tour = tour;
        this.setAQui(this._tour);
    }
}
