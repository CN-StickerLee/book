<%--
  Created by IntelliJ IDEA.
  User: huashi659
  Date: 2020/7/30
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme()
            + "://"
            + request.getServerName()
            + ":"
            + request.getServerPort()
            + request.getContextPath()
            +"/";

//    在当前页面设置当前基础路径的值，用于当前页面引用
    pageContext.setAttribute("basePath",basePath);
    //StringBuffer basePath = request.getRequestURL();
%>
<base href="<%=basePath%>" />
<link type="text/css" rel="stylesheet" href="static/css/style.css" />
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>