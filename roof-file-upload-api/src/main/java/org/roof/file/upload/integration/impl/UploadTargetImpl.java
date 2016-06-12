package org.roof.file.upload.integration.impl;

import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.file.upload.integration.api.UploadType;
import org.springframework.util.Assert;

public class UploadTargetImpl implements UploadTarget{
	private UploadType uploadType;
	private String hosts;
	private String user;
	private String password;
	private int port;
	private String remoteDirectory;
	private String remoteFileSeparator ;//linux default /




		public UploadTargetImpl(UploadType uploadType, String hosts, String user, String password, int port,String remoteDirectory,String remoteFileSeparator) {
		Assert.notNull(uploadType, "uploadType must not be null");
		Assert.notNull(hosts, "hosts must not be null");
		Assert.notNull(user, "user must not be null");
		Assert.notNull(password, "password must not be null");
		Assert.notNull(port, "port must not be null");
		Assert.notNull(remoteDirectory, "remoteDirectory must not be null");
		Assert.notNull(remoteFileSeparator, "remoteDirectory must not be null");
		this.uploadType = uploadType;
		this.hosts = hosts;
		this.user = user;
		this.password = password;
		this.port = port;
		this.remoteDirectory = remoteDirectory;
		this.remoteFileSeparator = remoteFileSeparator;
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

	public void setPort(int port) {
		this.port = port;
	}

	public UploadType getUploadType() {
		return uploadType;
	}

	public String getHosts() {
		return hosts;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String getRemoteDirectory() {
		return this.remoteDirectory;
	}

	@Override
	public String getRemoteFileSeparator() {
		return this.remoteFileSeparator;
	}

	public void setHosts(String hosts) {
		this.hosts = hosts;
	}




}
