<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KYC 身份认证</title>
<script src="jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function success(traderID) {
		var url = "KYCSuccessServlet?traderID=" + traderID + "&result=success"; 
		 window.location.replace(url);
	}
	
	function failed(traderID) {
		var url = "KYCSuccessServlet?traderID=" + traderID + "&result=failed"; 
		 window.location.replace(url);
	}
</script>
</head>
<body>
	<h1 align="center">KYC身份审核页面</h1>
	<div style="width: 50%" align="center" title="用户部分信息">
		<p style="padding-left: 200">用户ID:${loginInfo.m_userID}</p>
		<p style="padding-left: 200">交易ID:${loginInfo.m_traderID}</p>
		<p style="padding-left: 200">用户注册账号:${loginInfo.m_nickName}</p>
		<p style="padding-left: 200">姓名:${map.name}</p>
		<p style="padding-left: 200">身份证号:${map.id}</p>
	</div>
	<div align="center">
		<img src="/upload/${frontPath}" height="400" width="600" title="正面"/>
		<img src="/upload/${backPath}" height="400" width="600" title="背面"/>
	</div>
	
	<div align="center">
		<button type="button" onclick="success('${loginInfo.m_traderID}')">审核通过</button>
		<button type="button" onclick="failed('${loginInfo.m_traderID}')">审核不通过</button>
	</div>
</body>
</html>