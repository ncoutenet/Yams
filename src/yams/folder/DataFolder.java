/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.folder;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class DataFolder {
    private String _OS;
    private String _dirName;
    
    public DataFolder(){
        String workingDirectory;
        this._OS = System.getProperty("os.name").toUpperCase();
        
        if(this._OS.contains("WIN")){
            workingDirectory = System.getenv("AppData");
            this._dirName = workingDirectory + "/yams/";
        }
        else{
            workingDirectory = System.getProperty("user.home");
            if(this._OS.contains("MAC")){
                workingDirectory += "/Library/Application Support";
                this._dirName = workingDirectory + "/yams/";
            }
            else{
                this._dirName = workingDirectory + "/.yams/";
            }
        }
    }
    
    public boolean createDataFolder(){
        boolean isCreated = false;
        File dir = new File(this._dirName);
        
        isCreated = dir.mkdir();
        
        return isCreated;
    }
    
    public boolean createNewBDDFile(){
        boolean isCreated = false;
        File file = new File(this._dirName + "scores.xml");
        try {
            isCreated = file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isCreated;
    }
}
