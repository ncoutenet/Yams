/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author nicolas
 */
public class ConfirmQuitVue extends JDialog implements KeyListener{
    private JButton btnValider;
    private JButton btnAnnuler;
    
    public ConfirmQuitVue(boolean quit, JeuVue parent, YamControl yc){
        super(parent, true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        Color couleur = new Color(43, 133, 53);
        
        Container pan = this.getContentPane();
        pan.setBackground(couleur);
        
        if(quit){
            this.setTitle("Quitter");
        }
        else{
            this.setTitle("Nouvelle partie");
        }
        
        JLabel label = new JLabel("Êtes-vous sûr de vouloir abandonner cette partie?");
        label.setForeground(Color.WHITE);
        btnValider = new JButton("Oui");
        btnValider.addActionListener(new YamEvents(yc));
        btnValider.addKeyListener(this);
        
        if(!quit){
            btnValider.setActionCommand("nouveau");
        }
        else {
            btnValider.setActionCommand("quitter");
        }
        
        btnAnnuler = new JButton("Non");
        btnAnnuler.addActionListener(new YamEvents(yc));
        btnAnnuler.setActionCommand("annuler");
        
        JPanel panBtn = new JPanel(new FlowLayout());
        panBtn.setBackground(couleur);
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

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    
    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println("Touche pressée: " + ke.getKeyCode());
        if(ke.getKeyCode() == KeyEvent.VK_ENTER){
            this.btnValider.doClick();
        }
        if(ke.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.btnAnnuler.doClick();
        }
    }
}
