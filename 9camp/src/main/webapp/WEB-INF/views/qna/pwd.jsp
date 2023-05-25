<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.members-form { width: 430px; padding: 45px 10px 15px; margin: 0 auto; background: #fefeff; }
.members-form .members-title { text-align: center; padding: 10px 0 20px; }
.members-form .members-title > h3 { font-weight: bold; font-size:26px; color: #424951; }
.members-form .info-box { padding: 30px 20px; box-shadow: 0 0 15px 0 rgb(2 59 109 / 10%); }
.members-form .row { margin-bottom: 1.5rem; }
.members-form input { display: block; width: 100%; padding: 10px 10px; }

.members-message { margin: 0 auto; padding: 5px 5px 20px; }
.members-message p { color: #023b6d; }
</style>

<script type="text/javascript">
function sendOk() {
	const f = document.pwdForm;

	let str = f.userPwd.value;
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.userPwd.focus();
		return;
	}

	f.action = "${pageContext.request.contextPath}/qna/pwd_ok.do";
	f.submit();
}
</script>

</head>
<body>

<header>
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>

	<div class="container body-container">
		<div class="inner-page">
			<div class="members-form">
				<div class="members-title">
					<h3><i class="fa-solid fa-lock"></i> 비밀번호 확인</h3>
				</div>
				<div class="info-box">
					<form name="pwdForm" method="post">
						<div class="row text-center">
							게시글을 보기 위해서는 비밀번호를 입력해주세요.
						</div>
						<div class="row">
							<input name="userPwd" type="password" class="form-control" placeholder="패스워드">
						</div>
						<div>
							<button type="button" class="btnConfirm" onclick="sendOk();">확인</button>
							<input type="hidden" name="qnaNum" value="${qnaNum}">
						</div>
					</form>
				</div>
			</div>
			<div class="members-message">
				<p class="text-center">${message}</p>
			</div>
	    </div>
	</div>

</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<!-- jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" -->
</body>
</html>