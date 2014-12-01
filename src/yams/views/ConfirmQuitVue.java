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

/*
 * Fenêtre de confirmation d'abandon de partie
 * Elle s'affiche lors d'un clic sur les boutons "nouveau" et "quitter" pendant une partie
 */
public class ConfirmQuitVue extends JDialog{
    private YamControl _myControler;
    private JButton btnValider;
    private JButton btnAnnuler;
    
    public ConfirmQuitVue(boolean quit, JeuVue parent, YamControl yc){
        super(parent, true); //mise enplace de la modalité
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this._myControler = yc;
        
        //préparation de la couleur de fond
        Color couleur = new Color(43, 133, 53);
        
        //initialisation du conteneur principal
        Container pan = this.getContentPane();
        pan.setBackground(couleur); //"coloriage" du conteneur
        
        //mise en place du titre suivant le bouton qui a appelé cette fenêtre
        if(quit){
            this.setTitle("Quitter");
        }
        else{
            this.setTitle("Nouvelle partie");
        }
        
        //initialisation des composants de la fenêtre
        JLabel label = new JLabel("Êtes-vous sûr de vouloir abandonner cette partie?"); 
        label.setForeground(Color.WHITE); //label écrit en blanc
        btnValider = new JButton("Oui");
        btnValider.addActionListener(new YamEvents(this._myControler));
        
        //mise en place de la commande à executer lors de la validation
        if(!quit){
            btnValider.setActionCommand("nouveau");
        }
        else {
            btnValider.setActionCommand("quitter");
        }
        
        //suite de l'initialisation des composants
        btnAnnuler = new JButton("Non");
        btnAnnuler.addActionListener(new YamEvents(this._myControler));
        btnAnnuler.setActionCommand("annuler");
        
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
    /*
     * affichage de la fenêtre
     */
    public void activation(boolean enabled){
        this.setVisible(enabled);
    }
}
