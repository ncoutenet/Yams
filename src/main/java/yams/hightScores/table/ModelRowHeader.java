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
    private List<Integer> data;
    private final String[] entete = {"Classement"};
    
    public ModelRowHeader(){
        super();
        this.data = new ArrayList<Integer>(10);
        for(int i=0; i<10; i++){
            this.addData();
        }
    }
    
    private void addData(){
        this.data.add(this.data.size()+1);
    }

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.entete.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return this.data.get(rowIndex).toString();
        }
        else{
            return null;
        }
    }
    
}
