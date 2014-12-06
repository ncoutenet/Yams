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
          if (this._colorTable[row][column] == -1) { 
              this.setBackground(rate);
          }
          else {
              if (this._colorTable[row][column] == 1) { 
                   this.setBackground(valide);
              }
              else {
                  if (this._colorTable[row][column] == 2){
                      this.setBackground(titre);
                  }
                  else {
                   this.setBackground(Color.white);
                  }
              }      
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
