/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.regles;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author nicolas
 */
public class ReglesAleatoiresVue extends JFrame {

    public ReglesAleatoiresVue() {
        super("Règles du Jeu");
        
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        
        StringBuffer nom;
        nom = saisieTexte();
        
        JEditorPane jep = new JEditorPane("text/html", new String(nom));
        jep.setEditable(false);
        jep.setPreferredSize(new Dimension(500, 800));
        
        JScrollPane jsp = new JScrollPane(jep);
            
        pan.add(jsp, BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private StringBuffer saisieTexte(){
	StringBuffer texte = new StringBuffer();
        
        texte.append("<h1 align='center' color='red'>Règles du Yam's aléatoire</h1>");
        texte.append("<p>Au Yam’s le nombre de joueur n'est pas fixé, pour des raisons techniques ce logiciel ne gère que 10 joueurs maximum.</p>");
        texte.append("<p>Le but est, pour chaque joueur, de remplir une grille en totalisant un maximum de points. Pour remplir la grille il faut lancer des dés et réaliser des figures.</p>");
        texte.append("<p>Chaque joueur joue à tour de rôle. À chaque tour le joueur dispose de trois lancés de cinq dés. À l'issu des deux premiers lancés il peut:</p>");
        texte.append("<ul>");
        texte.append("  <li>garder ses 5 dés et remplir une case de sa grille,</li>");
        texte.append("    <li>écarter une partie des dés et relancer les autre.</li>");
        texte.append("</ul>");
        texte.append("<p>Pour garder les 5 dés il suffit de cliquer sur le bouton 'fin du tour'.<br/>Pour écarter un dé, il suffit de cliquer sur la case à cocher se trouvant à sa gauche</p>");
        texte.append("<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir une case de sa grille. "
                + "Si le joueur choisit une case alors qu'il n'a pas fait la figure demandé il ne marque pas de point et ne pourra pas retenter la figure "
                + "plus tard, elle est retirée des figures à obtenir.</p>");
        texte.append("<p>Il y a 12 figures à réaliser:</p>");
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
        texte.append("            <td>+</td>");
        texte.append("            <td>Maximum possible</td>");
        texte.append("            <td>Somme des dés</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>-</td>");
        texte.append("            <td>Minimum possible</td>");
        texte.append("            <td>Somme des dés</td>");
        texte.append("        </tr>");
        texte.append("        <tr>");
        texte.append("            <td>Suite</td>");
        texte.append("            <td>5 dés qui se suivent</td>");
        texte.append("            <td>20</td>");
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
        texte.append("    </tbody>");
        texte.append("</table>");
        texte.append("<p>Remarque: Si un joueur totalise 60 points ou plus avec les 6 premières lignes du tableau il gagne un bonus de 30 points.</p>");
        
	return texte; 
    }
}
