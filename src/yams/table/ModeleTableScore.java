/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.table;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import yams.JeuVue;
import yams.Joueur;

/**
 *
 * @author nicolas
 */
public class ModeleTableScore extends AbstractTableModel{
    private List<Joueur> _joueurs;
    private int _cptJoueurs;
    private final String[] _entetes = {"Joueur", "1", "2", "3", "4","5", "6", "Total", "Bonus", "Total", "+", "-", "Total", "suite", "full", "carré", "yam's", "TOTAL"};
    private JeuVue _maVue;
    
    public ModeleTableScore(int nbJoueurs, JeuVue vue){
        super();
        this._joueurs = new ArrayList<Joueur>();
        this._cptJoueurs = 0;
        this._maVue = vue;
    }

    public void addJoueur(Joueur j){
        this._joueurs.add(j);
        _cptJoueurs += 1;
        
        fireTableRowsInserted(this._joueurs.size() -1, this._joueurs.size() -1);
    }
    
    public Joueur getJoueur(int index){
        return this._joueurs.get(index);
    }
    
    public void setScoreJoueur(int index, int indexScore, int score){
        this._joueurs.get(index).setScore(indexScore, score);
        fireTableCellUpdated(index, indexScore);
    }
    
    @Override
    public int getRowCount() {
        return this._joueurs.size();
    }

    @Override
    public int getColumnCount() {
        return this._entetes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        if(colIndex == 0){
                return this._joueurs.get(rowIndex).getNom();
        }
        else if(colIndex < 18){
                return this._joueurs.get(rowIndex).getScore(colIndex-1);
        }
        else return null;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        return this._entetes[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return false;
            case 7:
                return false;
            case 8:
                return false;
            case 9:
                return false;
            case 12:
                return false;
            case 17:
                return false;
            default:
                return true;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        if(aValue != null){
            Joueur j = this._joueurs.get(rowIndex);

            if(columnIndex > 0){
                if((columnIndex != 7) && (columnIndex != 8) && (columnIndex != 9) && (columnIndex != 12) && (columnIndex != 17)){
                    j.setScore(columnIndex-1, (Integer)aValue);
                }
            }
//        }
    }
}
