/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.control.YamControl;
import yams.events.YamEvents;
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

/*
 * Fenêtre s'affichant à la fin de chaque tour en mode montant et descandant et au dernier coup du mode libre
 */
public class InfoScoreVue extends JDialog{
    private YamControl _myControler;
    private JButton _btnValider;
    
    public InfoScoreVue(int score, String index, JeuVue parent, YamControl yc){
        super(parent, true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        _myControler = yc;
        
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
        
        _btnValider = new JButton("Ok");
        _btnValider.addActionListener(new YamEvents(_myControler));
        _btnValider.setActionCommand("confScore");
        
        Container pan = this.getContentPane();
        pan.setLayout(new GridLayout(3, 1));
        pan.setBackground(couleur);
        
        
        pan.add(labScore);
        pan.add(panel);
        pan.add(_btnValider);
        
        this.setLocationRelativeTo(this.getParent());
        this.pack();
    }
    
    /*
     * gère l'affichage
     */
    public void activation(boolean enabled){
        this.setVisible(enabled);
    }    
}
