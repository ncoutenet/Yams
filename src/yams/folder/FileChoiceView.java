/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.folder;

import java.awt.Container;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author nicolas
 */
public class FileChoiceView extends JFrame {
    private String _path;
    
    public FileChoiceView(){
        this._path = new String();
        Container panel = this.getContentPane();
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Veuillez choisir un dossier de destination et un nom pour le fichier résultat");
        int returnVal = jfc.showSaveDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            this._path = jfc.getSelectedFile().getAbsolutePath();
        }
    }
    
    public String getPath(){
        return this._path;
    }
}
