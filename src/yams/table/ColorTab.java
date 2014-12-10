/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author nicolas
 */

/*
 * Classe permettant de colorier les cellules du tableau des scores suivant ce que fait le joueur
 */
public class ColorTab extends DefaultTableCellRenderer{
    public static final int VERT = 1;
    public static final int ROUGE = -1;
    public static final int GRIS = 2;
    public static final int BLANC = 0;
    
    private Color rate;
    private Color valide;
    private Color titre;
    private int[][] _colorTable;
    
    public ColorTab(int[][] couleurs, int nbJoueurs){
        this.rate = new Color(255, 128, 128); //rouge clair
        this.valide = new Color(132, 240, 110); //vert clair
        this.titre = new Color(191, 191, 191); //gris clair
        this._colorTable = new int[nbJoueurs][18];
        
        for(int i = 0; i < nbJoueurs; i++){
            for(int j = 0; j < 18; j++){
                this._colorTable[i][j] = couleurs[i][j];
            }
        }
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
          super.getTableCellRendererComponent (table,value,isSelected,hasFocus,row,column); 
          
          switch(this._colorTable[row][column]){
              case ColorTab.ROUGE: 
                  this.setBackground(rate);
                  break;
              case ColorTab.VERT:
                  this.setBackground(valide);
                  break;
              case ColorTab.GRIS:
                  this.setBackground(titre);
                  break;
              case ColorTab.BLANC:
                  this.setBackground(Color.white);
                  break;
              default:
                  break; //n'arrivera pas
          }
          this.setHorizontalAlignment(JLabel.CENTER);
          return this;
    }
    
    /*
     * met à jour le tableau interne des couleurs
     */
    public void setCouleurs(int lig, int col, int code){
        this._colorTable[lig][col] = code;
    }
}
