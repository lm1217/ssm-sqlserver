package com.iyeed.core.ftp;

import java.io.File;

public interface FTP {
	public void connect();
	
	public String cleanDirectory(String workingDirectory, String optionDirectory);
	
	public String cd(String remoteDirectory);
	
	public String[] ls(String remoteDirectory);
	
	public String[] ls(String remoteDirectory, final String[] filenameMatchArr);
	
	public void put(String localFilename, String remoteFilename);
	
	public File get(String remoteFilename, String localFilename);
	
	public String mkdir(String remoteDirectory, String[] newDirectoryTreeNameArr);
	
	public boolean isExistDir(String remoteDirectory);
	
	public boolean rename(String oldRemoteFilename, String newRemoteFilename);
	
	public boolean delete(String remoteFilename);
	
	public void disconnect();
}
