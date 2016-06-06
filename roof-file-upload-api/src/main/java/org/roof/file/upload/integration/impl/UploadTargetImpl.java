package org.roof.file.upload.integration.impl;

import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.file.upload.integration.api.UploadType;

public class UploadTargetImpl implements UploadTarget{
	private UploadType uploadType;
	private String hosts;
	private String user;
	private String password;
	private String port;
	
	

	public UploadTargetImpl(UploadType uploadType, String hosts, String user, String password, String port) {
		super();
		this.uploadType = uploadType;
		this.hosts = hosts;
		this.user = user;
		this.password = password;
		this.port = port;
	}



	public void setUploadType(UploadType uploadType) {
		this.uploadType = uploadType;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public UploadType getUploadType() {
		// TODO Auto-generated method stub
		return uploadType;
	}

	public String getHosts() {
		// TODO Auto-generated method stub
		return hosts;
	}

	public String getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public String getPort() {
		// TODO Auto-generated method stub
		return port;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}

}
