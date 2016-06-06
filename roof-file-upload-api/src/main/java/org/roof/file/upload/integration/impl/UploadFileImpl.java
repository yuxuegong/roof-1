package org.roof.file.upload.integration.impl;

import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadTarget;
import org.springframework.integration.file.support.FileExistsMode;

public class UploadFileImpl<T> implements UploadFile<T> {
	
	private UploadTarget target;
	
	private String fileDirectory;
	
	private T file;
	
	private FileExistsMode mode;
	
	

	public UploadFileImpl(UploadTarget target, String fileDirectory, T file, FileExistsMode mode) {
		super();
		this.target = target;
		this.fileDirectory = fileDirectory;
		this.file = file;
		this.mode = mode;
	}

	public UploadTarget getTarget() {
		// TODO Auto-generated method stub
		return target;
	}

	public String getFileDirectory() {
		// TODO Auto-generated method stub
		return fileDirectory;
	}

	public T getFile() {
		// TODO Auto-generated method stub
		return file;
	}

	public void setTarget(UploadTarget target) {
		this.target = target;
	}

	public void setFileDirectory(String fileDirectory) {
		this.fileDirectory = fileDirectory;
	}

	public void setFile(T file) {
		this.file = file;
	}

	public FileExistsMode getMode() {
		// TODO Auto-generated method stub
		return this.mode;
	}

	public void setMode(FileExistsMode mode) {
		this.mode = mode;
	}
	
	

}
