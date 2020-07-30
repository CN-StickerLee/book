<%--
  Created by IntelliJ IDEA.
  User: huashi659
  Date: 2020/7/30
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
<%--    a标签调用的是doGet方法，在Servlet需定义doGet方法。   --%>
<%--    <a href="manager/bookServlet?action=list">图书管理</a>--%>
    <a href="manager/bookServlet?action=page">图书管理</a>
    <a href="pages/manager/order_manager.jsp">订单管理</a>
    <a href="index.jsp">返回商城</a>
</div>
