/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.aide;

import java.awt.*;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import yams.ModeJeu;
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
    private static final String TR_OPEN = "        <tr>";
    private static final String TR_CLOSE = "        </tr>";
    private static final String TD_SOMME_DES = "            <td>Somme des dés</td>";
    private transient YamControl myControler;
    private ModeJeu mode;

    public ReglesVue(ModeJeu mode, YamControl yc) {
        super("Règles du Jeu");
        
        this.mode = mode;
        this.myControler = yc;
        
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
            height = tk.getScreenSize().height - (tk.getScreenInsets(gconf).bottom * 2) - (tk.getScreenInsets(gconf).top * 2);
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
    private void appendTitre(StringBuffer texte){
        switch(this.mode){
            case LIBRE:
                texte.append("<h1 align='center' color='red'>Règles du Yam's libre</h1>");
                break;
            case MONTANT:
                texte.append("<h1 align='center' color='red'>Règles du Yam's montant</h1>");
                break;
            case DESCENDANT:
                texte.append("<h1 align='center' color='red'>Règles du Yam's descendant</h1>");
                break;
            case SEC:
                texte.append("<h1 align='center' color='red'>Règles du Yam's sec</h1>");
                break;
            default:
                break;
        }
    }

    private void appendDeroulement(StringBuffer texte, boolean rules){
        texte.append(introDeroulement());
        int nbFigures = rules ? 12 : 13;
        texte.append("<p>Il y a ").append(nbFigures).append(" figures à réaliser ").append(ordreFigures()).append(":</p>");
    }

    private String introDeroulement(){
        switch(this.mode){
            case LIBRE:
                return "<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir une case de sa grille. "
                        + "Si le joueur choisit une case alors qu'il n'a pas fait la figure demandé il ne marque pas de point et ne pourra pas retenter la figure "
                        + "plus tard, elle est retirée des figures à obtenir.</p>";
            case SEC:
                return "<p>À l'issu de son unique lancer, le joueur doit remplir une case de sa grille. "
                        + "Si le joueur choisit une case alors qu'il n'a pas fait la figure demandé il ne marque pas de point et ne pourra pas retenter la figure "
                        + "plus tard, elle est retirée des figures à obtenir.</p>";
            case MONTANT:
            case DESCENDANT:
                return "<p>À l'issu du troisième lancé, ou lorsqu'il choisit de garder ses 5 dés, il doit remplir la case suivante de sa grille. "
                        + "Si le joueur n'a pas fait la figure demandé il ne marque pas de point.</p>";
            default:
                return "";
        }
    }

    private String ordreFigures(){
        switch(this.mode){
            case MONTANT:
                return "dans l'ordre du tableau (1, 2, etc...)";
            case DESCENDANT:
                return "dans l'ordre inverse du tableau (yam's, carré, etc...)";
            case LIBRE:
            case SEC:
            default:
                return "dans le désordre";
        }
    }

    private void appendTableau(StringBuffer texte, boolean rules){
        texte.append("<table border='1' align='center'>");
        texte.append("    <thead>");
        texte.append(TR_OPEN);
        texte.append("            <th>Nom</th>");
        texte.append("            <th>Description</th>");
        texte.append("            <th>Points</th>");
        texte.append(TR_CLOSE);
        texte.append("    </thead>");
        texte.append("    <tbody>");
        texte.append(TR_OPEN);
        texte.append("            <td>1</td>");
        texte.append("            <td>Maximum de 1</td>");
        texte.append("            <td>Somme des 1</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>2</td>");
        texte.append("            <td>Maximum de 2</td>");
        texte.append("            <td>Somme des 2</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>3</td>");
        texte.append("            <td>Maximum de 3</td>");
        texte.append("            <td>Somme des 3</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>4</td>");
        texte.append("            <td>Maximum de 4</td>");
        texte.append("            <td>Somme des 4</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>5</td>");
        texte.append("            <td>Maximum de 5</td>");
        texte.append("            <td>Somme des 5</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>6</td>");
        texte.append("            <td>Maximum de 6</td>");
        texte.append("            <td>Somme des 6</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        if(rules){
            texte.append("            <td>+</td>");
            texte.append("            <td>Maximum possible</td>");
            texte.append(TD_SOMME_DES);
        }
        else{
            texte.append("            <td>Brelan</td>");
            texte.append("            <td>3 dés identique</td>");
            texte.append("            <td>10</td>");
        }
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        if(rules){
            texte.append("            <td>-</td>");
            texte.append("            <td>Minimum possible</td>");
            texte.append(TD_SOMME_DES);
        }
        else{
            texte.append("            <td>Petite suite</td>");
            texte.append("            <td>1 2 3 4 5</td>");
            texte.append("            <td>15</td>");
        }
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        if(rules){
            texte.append("            <td>Suite</td>");
            texte.append("            <td>5 dés qui se suivent</td>");
            texte.append("            <td>20</td>");
        }
        else{
            texte.append("            <td>Grande suite</td>");
            texte.append("            <td>2 3 4 5 6</td>");
            texte.append("            <td>25</td>");
        }
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>Full</td>");
        texte.append("            <td>3+2 dés identiques</td>");
        texte.append("            <td>30</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>Carré</td>");
        texte.append("            <td>4 dés identiques</td>");
        texte.append("            <td>40</td>");
        texte.append(TR_CLOSE);
        texte.append(TR_OPEN);
        texte.append("            <td>Yam's</td>");
        texte.append("            <td>5 dés identiques</td>");
        texte.append("            <td>50</td>");
        texte.append(TR_CLOSE);
        if(!rules){
            texte.append(TR_OPEN);
            texte.append("            <td>Chance</td>");
            texte.append("            <td>Maximum possible</td>");
            texte.append(TD_SOMME_DES);
            texte.append(TR_CLOSE);
        }
        texte.append("    </tbody>");
        texte.append("</table>");
    }

    private StringBuffer saisieTexte(){
	StringBuffer texte = new StringBuffer();
        boolean rules = this.myControler.getPrefs().get(Yams.PREFRULES);
        boolean garde = this.myControler.getPrefs().get(Yams.PREFSELECT);

        this.appendTitre(texte);

        texte.append("<p>Le nombre de joueur est limité à 10.</p>");
        texte.append("<p>Le but est, pour chaque joueur, de remplir une grille en totalisant un maximum de points. Pour remplir la grille il faut lancer des dés et réaliser des combinaisons.</p>");
        if(this.mode == ModeJeu.SEC){
            texte.append("<p>Chaque joueur joue à tour de rôle et ne dispose que d'un seul lancer de cinq dés avant de devoir remplir une case de sa grille.</p>");
        }
        else{
            texte.append("<p>Chaque joueur joue à tour de rôle. À chaque tour le joueur dispose de trois lancés de cinq dés. À l'issu des deux premiers lancés il peut:</p>");
            texte.append("<ul>");
            texte.append("  <li>garder ses 5 dés et remplir une case de sa grille,</li>");
            texte.append("    <li>écarter une partie des dés et relancer les autre.</li>");
            texte.append("</ul>");
            texte.append("<p>Pour garder les 5 dés il suffit de cliquer sur le bouton 'fin du tour'.<br/>");
            if(garde){
                texte.append("Pour garder un dé, il suffit de cliquer dessus</p>");
            }
            else{
                texte.append("Pour relancer les dés il faut cliquer sur ceux que l'on souhaite relancer puis valider en cliquant sur le bouton \"lancer\"");
            }
        }
        this.appendDeroulement(texte, rules);
        this.appendTableau(texte, rules);
        texte.append("<p>Remarque: Si un joueur totalise 63 points ou plus avec les 6 premières lignes du tableau il gagne un bonus de 35 points.</p>");

	return texte;
    }
}
