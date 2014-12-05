/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import java.awt.*;
import javax.swing.*;
import yams.control.YamControl;
import yams.events.YamEvents;
import yams.events.mouseEvents.*;
import yams.pojos.Joueur;
import yams.table.ColorTab;
import yams.table.ModeleTableScore;

/**
 *
 * @author nicolas
 */


/*
 * Fenêtre principale du jeu.
 * Elle contient les dés et le tableau des scores
 */
public class JeuVue extends JFrame {
    private Icon[] _des;
    private Icon[]  _delSelect;
    private Icon[] _desUnSelect;
    private JLabel _aQui;
    private JLabel[] _labDes;
    private JLabel _nbLancers;
    private JButton _btnFinTour;
    private JButton _btnLancer;
    private JLabel _labTotalPoints;
    private JLabel _labPointsConserves;
    private JLabel _labCoupsRestants;
    private JLabel _labSound;
    private JMenuItem _mSound;
    private Icon[] _iSounds;
    
    private int[] _valDes;
    private boolean[] _selDes;
    private String[] _nomsJoueurs;
    private int _tour;
    private int _lancesRestants;
    private boolean _sound;
    
    private JTable _tableau;
    private ModeleTableScore _tabModel;
    private ColorTab _gestionnaire;
    private YamControl _myControler;
    
    public JeuVue(int nbJoueurs, String[] noms, int tour, YamControl yc, int mode, boolean sound){
        //prise en compte du mode de jeu
        if(mode == 0)
        {
            this.setTitle("Jeu du Yam's Libre");
        }
        else if(mode == 1){
            this.setTitle("Jeu du Yam's Montant");
        }
        else if(mode == 2){
            this.setTitle("Jeu du Yam's Descendant");
        }
        
        //sauvegarde du contrôleur
        _myControler = yc;
        
        //initialisations des préférences
        this._sound = sound;
        
        //initialisation des images des dés et de la couleur de fond
        this._delSelect = new Icon[6];
        this._delSelect[0] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/1_sel.png"));
        this._delSelect[1] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/2_sel.png"));
        this._delSelect[2] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/3_sel.png"));
        this._delSelect[3] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/4_sel.png"));
        this._delSelect[4] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/5_sel.png"));
        this._delSelect[5] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/6_sel.png"));
        
        this._desUnSelect = new Icon[6];
        this._desUnSelect[0] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/1_unsel.png"));
        this._desUnSelect[1] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/2_unsel.png"));
        this._desUnSelect[2] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/3_unsel.png"));
        this._desUnSelect[3] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/4_unsel.png"));
        this._desUnSelect[4] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/5_unsel.png"));
        this._desUnSelect[5] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/6_unsel.png"));
        
        this._des = new Icon[7];
        this._des[0] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/indef.png"));
        this._des[1] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/1.png"));
        this._des[2] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/2.png"));
        this._des[3] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/3.png"));
        this._des[4] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/4.png"));
        this._des[5] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/5.png"));
        this._des[6] = new ImageIcon(getClass().getResource("/yams/resources/images/dés/6.png"));
        this._labDes = new JLabel[5];
        Color couleur = new Color(43, 133, 53);
        for(int i = 0; i < 5; i++){
            this._labDes[i] = new JLabel();
            switch(i){
                case 0:
                    this._labDes[i].addMouseListener(new YamMouseEvent1(this));
                    break;
                case 1:
                    this._labDes[i].addMouseListener(new YamMouseEvent2(this));
                    break;
                case 2:
                    this._labDes[i].addMouseListener(new YamMouseEvent3(this));
                    break;
                case 3:
                    this._labDes[i].addMouseListener(new YamMouseEvent4(this));
                    break;
                case 4:
                    this._labDes[i].addMouseListener(new YamMouseEvent5(this));
                    break;
                default: //n'arrivera jamais
                    System.err.println("ERREUR: mauvais ID de label de dé");
                    break;
            }
        }
        
        //initialisation du tableau des scores
        this._tabModel = new ModeleTableScore(nbJoueurs, this._myControler.getPrefs().get(YamControl.PREFRULES));
        this.setJoueurs(noms);
        this._tableau = new JTable(_tabModel);
        this._tableau.setName("Tableau des scores");
        this._tableau.setFocusable(false);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
        this._tableau.setFont(font);
        this._tableau.setGridColor(Color.black);
//        this._tableau.getTableHeader().setDefaultRenderer(new HeaderRenderer());
        
        //initialisation du tableau des couleurs du tableau des scores
        int[][] colorTab = new int[nbJoueurs][18];
        for(int i = 0; i < nbJoueurs; i++){
            for(int j = 0; j < 18; j++){
                if(this._myControler.getPrefs().get(YamControl.PREFRULES) && ((j == 0) || (j == 7) || (j == 8) || (j == 9) || (j == 12) || (j == 17))){
                    colorTab[i][j] = 2;
                }
                else if(((j == 0) || (j == 7) || (j == 8) || (j == 9) || (j == 17))){
                    colorTab[i][j] = 2;
                }
                else{
                    colorTab[i][j] = 0;
                }
            }
        }
        
        //initialisation du gestionnaire de couleurs
        this._gestionnaire = new ColorTab(colorTab, nbJoueurs);
        
        //liaison du tableau avec son gestionnaire de couleur
        this._tableau.setDefaultRenderer(Object.class, this._gestionnaire);
        this._tableau.updateUI();
        
        //initialisation du tour, des valeurs et des sélections des dés
        this._tour = tour;
        this._valDes = new int[5];
        this._selDes = new boolean[5];
        this._aQui = new JLabel();
        this._aQui.setHorizontalAlignment(JLabel.CENTER);
        this._aQui.setForeground(Color.WHITE);
        this._aQui.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        for(int i = 0; i < 5; i++){
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
        panJeu.setBackground(couleur);
        
        JPanel panDes = new JPanel(new GridLayout(5, 1, 0, 5));
        
        for(int i = 0; i < 5; i++)
        {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBackground(couleur);
            panel.add(this._labDes[i]);
            panDes.add(panel);
        }
        panDes.setBackground(couleur);
        
        panJeu.add(panDes, BorderLayout.CENTER);
        panJeu.setBackground(couleur);
        
        //emplacement des dés
        JPanel panLancement = new JPanel(new GridLayout(4, 1));
        _btnLancer = new JButton("Lancer");
        _btnLancer.addActionListener(new YamEvents(_myControler));
        _btnLancer.setActionCommand("lancer");
        this._nbLancers = new JLabel();
        this._nbLancers.setHorizontalAlignment(JLabel.CENTER);
        this._nbLancers.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        this.setNbLancers(3);
        _btnFinTour = new JButton("Fin du Tour");
        _btnFinTour.addActionListener(new YamEvents(_myControler));
        _btnFinTour.setActionCommand("finTour");
        this.setEnabledFinTour(false);
        _labTotalPoints = new JLabel("0 points");
        this._labPointsConserves = new JLabel();
        this.setPointsSelect();
        JPanel panBtnLancement = new JPanel(new FlowLayout());
        panBtnLancement.add(_btnLancer);
        panBtnLancement.add(this._btnFinTour);
        panBtnLancement.setBackground(couleur);
        panLancement.add(panBtnLancement);
        panLancement.add(this._labTotalPoints);
        panLancement.add(this._labPointsConserves);
        panLancement.add(this._nbLancers);
        this._nbLancers.setForeground(Color.WHITE);
        this._labTotalPoints.setForeground(Color.WHITE);
        this._labTotalPoints.setHorizontalAlignment(JLabel.CENTER);
        this._labPointsConserves.setForeground(Color.WHITE);
        this._labPointsConserves.setHorizontalAlignment(JLabel.CENTER);
        panLancement.setBackground(couleur);
        panJeu.add(panLancement, BorderLayout.SOUTH);
        
        //barre des menus
        JMenuBar barre = new MyMenuBar(this._myControler, "jeu");
        this.setJMenuBar(barre);
        
        //bouton du son 
        JPanel panBtnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panBtnBar.setBackground(couleur);
        this._iSounds = new Icon[2];
        this._iSounds[0] = new ImageIcon(getClass().getResource("/yams/resources/images/sound/soundOff.png"));
        this._iSounds[1] = new ImageIcon(getClass().getResource("/yams/resources/images/sound/soundOn.png"));
        this._labSound = new JLabel();
        this._labSound.addMouseListener(new YamSoundEvent(this._myControler));
        this.majSound(this._sound);
        panBtnBar.add(this._labSound);
        
        //label des couts restants
        this._labCoupsRestants = new JLabel();
        this._labCoupsRestants.setHorizontalAlignment(JLabel.CENTER);
        this._labCoupsRestants.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        
        //assemblage des éléments de la fenêtre
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.add(panBtnBar, BorderLayout.NORTH);
        pan.add(panJeu, BorderLayout.WEST);
        JScrollPane spScores = new JScrollPane(this._tableau);
        spScores.setPreferredSize(this._tableau.getPreferredSize());
        pan.add(spScores, BorderLayout.CENTER);
        pan.add(this._labCoupsRestants, BorderLayout.SOUTH);
        this._labCoupsRestants.setForeground(Color.WHITE);
        pan.setBackground(couleur);
        
        //mise en place des détails de la fenêtre
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Affichage de la fenêtre
     */
    public void affichage(boolean enable){
        if(enable){
            this._myControler.setActualWindow("Jeu");
        }
        this.setVisible(enable);
    }
    
    /*
     * Permet la mise à jour de l'icone du son
     */
    public final void majSound(boolean init){
        this._sound = init;
            
        if(this._sound){
            this._labSound.setIcon(this._iSounds[1]);
        }else{
            this._labSound.setIcon(this._iSounds[0]);
        }
    }
    
    /*
     * Prend le numéro du joueur en paramètre et retourne le joueur correspondant
     */
    public Joueur getJoueur(int index){
        return this._tabModel.getJoueur(index);
    }
    
    /*
     * Met à jour la couleur du tableau suivant les index donnés
     */
    public void majColorTab(int joueur, int index, int type){
        this._gestionnaire.setCouleurs(joueur, index, type);
    }
    
    /*
     * Met de coté les dés à conserver via le tableau de booléens _selDes
     */
    public void majSelDes(int index){
        if(this._valDes[index] == 0){
            System.err.println("Erreur: Dé non lancé!!!");
        }
        else {
            this._selDes[index] = !_selDes[index];
            if(this._selDes[index]){
                if(this._myControler.getPrefs().get(YamControl.PREFSELECT)){
                    this._labDes[index].setIcon(this._delSelect[this._valDes[index]-1]);
                }
                else{
                    this._labDes[index].setIcon(this._desUnSelect[this._valDes[index]-1]);
                    
                }
            }
            else{
                this._labDes[index].setIcon(this._des[this._valDes[index]]);
            }
            this.setPointsSelect();
            this._myControler.checkDes();
        }
    }
    
    /*
     * Prend la liste des coups restants en paramètres et permet son affichage
     */
    public void majCoupsRestants(String coups){
        String texte = "Coups restants: ";
        texte += coups;
        this._labCoupsRestants.setText(texte);
    }
    
    /*
     * Permet la mise à jour des affichages des dés lorsque les dés sélectionnés sont conservés
     */
    private void majDes(int index){
        this._labDes[index].setIcon(this._des[this._valDes[index]]);
        this.getContentPane().repaint();
    }
    
    /*
     * Permet la mise à jour des affichages des dés lorsque les dés sélectionnés sont relancés
     */
    private void majDesSelect(int index){
        this._labDes[index].setIcon(this._delSelect[this._valDes[index]-1]);
        this.getContentPane().repaint();
    }
    
    /*
     * Permet la mise à jour de la valeur d'un dé
     */
    public void setValDe(int index, int val, boolean init){
        this._valDes[index] = val;
        if(init){
            this.majDes(index);
        }
        else{
            boolean garde = this._myControler.getPrefs().get(YamControl.PREFSELECT);
            this.majDes(index);
            if(!garde){
                this._selDes[index] = false;
            }
        }
    }
    
    /*
     * Retourne la valeur des dés
     */
    public int[] getDes(){
        return this._valDes;
    }
    
    /*
     * calcule le nombre de points tirés aux dés
     */
    public void setTotalPoints(boolean init){
        int somme = 0;
        String texte;
        if(!init){
            for(int i = 0; i < 5; i++){
                somme += this._valDes[i];
            }
        }
        
        texte = String.valueOf(somme);
        texte += " points";
        
        this._labTotalPoints.setText(texte);
        this._labTotalPoints.setForeground(Color.WHITE);
        
        if((!init) && (!this._myControler.getPrefs().get(YamControl.PREFSELECT))){
            texte = "(";
            texte += String.valueOf(somme);
            texte += " points conservés)";
            this._labPointsConserves.setText(texte);
        }
    }
    
    /*
     * Calcule les points des dés sélectionnés
     */
    private void setPointsSelect(){
        int score = 0;
        String texte;
        
        for(int i = 0; i < 5; i++){
            if(this._selDes[i] == this._myControler.getPrefs().get(YamControl.PREFSELECT)){
                score += this._valDes[i];
            }
        }
        
        texte = "(";
        texte += String.valueOf(score);
        texte += " points conservés)";
        
        this._labPointsConserves.setText(texte);
    }
    
    /*
     * Permet l'activation/désactivation du bouton "Fin du tour"
     */
    public final void setEnabledFinTour(boolean enable){
        this._btnFinTour.setEnabled(enable);
    }
    
    /*
     * Permet l'activation/désactivation du bouton "lancer"
     */
    public void setEnabledLancer(boolean enable){
        this._btnLancer.setEnabled(enable);
    }
    
    /*
     * Permet d'afficher le tour du joueur
     */
    private void setAQui(int index){
        String tour = "Tour de: ";
        String nom = this._nomsJoueurs[index];
            tour += nom;
        this._aQui.setText(tour);
    }
    
    /*
     * Permet d'afficher le nombre de lancers restant avant la fin du tour
     */
    public final void setNbLancers(int nb){
        String lancer = "Reste ";
        
        lancer += String.valueOf(nb);
        lancer += " lancers";
        this._nbLancers.setText(lancer);
        this._lancesRestants = nb;
    }
    
    /*
     * Permet la mise à jour de l'affichage des dés
     */
    public final void refreshDes(){
        for(int i = 0; i < 5; i++){
            _labDes[i].setIcon(_des[_valDes[i]]);
        }
    }
    
    /*
     * Remet à zéro les compteurs de point des dés
     */
    public void initScoreTour(){
        this.setPointsSelect();
        this.setTotalPoints(true);
    }
    
    /*
     * Permet l'initialisation des cases à cocher et l'affichage des dés au début de chaque tour
     */
    public void initDes(){
        for(int i = 0; i < 5; i++){
            this._selDes[i] = false;
        }
        for(int i = 0; i < 5; i++){
            this.setValDe(i, 0, true);
        }
    }
    /*
     * met à jour l'affichage du nombrre de lancés restants
     */
    public void set_nb_Lances(){
        int nb = this.getLancesRestants();
        String lances = "Reste ";
        lances += String.valueOf(nb);
        lances += " lancers.";
        
        this._nbLancers.setText(lances);
    }
    /*
     * retourne le nombre de lancés restants
     */
    public int getLancesRestants(){
        return this._lancesRestants;
    }
    
    /*
     * Ajoute les joueurs au tableau des scores
     */
    public final void setJoueurs(String[] joueurs){
        this._nomsJoueurs = new String[joueurs.length];
        for(int i = 0; i < joueurs.length; i++){
            Joueur j = new Joueur(joueurs[i]);
            this._tabModel.addJoueur(j);
            this._nomsJoueurs[i] = joueurs[i];
        }
    }
    
    /*
     * Met à jour le tableau des scores
     */
    public void setScore(int joueur, int index, int score){
        this._tabModel.setScoreJoueur(joueur, index, score);
        this._tableau.updateUI();
    }
    
    /*
     * Retourne le tableau de la séléction des dés
     */
    public boolean[] getSelectedDes(){
        return this._selDes;
    }
    
    /*
     * Définit à qui est le tour
     */
    public void setTour(int tour){
        this._tour = tour;
        this.setAQui(this._tour);
    }
}
