/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.aide;

import java.awt.*;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import yams.Yams;
import yams.control.YamControl;

/**
 *
 * @author nicolas
 */

/*
 * Fenêtre d'affichage des règles
 */
public class ReglesVue extends JFrame {
    private YamControl _myControler;
    private int _mode;

    public ReglesVue(int mode, YamControl yc) {
        super("Règles du Jeu");
        
        this._mode = mode;
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
        
        if(this._mode == Yams.MODELIBRE){
        texte.append("<h1 align='center' color='red'>Règles du Yam's libre</h1>");
        }
        else if(this._mode == Yams.MODEMONTANT){
        texte.append("<h1 align='center' color='red'>Règles du Yam's montant</h1>");
        }
        else if(this._mode == Yams.MODEDESCENDANT){
        texte.append("<h1 align='center' color='red'>Règles du Yam's descendant</h1>");
        }
        
        texte.append("<p>Le nombre de joueur est limité à 10.</p>");
        texte.append("<p>Le but est, pour chaque joueur, de remplir une grille en totalisant un maximum de points. Pour remplir la grille il faut lancer des dés et réaliser des combinaisons.</p>");
        texte.append("<p>Chaque joueur joue à tour de rôle. À chaque tour le joueur dispose de trois lancés de cinq dés. À l'issu des deux premiers lancés il peut:</p>");
        texte.append("<ul>");
        texte.append("  <li>garder ses 5 dés et remplir une case de sa grille,</li>");
        texte.append("    <li>écarter une partie des dés et relancer les autre.</li>");
        texte.append("</ul>");
        texte.append("<p>Pour garder les 5 dés il suffit de cliquer sur le bouton 'fin du tour'.<br/>");
        if(this._myControler.getPrefs().get(Yams.PREFSELECT)){
            texte.append("Pour garder un dé, il suffit de cliquer dessus</p>");
        }
        else{
            texte.append("Pour relancer les dés il faut cliquer sur ceux que l'on souhaite relancer puis valider en cliquant sur le bouton \"lancer\"");
        }
        if(this._mode == Yams.MODELIBRE){
            texte.append("<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir une case de sa grille. "
                    + "Si le joueur choisit une case alors qu'il n'a pas fait la figure demandé il ne marque pas de point et ne pourra pas retenter la figure "
                    + "plus tard, elle est retirée des figures à obtenir.</p>");
            if(this._myControler.getPrefs().get(Yams.PREFRULES)){
                texte.append("<p>Il y a 12 figures à réaliser dans le désordre:</p>");
            }
            else{
                texte.append("<p>Il y a 13 figures à réaliser dans le désordre:</p>");
            }
        }
        else if(this._mode == Yams.MODEMONTANT){
            texte.append("<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir la case suivante de sa grille. "
                    + "Si le joueur n'a pas fait la figure demandé il ne marque pas de point.</p>");
            if(this._myControler.getPrefs().get(Yams.PREFRULES)){
                texte.append("<p>Il y a 12 figures à réaliser dans l'ordre du tableau (1, 2, etc...):</p>");
            }
            else{
                texte.append("<p>Il y a 13 figures à réaliser dans l'ordre du tableau (1, 2, etc...):</p>");
            }
        }
        else if(this._mode == Yams.MODEDESCENDANT){
            texte.append("<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir la case suivante de sa grille. "
                    + "Si le joueur n'a pas fait la figure demandé il ne marque pas de point.</p>");
            if(this._myControler.getPrefs().get(Yams.PREFRULES)){
                texte.append("<p>Il y a 12 figures à réaliser dans l'ordre inverse du tableau (yam's, carré, etc...):</p>");
            }
            else{
                texte.append("<p>Il y a 13 figures à réaliser dans l'ordre inverse du tableau (yam's, carré, etc...):</p>");
            }
        }
        texte.append("<table border='1' align='center'>");
        texte.append("    <thead>");
        texte.append("        <tr>");
        texte.append("            <th>Nom</th>");
        texte.append("            <th>Description</th>");
        texte.append("            <th>Points</th>");
        texte.append("        </tr>");
        texte.append("    </thead>");
        texte.append("    <tbody>");
        texte.append("        <tr>");
        texte.append("            <td>1</td>");
        texte.append("            <td>Maximum de 1</td>");
        texte.append("            <td>Somme des 1</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>2</td>");
        texte.append("            <td>Maximum de 2</td>");
        texte.append("            <td>Somme des 2</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>3</td>");
        texte.append("            <td>Maximum de 3</td>");
        texte.append("            <td>Somme des 3</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>4</td>");
        texte.append("            <td>Maximum de 4</td>");
        texte.append("            <td>Somme des 4</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>5</td>");
        texte.append("            <td>Maximum de 5</td>");
        texte.append("            <td>Somme des 5</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>6</td>");
        texte.append("            <td>Maximum de 6</td>");
        texte.append("            <td>Somme des 6</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        if(this._myControler.getPrefs().get(Yams.PREFRULES)){
            texte.append("            <td>+</td>");
            texte.append("            <td>Maximum possible</td>");
            texte.append("            <td>Somme des dés</td>");
        }
        else{
            texte.append("            <td>Brelan</td>");
            texte.append("            <td>3 dés identique</td>");
            texte.append("            <td>10</td>");
        }
        texte.append("        </tr>");
        texte.append("        <tr>");
        if(this._myControler.getPrefs().get(Yams.PREFRULES)){
            texte.append("            <td>-</td>");
            texte.append("            <td>Minimum possible</td>");
            texte.append("            <td>Somme des dés</td>");
        }
        else{
            texte.append("            <td>Petite suite</td>");
            texte.append("            <td>1 2 3 4 5</td>");
            texte.append("            <td>15</td>");
        }
        texte.append("        </tr>");
        texte.append("        <tr>");
        if(this._myControler.getPrefs().get(Yams.PREFRULES)){
            texte.append("            <td>Suite</td>");
            texte.append("            <td>5 dés qui se suivent</td>");
            texte.append("            <td>20</td>");
        }
        else{
            texte.append("            <td>Grande suite</td>");
            texte.append("            <td>2 3 4 5 6</td>");
            texte.append("            <td>25</td>");
        }
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>Full</td>");
        texte.append("            <td>3+2 dés identiques</td>");
        texte.append("            <td>30</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>Carré</td>");
        texte.append("            <td>4 dés identiques</td>");
        texte.append("            <td>40</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>Yam's</td>");
        texte.append("            <td>5 dés identiques</td>");
        texte.append("            <td>50</td>");
        texte.append("        </tr>");
        if(!this._myControler.getPrefs().get(Yams.PREFRULES)){
            texte.append("        <tr>");
            texte.append("            <td>Chance</td>");
            texte.append("            <td>Maximum possible</td>");
            texte.append("            <td>Somme des dés</td>");
            texte.append("        </tr>");
        }
        texte.append("    </tbody>");
        texte.append("</table>");
        texte.append("<p>Remarque: Si un joueur totalise 63 points ou plus avec les 6 premières lignes du tableau il gagne un bonus de 35 points.</p>");
        
	return texte; 
    }
}
