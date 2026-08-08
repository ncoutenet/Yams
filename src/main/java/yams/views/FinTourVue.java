/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import yams.Yams;
import yams.control.YamControl;
import yams.events.YamEvents;

/**
 *
 * @author nicolas
 */

/*
 * Fenêtre s'affichant à la fin de chaque tour en mode libre
 * Elle permet au joueur de sélectionner la case où il veut placer ses points
 */
public class FinTourVue extends JDialog{
    private static final Logger LOGGER = Logger.getLogger(FinTourVue.class.getName());
    private transient YamControl myControler;
    
    private JComboBox cbChoix;
    private boolean[][] choixValides;
    private int noJoueur;
    private JButton btnVal;
    private  JButton btnAnnuler;
    
    public FinTourVue(boolean[][] choix, int joueur, YamControl yc, boolean fin, JeuVue parent){
        super(parent, "Fin Du Tour", true);
        super.setResizable(false);
        
        Color couleur = new Color(43, 133, 53);
        
        if(fin){
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
        else{
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        this.myControler = yc;
        
        //initialisation des variables locales
        this.choixValides = choix;
        this.noJoueur = joueur;
        
        //création du message
        JLabel labMessage = new JLabel("Choisissez où vous voulez placer les points");
        labMessage.setForeground(Color.WHITE);
        
        //création des boutons de validation
        this.btnVal = new JButton("Valider");
        this.btnVal.addActionListener(new YamEvents(this.myControler));
        this.btnVal.setActionCommand("validerFinTour");
            this.btnAnnuler = new JButton("Annuler");
            this.btnAnnuler.addActionListener(new YamEvents(this.myControler));
            this.btnAnnuler.setActionCommand("annulerFinTour");
        
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(couleur);
        if(!fin){
            panel.add(this.btnVal);
            panel.add(this.btnAnnuler);
            panel.setSize(panel.getWidth(), btnVal.getHeight());
        }
        //création de la liste déroulante
        this.setChoix();
        
        //initialisation du panel principal
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout(0, 10));
        
        //verification du dernier lancer
        boolean[] scores = myControler.getScoresValides();
        int cpt = 0;
        int nbCoupsMax;
        boolean rules = this.myControler.getPrefs().get(Yams.PREFRULES);

        if(rules){
            nbCoupsMax = 12;
        }
        else{
            nbCoupsMax = 13;
        }
        
        for(int i = 0; i < nbCoupsMax; i++){
            if(!scores[i]){
                cpt++;
            }
        }
        if(cpt == nbCoupsMax-1){
            int index = 0;
            while(!scores[index]){
                index++;
            }
            JLabel message = new JLabel("Il ne reste qu'un score à ajouter:");
            message.setForeground(Color.WHITE);
            JLabel score = new JLabel(this.getDernierScore());
            score.setForeground(Color.WHITE);
            score.setHorizontalAlignment(SwingConstants.CENTER);
            pan.add(message, BorderLayout.NORTH);
            pan.add(score, BorderLayout.CENTER);
            
        }
        else {
            LOGGER.log(Level.INFO, "{0}", cpt);
            pan.add(labMessage, BorderLayout.NORTH);
            pan.add(this.cbChoix, BorderLayout.CENTER);
        }
        
        //définition de la couleur de fond de la fenêtre
        pan.setBackground(couleur);
        
        //définition du nombre de boutons utiles
        if(!fin){
            pan.add(panel, BorderLayout.SOUTH);
        }
        else pan.add(this.btnVal, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Retourne la sélection du joueur
     */
    public String getChoix(){
        if(this.cbChoix.getSelectedItem().getClass().equals(String.class)){
            return (String)this.cbChoix.getSelectedItem();
        }
        else return null;
    }
    
    /*
     * Permet la mise à jour des choix possibles
     */
    private int nbCoups(boolean rules){
        return rules ? 12 : 13;
    }

    private String nomCoup(int i, boolean rules){
        String type = "";
        switch(i){
            case 0:
                type = "1";
                break;
            case 1:
                type = "2";
                break;
            case 2:
                type = "3";
                break;
            case 3:
                type = "4";
                break;
            case 4:
                type = "5";
                break;
            case 5:
                type = "6";
                break;
            case 6:
                type = rules ? "+" : "brelan";
                break;
            case 7:
                type = rules ? "-" : "petite suite";
                break;
            case 8:
                type = rules ? "suite" : "grande suite";
                break;
            case 9:
                type = "full";
                break;
            case 10:
                type = "carré";
                break;
            case 11:
                type = "yam's";
                break;
            case 12:
                if(!rules){
                    type = "chance";
                }
                break;
            default:
                break;
        }
        return type;
    }

    private void setChoix(){
        boolean rules = this.myControler.getPrefs().get(Yams.PREFRULES);
        int nbCoups = nbCoups(rules);
        Object[] types;
        java.util.List<String> coups = new ArrayList<String>();
        for(int i = 0; i < nbCoups; i++){
            String type = nomCoup(i, rules);
            if(this.choixValides[this.noJoueur][i]){
                coups.add(type);
            }
        }

        types = new Object[coups.size()];
        for(int i = 0; i < coups.size(); i++){
            types[i] = coups.get(i);
        }

        this.cbChoix = new JComboBox(types);
    }

    /*
     * Retourne le dernier score à effectuer
     */
    private String getDernierScore(){
        String score = null;
        boolean rules = this.myControler.getPrefs().get(Yams.PREFRULES);
        int nbCoups = nbCoups(rules);

        for(int i = 0; i < nbCoups; i++){
            String type = nomCoup(i, rules);
            if(this.choixValides[this.noJoueur][i]){
                score = type;
            }
        }
        return score;
    }
    
    /*
     * gère l'affichage
     */
    public void setAffichage(boolean enable){
        this.setVisible(enable);
    }
}
