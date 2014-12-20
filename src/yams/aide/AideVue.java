/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.aide;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import yams.Yams;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */
public class AideVue extends JFrame{
    private YamControl _myControler;
    
    public AideVue(YamControl yc){
        super("Aide");
        this._myControler = yc;
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        int width = 500;
        int height = 800;
        StringBuffer texte;
        texte = saisieTexte();
        
        JEditorPane jep = new JEditorPane("text/html", new String(texte));
        jep.setEditable(false);
        jep.setCaretPosition(0);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        GraphicsConfiguration gconf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        
        if((tk.getScreenSize().getHeight() - (tk.getScreenInsets(gconf).bottom * 2) - (tk.getScreenInsets(gconf).top * 2)) < height){
            height = (int)(tk.getScreenSize().height - (tk.getScreenInsets(gconf).bottom * 2) - (tk.getScreenInsets(gconf).top * 2)); 
        }
        
        jep.setPreferredSize(new Dimension(width, height));
        
        JScrollPane jsp = new JScrollPane(jep);
            
        pan.add(jsp, BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    /*
     * Retourne les règles suivant le mode de jeu
     */
    private StringBuffer saisieTexte(){
        String soundOn = getClass().getResource("/resources/images/sound/soundOn.png").toString();
        String soundOff = getClass().getResource("/resources/images/sound/soundOff.png").toString();
	StringBuffer texte = new StringBuffer();
        
        texte.append("<h1 align='center' color='red'>Mode d'emploi</h1>");
        
        texte.append("<h2>Tableau des meilleurs scores</h2>");
        texte.append("<p>Ici sont sauvegardé les meilleurs score. Chaque mode de jeu a son tableau.</p>");
        texte.append("<p>Pour afficher le tableau d'un mode de jeu, il suffit de le choisir dans le menu déroulant du tableau.</p>");
        texte.append("<p>Le bouton \"Reset...\" en bas à gauche du tableau permet d'effacer soit"
                    + "    <ul>"
                    + "        <li>Le tableau en cours d'affichage</li>"
                    + "        <li>Tous les tableaux</li>"
                    + "    </ul></p>");
        texte.append("<p>Le bouton \"Exporter...\" à coté du bouton reset permet d'exporter les scores soit"
                    + "    <ul>"
                    + "        <li>en CSV avec séparateur point-virgule (pour excel)</li>"
                    + "        <li>en XML</li>"
                    + "    </ul></p>");
        texte.append("<h2>Préférences</h2>");
        texte.append("<p>Pour désactiver le son il suffit de cliquer sur <img src=\""+soundOn+"\" /><br/>");
        texte.append("Pour le réactiver, cliquer sur <img src=\""+soundOff+"\" /></p>");
        texte.append("<h3>Menu Préférences</h3>");
        texte.append("<p>Dans ce menu il est possible de régler le son, la manière de lancer les dés et les coups à jouer.</p>");
        texte.append("<p>Si la case \"son\" est cochée, l'application jouera les sons</p>");
        texte.append("<p>l'option \"Garder les dés sélectionnés\" permet de <strong>conserver</strong> les dés sélectionnés.<br/>"
                + "l'option\"Relancer les dés sélectionnés\" permet de <strong>relancer</strong> les dés sélectionnés.</p>");
        texte.append("<p>Les dés sélectionné pour être gardés sont coloriés en <span style=\"color:green;\">vert</span>.<br/>"
                + "Les dés sélectionnés pour être relancé seront coloriés en <span style=\"color:red;\">rouge</span>.</p>");
        
	return texte; 
    }
}
