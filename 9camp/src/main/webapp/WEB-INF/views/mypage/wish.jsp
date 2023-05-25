<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">

.body-main {
	max-width: 700px;
}

/* form-control */
.btn {
	border: 1px solid #999;
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
.table-border tbody > tr { border-bottom: 1px solid gray; }
.td-border td { border: 1px solid #ced4da; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }



.text-left { text-align: left; }
.text-right { text-align: right; }
.text-center { text-align: center; }

.clear { clear: both; }
.clear:after { content:''; display:block; clear: both; }

.mx-auto { margin-left: auto; margin-right: auto; }


.btnConfirm {
	background-color:#507cd1; border:none;
	width: 100%; padding: 15px 0;
	font-size: 15px; color:#ffffff; font-weight: 700;  cursor: pointer; vertical-align: baseline;
}

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
    border-bottom: 2px solid #eee;
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

.table-list thead > tr:first-child { color: #4e4e4e; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px; }
.table-list .subject {  }
.table-list .name { width: 100px; }
.table-list .date { width: 100px; }
.table-list .hit { width: 70px; }
.table-list .file { width: 50px; }

/*작성자 이름 부분*/
.writer_info {
	overflow: visible;
	position: relative;
}

.writer_info #writer_modal {
	
}

.writer_info #writer_modal .writer_submenu {
	position: absolute;
	background: #fff;
	border: 1px solid #ddd;
	box-shadow: 2px 2px 5px 0 rgba(0, 0, 0, 0.2);
	width: 113px;
	font-size: 14px;
	left: 35%;
	z-index: 9;
	display: none;
}

.writer_info #writer_modal .writer_submenu > li a {
	padding: 10px 15px;
}

.writer_info #writer_modal .writer_submenu > li a:hover {
	background: #eee;
	font-weight: bold;
}

#open_layer {
	position: absolute;
	top: 0;
	left: 0;
	z-index: 3;
	width: 100%;
	height: 100%;
	display: none;
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
</style>

<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
}



	$(function(){  
		$("#chkAll").click(function(){
			if($(this).is(":checked")) {
				$("input[name=nums]").prop("checked", true);
			} else {
				$("input[name=nums]").prop("checked", false);
			}
		});
		
	});
</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-clipboard-list"></i> 나의 찜 목록 </h2>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/main.do'">뒤로 가기</button>
	    </div>
	    
	    <div class="body-main mx-auto">
	        <form name="listForm" method="post">
				<table class="table">
					<tr>
						<td align="right">
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="condition" value="${condition}">
							<input type="hidden" name="keyword" value="${keyword}">
						</td>
					</tr>
				</table>
				
				<table class="table table-border table-list">
					<thead>
						<tr>
							<c:if test="${sessionScope.member.userId=='admin'}">
								<th class="id">아이디</th>
							</c:if>
							<th class="num">번호</th>
							<th class="subject">제목</th>
							<th class="date">작성일</th>
							<th class="addr">주소</th>
							<th class="thema">테마</th>
							<th class="content">내용</th>
							<th class="chk">
								<input type="checkbox" name="chkAll" id="chkAll">        
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<c:if test="${sessionScope.member.userId=='admin'}">
								<td>${dto.userId}</td>
							</c:if>
							<td style='cursor:pointer;' onClick="location.href='${pageContext.request.contextPath}/campInfo/article.do?num=${dto.camInfoNum}'">${dto.camInfoNum}</td>
							<td>${dto.camInfoSubject}</td>
							<td>${dto.camInfoRegDate}</td>
							<td>${dto.camInfoAddr}</td>
							<td>${dto.camThemaName}</td>
							<td>${dto.camInfoContent}</td>
							<td>
								<input type="checkbox" name="nums" value="${dto.camInfoNum}">
							</td>
						</tr>
						</c:forEach>

						
					</tbody>
				</table>
			</form>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 찜이 없습니다." : paging}
			</div>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/mypage/wish.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/mypage/wish.do" method="post">
							<select name="condition" class="form-select">
								<c:if test="${sessionScope.member.userId=='admin'}">
									<option value="userId" ${condition=="userId"?"selected='selected'":"" }>아이디</option>
								</c:if>
								<option value="campWish.camInfoNum" ${condition=="campWish.camInfoNum"?"selected='selected'":"" }>번호</option>
								<option value="camInfoSubject"  ${condition=="camInfoSubject"?"selected='selected'":"" }>제목</option>
								<option value="camInfoRegDate"  ${condition=="camInfoRegDate"?"selected='selected'":"" }>작성일</option>
								<option value="camInfoAddr"  ${condition=="camInfoAddr"?"selected='selected'":"" }>주소</option>
								<option value="camThemaName" ${condition=="camThemaName"?"selected='selected'":"" }>테마</option>
								<option value="camInfoContent"  ${condition=="camInfoContent"?"selected='selected'":"" }>내용</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							<input type="hidden" name="size" value="${size}">
							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<td align="right" width="100">
						<button type="button" class="btn" onclick="btnDeleteList();">삭제 하기</button>
						<script type="text/javascript">
						function btnDeleteList() {
							let cnt = $("input[name=nums]:checked").length;
							if(cnt === 0) {
								alert("삭제할 찜을 먼저 선택하세요.");
								return false;
							}

							if(confirm("선택한 찜을 삭제 하시겠습니까 ?")) {
								const f = document.listForm;
								f.action="${pageContext.request.contextPath}/mypage/deleteWish.do";
								f.submit();
							}
						}
							
						</script>
					</td>
				</tr>
			</table>

	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>