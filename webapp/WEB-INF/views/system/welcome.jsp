<%@ page language="java" contentType="text/html; charset=UTF-8"  isErrorPage="true"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎页面</title>
</head>
<body>
<div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; " >
	<p style="font-size: 50px; line-height: 60px; height: 60px;">【${currentUser.username}】</p>
	<p style="font-size: 25px; line-height: 30px; height: 30px;">欢迎来到SSM管理系统</p>
  	<p style="font-size:14px;">开发人员：云过梦无痕</p>
  	<p>开发周期：2020/04/05 --- 2020/04/10（共计5天）</p>
  	<hr />
  	<h2 style="font-size:14px;">系统环境</h2>
  	<p style="font-size:14px;">系统环境：Windows Server2012</p>
	<p style="font-size:14px;">开发工具：Eclipse</p>
	<p style="font-size:14px;">Java版本：JDK 1.8</p>
	<p style="font-size:14px;">服务器：tomcat 8.5</p>
	<p style="font-size:14px;">数据库：MySQL 8.0.11</p>
	<p style="font-size:14px;">系统采用技术： Servlet+Jsp+Jdbc+dbutils+EasyUI+jQuery+Ajax+面向接口编程</p>
</div>
</body>
</html>