<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
	response.sendRedirect("install/index.jsp");
%>