/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import yams.pojos.Joueur;

/**
 *
 * @author nicolas
 */

/*
 * Classe gérant le tableau des scores
 */
public class ModeleTableScore extends AbstractTableModel{
    private List<Joueur> _joueurs;
    private int _cptJoueurs;
    private boolean _rules;
    private final String[][] _entetes = {{"Joueur", "1", "2", "3", "4","5", "6", "Total", "Bonus", "Total", "+", "-", "Total", "suite", "full", "carré", "yam's", "TOTAL"},{"Joueur", "1", "2", "3", "4","5", "6", "Total", "Bonus", "Total", "brelan", "petite suite", "grande suite", "full", "carré", "yam's", "chance", "TOTAL"}};
    
    public ModeleTableScore(int nbJoueurs, boolean rule){
        super();
        this._joueurs = new ArrayList<Joueur>();
        this._cptJoueurs = 0;
        this._rules = rule;
    }

    /*
     * ajoute un joueur
     */
    public void addJoueur(Joueur j){
        this._joueurs.add(j);
        this._cptJoueurs += 1;
        
        fireTableRowsInserted(this._joueurs.size() -1, this._joueurs.size() -1);
    }
    
    /*
     * retourne un joueur
     */
    public Joueur getJoueur(int index){
        return this._joueurs.get(index);
    }
    
    /*
     * Enregistre le score du joueur indexé à l'emplacement désigné par l'index du score
     */
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
        if(this._rules){
            return this._entetes[0].length;
        }
        else{
            return this._entetes[1].length;
        }
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
        if(this._rules){
            return this._entetes[0][columnIndex];
        }
        else{
            return this._entetes[1][columnIndex];
        }
    }
}
