/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import yams.control.YamControl;
import yams.hightScores.pojos.Score;

/**
 *
 * @author Nicolas
 */
public class DataFolder{
    private YamControl _myControler;
    
    private String _OS;
    private String _dirName;
    private boolean _created;
    
    public DataFolder(YamControl ctrl){
        this._myControler = ctrl;
        
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
    
    public void saveScores(List<Score> scores){
        File svg = null;
        FileWriter writer = null;
        
        try{
            svg = new File(this._dirName+"scores.xml"); //fichier de sauvegarde
            svg.createNewFile(); //remise à zéro du fichier
            writer = new FileWriter(svg); //écrivain
            try{
                writer.write("<?xml version='1.0' encoding='utf-8'?>\n"); //encodage du document
                writer.write("<scores>\n");
                for(int i=0; i<scores.size(); i++){
                    writer.write("\t<joueur name=\""+scores.get(i).getName()+"\" score=\""+scores.get(i).getScore()+"\" />\n");
                }
                writer.write("</scores>");
            } finally{
                writer.close();
            }
        } catch(Exception e){
            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<Score> loadScores(){
        List<Score> result = new ArrayList<Score>();
        try {
            Scanner scan = new Scanner(new File(this._dirName+"scores.xml"));
            for(int i=0; i<2; i++){ //on ignore les deux premières lignes
                scan.nextLine();
            }
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(!line.equals("</scores>")){ //on ignore la dernière ligne
                    String[] subLine = line.split("\"");
                    result.add(new Score(subLine[1], Integer.decode(subLine[3]).intValue()));
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return result;
    }
}
