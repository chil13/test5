<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>就活面接 一問一答</title>
</head>
<body>

	<div class="non-register">
		<c:out value="${nonMsg}"></c:out>
	</div>


	<form name=form1 action="Test" method="get">
		<c:forEach var="qList" items="${qList}">
			■<input type="submit" id="question" name="question"
				value="${qList.question}">
			<br>
		</c:forEach>
	</form>

	<br>

	<a href="mmMenuServlet">MENUに戻る</a>



	<div class="error-area-one">
		<c:out value="${errMsg}"></c:out>
	</div>
</body>
</html>