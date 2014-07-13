/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.views;

import yams.control.YamControl;
import yams.events.YamEvents;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author nicolas
 */

/*
 * Fenêtre s'affichant à la fin de chaque tour en mode libre
 * Elle permet au joueur de sélectionner la case où il veut placer ses points
 */
public class FinTourVue extends JDialog{
    private YamControl _myControler;
    
    private JComboBox _cbChoix;
    private boolean[][] _choixValides;
    private int _noJoueur;
    private JButton _btnVal;
    private  JButton _btnAnnuler;
    
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
        this._myControler = yc;
        
        //initialisation des variables locales
        this._choixValides = choix;
        this._noJoueur = joueur;
        
        //création du message
        JLabel labMessage = new JLabel("Choisissez où vous voulez placer les points");
        labMessage.setForeground(Color.WHITE);
        
        //création des boutons de validation
        this._btnVal = new JButton("Valider");
        this._btnVal.addActionListener(new YamEvents(this._myControler));
        this._btnVal.setActionCommand("validerFinTour");
            this._btnAnnuler = new JButton("Annuler");
            this._btnAnnuler.addActionListener(new YamEvents(this._myControler));
            this._btnAnnuler.setActionCommand("annulerFinTour");
        
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(couleur);
        if(!fin){
            panel.add(this._btnVal);
            panel.add(this._btnAnnuler);
            panel.setSize(panel.getWidth(), _btnVal.getHeight());
        }
        //création de la liste déroulante
        this.setChoix();
        
        //initialisation du panel principal
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout(0, 10));
        
        //verification du dernier lancer
        boolean[] scores = _myControler.getScoresValides();
        int cpt = 0;
        for(int i = 0; i < 12; i++){
            if(!scores[i]){
                cpt++;
            }
        }
        if(cpt == 11){
            int index = 0;
            while(!scores[index]){
                index++;
            }
            JLabel message = new JLabel("Il ne reste qu'un score à ajouter:");
            message.setForeground(Color.WHITE);
            JLabel score = new JLabel(this.getDernierScore());
            score.setForeground(Color.WHITE);
            score.setHorizontalAlignment(JLabel.CENTER);
            pan.add(message, BorderLayout.NORTH);
            pan.add(score, BorderLayout.CENTER);
            
        }
        else {
            System.out.println(cpt);
            pan.add(labMessage, BorderLayout.NORTH);
            pan.add(this._cbChoix, BorderLayout.CENTER);
        }
        
        //définition de la couleur de fond de la fenêtre
        pan.setBackground(couleur);
        
        //définition du nombre de boutons utiles
        if(!fin){
            pan.add(panel, BorderLayout.SOUTH);
        }
        else pan.add(this._btnVal, BorderLayout.SOUTH);
        
        this.pack();
        this.setLocationRelativeTo(this.getParent());
    }
    
    /*
     * Retourne la sélection du joueur
     */
    public String getChoix(){
        if(this._cbChoix.getSelectedItem().getClass().equals(String.class)){
            return (String)this._cbChoix.getSelectedItem();
        }
        else return null;
    }
    
    /*
     * Permet la mise à jour des choix possibles
     */
    private void setChoix(){
        Object[] types;
        java.util.List<String> coups = new ArrayList<String>();
        for(int i = 0; i < 12; i++){
            String type = new String();
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
                    type = "+";
                    break;
                case 7:
                    type = "-";
                    break;
                case 8:
                    type = "suite";
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
                default:
                    break;
            }
            if(this._choixValides[this._noJoueur][i]){
                coups.add(type);
            }
        }
        
        types = new Object[coups.size()];
        for(int i = 0; i < coups.size(); i++){
            types[i] = coups.get(i);
        }
        
        this._cbChoix = new JComboBox(types);
    }
    
    /*
     * Retourne le dernier score à effectuer
     */
    private String getDernierScore(){
        String score = null;
        
        for(int i = 0; i < 12; i++){
            String type = new String();
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
                    type = "+";
                    break;
                case 7:
                    type = "-";
                    break;
                case 8:
                    type = "suite";
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
                default:
                    break;
            }
            if(this._choixValides[this._noJoueur][i]){
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
