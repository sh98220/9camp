<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.container {
	width: 100%;
	font-size: 24px;
	min-height: 500px;
	padding: 35px 0;
	display: flex;
    justify-content: center;
    align-items: center;
}

.body-container {
	min-width: 800px;
	margin: 0 auto;
	border: 1px solid #eee;
	padding: 50px 0;
	border: 1px solid #ddd;
	box-shadow: 2px 2px 5px 0 rgba(0, 0, 0, 0.2);
}

.center {
	text-align: center;
}

.btn-cont {
	text-align: center;
	margin-top: 35px;
}

.btn {
	border: 1px solid #999;
	padding: 5px 10px;
	font-weight: 500;
	cursor:pointer;
	vertical-align: middle;
	display: inline-block;
	font-size: 16px;
	color: #000;
}

.ic_send {
	color: #fff;
	background: #ff5522;
	border-radius: 50px;
	display: inline-block;
	width: 50px;
	height: 50px;
	line-height: 50px;
	margin-bottom: 35px;
}

</style>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="container">
		<div class="body-container center">
			<p class="center ic_send">
				<i class="fa-solid fa-paper-plane"></i>
			</p>	
			<p class="center">${message}</p>
			<div class="btn-cont">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/listRecMsg.do';">쪽지보기</button>
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/write.do';">쪽지쓰기</button>
			</div>
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>