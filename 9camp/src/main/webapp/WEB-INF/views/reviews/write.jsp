<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>전국캠핑자랑</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
.body-main {
	max-width: 700px;
	padding-top: 15px;
}


.body-main {
	max-width: 700px;
}

/* form-control */
.btn {
	color: #333;
	border: 1px solid #999;
	background-color: #eee;
	padding: 5px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:active, .btn:focus, .btn:hover {
	background-color: #333;
	color:#fff;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: not-allowed;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
}


.form-control {
	border: 1px solid #999999; border-radius: 4px; background-color: #ffffff;
	padding: 5px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	border: 1px solid #999999; border-radius: 4px; background-color: #ffffff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #666; border-bottom: 1px solid #666; }
.table-border tbody > tr { border-bottom: 1px solid #666; }

.td-border td { border: 1px solid #ced4da; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }



.text-left { text-align: left; }
.text-right { text-align: right; }
.text-center { text-align: center; }

.clear { clear: both; }
.clear:after { content:''; display:block; clear: both; }

.mx-auto { margin-left: auto; margin-right: auto; }

.container {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;

}

/* body-container */
.body-container {
	min-height: 500px;
}

.body-title {
    color: #424951;
    padding-top: 35px;
    padding-bottom: 7px;
    margin: 0 0 25px 0;
    border-bottom: 2px solid #eee;;
}

.body-title h2 {
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

.body-main {
	display: block;
	padding-bottom: 15px;
}

.inner-page{
	display: block;
	padding-top: 35px;
}

.table-list thead > tr:first-child { background: skyblue; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px; color: #ff5522; }
.table-list .subject { color: #ff5522; }
.table-list .name { width: 100px; color: #ff5522; }
.table-list .date { width: 100px; color: #ff5522; }
.table-list .hit { width: 70px; color: #ff5522; }
.table-list .file { width: 50px; color: #ff5522; }

.table-form td { padding: 7px 0; }
.table-form p { line-height: 200%; }
.table-form tr:first-child { border-top: 2px solid #666;  }
.table-form tr > td:first-child { width: 110px; text-align: center; background: #eee; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }

.table-form input[type=text], .table-form input[type=file], .table-form textarea {
	border: 1px solid #999;
	width: 96%;
}
	
@media (min-width: 576px) {
	.container {
	    max-width: 540px;
	}
}
@media (min-width: 768px) {
	.container {
	    max-width: 720px;
	}
}
@media (min-width: 992px) {
	.container {
		max-width: 960px;
	}
}
@media (min-width: 1200px) {
	.container {
	    max-width: 750px;
	}
}
	
.img-box {
	max-width: 600px;
	padding: 5px;
	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 37px; height: 37px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

</style>

<script type="text/javascript">
function sendOk() {
    const f = document.reviewForm;
	let str;
	
    str = f.camRevsubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.camRevsubject.focus();
        return;
    }

    str = f.camRevcontent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.camRevcontent.focus();
        return;
    }

    let mode = "${mode}";
    if( (mode === "write") && (!f.selectFile.value) ) {
        alert("이미지 파일을 추가 하세요. ");
        f.selectFile.focus();
        return;
    }

    
    f.action = "${pageContext.request.contextPath}/reviews/${mode}_ok.do";
    f.submit();
}

<c:if test="${mode == 'update'}">
	function deleteFile(camRevphotonum) {
		if(confirm('이미지를 삭제하시겠습니까 ?')){
			let query = "camRevnum=${dto.camRevnum}&camRevphotonum=" + camRevphotonum + "&page=${page}";
			let url = "${pageContext.request.contextPath}/reviews/deleteFile.do";
			location.href = url + "?" + query;
		}
	}
</c:if>
</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-graduation-cap"></i> 전국캠핑자랑 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="reviewForm" method="post" enctype="multipart/form-data">
				<table class="table table-border table-form">
					<tr> 
						<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						<td> 
							<input type="text" name="camRevsubject" maxlength="100" class="form-control" value="${dto.camRevsubject}">
						</td>
					</tr>
					
					<tr> 
						<td>작성자</td>
						<td> 
							<p>${sessionScope.member.userName}</p> 
						</td>
					</tr>
					
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td> 
							<textarea name="camRevcontent" class="form-control">${dto.camRevcontent}</textarea>
						</td>
					</tr>
					
					<tr>
						<td>첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
						<td> 
							<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="form-control">
						</td>
					</tr>
					
					<c:if test="${mode == 'update' }">
						<tr>
							<td>등록이미지</td>
							<td>
								<div class="img-box">
									<c:forEach var="vo" items="${listFile}">
										<img src="${pageContext.request.contextPath}/uploads/reviews/${vo.camRevphotoname}"
											onclick="deleteFile('${vo.camRevphotonum}');">
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:if>
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendOk();">${mode=="update"?"수정완료":"등록완료"}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/reviews/list.do';">${mode=="update"?"수정취소":"등록취소"}</button>
							<c:if test="${mode=='update' }">
								<input type="hidden" name="camRevnum" value="${dto.camRevnum}">
								<input type="hidden" name="page" value="${page}">
							</c:if>							
						</td>
					</tr>
				</table>
		
			</form>

	    </div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>