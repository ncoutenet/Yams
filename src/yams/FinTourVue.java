/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author nicolas
 */
public class FinTourVue extends JDialog{
    private YamControl _myControler;
    
    private JComboBox _cbChoix;
    private boolean[][] _choixValides;
    private int _noJoueur;
    private JButton _btnVal;

    public FinTourVue(boolean[][] choix, int joueur, YamControl yc){
        super.setTitle("Fin Du Tour");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this._myControler = yc;
        
        //initialisation des variables locales
        this._choixValides = choix;
        this._noJoueur = joueur;
        
        //création du message
        JLabel labMessage = new JLabel("Choisissez où vous voulez placer les points");
        
        //création du bouton valider
        this._btnVal = new JButton("Valider");
        this._btnVal.addActionListener(new YamEvents(this._myControler));
        this._btnVal.setActionCommand("validerFinTour");
        //création de la liste déroulante
        this.setChoix();
        
        
        //assemblage de la fenêtre
        Container pan = this.getContentPane();
        pan.setLayout(new BorderLayout());
        pan.add(labMessage, BorderLayout.NORTH);
        pan.add(this._cbChoix, BorderLayout.CENTER);
        pan.add(this._btnVal, BorderLayout.SOUTH);
        
        this.pack();
    }
    
    public String getChoix(){
        if(this._cbChoix.getSelectedItem().getClass().equals(String.class)){
            return (String)this._cbChoix.getSelectedItem();
        }
        else return null;
    }
    
    private void setChoix(){
        Object[] types = new Object[12];
        int cpt = 0;
            int j;
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
                types[cpt] = type;
                cpt++;
            }
        }
        this._cbChoix = new JComboBox(types);
    }
    
    public void setAffichage(boolean enable){
        this.setVisible(enable);
    }
}
