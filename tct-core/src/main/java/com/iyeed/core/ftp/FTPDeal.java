package com.iyeed.core.ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;

import com.aliyun.oss.ServiceException;
import com.iyeed.core.ConstantsEJS;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FTPDeal extends FTPAbstract {
	public static final int DEFAULT_SO_TIMEOUT = 5000;
	
	private Integer soTimeout = DEFAULT_SO_TIMEOUT;
	private String fileType = "BIN";
	
	private FTPClient ftpClient;
	
	private String workingDirectory = null;
	
	public FTPDeal() {
		this.setHostname(ConstantsEJS.FTPDEAL_HOSTNAME);
		this.setPort(ConstantsEJS.FTPDEAL_PORT);
		this.setUsername(ConstantsEJS.FTPDEAL_USERNAME);
		this.setPassword(ConstantsEJS.FTPDEAL_PASSWORD);
	}
	
	public FTPDeal(String hostname, Integer port, String username, String password) {
		super(hostname, port, username, password);
	}
	
	public FTPDeal(String hostname, String username, String password) {
		super(hostname, 21, username, password);
	}
	
	@Override
	public void connect() {
		try {
			this.ftpClient = new FTPClient();
			
			this.ftpClient.connect(this.hostname, this.port);
			
			this.ftpClient.login(this.username, this.password);
			
			this.ftpClient.enterLocalPassiveMode();
			
			if (!FTPReply.isPositiveCompletion(this.ftpClient.getReplyCode())) {
				this.ftpClient.disconnect();
				throw new ServiceException("FTP server refused connection.");
			}
			
			if (this.fileType.toUpperCase().equals("ASCII")) {
				this.ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			} else {
				this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
			
			this.ftpClient.setSoTimeout(this.soTimeout);
			
			if (this.workingDirectory != null) {
				this.ftpClient.changeWorkingDirectory(this.workingDirectory);
			}
		} catch (Exception ex) {
			this.ftpClient = null;
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public String cd(String remoteDirectory) {
		try {
			if (remoteDirectory != null) {
				remoteDirectory = this.cleanDirectory(this.ftpClient.printWorkingDirectory(), remoteDirectory);
				
				if (!this.ftpClient.changeWorkingDirectory(remoteDirectory)) {
					throw new ServiceException("change working directory error");
				}
			}
			
			return this.ftpClient.printWorkingDirectory();
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public String[] ls(String remoteDirectory) {
		return ls(remoteDirectory, null);
	}
	
	@Override
	public String[] ls(String remoteDirectory, final String[] filenameMatchArr) {
		String[] filenameArr = null;
		try {
			FTPFile[] ftpFiles = null;
			
			remoteDirectory = this.cleanDirectory(this.ftpClient.printWorkingDirectory(), remoteDirectory);
			
			if (filenameMatchArr != null && filenameMatchArr.length > 0) {
				ftpFiles = this.ftpClient.listFiles(remoteDirectory, new FTPFileFilter() {
					@Override
					public boolean accept(FTPFile ftpFile) {
						for (String filenameMatch : filenameMatchArr) {
							if (ftpFile.getName().matches(filenameMatch)) {
								return true;
							}
						}
						return false;
					}
				});
			} else {
				ftpFiles = this.ftpClient.listFiles(remoteDirectory);
			}
			filenameArr = new String[ftpFiles.length];
			for (int i = 0; i < ftpFiles.length; i++) {
				filenameArr[i] = ftpFiles[i].getName();
			}
			return filenameArr;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void put(String localFilename, String remoteFilename) {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(localFilename));
			this.ftpClient.storeFile(remoteFilename, bis);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		} finally {
			try {
				if(bis != null) {
					bis.close();
					bis = null;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public File get(String remoteFilename, String localFilename) {
		File localFile = null;
		BufferedOutputStream bos = null;
		try {
			String workingDirectory = this.ftpClient.printWorkingDirectory();
			
			try {
				localFile = new File(localFilename);
				bos = new BufferedOutputStream(new FileOutputStream(localFile));
				this.ftpClient.retrieveFile(remoteFilename, bos);
				
				return localFile;
			} catch (SocketTimeoutException ex) {
				logger.warn("remote file is being written, not read [" + remoteFilename + "]:" + ex.getMessage());
				
				try {
					if(bos != null) {
						bos.close();
						bos = null;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				try {
					if (localFile != null && localFile.exists()) {
						localFile.delete();
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				try {
					if (this.ftpClient != null && this.ftpClient.isConnected()) {
						this.ftpClient.disconnect();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				this.connect();
				this.cd(workingDirectory);
				
				return null;
			}
		} catch (Exception ex) {
			try {
				if(bos != null) {
					bos.close();
					bos = null;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if (localFile != null && localFile.exists()) {
					localFile.delete();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			throw new ServiceException(ex);
		} finally {
			try {
				if(bos != null) {
					bos.close();
					bos = null;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String mkdir(String remoteDirectory, String[] newDirectoryTreeNameArr) {
		try {
			remoteDirectory = this.cleanDirectory(this.ftpClient.printWorkingDirectory(), remoteDirectory);
			
			String relPath = "";
			if (newDirectoryTreeNameArr != null) {
				for (String directoryName : newDirectoryTreeNameArr) {
					relPath += "/" + directoryName;
					if (!this.isExistDir(remoteDirectory + relPath)) {
						this.ftpClient.makeDirectory(remoteDirectory + relPath);
					}
				}
				return remoteDirectory + relPath;
			} else {
				if (!this.isExistDir(remoteDirectory)) {
					this.ftpClient.makeDirectory(remoteDirectory);
				}
				return remoteDirectory;
			}
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public boolean isExistDir(String remoteDirectory) {
		try {
			String workingDirectory = this.ftpClient.printWorkingDirectory();
			
			remoteDirectory = this.cleanDirectory(this.ftpClient.printWorkingDirectory(), remoteDirectory);
			
			boolean isExist = this.ftpClient.changeWorkingDirectory(remoteDirectory);
			
			this.ftpClient.changeWorkingDirectory(workingDirectory);
			
			return isExist;
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public boolean rename(String oldRemoteFilename, String newRemoteFilename) {
		try {
			return this.ftpClient.rename(oldRemoteFilename, newRemoteFilename);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public boolean delete(String remoteFilename) {
		try {
			return this.ftpClient.deleteFile(remoteFilename);
		} catch (Exception ex) {
			throw new ServiceException(ex);
		}
	}
	
	@Override
	public void disconnect() {
		try {
			if (this.ftpClient != null && this.ftpClient.isConnected()) {
				this.ftpClient.logout();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (this.ftpClient != null && this.ftpClient.isConnected()) {
				this.ftpClient.disconnect();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.ftpClient = null;
	}
	
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public Integer getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}

	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public static void main(String[] args) {
		FTP ftp = null;
		try {
			ftp = new FTPDeal("192.168.1.235", "ftpuser", "ftpuser");
			
			ftp.connect();
			
			ftp.get("/WIN_2003_SP2.iso", "D:/a.zip");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (ftp != null) {
				ftp.disconnect();
			}
		}
	}
}
