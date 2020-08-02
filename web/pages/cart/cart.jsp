<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<%--	这里的地址应该是由服务器来解析的 --%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			//删除商品项的确定函数
			$("a.deleteItem").click(function () {
				return confirm("你确定要删除【"+$(this).parent().parent().find("td:first").text()+"】吗？")
			});

			//清空购物车的确定函数
			$("#clearCart").click(function () {
				return confirm("你确定要清空购物车吗？");
			});
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
			<%@include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>

			<c:if test="${empty sessionScope.cart.items}">
<%--				购物车为空的情况--%>
				<tr>
					<td colspan="5"><a href="index.jsp">亲，当前购物车为空。快去浏览添加你喜爱的商品把。</a> </td>
				</tr>
			</c:if>

			<c:if test="${not empty sessionScope.cart.items}">
				<c:forEach items="${sessionScope.cart.items}" var="item">
					<tr>
						<td>${item.value.name}</td>
						<td>${item.value.count}</td>
						<td>${item.value.price}</td>
						<td>${item.value.totalPrice}</td>
						<td><a class="deleteItem" href="cartServlet?action=deleteItem&id=${item.value.id}">删除</a></td>
					</tr>
				</c:forEach>
			</c:if>

		</table>

		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
				<span id="clearCart" class="cart_span"><a href="cartServlet?action=cleanCart">清空购物车</a></span>
				<span class="cart_span"><a href="pages/cart/checkout.jsp">去结账</a></span>
			</div>
		</c:if>
	
	</div>
	
<%@include file="/pages/common/footer.jsp"%>
</body>
</html>