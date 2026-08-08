/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
    public static final int BLEU = 3;
    
    private Color rate;
    private Color valide;
    private Color titre;
    private int[][] colorTable;
    
    public ColorTab(int nbRow, int nbCol){
        this.rate = new Color(255, 128, 128); //rouge clair
        this.valide = new Color(132, 240, 110); //vert clair
        this.titre = new Color(191, 191, 191); //gris clair
        this.colorTable = new int[nbRow][nbCol];
    }
    
    @Override
    public Component getTableCellRendererComponent (JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
          super.getTableCellRendererComponent (table,value,isSelected,hasFocus,row,column); 
          
          switch(this.colorTable[row][column]){
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
              case ColorTab.BLEU:
                  this.setBackground(new Color(235, 240, 249));
                  break;
              default:
                  break; //n'arrivera pas
          }
          this.setHorizontalAlignment(SwingConstants.CENTER);
          return this;
    }
    
    /*
     * met à jour le tableau interne des couleurs
     */
    public void setCouleurs(int lig, int col, int code){
        this.colorTable[lig][col] = code;
    }
    
    /*
     * Réinitialise le tableau interne des couleurs
     */
    public void clear(){
        this.colorTable = new int[this.colorTable.length][this.colorTable[0].length];
    }
}
