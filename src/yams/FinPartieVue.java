/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author nicolas
 */
public class FinPartieVue extends JDialog{
    private YamControl _myControler;
    private JButton btnNouveau;
    private JButton btnQuitter;
    
    public FinPartieVue(YamControl yc, JeuVue parent, Joueur gagnant){
        super(parent, "Fin de la partie", true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this._myControler = yc;
        Color couleur = new Color(43, 133, 53);
        
        String strGagnant = gagnant.getNom();
        String strChoix = "Que souhaitez-vous faire?";
        
        strGagnant += " à gagné la partie avec " + gagnant.getScore(16) + " points!!!";
        
        JLabel labGagnant = new JLabel(strGagnant);
        labGagnant.setForeground(Color.WHITE);
        JLabel labChoix = new JLabel(strChoix);
        labChoix.setForeground(Color.WHITE);
        btnNouveau = new JButton("Nouveau");
        btnQuitter = new JButton("Quitter");
        
        JPanel panBoutons = new JPanel(new FlowLayout());
        panBoutons.setBackground(couleur);
        JPanel panLabels = new JPanel(new GridLayout(2, 1, 0, 5));
        panLabels.setBackground(couleur);
        
        labGagnant.setHorizontalAlignment(JLabel.CENTER);
        labChoix.setHorizontalAlignment(JLabel.CENTER);
        panLabels.add(labGagnant);
        panLabels.add(labChoix);
        
        btnNouveau.addActionListener(new YamEvents(_myControler));
        btnNouveau.setActionCommand("recommencer");
        panBoutons.add(btnNouveau);
        
        btnQuitter.addActionListener(new YamEvents(_myControler));
        btnQuitter.setActionCommand("quitter");
        panBoutons.add(btnQuitter);
        panBoutons.setBackground(couleur);
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.setBackground(couleur);
        
        pan.add(panLabels, BorderLayout.CENTER);
        pan.add(panBoutons, BorderLayout.SOUTH);
        
        this.setSize(pan.getPreferredSize().width*2, pan.getPreferredSize().height*3);
        this.setLocationRelativeTo(this.getParent());
    }
    
    public void affichage(boolean enable){
        this.setVisible(enable);
    }
}
