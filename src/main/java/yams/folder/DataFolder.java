/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package yams.folder;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import yams.ModeJeu;
import yams.hight_scores.pojos.Score;

/**
 *
 * @author NicolasLIBRE
 */

public class DataFolder{
    private static final Logger LOGGER = Logger.getLogger(DataFolder.class.getName());
    private static final String LIBRE = "scoresLibres.dat";
    private static final String MONTANT = "scoresMontants.dat";
    private static final String DESCENDANT = "scoresDescendants.dat";
    private static final String SEC = "scoresSecs.dat";
    private static final String PREFERENCES = "preferences.dat";
    private static final String TAG_MODE_CLOSE = "\t</Mode>\r\n";
    private static final String SCORE_OPEN = "\t\t<Score name=\"";
    private static final String ATTR_VALUE = "\" value=\"";
    private static final String ATTR_DATE = "\" date=\"";
    private static final String SELF_CLOSE = "\" />";
    private String os;
    private String dirName;
    private boolean created;

    public DataFolder(){
    	this.created = false;
        String workingDirectory;
        this.os = System.getProperty("os.name").toUpperCase();

        if(this.os.contains("WIN")){
            workingDirectory = System.getenv("AppData");
            this.dirName = workingDirectory + "/yams/";
        }
        else{
            workingDirectory = System.getProperty("user.home");
            if(this.os.contains("MAC")){
                workingDirectory += "/Library/Application Support";
                this.dirName = workingDirectory + "/yams/";
            }
            else{
                this.dirName = workingDirectory + "/.yams/";
            }
        }

        File f = new File(this.dirName);
        if(f.exists()){
        	this.created = true;
        }
    }

    public void createDataFolder(){
        boolean isCreated = false;
        if(!this.created){
	        File dir = new File(this.dirName);
	        isCreated = dir.mkdir();
        }
        if(isCreated){
        	LOGGER.info("folder created");
        }
        else{
        	LOGGER.info("folder not created");
        }
    }

    public void createNewBDDFiles(){
        createFileIfMissing(new File(this.dirName + LIBRE), "file 1");
        createFileIfMissing(new File(this.dirName + MONTANT), "file 2");
        createFileIfMissing(new File(this.dirName + DESCENDANT), "file 3");
        createFileIfMissing(new File(this.dirName + PREFERENCES), "file 4");
        createFileIfMissing(new File(this.dirName + SEC), "file 5");
    }

    private void createFileIfMissing(File file, String label){
        boolean isCreated = false;
        if(!file.exists()){
	        try {
	            isCreated = file.createNewFile();
	        } catch (IOException ex) {
	            LOGGER.log(Level.SEVERE, null, ex);
	        }
        }
        if(isCreated){
        	LOGGER.log(Level.INFO, "{0} created", label);
        }
        else{
        	LOGGER.log(Level.INFO, "{0} not created", label);
        }
    }

    public void saveScores(List<Score> scores, ModeJeu mode){
        String f;

        switch(mode){
            case LIBRE:
                f = LIBRE;
                break;
            case MONTANT:
                f = MONTANT;
                break;
            case DESCENDANT:
                f = DESCENDANT;
                break;
            case SEC:
                f = SEC;
                break;
            default:
                f = ""; //n'arrivera pas
        }

        File file = new File(this.dirName + f);
        try{
            boolean deleted = Files.deleteIfExists(file.toPath());
            if(LOGGER.isLoggable(Level.FINE)){
                LOGGER.fine(deleted ? "ancien fichier supprimé" : "aucun fichier existant à supprimer");
            }
            try(FileOutputStream svg = new FileOutputStream(file);
                ObjectOutputStream saver = new ObjectOutputStream(svg)){
                saver.writeObject(scores);
            }
        } catch(IOException e){
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public List<Score> loadScores(ModeJeu mode){
        List<Score> result;
        String f;

        switch(mode){
            case LIBRE:
                f = LIBRE;
                break;
            case MONTANT:
                f = MONTANT;
                break;
            case DESCENDANT:
                f = DESCENDANT;
                break;
            case SEC:
                f = SEC;
                break;
            default:
                f = ""; //n'arrivera pas
                break;
        }

        try(ObjectInputStream loader = new ScoreCompatibleObjectInputStream(new BufferedInputStream(new FileInputStream(new File(this.dirName + f))))){
            result = (ArrayList<Score>)loader.readObject();
            if(LOGGER.isLoggable(Level.INFO)){
                LOGGER.log(Level.INFO, "chargement de {0} score(s)", result.size());
            }
        }catch(EOFException e){
            LOGGER.warning(e.toString());
            result = new ArrayList<>();
        }catch(IOException | ClassNotFoundException e){
            LOGGER.log(Level.SEVERE, null, e);
            result = new ArrayList<>();
        }

        return result;
    }

    public void savePrefs(List<Boolean> prefs){
        File file = new File(this.dirName + PREFERENCES);
        try{
            boolean deleted = Files.deleteIfExists(file.toPath());
            if(LOGGER.isLoggable(Level.FINE)){
                LOGGER.fine(deleted ? "ancien fichier supprimé" : "aucun fichier existant à supprimer");
            }
            try(FileOutputStream svg = new FileOutputStream(file);
                ObjectOutputStream saver = new ObjectOutputStream(svg)){
                saver.writeObject(prefs);
            }
        } catch(IOException e){
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public List<Boolean> loadPrefs(){
        List<Boolean> result;

        try(ObjectInputStream loader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(this.dirName + PREFERENCES))))){
            result = (ArrayList<Boolean>)loader.readObject();
            if(LOGGER.isLoggable(Level.INFO)){
                LOGGER.log(Level.INFO, "chargement de {0} préférences", result.size());
            }
        }catch(EOFException e){
            LOGGER.warning(e.toString());
            result = new ArrayList<>();
        }catch(IOException | ClassNotFoundException e){
            LOGGER.log(Level.SEVERE, null, e);
            result = new ArrayList<>();
        }

        if(result.isEmpty()){
            for(int i=0; i<3; i++){
                result.add(true);
            }
        }
        return result;
    }
    /**
     * Exporte les scores
     * @param extantion
     * @param path
     * @param libres
     * @param montants
     * @param descendants
     * @param secs
     */
    public void exportScores(String extantion, String path, List<Score> libres, List<Score> montants, List<Score> descendants, List<Score> secs){
        if(extantion.equals("*.csv")){
            this.writeCSVFile(path, libres, montants, descendants, secs);
            return;
        }
        if(extantion.equals("*.cvs")){
            this.writeXMLFile(path, libres, montants, descendants, secs);
        }
    }

    /**
     * Ecrit le fichier csv
     * @param path
     * @param libres
     * @param montants
     * @param descendants
     * @param secs
     */
    private void writeCSVFile(String path, List<Score> libres, List<Score> montants, List<Score> descendants, List<Score> secs){
        if(!path.endsWith(".csv")){
            path = path + ".csv";
        }
        File f = new File(path);

        try(FileWriter fw = new FileWriter(f)){
            fw.write("Meilleurs scores du yam's;;;;;;;;;;;;\r\n");
            fw.write(";;;;;;;;;;;;\r\n");
            fw.write(";Libre;;;;Montant;;;;Descendant;;;;Sec;;\r\n");

            for(int i=0; i<10; i++){
                fw.write(";");
                if(i<libres.size()){
                    fw.write(libres.get(i).getName()+";"+libres.get(i).getScore()+";"+libres.get(i).getDate()+";;");
                }
                else{
                    fw.write(";;;;");
                }

                if(i<montants.size()){
                    fw.write(montants.get(i).getName()+";"+montants.get(i).getScore()+";"+montants.get(i).getDate()+";;");
                }
                else{
                    fw.write(";;;;");
                }

                if(i<descendants.size()){
                    fw.write(descendants.get(i).getName()+";"+descendants.get(i).getScore()+";"+descendants.get(i).getDate()+";;");
                }
                else{
                    fw.write(";;;;");
                }

                if(i<secs.size()){
                    fw.write(secs.get(i).getName()+";"+secs.get(i).getScore()+";"+secs.get(i).getDate()+";");
                }
                else{
                    fw.write(";;;");
                }
                fw.write("\r\n");
            }
        }catch(IOException e){
            LOGGER.log(Level.SEVERE, "Erreur lors de l'export", e);
        }
    }

    private void writeXMLFile(String path, List<Score> libres, List<Score> montants, List<Score> descendants, List<Score> secs){
        if(!path.endsWith(".xml")){
            path = path + ".xml";
        }
        File f = new File(path);

        try(FileWriter fw = new FileWriter(f)){
            fw.write("<Scores>\r\n");

            fw.write("\t<Mode type=\"Libre\">\r\n");
            for(int i=0; i<libres.size(); i++){
                fw.write(SCORE_OPEN+libres.get(i).getName()+ATTR_VALUE+libres.get(i).getScore()+ATTR_DATE+libres.get(i).getDate()+SELF_CLOSE);
                fw.write("\r\n");
            }
            fw.write(TAG_MODE_CLOSE);

            fw.write("\t<Mode type=\"Montant\">\r\n");
            for(int i=0; i<montants.size(); i++){
                fw.write(SCORE_OPEN+montants.get(i).getName()+ATTR_VALUE+montants.get(i).getScore()+ATTR_DATE+montants.get(i).getDate()+SELF_CLOSE);
                fw.write("\r\n");
            }
            fw.write(TAG_MODE_CLOSE);

            fw.write("\t<Mode type=\"Descedant\">\r\n");
            for(int i=0; i<descendants.size(); i++){
                fw.write(SCORE_OPEN+descendants.get(i).getName()+ATTR_VALUE+descendants.get(i).getScore()+ATTR_DATE+descendants.get(i).getDate()+SELF_CLOSE);
                fw.write("\r\n");
            }
            fw.write(TAG_MODE_CLOSE);

            fw.write("\t<Mode type=\"Sec\">\r\n");
            for(int i=0; i<secs.size(); i++){
                fw.write(SCORE_OPEN+secs.get(i).getName()+ATTR_VALUE+secs.get(i).getScore()+ATTR_DATE+secs.get(i).getDate()+SELF_CLOSE);
                fw.write("\r\n");
            }
            fw.write(TAG_MODE_CLOSE);

            fw.write("</Scores>");
        }catch(IOException e){
            LOGGER.log(Level.SEVERE, "Erreur lors de l'export", e);
        }
    }

    // redirige les .dat sérialisés avant le renommage du paquet yams.hightScores -> yams.hight_scores vers la classe Score actuelle
    // visibilité paquet (au lieu de private) pour être exercée directement par DataFolderTest
    static final class ScoreCompatibleObjectInputStream extends ObjectInputStream {
        private static final String OLD_SCORE_CLASS_NAME = "yams.hightScores.pojos.Score";

        ScoreCompatibleObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if(OLD_SCORE_CLASS_NAME.equals(desc.getName())){
                return Score.class;
            }
            return super.resolveClass(desc);
        }
    }
}
