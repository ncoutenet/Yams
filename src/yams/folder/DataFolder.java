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
public class DataFolder{
    private String _OS;
    private String _dirName;
    private boolean _created;
    
    public DataFolder(){
    	this._created = false;
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
        
        File f = new File(this._dirName);
        if(f.exists()){
        	this._created = true;
        }
    }
    
    public void createDataFolder(){
        boolean isCreated = false;
        if(!this._created){
	        File dir = new File(this._dirName);
	        isCreated = dir.mkdir();
        }
        if(isCreated){
        	System.out.println("folder created");
        }
        else{
        	System.out.println("folder not created");
        }
    }
    
    public void createNewBDDFile(){
        boolean isCreated = false;
        File file = new File(this._dirName + "scores.xml");
        if(!file.exists()){
	        try {
	            isCreated = file.createNewFile();
	        } catch (IOException ex) {
	            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated){
        	System.out.println("file created");
        }
        else{
        	System.out.println("file not created");
        }
    }
}
