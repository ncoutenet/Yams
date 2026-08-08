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
    private static final String TOTAL = "Total";
    private transient List<Joueur> joueurs;
    private boolean rules;
    private final String[][] entetes = {{"Joueur", "1", "2", "3", "4","5", "6", TOTAL, "Bonus", TOTAL, "+", "-", TOTAL, "suite", "full", "carré", "yam's", "TOTAL"},{"Joueur", "1", "2", "3", "4","5", "6", TOTAL, "Bonus", TOTAL, "brelan", "petite suite", "grande suite", "full", "carré", "yam's", "chance", "TOTAL"}};

    public ModeleTableScore(boolean rule){
        super();
        this.joueurs = new ArrayList<Joueur>();
        this.rules = rule;
    }

    /*
     * ajoute un joueur
     */
    public void addJoueur(Joueur j){
        this.joueurs.add(j);

        fireTableRowsInserted(this.joueurs.size() -1, this.joueurs.size() -1);
    }
    
    /*
     * retourne un joueur
     */
    public Joueur getJoueur(int index){
        return this.joueurs.get(index);
    }
    
    /*
     * Enregistre le score du joueur indexé à l'emplacement désigné par l'index du score
     */
    public void setScoreJoueur(int index, int indexScore, int score){
        this.joueurs.get(index).setScore(indexScore, score);
        fireTableCellUpdated(index, indexScore);
    }
    
    @Override
    public int getRowCount() {
        return this.joueurs.size();
    }

    @Override
    public int getColumnCount() {
        if(this.rules){
            return this.entetes[0].length;
        }
        else{
            return this.entetes[1].length;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        if(colIndex == 0){
                return this.joueurs.get(rowIndex).getNom();
        }
        else if(colIndex < 18){
                return this.joueurs.get(rowIndex).getScore(colIndex-1);
        }
        else return null;
    }
    
    @Override
    public String getColumnName(int columnIndex){
        if(this.rules){
            return this.entetes[0][columnIndex];
        }
        else{
            return this.entetes[1][columnIndex];
        }
    }
}
