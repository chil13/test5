<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>就活面接 一問一答</title>
</head>
<body>
	<form action="LoginServlet" method="get">
		<div>nick name</div>
		<input type="text" name="nickName" value="">
		<div>password</div>
		<input type="text" name="pass" value="">
		<button type="submit">login</button>
	</form>
	<div class="error-area-one">
		<c:out value="${errMsg}"></c:out>
	</div>
</body>
</html>