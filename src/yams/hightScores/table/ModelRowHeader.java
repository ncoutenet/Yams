/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.hightScores.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Nicolas
 */
public class ModelRowHeader extends AbstractTableModel{
    private List<Integer> _data;
    private final String[] _entete = {"Classement"};
    
    public ModelRowHeader(){
        super();
        this._data = new ArrayList<Integer>(10);
    }
    
    public void addData(){
        this._data.add(this._data.size()+1);
    }

    @Override
    public int getRowCount() {
        return this._data.size();
    }

    @Override
    public int getColumnCount() {
        return this._entete.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return this._data.get(rowIndex).toString();
        }
        else{
            return null;
        }
    }
    
}
