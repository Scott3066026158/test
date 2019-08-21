<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息管理系统</title>
<script src="jquery.min.js" type="text/javascript"></script>
<script>
	function showtable() {
		var url = "UserManagement?page=All"; 
		 window.location.replace(url);
	}
	
	function showtable2() {
		var url = "UserManagement?page=KYC"; 
		 window.location.replace(url);
	}
	function GotoKYC(traderID) {
		var url = "KYCServlet?traderID=" + traderID; 
		 window.location.replace(url);
	}
	$(document).ready(function () {
		if((${page}) != 500){
			if((${page}) == 1){
				document.getElementById("tab").style.display="";
				document.getElementById("tab2").style.display="none";
			}
			if((${page}) == 0){
				document.getElementById("tab").style.display="none";
				document.getElementById("tab2").style.display="";
			}
		}
	});
</script>
</head>
<body>
	<h1 align="center" >BiTAMex用户信息管理系统</h1>
	<div>
		<button type="button" onclick="showtable()">
			显示所有用户的信息
		</button>
		<button type="button" onclick="showtable2()">
			显示需要KYC验证的用户
		</button>
	</div>
	<div>
		<table id="tab"  border="1" width="80%" align="center" style="display: none">
			<tr>
				<th>用户ID</th>
				<th>交易名</th>
				<th>交易ID</th>
				<th>手机号</th>
				<th>注册方式</th>
				<th>用户权限等级</th>
			</tr>
			<c:if test="${page < 10}">
				<c:forEach var="item" items="${UserInfo}">
					<tr align="center">
						<td>${item.m_userID}</td>
						<td>${item.m_traderName}</td>
						<td>${item.m_traderID}</td>
						<td>${item.m_userInfoPhone}</td>
						<td>${item.m_registerType}</td>
						<td>${item.m_userClientType}</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<table id="tab2"  border="1" width="80%" align="center" style="display: none">
			<tr>
				<th>用户ID</th>
				<th>交易名</th>
				<th>交易ID</th>
				<th>手机号</th>
				<th>注册方式</th>
				<th>用户权限等级</th>
			</tr>
			<c:if test="${page < 10}">
				<c:forEach var="item" items="${UserInfo}">
					<c:if test="${item.m_userClientType == 2}">
						<tr align="center">
							<td>${item.m_userID}</td>
							<td>${item.m_traderName}</td>
							<td>${item.m_traderID}</td>
							<td>${item.m_userInfoPhone}</td>
							<td>${item.m_registerType}</td>
							<td>	${item.m_userClientType} 
								<button type="button" onclick="GotoKYC('${item.m_traderID}')">
								 	去审核
								</button>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>