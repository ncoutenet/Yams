/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import yams.control.YamControl;
import yams.events.YamEvents;

/**
 *
 * @author nicolas
 */
public class ConfirmScoreVue extends JDialog{
    private YamControl _myControler;
    private JButton btnValider;
    private JButton btnAnnuler;
    
    public ConfirmScoreVue(YamControl yc, JeuVue parent){
        super(parent, true); //mise en place de la modalité
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this._myControler = yc;
        
        //préparation de la couleur de fond
        Color couleur = new Color(43, 133, 53);
        
        //initialisation du conteneur principal
        Container pan = this.getContentPane();
        pan.setBackground(couleur); //"coloriage" du conteneur
        this.setTitle("Fin du tour");
        
        //initialisation des composants de la fenêtre
        JLabel label = new JLabel("Êtes-vous sûr de vouloir finir ce tour maintenant?"); 
        label.setForeground(Color.WHITE); //label écrit en blanc
        btnValider = new JButton("Oui");
        btnValider.addActionListener(new YamEvents(this._myControler));
        btnValider.setActionCommand("confirmFinTour");
        
        btnAnnuler = new JButton("Non");
        btnAnnuler.addActionListener(new YamEvents(this._myControler));
        btnAnnuler.setActionCommand("cancelFinTour");
        
        //initialisation des sous-conteneurs de la fenêtre
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.setBackground(couleur);
        //mise en place des composants
        panBtn.add(btnValider);
        panBtn.add(btnAnnuler);
        
        //mise en place du conteneur et du label dans le conteneur principal de la fenêtre
        pan.setLayout(new GridLayout(2, 1));
        pan.add(label);
        pan.add(panBtn);
        
        //définition de la taille de la fenêtre
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(this.getParent());
    }
}
