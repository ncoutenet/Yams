/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.regles;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
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
        nom = saisieTexte("src/yams/regles/regles_simples.html");
        
        JEditorPane jep = new JEditorPane("text/html", new String(nom));
        jep.setEditable(false);
        JScrollBar jsbv = new JScrollBar(JScrollBar.VERTICAL);
        jep.setPreferredSize(new Dimension(500, 800));
        
        JScrollPane jsp = new JScrollPane(jep);
        jsp.setVerticalScrollBar(jsbv);
            
        pan.add(jsp, BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    private StringBuffer saisieTexte(String nomFichier){
       File fichier = new File(nomFichier);
	StringBuffer texte = new StringBuffer();
	
	try {
	Scanner lecteur = new Scanner(fichier);
	
	while (lecteur.hasNext()) texte.append(lecteur.nextLine());
	}
	catch (FileNotFoundException exc) {
	System.err.println("Fichier inexistant " + exc);
	}
	return texte; 
    }
}
