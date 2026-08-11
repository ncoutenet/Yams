package yams.folder;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter {
	//description et extention acceptée par le filtre
	private String description;
	private String extension;
	
	public MyFileFilter(String description, String extension){
		if(description == null || extension == null){
			throw new NullPointerException("La description (ou l'extension) ne peut être nulle");
		}
		this.description = description;
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		String fileName = f.getName().toLowerCase();
		
		return fileName.endsWith("."+this.extension);
	}

	@Override
	public String getDescription() {
		return description;
	}

}
