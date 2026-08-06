/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import yams.hightScores.pojos.Score;

/**
 *
 * @author Nicolas
 */
public class ModeleTableHightScore extends AbstractTableModel{
    private List<Score> _scores;
    private final String[] _entetes = {"Nom", "Score", "date"};
    
    public ModeleTableHightScore(){
        super();
        this._scores = new ArrayList<Score>();
    }
    
    /*
     * ajoute un score
     */
    public void addScore(Score score){
        this._scores.add(score);
        
        fireTableRowsInserted(this._scores.size() -1, this._scores.size() -1);
    }
    
    /*
     * supprime les scores
     */
    public void delScores(){
        this._scores.clear();
    }

    @Override
    public int getRowCount() {
        return this._scores.size();
    }

    @Override
    public int getColumnCount() {
        return this._entetes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return this._scores.get(rowIndex).getName();
            case 1:
                return this._scores.get(rowIndex).getScore();
            case 2:
                try{
                    return this._scores.get(rowIndex).getDate();
                } catch(NullPointerException e){
                    System.err.println("Pas de date à récupérer pour le score numéro " + (rowIndex+1));
                    return "";
                }
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return this._entetes[column];
    }
    
    
}
