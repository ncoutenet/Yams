/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nicolas
 */
public class ConnectionVue extends JFrame{
    
    private int VAL = 1;
    private int MIN = 1;
    private int MAX = 10;
    
    private YamControl _myControler;
    private JSpinner _spinner;
    private JTextField[] _joueurs;
    private JPanel _panJoueurs;
    
    public ConnectionVue(YamControl yc){
        super("Yam's");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        _myControler = yc;
        Container pan = this.getContentPane();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        
        JLabel labMessage = new JLabel("Veuillez choisir le nombre de joueurs (" + MAX + " joueurs max) et indiquer leurs noms");
        labMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        pan.add(labMessage);
        
        JPanel panNb = new JPanel(new FlowLayout());
        JLabel labNbJoueurs = new JLabel("Nombre de joueurs:");
        panNb.add(labNbJoueurs);
        SpinnerModel model = new SpinnerNumberModel(VAL, MIN, MAX, 1);
        _spinner = new JSpinner(model);
        _spinner.addChangeListener(new NbJoueursEvents(_myControler));
        panNb.add(_spinner);
        pan.add(panNb);
        
        _panJoueurs = new JPanel(new GridLayout(MAX, 1));
        this.setJoueurs();
        pan.add(_panJoueurs);
        
        JButton btnValider = new JButton("Commencer");
        btnValider.addActionListener(new YamEvents(_myControler));
        btnValider.setActionCommand("commencer");
        pan.add(btnValider);
        
        this.pack();
        
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
            strJ += ":";
            labJ = new JLabel(strJ);
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
}
