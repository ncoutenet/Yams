/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.pojos.MyMenuBar;
import java.awt.*;
import java.util.Random;
import java.util.logging.Logger;
import javax.swing.*;
import yams.Yams;
import yams.control.YamControl;
import yams.events.GameWindowStateListener;
import yams.events.YamEvents;
import yams.events.mouseevents.*;
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
    private static final Logger LOGGER = Logger.getLogger(JeuVue.class.getName());
    private static final Random RANDOM = new Random();
    private ImageIcon[] des;
    private ImageIcon[]  delSelect;
    private ImageIcon[] desUnSelect;
    private JLabel aQui;
    private JLabel[] labDes;
    private JLabel nbLancers;
    private JButton btnFinTour;
    private JButton btnLancer;
    private JLabel labTotalPoints;
    private JLabel labPointsConserves;
    private JLabel labCoupsRestants;
    private JLabel labSound;
    private transient Icon[] iSounds;
    private double normalHeight;
    
    private int[] valDes;
    private boolean[] selDes;
    private String[] nomsJoueurs;
    private int tour;
    private int lancesRestants;
    private boolean sound;
    private boolean animationEnCours;
    
    private JTable tableau;
    private ModeleTableScore tabModel;
    private ColorTab gestionnaire;
    private transient YamControl myControler;

    private void setTitreMode(int mode){
        if(mode == Yams.MODELIBRE)
        {
            this.setTitle("Jeu du Yam's Libre");
        }
        else if(mode == Yams.MODEMONTANT){
            this.setTitle("Jeu du Yam's Montant");
        }
        else if(mode == Yams.MODEDESCENDANT){
            this.setTitle("Jeu du Yam's Descendant");
        }
    }

    private void initImagesDes(){
        this.delSelect = new ImageIcon[6];
        this.delSelect[0] = new ImageIcon(getClass().getResource("/images/dés/select/1.png"));
        this.delSelect[1] = new ImageIcon(getClass().getResource("/images/dés/select/2.png"));
        this.delSelect[2] = new ImageIcon(getClass().getResource("/images/dés/select/3.png"));
        this.delSelect[3] = new ImageIcon(getClass().getResource("/images/dés/select/4.png"));
        this.delSelect[4] = new ImageIcon(getClass().getResource("/images/dés/select/5.png"));
        this.delSelect[5] = new ImageIcon(getClass().getResource("/images/dés/select/6.png"));

        this.desUnSelect = new ImageIcon[6];
        this.desUnSelect[0] = new ImageIcon(getClass().getResource("/images/dés/unselect/1.png"));
        this.desUnSelect[1] = new ImageIcon(getClass().getResource("/images/dés/unselect/2.png"));
        this.desUnSelect[2] = new ImageIcon(getClass().getResource("/images/dés/unselect/3.png"));
        this.desUnSelect[3] = new ImageIcon(getClass().getResource("/images/dés/unselect/4.png"));
        this.desUnSelect[4] = new ImageIcon(getClass().getResource("/images/dés/unselect/5.png"));
        this.desUnSelect[5] = new ImageIcon(getClass().getResource("/images/dés/unselect/6.png"));

        this.des = new ImageIcon[7];
        this.des[0] = new ImageIcon(getClass().getResource("/images/dés/normal/indef.png"));
        this.des[1] = new ImageIcon(getClass().getResource("/images/dés/normal/1.png"));
        this.des[2] = new ImageIcon(getClass().getResource("/images/dés/normal/2.png"));
        this.des[3] = new ImageIcon(getClass().getResource("/images/dés/normal/3.png"));
        this.des[4] = new ImageIcon(getClass().getResource("/images/dés/normal/4.png"));
        this.des[5] = new ImageIcon(getClass().getResource("/images/dés/normal/5.png"));
        this.des[6] = new ImageIcon(getClass().getResource("/images/dés/normal/6.png"));
    }

    private void initLabelsDes(){
        this.labDes = new JLabel[5];
        for(int i = 0; i < 5; i++){
            this.labDes[i] = new JLabel();
            switch(i){
                case 0:
                    this.labDes[i].addMouseListener(new YamMouseEvent1(this));
                    break;
                case 1:
                    this.labDes[i].addMouseListener(new YamMouseEvent2(this));
                    break;
                case 2:
                    this.labDes[i].addMouseListener(new YamMouseEvent3(this));
                    break;
                case 3:
                    this.labDes[i].addMouseListener(new YamMouseEvent4(this));
                    break;
                case 4:
                    this.labDes[i].addMouseListener(new YamMouseEvent5(this));
                    break;
                default: //n'arrivera jamais
                    LOGGER.warning("ERREUR: mauvais ID de label de dé");
                    break;
            }
        }
    }

    private void initCouleursTableau(int nbJoueurs, boolean rules){
        for(int i = 0; i < nbJoueurs; i++){
            for(int j = 0; j < 18; j++){
                if(rules && ((j == 0) || (j == 7) || (j == 8) || (j == 9) || (j == 12) || (j == 17))){
                    this.gestionnaire.setCouleurs(i, j, ColorTab.GRIS);
                }
                else if(((j == 0) || (j == 7) || (j == 8) || (j == 9) || (j == 17))){
                    this.gestionnaire.setCouleurs(i, j, ColorTab.GRIS);
                }
                else{
                    this.gestionnaire.setCouleurs(i, j, ColorTab.BLANC);
                }
            }
        }
    }

    public JeuVue(int nbJoueurs, String[] noms, int tour, YamControl yc, int mode, boolean sound){
        //prise en compte du mode de jeu
        this.setTitreMode(mode);

        //sauvegarde du contrôleur
        myControler = yc;

        //initialisations des préférences
        this.sound = sound;

        //initialisation des images des dés et de la couleur de fond
        this.initImagesDes();
        Color couleur = new Color(43, 133, 53);
        this.initLabelsDes();

        //initialisation du tableau des scores
        this.tabModel = new ModeleTableScore(this.myControler.getPrefs().get(Yams.PREFRULES));
        this.setJoueurs(noms);
        this.tableau = new JTable(tabModel);
        this.tableau.setName("Tableau des scores");
        this.tableau.setFocusable(false);
        Font font = new Font(Font.DIALOG, Font.PLAIN, 15);
        this.tableau.setFont(font);
        this.tableau.setGridColor(Color.black);

        //initialisation du gestionnaire de couleurs
        this.gestionnaire = new ColorTab(nbJoueurs, 18);
        boolean rules = this.myControler.getPrefs().get(Yams.PREFRULES);
        this.initCouleursTableau(nbJoueurs, rules);

        //liaison du tableau avec son gestionnaire de couleur
        this.tableau.setDefaultRenderer(Object.class, this.gestionnaire);
        this.tableau.updateUI();
        
        //initialisation du tour, des valeurs et des sélections des dés
        this.tour = tour;
        this.valDes = new int[5];
        this.selDes = new boolean[5];
        this.aQui = new JLabel();
        this.aQui.setHorizontalAlignment(SwingConstants.CENTER);
        this.aQui.setForeground(Color.WHITE);
        this.aQui.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        for(int i = 0; i < 5; i++){
            selDes[i] = false;
            valDes[i] = 0;
        }
        this.setAQui(this.tour);
        this.refreshDes();
        
        //initialisation des variables locales restantes
        this.lancesRestants = 3;
        
        //fabrication de la fenêtre
        JPanel panJeu = new JPanel(new BorderLayout());
        panJeu.add(aQui, BorderLayout.NORTH);
        panJeu.setBackground(couleur);
        
        JPanel panDes = new JPanel(new GridLayout(5, 1, 0, 5));
        
        for(int i = 0; i < 5; i++)
        {
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBackground(couleur);
            panel.add(this.labDes[i]);
            panDes.add(panel);
        }
        panDes.setBackground(couleur);
        
        panJeu.add(panDes, BorderLayout.CENTER);
        panJeu.setBackground(couleur);
        
        //emplacement des dés
        JPanel panLancement = new JPanel(new GridLayout(4, 1));
        btnLancer = new JButton("Lancer");
        btnLancer.addActionListener(new YamEvents(myControler));
        btnLancer.setActionCommand("lancer");
        this.nbLancers = new JLabel();
        this.nbLancers.setHorizontalAlignment(SwingConstants.CENTER);
        this.nbLancers.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        this.setNbLancers(3);
        btnFinTour = new JButton("Fin du Tour");
        btnFinTour.addActionListener(new YamEvents(myControler));
        btnFinTour.setActionCommand("finTour");
        this.setEnabledFinTour(false);
        labTotalPoints = new JLabel("0 points");
        this.labPointsConserves = new JLabel();
        this.setPointsSelect();
        JPanel panBtnLancement = new JPanel(new FlowLayout());
        panBtnLancement.add(btnLancer);
        panBtnLancement.add(this.btnFinTour);
        panBtnLancement.setBackground(couleur);
        panLancement.add(panBtnLancement);
        panLancement.add(this.labTotalPoints);
        panLancement.add(this.labPointsConserves);
        panLancement.add(this.nbLancers);
        this.nbLancers.setForeground(Color.WHITE);
        this.labTotalPoints.setForeground(Color.WHITE);
        this.labTotalPoints.setHorizontalAlignment(SwingConstants.CENTER);
        this.labPointsConserves.setForeground(Color.WHITE);
        this.labPointsConserves.setHorizontalAlignment(SwingConstants.CENTER);
        panLancement.setBackground(couleur);
        panJeu.add(panLancement, BorderLayout.SOUTH);
        
        //barre des menus
        JMenuBar barre = new MyMenuBar(this.myControler, "jeu");
        this.setJMenuBar(barre);
        
        //bouton du son 
        JPanel panBtnBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panBtnBar.setBackground(couleur);
        this.iSounds = new Icon[2];
        this.iSounds[0] = new ImageIcon(getClass().getResource("/images/sound/soundOff.png"));
        this.iSounds[1] = new ImageIcon(getClass().getResource("/images/sound/soundOn.png"));
        this.labSound = new JLabel();
        this.labSound.addMouseListener(new YamSoundEvent(this.myControler));
        this.majSound(this.sound);
        panBtnBar.add(this.labSound);
        
        //label des couts restants
        this.labCoupsRestants = new JLabel();
        this.labCoupsRestants.setHorizontalAlignment(SwingConstants.CENTER);
        this.labCoupsRestants.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        
        //assemblage des éléments de la fenêtre
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.add(panBtnBar, BorderLayout.NORTH);
        pan.add(panJeu, BorderLayout.WEST);
        JScrollPane spScores = new JScrollPane(this.tableau);
        spScores.setPreferredSize(this.tableau.getPreferredSize());
        pan.add(spScores, BorderLayout.CENTER);
        pan.add(this.labCoupsRestants, BorderLayout.SOUTH);
        this.labCoupsRestants.setForeground(Color.WHITE);
        pan.setBackground(couleur);
        
        //mise en place des détails de la fenêtre
        this.pack();
        this.normalHeight = this.getBounds().getHeight();
        this.addWindowStateListener(new GameWindowStateListener(this.myControler));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Affichage de la fenêtre
     */
    public void affichage(boolean enable){
        if(enable){
            this.myControler.setActualWindow("Jeu");
        }
        this.setVisible(enable);
    }
    
    /*
     * Permet la mise à jour de l'icone du son
     */
    public final void majSound(boolean init){
        this.sound = init;
            
        if(this.sound){
            this.labSound.setIcon(this.iSounds[1]);
        }else{
            this.labSound.setIcon(this.iSounds[0]);
        }
    }
    
    /*
     * Prend le numéro du joueur en paramètre et retourne le joueur correspondant
     */
    public Joueur getJoueur(int index){
        return this.tabModel.getJoueur(index);
    }
    
    /*
     * Met à jour la couleur du tableau suivant les index donnés
     */
    public void majColorTab(int joueur, int index, int type){
        this.gestionnaire.setCouleurs(joueur, index, type);
    }
    
    /*
     * Met de coté les dés à conserver via le tableau de booléens selDes
     */
    public void majSelDes(int index){
        if(this.animationEnCours){
            return;
        }
        if(this.valDes[index] == 0){
            LOGGER.warning("Erreur: Dé non lancé!!!");
        }
        else {
            this.selDes[index] = !selDes[index];
            if(this.selDes[index]){
                boolean garde = this.myControler.getPrefs().get(Yams.PREFSELECT);
                if(garde){
                    this.labDes[index].setIcon(this.delSelect[this.valDes[index]-1]);
                }
                else{
                    this.labDes[index].setIcon(this.desUnSelect[this.valDes[index]-1]);
                    
                }
            }
            else{
                this.labDes[index].setIcon(this.des[this.valDes[index]]);
            }
            this.setPointsSelect();
            this.myControler.checkDes();
        }
    }
    
    /*
     * Prend la liste des coups restants en paramètres et permet son affichage
     */
    public void majCoupsRestants(String coups){
        String texte = "Coups restants: ";
        texte += coups;
        this.labCoupsRestants.setText(texte);
    }
    
    /*
     * Permet la mise à jour des affichages des dés lorsque les dés sélectionnés sont conservés
     */
    private void majDes(int index){
        this.labDes[index].setIcon(this.des[this.valDes[index]]);
        this.getContentPane().repaint();
    }
    
    /*
     * Permet la mise à jour de la valeur d'un dé
     */
    public void setValDe(int index, int val, boolean init){
        this.valDes[index] = val;
        if(init){
            this.majDes(index);
        }
        else{
            boolean garde = this.myControler.getPrefs().get(Yams.PREFSELECT);
            this.majDes(index);
            if(!garde){
                this.selDes[index] = false;
            }
        }
    }
    
    /*
     * Retourne la valeur des dés
     */
    public int[] getDes(){
        return this.valDes;
    }

    /*
     * Affiche une face aléatoire sur les dés concernés (utilisé pendant l'animation de lancer)
     */
    public void afficherFacesAleatoires(boolean[] aAnimer){
        for(int i = 0; i < 5; i++){
            if(aAnimer[i]){
                this.labDes[i].setIcon(this.des[1 + RANDOM.nextInt(6)]);
            }
        }
    }

    /*
     * Indique si une animation de lancer est en cours, pour bloquer la sélection des dés
     */
    public void setAnimationEnCours(boolean animationEnCours){
        this.animationEnCours = animationEnCours;
    }
    
    /*
     * calcule le nombre de points tirés aux dés
     */
    public void setTotalPoints(boolean init){
        int somme = 0;
        String texte;
        if(!init){
            for(int i = 0; i < 5; i++){
                somme += this.valDes[i];
            }
        }
        
        texte = String.valueOf(somme);
        texte += " points";
        
        this.labTotalPoints.setText(texte);
        this.labTotalPoints.setForeground(Color.WHITE);
        
        boolean garde = this.myControler.getPrefs().get(Yams.PREFSELECT);
        if((!init) && (!garde)){
            texte = "(";
            texte += String.valueOf(somme);
            texte += " points conservés)";
            this.labPointsConserves.setText(texte);
        }
    }
    
    /*
     * Calcule les points des dés sélectionnés
     */
    private void setPointsSelect(){
        int score = 0;
        String texte;
        boolean garde = this.myControler.getPrefs().get(Yams.PREFSELECT);

        for(int i = 0; i < 5; i++){
            if(this.selDes[i] == garde){
                score += this.valDes[i];
            }
        }
        
        texte = "(";
        texte += String.valueOf(score);
        texte += " points conservés)";
        
        this.labPointsConserves.setText(texte);
    }
    
    /*
     * Permet l'activation/désactivation du bouton "Fin du tour"
     */
    public final void setEnabledFinTour(boolean enable){
        this.btnFinTour.setEnabled(enable);
    }
    
    /*
     * Permet l'activation/désactivation du bouton "lancer"
     */
    public void setEnabledLancer(boolean enable){
        this.btnLancer.setEnabled(enable);
    }
    
    /*
     * Permet d'afficher le tour du joueur
     */
    private void setAQui(int index){
        String texteTour = "Tour de: ";
        String nom = this.nomsJoueurs[index];
            texteTour += nom;
        this.aQui.setText(texteTour);
    }
    
    /*
     * Permet d'afficher le nombre de lancers restant avant la fin du tour
     */
    public final void setNbLancers(int nb){
        String lancer = "Reste ";
        
        lancer += String.valueOf(nb);
        lancer += " lancers";
        this.nbLancers.setText(lancer);
        this.lancesRestants = nb;
    }
    
    /*
     * Permet la mise à jour de l'affichage des dés
     */
    public final void refreshDes(){
        for(int i = 0; i < 5; i++){
            labDes[i].setIcon(des[valDes[i]]);
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
     * Permet l'initialisation et l'affichage des dés au début de chaque tour
     */
    public void initDes(){
        for(int i = 0; i < 5; i++){
            this.selDes[i] = false;
        }
        for(int i = 0; i < 5; i++){
            this.setValDe(i, 0, true);
        }
    }
    /*
     * met à jour l'affichage du nombrre de lancés restants
     */
    public void setNbLances(){
        int nb = this.getLancesRestants();
        String lances = "Reste ";
        lances += String.valueOf(nb);
        lances += " lancers.";
        
        this.nbLancers.setText(lances);
    }
    /*
     * retourne le nombre de lancés restants
     */
    public int getLancesRestants(){
        return this.lancesRestants;
    }
    
    /*
     * Ajoute les joueurs au tableau des scores
     */
    public final void setJoueurs(String[] joueurs){
        this.nomsJoueurs = new String[joueurs.length];
        for(int i = 0; i < joueurs.length; i++){
            Joueur j = new Joueur(joueurs[i], this.myControler.getPrefs().get(Yams.PREFRULES));
            this.tabModel.addJoueur(j);
            this.nomsJoueurs[i] = joueurs[i];
        }
    }
    
    /*
     * Met à jour le tableau des scores
     */
    public void setScore(int joueur, int index, int score){
        this.tabModel.setScoreJoueur(joueur, index, score);
        this.tableau.updateUI();
    }
    
    /*
     * Retourne le tableau de la séléction des dés
     */
    public boolean[] getSelectedDes(){
        return this.selDes;
    }
    
    /*
     * Définit à qui est le tour
     */
    public void setTour(int tour){
        this.tour = tour;
        this.setAQui(this.tour);
    }
    
    /**
     * Redimentionne les dés
     */
    public void redimDices(boolean big){
        if(big){
            double maximizedHeight = this.getBounds().getHeight();
            int coef = (int)maximizedHeight / (int)this.normalHeight;
            coef = coef * 100;
            coef = coef - 15; //correction du coefficient pour un affichage complet sur petit écran
            
            this.des[0] = new ImageIcon(this.des[0].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[1] = new ImageIcon(this.des[1].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[2] = new ImageIcon(this.des[2].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[3] = new ImageIcon(this.des[3].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[4] = new ImageIcon(this.des[4].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[5] = new ImageIcon(this.des[5].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.des[6] = new ImageIcon(this.des[6].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            
            this.delSelect[0] = new ImageIcon(this.delSelect[0].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.delSelect[1] = new ImageIcon(this.delSelect[1].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.delSelect[2] = new ImageIcon(this.delSelect[2].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.delSelect[3] = new ImageIcon(this.delSelect[3].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.delSelect[4] = new ImageIcon(this.delSelect[4].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.delSelect[5] = new ImageIcon(this.delSelect[5].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            
            this.desUnSelect[0] = new ImageIcon(this.desUnSelect[0].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.desUnSelect[1] = new ImageIcon(this.desUnSelect[1].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.desUnSelect[2] = new ImageIcon(this.desUnSelect[2].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.desUnSelect[3] = new ImageIcon(this.desUnSelect[3].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.desUnSelect[4] = new ImageIcon(this.desUnSelect[4].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
            this.desUnSelect[5] = new ImageIcon(this.desUnSelect[5].getImage().getScaledInstance(coef, coef, Image.SCALE_DEFAULT));
        }
        else{
            this.des[0] = new ImageIcon(getClass().getResource("/images/dés/normal/indef.png"));
            this.des[1] = new ImageIcon(getClass().getResource("/images/dés/normal/1.png"));
            this.des[2] = new ImageIcon(getClass().getResource("/images/dés/normal/2.png"));
            this.des[3] = new ImageIcon(getClass().getResource("/images/dés/normal/3.png"));
            this.des[4] = new ImageIcon(getClass().getResource("/images/dés/normal/4.png"));
            this.des[5] = new ImageIcon(getClass().getResource("/images/dés/normal/5.png"));
            this.des[6] = new ImageIcon(getClass().getResource("/images/dés/normal/6.png"));
            
            this.delSelect[0] = new ImageIcon(getClass().getResource("/images/dés/select/1.png"));
            this.delSelect[1] = new ImageIcon(getClass().getResource("/images/dés/select/2.png"));
            this.delSelect[2] = new ImageIcon(getClass().getResource("/images/dés/select/3.png"));
            this.delSelect[3] = new ImageIcon(getClass().getResource("/images/dés/select/4.png"));
            this.delSelect[4] = new ImageIcon(getClass().getResource("/images/dés/select/5.png"));
            this.delSelect[5] = new ImageIcon(getClass().getResource("/images/dés/select/6.png"));
            
            this.desUnSelect[0] = new ImageIcon(getClass().getResource("/images/dés/unselect/1.png"));
            this.desUnSelect[1] = new ImageIcon(getClass().getResource("/images/dés/unselect/2.png"));
            this.desUnSelect[2] = new ImageIcon(getClass().getResource("/images/dés/unselect/3.png"));
            this.desUnSelect[3] = new ImageIcon(getClass().getResource("/images/dés/unselect/4.png"));
            this.desUnSelect[4] = new ImageIcon(getClass().getResource("/images/dés/unselect/5.png"));
            this.desUnSelect[5] = new ImageIcon(getClass().getResource("/images/dés/unselect/6.png"));
        }
        
        for(int i=0; i<5; i++){
            this.majDes(i);
        }
    }
}
