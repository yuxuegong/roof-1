package org.roof.file.upload.integration.impl;

import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadFileOperate;
import org.roof.file.upload.integration.api.UploadTarget;
import org.springframework.util.Assert;

public class UploadFileImpl<T> implements UploadFile<T> {
	
	private UploadTarget target;
	
	private String fileDirectory;
	
	private T file;
	
	
	private String fileName;
	
	private UploadFileOperate operate;
	
	

	public UploadFileImpl(UploadTarget target, String fileDirectory, T file, String fileName,UploadFileOperate operate) {
		Assert.notNull(target, "target must not be null");
		Assert.notNull(fileName, "fileName must not be null");
		Assert.notNull(operate, "operate must not be null");

		this.target = target;
		this.fileDirectory = fileDirectory;
		this.file = file;
		this.fileName = fileName;
		this.operate = operate;
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

	@Override
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public UploadFileOperate getOperate() {
		return this.operate;
	}

	public void setOperate(UploadFileOperate operate) {
		this.operate = operate;
	}

	
}
