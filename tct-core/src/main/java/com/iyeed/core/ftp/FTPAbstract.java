package com.iyeed.core.ftp;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FTPAbstract implements FTP {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String hostname;
	protected Integer port;
	
	protected String username;
	protected String password;
	
	public FTPAbstract() {
		
	}
	
	public FTPAbstract(String hostname, Integer port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public FTPAbstract(String hostname, String username, String password) {
		this.hostname = hostname;
		this.username = username;
		this.password = password;
	}
	
	public String cleanDirectory(String workingDirectory, String optionDirectory) {
		if (workingDirectory == null) {
			workingDirectory = "";
		}
		workingDirectory = StringUtils.replace(workingDirectory, "\\", "/").trim();
		if (workingDirectory.indexOf("/") != 0) {
			workingDirectory = "/" + workingDirectory;
		}
		if (workingDirectory.lastIndexOf("/") > 0 && workingDirectory.lastIndexOf("/") == workingDirectory.length() - 1) {
			workingDirectory = workingDirectory.substring(0, workingDirectory.lastIndexOf("/"));
		}
		
		if (optionDirectory == null) {
			return workingDirectory;
		} else {
			optionDirectory = StringUtils.replace(optionDirectory, "\\", "/").trim();
			if (optionDirectory.lastIndexOf("/") > 0 && optionDirectory.lastIndexOf("/") == optionDirectory.length() - 1) {
				optionDirectory = optionDirectory.substring(0, optionDirectory.lastIndexOf("/"));
			}
			if (optionDirectory.indexOf("/") == 0) {
				return optionDirectory;
			} else {
				return workingDirectory + (workingDirectory.equals("/") ? "" : "/") + optionDirectory;
			}
		}
	}
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
