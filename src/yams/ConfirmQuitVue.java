/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nicolas
 */
public class ConfirmQuitVue extends JDialog{
    public ConfirmQuitVue(boolean quit, JeuVue parent, YamControl yc){
        super(parent, true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container pan = this.getContentPane();
        
        if(quit){
            this.setTitle("Quitter");
        }
        else{
            this.setTitle("Nouvelle partie");
        }
        
        JLabel label = new JLabel("Êtes-vous sûr de vouloir abandonner cette partie?");
        JButton btnValider = new JButton("Oui");
        btnValider.addActionListener(new YamEvents(yc));
        if(!quit){
            btnValider.setActionCommand("nouveau");
        }
        else {
            btnValider.setActionCommand("quitter");
        }
        
        JButton btnAnnuler = new JButton("Non");
        btnAnnuler.addActionListener(new YamEvents(yc));
        btnAnnuler.setActionCommand("annuler");
        
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.add(btnValider);
        panBtn.add(btnAnnuler);
        
        pan.setLayout(new GridLayout(2, 1));
        pan.add(label);
        pan.add(panBtn);
        
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(this.getParent());
    }
    
    public void activation(boolean enabled){
        this.setVisible(enabled);
    }
}
