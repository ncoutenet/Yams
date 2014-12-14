/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.pojos.MyMenuBar;
import java.awt.*;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import yams.control.YamControl;
import yams.hightScores.pojos.Score;
import yams.pojos.Joueur;
/**
 *
 * @author nicolas
 */

/*
 * Fenêtre affichée en fin de partie. Elle donne le pseudo et le score du gagnant puis elle demande à l'utilisateur ce qu'il veux faire
 */
public class FinPartieVue extends JDialog{
    private YamControl _myControler;
    
    public FinPartieVue(YamControl yc, JeuVue parent, Joueur[] gagnants){
        super(parent, "Fin de la partie", false); //fenêtre non modale pour pouvoir afficher les scores et modifier les préférences
        this._myControler = yc;
        this.setJMenuBar(new MyMenuBar(this._myControler, "finPartie"));
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //fermeture impossible (bouton quitter pour quitter)
        Color couleur = new Color(43, 133, 53);
        int max = gagnants.length-1;
        
        String strGagnant = gagnants[max].getNom();
        String strChoix = "Que souhaitez-vous faire?";
        
        strGagnant += " à gagné la partie avec " + gagnants[max].getScore(16) + " points!!!";
        
        JEditorPane listeJoueurs;
        StringBuffer liste = new StringBuffer();
        if(gagnants.length > 1){
            int position = 1;
            for(max = gagnants.length-1; max >= 0; max--){
                String texte = new String("<center><b><span color='white'>");
                texte += String.valueOf(position);
                if(max == gagnants.length-1){
                    texte += "er: ";
                }
                else{
                    texte += "ème: ";
                }
                texte += gagnants[max].getNom();
                texte += " avec ";
                texte += gagnants[max].getScore(16);
                texte += " points</span></b></center>";
                liste.append(texte);
                position++;
            }
        }
        for(int i = gagnants.length-1; i>=0; i--){
            this._myControler.addAScore(new Score(gagnants[i].getNom(), gagnants[i].getScore(16)));
        }
        
        listeJoueurs = new JEditorPane("text/html", new String(liste));
        listeJoueurs.setEditable(false);
        listeJoueurs.setBackground(couleur);
        
        JLabel labGagnant = new JLabel(strGagnant);
        labGagnant.setForeground(Color.WHITE);
        labGagnant.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        
        JPanel panLabels = new JPanel(new GridLayout(3, 1, 0, 5));
        panLabels.setBackground(couleur);
        
        labGagnant.setHorizontalAlignment(JLabel.CENTER);
        panLabels.add(labGagnant);
        if(gagnants.length > 1){
            panLabels.add(listeJoueurs);
        }
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.setBackground(couleur);
        
        pan.add(panLabels, BorderLayout.CENTER);
        
        if(gagnants.length == 1){
            this.setSize(pan.getPreferredSize().width*2, pan.getPreferredSize().height*2);
            
        }
        else {
            this.setSize(pan.getPreferredSize().width*2, pan.getPreferredSize().height+100);
        }
        this.setLocationRelativeTo(this.getParent());
        Point p = this.getLocation();
        p.setLocation(p.getX(), p.getY() + (parent.getLocation().getY() - (this.getHeight()/2)));
    }
    
    /*
     * Gère l'affichage
     */
    public void affichage(boolean enable){
        if(enable){
            this._myControler.setActualWindow("FinPartie");
        }
        this.setVisible(enable);
    }
}
