/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.plugin.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import com.dp2345.FileInfo;
import com.dp2345.entity.PluginConfig;
import com.dp2345.plugin.StoragePlugin;

/**
 * Plugin - FTP
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("ftpPlugin")
public class FtpPlugin extends StoragePlugin {

	@Override
	public String getName() {
		return "FTP存储";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "CETVISION CORP";
	}

	@Override
	public String getSiteUrl() {
		return "http://www.cetvision.com";
	}

	@Override
	public String getInstallUrl() {
		return "ftp/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "ftp/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "ftp/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String host = pluginConfig.getAttribute("host");
			Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			String username = pluginConfig.getAttribute("username");
			String password = pluginConfig.getAttribute("password");
			FTPClient ftpClient = new FTPClient();
			InputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
					String directory = StringUtils.substringBeforeLast(path, "/");
					String filename = StringUtils.substringAfterLast(path, "/");
					if (!ftpClient.changeWorkingDirectory(directory)) {
						String[] paths = StringUtils.split(directory, "/");
						String p = "/";
						ftpClient.changeWorkingDirectory(p);
						for (String s : paths) {
							p += s + "/";
							if (!ftpClient.changeWorkingDirectory(p)) {
								ftpClient.makeDirectory(s);
								ftpClient.changeWorkingDirectory(p);
							}
						}
					}
					ftpClient.storeFile(filename, inputStream);
					ftpClient.logout();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	@Override
	public String getUrl(String path) {
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			return urlPrefix + path;
		}
		return null;
	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		PluginConfig pluginConfig = getPluginConfig();
		if (pluginConfig != null) {
			String host = pluginConfig.getAttribute("host");
			Integer port = Integer.valueOf(pluginConfig.getAttribute("port"));
			String username = pluginConfig.getAttribute("username");
			String password = pluginConfig.getAttribute("password");
			String urlPrefix = pluginConfig.getAttribute("urlPrefix");
			FTPClient ftpClient = new FTPClient();
			try {
				ftpClient.connect(host, port);
				ftpClient.login(username, password);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && ftpClient.changeWorkingDirectory(path)) {
					for (FTPFile ftpFile : ftpClient.listFiles()) {
						FileInfo fileInfo = new FileInfo();
						fileInfo.setName(ftpFile.getName());
						fileInfo.setUrl(urlPrefix + path + ftpFile.getName());
						fileInfo.setIsDirectory(ftpFile.isDirectory());
						fileInfo.setSize(ftpFile.getSize());
						fileInfo.setLastModified(ftpFile.getTimestamp().getTime());
						fileInfos.add(fileInfo);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException e) {
					}
				}
			}
		}
		return fileInfos;
	}

}