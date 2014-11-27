/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.folder;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import yams.control.YamControl;
import yams.hightScores.pojos.Score;

/**
 *
 * @author NicolasLIBRE
 */

public class DataFolder{
    private final String LIBRE = "scoresLibres.dat";
    private final String MONTANT = "scoresMontants.dat";
    private final String DESCENDANT = "scoresDescendants.dat";
    private final String PREFERENCES = "preferences.dat";
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
    
    public void createNewBDDFiles(){
        boolean isCreated1 = false;
        boolean isCreated2 = false;
        boolean isCreated3 = false;
        boolean isCreated4 = false;
        File file1 = new File(this._dirName + LIBRE);
        File file2 = new File(this._dirName + MONTANT);
        File file3 = new File(this._dirName + DESCENDANT);
        File file4 = new File(this._dirName + PREFERENCES);
        if(!file1.exists()){
	        try {
	            isCreated1 = file1.createNewFile();
	        } catch (IOException ex) {
	            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated1){
        	System.out.println("file 1 created");
        }
        else{
        	System.out.println("file 1 not created");
        }
        
        if(!file2.exists()){
	        try {
	            isCreated2 = file2.createNewFile();
	        } catch (IOException ex) {
	            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated2){
        	System.out.println("file 2 created");
        }
        else{
        	System.out.println("file 2 not created");
        }
        
        if(!file3.exists()){
	        try {
	            isCreated3 = file3.createNewFile();
	        } catch (IOException ex) {
	            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated3){
        	System.out.println("file 3 created");
        }
        else{
        	System.out.println("file 3 not created");
        }
        
        if(!file4.exists()){
	        try {
	            isCreated4 = file4.createNewFile();
	        } catch (IOException ex) {
	            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated4){
        	System.out.println("file 4 created");
        }
        else{
        	System.out.println("file 4 not created");
        }
    }
    
    public void saveScores(List<Score> scores, int mode){
        String f;
        FileOutputStream svg = null;
        ObjectOutputStream saver = null;
        
        switch(mode){
            case 0:
                f = new String(LIBRE);
                break;
            case 1:
                f = new String(MONTANT);
                break;
            case 2:
                f = new String(DESCENDANT);
                break;
            default:
                f = new String(); //n'arrivera pas
        }
        
        try{
            try{
                File file = new File(this._dirName + f);
                file.delete();
                svg = new FileOutputStream(file);
                saver = new ObjectOutputStream(svg);
                saver.writeObject(scores);
                saver.flush();
                saver.close();
            } finally{
                saver.flush();
                saver.close();
            }
        } catch(IOException e){
            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<Score> loadScores(int mode){
        List<Score> result = null;
        String f;
        FileInputStream svg = null;
        ObjectInputStream loader = null;
        
        switch(mode){
            case 0:
                f = new String(LIBRE);
                break;
            case 1:
                f = new String(MONTANT);
                break;
            case 2:
                f = new String(DESCENDANT);
                break;
            default:
                f = new String(); //n'arrivera pas
                break;
        }
        
        try{
            try{
                svg = new FileInputStream(new File(this._dirName + f));
                    loader = new ObjectInputStream(new BufferedInputStream(svg)); 
                    result = (ArrayList<Score>)loader.readObject();
                    System.out.println("chargement de "+result.size()+" score(s)");
            }catch(EOFException e){
                System.err.println(e.toString());
                result = new ArrayList<Score>();
            }finally{
                if(loader != null){
                    loader.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(result == null){
            result = new ArrayList<Score>();
        }
        return result;
    }
    
    public void savePrefs(List<Boolean> prefs){
        FileOutputStream svg = null;
        ObjectOutputStream saver = null;
        
        try{
            try{
                File file = new File(this._dirName + PREFERENCES);
                file.delete();
                svg = new FileOutputStream(file);
                saver = new ObjectOutputStream(svg);
                saver.writeObject(prefs);
                saver.flush();
                saver.close();
            } finally{
                saver.flush();
                saver.close();
            }
        } catch(IOException e){
            Logger.getLogger(DataFolder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public List<Boolean> loadPrefs(){
        List<Boolean> result = null;
        FileInputStream svg = null;
        ObjectInputStream loader = null;
        
        try{
            try{
                svg = new FileInputStream(new File(this._dirName + PREFERENCES));
                    loader = new ObjectInputStream(new BufferedInputStream(svg)); 
                    result = (ArrayList<Boolean>)loader.readObject();
                    System.out.println("chargement de " + result.size() + " préférences");
            }catch(EOFException e){
                System.err.println(e.toString());
                result = new ArrayList<Boolean>();
            }finally{
                if(loader != null){
                    loader.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        if(result == null){
            result = new ArrayList<Boolean>();
            for(int i=0; i<3; i++){
                result.add(true);
            }
        }
        else if(result.isEmpty()){
            for(int i=0; i<3; i++){
                result.add(true);
            }
        }
        return result;
    }
}
