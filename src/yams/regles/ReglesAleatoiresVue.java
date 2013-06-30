/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.regles;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.net.URL;
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
        
        JEditorPane jep = new JEditorPane("text/html", "regles_simples.html"); //prise en compte du fichier html à revoir
        jep.setEditable(false);
        JScrollBar jsbv = new JScrollBar(JScrollBar.VERTICAL);
        JScrollPane jsp = new JScrollPane();
        jsp.setSize(500, 800);
        jsp.setPreferredSize(jsp.getSize());
        jsp.setVerticalScrollBar(jsbv);
        jsp.add(jep);
            
        pan.add(jsp, BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
