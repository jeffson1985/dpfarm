/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.plugin.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.dp2345.FileInfo;
import com.dp2345.Setting;
import com.dp2345.plugin.StoragePlugin;
import com.dp2345.util.SettingUtils;

/**
 * Plugin - 本地文件存储
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Component("filePlugin")
public class FilePlugin extends StoragePlugin implements ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public String getName() {
		return "本地文件存储";
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
		return null;
	}

	@Override
	public String getUninstallUrl() {
		return null;
	}

	@Override
	public String getSettingUrl() {
		return "file/setting.jhtml";
	}

	@Override
	public void upload(String path, File file, String contentType) {
		File destFile = new File(servletContext.getRealPath(path));
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl(String path) {
		Setting setting = SettingUtils.get();
		return setting.getSiteUrl() + path;
	}

	@Override
	public List<FileInfo> browser(String path) {
		Setting setting = SettingUtils.get();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		File directory = new File(servletContext.getRealPath(path));
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setUrl(setting.getSiteUrl() + path + file.getName());
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setSize(file.length());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}

}