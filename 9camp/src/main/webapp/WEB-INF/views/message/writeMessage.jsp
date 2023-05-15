<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지보내기</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
#writeMessageWrap {
	max-width: 700px;
	margin: 0 auto;
	padding-top: 35px;
	min-height: 500px;
}

#writeMessageWrap .body-title {
    color: #424951;
    padding-top: 35px;
    padding-bottom: 7px;
    margin: 0 0 25px 0;
    border-bottom: 2px solid #eee;
}

#writeMessageWrap .body-title h2 {
    font-size: 24px;
    min-width: 300px;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
    color: #ff5522;
    font-weight: 700;
    padding-bottom: 10px;
    display: inline-block;
    margin: 0 0 -7px 0;
    border-bottom: 3px solid #ff5522;
}

#writeMessageWrap .btn {
	padding: 5px 10px;
	font-size: 14px;
	color: #333; font-weight: 500;
	border: 1px solid #999; border-radius: 4px;
	background-color: #fff;
	cursor:pointer;
	vertical-align: baseline;
}
#writeMessageWrap .btn:active, #writeMessageWrap .btn:focus, #writeMessageWrap .btn:hover {
	color:#fff;
	background-color: #333;
}
#writeMessageWrap .btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: default;
	opacity: .65;
}

/**/
#writeMessageWrap .tf_tit input#who {
	border: 1px solid #adafaa;
	padding: 5px;
	height: 30px;
	vertical-align: middle;
}

#writeMessageWrap .searchSender {
	display: inline-block;
	vertical-align: middle;
}

#writeMessageWrap .searchSender:hover {
	
}

#writeMessageWrap .writing_area {
	width: 100%;
}

#writeMessageWrap .writing_area {
    clear: both;
    border: 1px solid #adafaa;
    height: 200px;
    position: relative;
    padding: 5px 8px;
    background-color: #fff;
    margin: 10px 0;
}

#writeMessageWrap .writing_area textarea {
	width: 100%;
	height: 100%;
	border: none;
}
</style>

<script type="text/javascript">

function sendOk() {
	
}
</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div id="writeMessageWrap">
		<div class="body-title">
			<h2><i class="fa-regular fa-envelope"></i> 쪽지보내기 </h2>
	    </div>
	    
		<form name="noteForm" method="post">
			<div id="normalMode">
				<div class="tf_tit">
					<label for="who" class="recipient">받는사람</label>
					<input type="text" id="who" value="">
					<a href="#" class="btn searchSender">검색</a>
				</div>
				<div class="writing_area">
					<textarea id="writeNote" style="resize:none;" rows="5" cols="55" title="쪽지 내용을 입력해 주세요."></textarea>
				</div>
			</div>
			<button type="button" class="btn" onclick="sendOk();"> 보내기 </button>
			<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/list.do';"> 취소 </button>
		</form> 
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>