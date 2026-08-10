/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hight_scores.table;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import yams.hight_scores.pojos.Score;

/**
 *
 * @author Nicolas
 */
public class ModeleTableHightScore extends AbstractTableModel{
    private static final Logger LOGGER = Logger.getLogger(ModeleTableHightScore.class.getName());
    private List<Score> scores;
    private final String[] entetes = {"Nom", "Score", "date"};
    
    public ModeleTableHightScore(){
        super();
        this.scores = new ArrayList<Score>();
    }
    
    /*
     * ajoute un score
     */
    public void addScore(Score score){
        this.scores.add(score);
        
        fireTableRowsInserted(this.scores.size() -1, this.scores.size() -1);
    }
    
    /*
     * supprime les scores
     */
    public void delScores(){
        this.scores.clear();
    }

    @Override
    public int getRowCount() {
        return this.scores.size();
    }

    @Override
    public int getColumnCount() {
        return this.entetes.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return this.scores.get(rowIndex).getName();
            case 1:
                return this.scores.get(rowIndex).getScore();
            case 2:
                try{
                    return this.scores.get(rowIndex).getDate();
                } catch(NullPointerException e){
                    LOGGER.warning("Pas de date à récupérer pour le score numéro " + (rowIndex+1));
                    return "";
                }
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return this.entetes[column];
    }
    
    
}
