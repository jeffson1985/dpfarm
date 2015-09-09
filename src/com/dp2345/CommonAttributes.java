/*
 * Copyright 2013-2015 cetvision.com. All rights reserved.
 * Support: http://www.cetvision.com
 * License: http://www.cetvision.com/license
 */
package com.dp2345;

/**
 * 公共参数
 * 
 * @author CETVISION CORP
 * @version 2.0.3
 */
public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** dp2345.xml文件路径 */
	public static final String DP2345_XML_PATH = "/dp2345.xml";

	/** dp2345.properties文件路径 */
	public static final String DP2345_PROPERTIES_PATH = "/dp2345.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}