package com.listacl.ncoutenet.views.files;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter {
	//description et extention acceptée par le filtre
	private String _description;
	private String _extension;
	
	public MyFileFilter(String description, String extension){
		if(description == null || extension == null){
			throw new NullPointerException("La description (ou l'extension) ne peut être nulle");
		}
		this._description = description;
		this._extension = extension;
	}
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		String fileName = f.getName().toLowerCase();
		
		return fileName.endsWith(_extension);
	}

	@Override
	public String getDescription() {
		return _description;
	}

}
