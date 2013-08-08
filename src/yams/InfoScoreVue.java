/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.Color;
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
public class InfoScoreVue extends JDialog{
    public InfoScoreVue(int score, String index, JeuVue parent, YamControl yc){
        super(parent, true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        Color couleur = new Color(43, 133, 53);
        
        String strScore = new String("Vous venez de marquer ");
        strScore += String.valueOf(score);
        strScore += " points";
        
        String strCase = new String("dans la case ");
        strCase += index;
        strCase += "!";
        JLabel labScore = new JLabel(strScore);
        labScore.setForeground(Color.WHITE);
        JLabel labCase = new JLabel(strCase);
        labCase.setForeground(Color.WHITE);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(labCase);
        panel.setBackground(couleur);
        
        JButton btnValider = new JButton("Ok");
        btnValider.addActionListener(new YamEvents(yc));
        btnValider.setActionCommand("confScore");
        
        Container pan = this.getContentPane();
        pan.setLayout(new GridLayout(3, 1));
        pan.setBackground(couleur);
        
        
        pan.add(labScore);
        pan.add(panel);
        pan.add(btnValider);
        
        this.setLocationRelativeTo(this.getParent());
        this.pack();
    }
    
    public void activation(boolean enabled){
        this.setVisible(enabled);
    }
    
}
