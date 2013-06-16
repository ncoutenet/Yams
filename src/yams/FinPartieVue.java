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
public class FinPartieVue extends JDialog {
    private YamControl _myControler;
    
    
    public FinPartieVue(YamControl yc, Joueur gagnant){
        super.setTitle("Fin de la partie");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this._myControler = yc;
        
        String strGagnant = new String(gagnant.getNom());
        String strChoix = new String("Que souhaitez-vous faire?");
        
        strGagnant += " à gagné la partie avec " + gagnant.getScore(16) + " points!!!";
        
        JLabel labGagnant = new JLabel(strGagnant);
        JLabel labChoix = new JLabel(strChoix);
        JButton btnNouveau = new JButton("Nouveau");
        JButton btnQuitter = new JButton("Quitter");
        
        JPanel panBoutons = new JPanel(new FlowLayout());
        JPanel panLabels = new JPanel(new GridLayout(2, 1, 0, 5));
        
        panLabels.add(labGagnant);
        panLabels.add(labChoix);
        
        btnNouveau.addActionListener(new YamEvents(_myControler));
        btnNouveau.setActionCommand("recommencer");
        panBoutons.add(btnNouveau);
        
        btnQuitter.addActionListener(new YamEvents(_myControler));
        btnQuitter.setActionCommand("quitter");
        panBoutons.add(btnQuitter);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        pan.add(panLabels, BorderLayout.CENTER);
        pan.add(panBoutons, BorderLayout.SOUTH);
        
        this.setSize(pan.getPreferredSize().width*2, pan.getPreferredSize().height*3);
    }
    
    public void affichage(boolean enable){
        this.setVisible(enable);
    }
}
