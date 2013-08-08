/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author nicolas
 */
public class ConnectionVue extends JFrame{
    
    private int VAL = 1;
    private int MIN = 1;
    private int MAX = 10;
    private Color COULEUR;
    
    private YamControl _myControler;
    private JSpinner _spinner;
    private JTextField[] _joueurs;
    private JPanel _panJoueurs;
    private JComboBox _cbModeJeu;
    
    public ConnectionVue(YamControl yc){
        super("Yam's");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        _myControler = yc;
        Container pan = this.getContentPane();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        
        //initialisation de la couleur de fond
        COULEUR = new Color(43, 133, 53);
        pan.setBackground(COULEUR);
        
        //initialisation du menu déroulant
        Object[] modes = new Object[3];
        modes[0] = new String("Aléatoire");
        modes[1] = new String("Montant");
        modes[2] = new String("Descendant");
        
        //instanciation du menu
        this._cbModeJeu = new JComboBox(modes);
        JLabel labModes = new JLabel("Mode de jeu: ");
        labModes.setForeground(Color.WHITE);
        
        //création du bouton des règles
        JButton btnRegles = new JButton("Règles");
        btnRegles.addActionListener(new YamEvents(_myControler));
        btnRegles.setActionCommand("aperçuRegles");
        
        //assemblage 
        JPanel panModesJeu = new JPanel(new FlowLayout());
        panModesJeu.add(labModes);
        panModesJeu.add(this._cbModeJeu);
        panModesJeu.add(btnRegles);
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
        btnValider.setHorizontalAlignment(JButton.CENTER);
        btnValider.addActionListener(new YamEvents(_myControler));
        btnValider.setActionCommand("commencer");
        pan.add(btnValider);
        
        this.pack();
        this.setLocationRelativeTo(this.getParent());
        
    }
    
    public void affichage(boolean enable){
        this.setVisible(enable);
    }
    
    public final void setJoueurs(){
        String strJ;
        JPanel panel;
        JLabel labJ;
        _panJoueurs.removeAll();
        _joueurs = new JTextField[(Integer)_spinner.getValue()];
        for(int i = 0; i < (Integer)_spinner.getValue(); i++){
            strJ = "Joueur ";
            strJ += String.valueOf(i+1);
            _joueurs[i] = new JTextField(10);
            _joueurs[i].addActionListener(new YamEvents(_myControler));
            _joueurs[i].setText(strJ);
            panel = new JPanel(new FlowLayout());
            panel.setBackground(COULEUR);
            strJ += ":";
            labJ = new JLabel(strJ);
            labJ.setForeground(Color.WHITE);
            panel.add(labJ);
            panel.add(_joueurs[i]);
            _panJoueurs.add(panel);
        }
        _panJoueurs.updateUI();
    }
    
    public String[] getNomsJoueurs(){
        String[] noms = new String[_joueurs.length];
        for(int i = 0; i < _joueurs.length; i++){
            noms[i] = _joueurs[i].getText();
        }
        return noms;
    }
    
    public int getNbJoueurs(){
        return _joueurs.length;
    }
    
    public int getModeJeu(){
        if(this._cbModeJeu.getSelectedItem().getClass().equals(String.class)){
            if(this._cbModeJeu.getSelectedItem().equals("Aléatoire")){
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
