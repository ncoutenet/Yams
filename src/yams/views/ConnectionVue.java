/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.control.YamControl;
import yams.events.NbJoueursEvents;
import yams.events.YamEvents;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import yams.events.mouseEvents.YamSoundEvent;

/**
 *
 * @author nicolas
 */

/*
 * Fenêtrre permettant de choisir le mode de jeu, le nombre de joueurs et leur pseudo
 */
public class ConnectionVue extends JFrame{
    
    private int VAL = 1;
    private int MIN = 1;
    private int MAX = 10;
    private Color COULEUR;
    private Integer _oldVal;
    
    private boolean _sound;
    private Icon[] _iSounds;
    private JLabel _labSound;
    
    private YamControl _myControler;
    private JSpinner _spinner;
    private List<JTextField> _joueurs;
    private JPanel _panJoueurs;
    private JComboBox _cbModeJeu;
    
    public ConnectionVue(YamControl yc, boolean sound){
        super("Yam's");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        _myControler = yc;
        //initialisation du conteneur principal
        Container pan = this.getContentPane();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        
        //initialisation des variables internes
        this._oldVal = new Integer(0);
        this._joueurs = new ArrayList<JTextField>();
        
        //initialisations des préférences
        this._sound = sound;
        
        //initialisation de la couleur de fond
        COULEUR = new Color(43, 133, 53);
        pan.setBackground(COULEUR);
        
        //initialisation du menu déroulant
        Object[] modes = new Object[3];
        modes[0] = new String("Libre");
        modes[1] = new String("Montant");
        modes[2] = new String("Descendant");
        
        //instanciation du menu
        this._cbModeJeu = new JComboBox(modes);
        JLabel labModes = new JLabel("Mode de jeu: ");
        labModes.setForeground(Color.WHITE);
        
        //création du bouton des scores
        JButton btnScores = new JButton("Scores");
        btnScores.setActionCommand("openHightScores");
        btnScores.addActionListener(new YamEvents(_myControler));
        
        //création du bouton des règles
        JButton btnRegles = new JButton("Règles");
        btnRegles.addActionListener(new YamEvents(_myControler));
        btnRegles.setActionCommand("aperçuRegles");
        
        //création du bouton pour le son
        this._iSounds = new Icon[2];
        this._iSounds[0] = new ImageIcon(getClass().getResource("../images/sound/soundOff.png"));
        this._iSounds[1] = new ImageIcon(getClass().getResource("../images/sound/soundOn.png"));
        this._labSound = new JLabel();
        this._labSound.addMouseListener(new YamSoundEvent(this._myControler));
        this.majSound(this._sound);
        
        //assemblage 
        JPanel panModesJeu = new JPanel(new FlowLayout());
        panModesJeu.add(labModes);
        panModesJeu.add(this._cbModeJeu);
        panModesJeu.add(btnScores);
        panModesJeu.add(btnRegles);
        panModesJeu.add(this._labSound);
        panModesJeu.setBackground(COULEUR);
        pan.add(panModesJeu);
        
        //création du message pour le choix du nombre de joueurs
        JLabel labMessage = new JLabel("Veuillez choisir le nombre de joueurs (" + MAX + " joueurs max) et indiquer leurs noms");
        labMessage.setForeground(Color.WHITE);
        labMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        //assemblage
        pan.add(labMessage);
        
        //création du label du nombre de joueurs
        JPanel panNb = new JPanel(new FlowLayout());
        panNb.setBackground(COULEUR);
        JLabel labNbJoueurs = new JLabel("Nombre de joueurs:");
        labNbJoueurs.setForeground(Color.WHITE);
        panNb.add(labNbJoueurs);
        //création de la boite du choix du nombre de joueurs
        SpinnerModel model = new SpinnerNumberModel(VAL, MIN, MAX, 1);
        _spinner = new JSpinner(model);
        _spinner.addChangeListener(new NbJoueursEvents(_myControler));
        panNb.add(_spinner);
        //assemblage
        pan.add(panNb);
        
        //initialisation de l'emplacement des joueurs choisis
        _panJoueurs = new JPanel(new GridLayout(MAX, 1));
        _panJoueurs.setBackground(COULEUR);
        this.setJoueurs();
        //assemblage
        pan.add(_panJoueurs);
        
        JButton btnValider = new JButton("Commencer");
        btnValider.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnValider.addActionListener(new YamEvents(_myControler));
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
        this.setVisible(enable);
    }
    
    /*
     * Permet la mise à jour de l'icone du son
     */
    public void majSound(boolean init){
        this._sound = init;
            
        if(this._sound){
            this._labSound.setIcon(this._iSounds[1]);
        }else{
            this._labSound.setIcon(this._iSounds[0]);
        }
    }
    
    /*
     * Ajout d'un joueur, cette fonction permet de conserver les pseudos des joueurs précédants
     */
    private void addJoueurs(){
        String string;
        JPanel panel;
        JLabel label;
        JTextField textField;
        
        for(Integer i = this._oldVal; i < (Integer)this._spinner.getValue(); i++){
            string = "Joueur ";
            string += String.valueOf(i+1);
            textField = new JTextField(10);
            panel = new JPanel(new FlowLayout());
            panel.setBackground(COULEUR);
            label = new JLabel();
            
            textField.setText(string);
            this._joueurs.add(textField);
            
            string += ":";
            label.setText(string);
            label.setForeground(Color.WHITE);
            
            panel.add(label);
            panel.add(this._joueurs.get(i));
            this._panJoueurs.add(panel);
        }
    }
    
    /*
     * Suppression d'un joueur
     */
    private void delJoueurs(){
        for(Integer i = this._oldVal; i > (Integer)this._spinner.getValue(); i--){
            this._panJoueurs.remove(i-1);
            this._joueurs.remove(i-1);
        }
    }
    
    /*
     * Deffinition du nombre de joueurs
     */
    public final void setJoueurs(){
        if(this._oldVal < (Integer)this._spinner.getValue()){
            this.addJoueurs();
        }
        else if(this._oldVal > (Integer)this._spinner.getValue()){
            this.delJoueurs();
        }
        
        this._oldVal = (Integer)this._spinner.getValue();
        _panJoueurs.updateUI();
    }
    
    /*
     * Retourne les pseudos des joueurs
     */
    public String[] getNomsJoueurs(){
        String[] noms = new String[_joueurs.size()];
        for(int i = 0; i < _joueurs.size(); i++){
            noms[i] = _joueurs.get(i).getText();
        }
        return noms;
    }
    
    /*
     * Retourne le nombre de joueurs
     */
    public int getNbJoueurs(){
        return _joueurs.size();
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
