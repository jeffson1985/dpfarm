/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dp2345.FileInfo;
import com.dp2345.Message;
import com.dp2345.FileInfo.FileType;
import com.dp2345.FileInfo.OrderType;
import com.dp2345.service.FileService;
import com.dp2345.util.JsonUtils;

/**
 * Controller - 文件处理
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
@Controller("adminFileController")
@RequestMapping("/admin/file")
public class FileController extends BaseController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	/**
	 * 上传
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html; charset=UTF-8")
	public void upload(FileType fileType, MultipartFile file, HttpServletResponse response) {
		Map<String, Object> data = new HashMap<String, Object>();
		if (!fileService.isValid(fileType, file)) {
			data.put("message", Message.warn("admin.upload.invalid"));
		} else {
			String url = fileService.upload(fileType, file, false);
			if (url == null) {
				data.put("message", Message.warn("admin.upload.error"));
			} else {
				data.put("message", SUCCESS_MESSAGE);
				data.put("url", url);
			}
		}
		try {
			response.setContentType("text/html; charset=UTF-8");
			JsonUtils.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 浏览
	 */
	@RequestMapping(value = "/browser", method = RequestMethod.GET)
	public @ResponseBody
	List<FileInfo> browser(String path, FileType fileType, OrderType orderType) {
		return fileService.browser(path, fileType, orderType);
	}

}