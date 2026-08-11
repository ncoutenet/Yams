/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.pojos.MyMenuBar;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import yams.ModeJeu;
import yams.control.YamControl;
import yams.events.NbJoueursEvents;
import yams.events.YamEvents;
import yams.events.mouseevents.YamSoundEvent;
import yams.pojos.LimitedTextField;

/**
 *
 * @author nicolas
 */

/*
 * Fenêtrre permettant de choisir le mode de jeu, le nombre de joueurs et leur pseudo
 */
public class ConnexionVue extends JFrame{
    
    private int val = 1;
    private int min = 1;
    private int max = 10;
    private Color couleur;
    private Integer oldVal;
    
    private boolean sound;
    private transient Icon[] iSounds;
    private JLabel labSound;
    
    private transient YamControl myControler;
    private JSpinner spinner;
    private List<JTextField> joueurs;
    private JPanel panJoueurs;
    private JComboBox<ModeJeu> cbModeJeu;
    
    public ConnexionVue(YamControl yc, boolean sound){
        super("Yam's");
        super.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        myControler = yc;
        //initialisation du conteneur principal
        Container pan = this.getContentPane();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        
        this.setJMenuBar(new MyMenuBar(this.myControler, "connexion"));
        
        //initialisation des variables internes
        this.oldVal = 0;
        this.joueurs = new ArrayList<JTextField>();
        
        //initialisations des préférences
        this.sound = sound;
        
        //initialisation de la couleur de fond
        couleur = new Color(43, 133, 53);
        pan.setBackground(couleur);
        
        //instanciation du menu déroulant
        this.cbModeJeu = new JComboBox<>(ModeJeu.values());
        JLabel labModes = new JLabel("Mode de jeu: ");
        labModes.setForeground(Color.WHITE);
        
        //création du bouton pour le son
        this.iSounds = new Icon[2];
        this.iSounds[0] = new ImageIcon(getClass().getResource("/images/sound/soundOff.png"));
        this.iSounds[1] = new ImageIcon(getClass().getResource("/images/sound/soundOn.png"));
        this.labSound = new JLabel();
        this.labSound.addMouseListener(new YamSoundEvent(this.myControler));
        this.majSound(this.sound);
        
        //assemblage 
        JPanel panSound = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panSound.add(this.labSound);
        panSound.setBackground(couleur);
        pan.add(panSound);
        JPanel panModesJeu = new JPanel(new FlowLayout());
        panModesJeu.add(labModes);
        panModesJeu.add(this.cbModeJeu);
        panModesJeu.setBackground(couleur);
        pan.add(panModesJeu);
        
        //création du message pour le choix du nombre de joueurs
        JLabel labMessage = new JLabel("Veuillez choisir le nombre de joueurs (" + max + " joueurs max) et indiquer leurs noms");
        labMessage.setForeground(Color.WHITE);
        labMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        //assemblage
        pan.add(labMessage);
        
        //création du label du nombre de joueurs
        JPanel panNb = new JPanel(new FlowLayout());
        panNb.setBackground(couleur);
        JLabel labNbJoueurs = new JLabel("Nombre de joueurs:");
        labNbJoueurs.setForeground(Color.WHITE);
        panNb.add(labNbJoueurs);
        //création de la boite du choix du nombre de joueurs
        SpinnerModel model = new SpinnerNumberModel(val, min, max, 1);
        spinner = new JSpinner(model);
        spinner.addChangeListener(new NbJoueursEvents(myControler));
        panNb.add(spinner);
        //assemblage
        pan.add(panNb);
        
        //initialisation de l'emplacement des joueurs choisis
        panJoueurs = new JPanel(new GridLayout(max, 1));
        panJoueurs.setBackground(couleur);
        this.setJoueurs();
        //assemblage
        pan.add(panJoueurs);
        
        JButton btnValider = new JButton("Commencer");
        btnValider.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnValider.addActionListener(new YamEvents(myControler));
        btnValider.setActionCommand("commencer");
        pan.add(btnValider);
        
        //compactage et placement dans l'écran
        this.pack();
        this.setLocationRelativeTo(this.getParent());
        
    }
    
    /*
     * Permet l'affichage/masquage de la fenêtre
     */
    public void affichage(boolean enable){
        if(enable){
            this.myControler.setActualWindow("Connexion");
        }
        this.setVisible(enable);
    }
    
    /*
     * Permet la mise à jour de l'icone du son
     */
    public void majSound(boolean init){
        this.sound = init;
            
        if(this.sound){
            this.labSound.setIcon(this.iSounds[1]);
        }else{
            this.labSound.setIcon(this.iSounds[0]);
        }
    }
    
    /*
     * Ajout d'un joueur, cette fonction permet de conserver les pseudos des joueurs précédants
     */
    private void addJoueurs(){
        JPanel panel;
        JLabel label;
        LimitedTextField textField;

        for(Integer i = this.oldVal; i < (Integer)this.spinner.getValue(); i++){
            StringBuilder string = new StringBuilder("Joueur ");
            string.append(String.valueOf(i+1));
            textField = new LimitedTextField(9);
            textField.setColumns(5);
            panel = new JPanel(new FlowLayout());
            panel.setBackground(couleur);
            label = new JLabel();

            textField.setText(string.toString());

            this.joueurs.add(textField);

            string.append(":");
            label.setText(string.toString());
            label.setForeground(Color.WHITE);

            panel.add(label);
            panel.add(this.joueurs.get(i));
            this.panJoueurs.add(panel);
        }
    }
    
    /*
     * Suppression d'un joueur
     */
    private void delJoueurs(){
        for(Integer i = this.oldVal; i > (Integer)this.spinner.getValue(); i--){
            this.panJoueurs.remove(i-1);
            this.joueurs.remove(i-1);
        }
    }
    
    /*
     * Deffinition du nombre de joueurs
     */
    public final void setJoueurs(){
        if(this.oldVal < (Integer)this.spinner.getValue()){
            this.addJoueurs();
        }
        else if(this.oldVal > (Integer)this.spinner.getValue()){
            this.delJoueurs();
        }
        
        this.oldVal = (Integer)this.spinner.getValue();
        panJoueurs.updateUI();
    }
    
    /*
     * Retourne les pseudos des joueurs
     */
    public String[] getNomsJoueurs(){
        String[] noms = new String[joueurs.size()];
        for(int i = 0; i < joueurs.size(); i++){
            noms[i] = joueurs.get(i).getText();
        }
        return noms;
    }
    
    /*
     * Retourne le nombre de joueurs
     */
    public int getNbJoueurs(){
        return joueurs.size();
    }
    
    /*
     * Retourne le code du mode de jeu
     */
    public ModeJeu getModeJeu(){
        return (ModeJeu) this.cbModeJeu.getSelectedItem();
    }
}
