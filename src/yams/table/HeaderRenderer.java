/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author nicolas
 */

/*
 * Classe permettant de redéfinir le comportement du header du tableau des scores
 */

/*
 * TODO remettre le rendu visuel d'origine en gardant le centrage du texte
 */
public class HeaderRenderer extends DefaultTableCellRenderer{
    
    @Override
    public Component getTableCellRendererComponent (JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
          super.getTableCellRendererComponent (table,value,isSelected,hasFocus,row,column); 
          this.setHorizontalAlignment(JLabel.CENTER);
          
          return this;
    }
}
