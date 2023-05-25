<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<body>



<c:if test="${sessionScope.member.userId=='admin'}">
	<button type="button" onclick="location.href='adminList.do'">모든 회원</button>
	<button type="button" onclick="location.href='stats.do'">가입자 통계</button>
</c:if>
</body>
</html>