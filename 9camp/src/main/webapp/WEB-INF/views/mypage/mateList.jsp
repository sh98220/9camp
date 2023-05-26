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
.table { width: 123%; border-spacing: 0; border-collapse: collapse; margin-left: -14%;}
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #666; border-bottom: 1px solid #666; }
.table-border tbody > tr { border-bottom: 1px solid #ff5522; }
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
    margin-left: -86px;
    width: 119%;
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

.body-title h2 i {
	
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
			<h2><i class="fas fa-clipboard-list"></i> 나의 메이트 목록 </h2>
			<button type="button" class="btn" style = "float:right " onclick="location.href='${pageContext.request.contextPath}/main.do'">뒤로 가기</button>
	    </div>
	    
	    <div class="body-main mx-auto">
	        <form name="listForm" method="post" action="${pageContext.request.contextPath}/mypage/mateList.do">
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
							<th class="num">번호</th>
							<th class="mateSubject">메이트제목</th>
							<th class="campSubject">캠핑장제목</th>
							<th class="startDate">시작일</th>
							<th class="endDate">종료일</th>
							<th class="admin">관리자 닉네임</th>
							<th class="dues">최대 인원</th>
							<th class="memberlist">멤버 리스트</th>
							<th class="memberWait">신청 대기 리스트</th>
							<th class="chk">
								<input type="checkbox" name="chkAll" id="chkAll">
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="mateList" items="${list}" varStatus="status">
						<tr>
							<td>${mateList.camMateNum}</td>
							<td>${mateList.camMateSubject}</td>
							<td>${mateList.camInfoSubject}</td>
							<td>${mateList.camMateStartDate}</td>
							<td>${mateList.camMateEndDate}</td>
							<td>${mateList.userNickName}</td>
							<td>${mateList.camMateDues}</td>
							<td><button type="button" class="btn" onclick="location.href='${AdminUrl}&num=${mateList.camMateNum}'">멤버 보기</button></td>
							<td><button type="button" class="btn" onclick="location.href='${waitUrl}&num=${mateList.camMateNum}'">대기 보기</button></td>
							<td>
								<input type="checkbox" name="nums" value="${mateList.camMateNum}">
							</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 메이트가 없습니다." : paging}
			</div>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/mypage/mateList.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/mypage/mateList.do" method="post">
							<select name="condition" class="form-select">
								<!-- <option value="all" ${condition=="all"?"selected='selected'":"" }>제목+내용</option> -->
								<option value="camMateNum" ${condition=="camMateNum"?"selected='selected'":"" }>메이트번호</option>
								<option value="camMateSubject"  ${condition=="camMateSubject "?"selected='selected'":"" }>메이트제목</option>
								<option value="camMateContent"  ${condition=="camMateContent "?"selected='selected'":"" }>메이트내용</option>
								<option value="camInfoSubject"  ${condition=="camInfoSubject "?"selected='selected'":"" }>캠핑장제목</option>
								<option value="userNickName"  ${condition=="userNickName "?"selected='selected'":"" }>관리자 닉네임</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">

							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<td align="right" width="100">
						<button type="button" class="btn" onclick="btnDeleteMate();">삭제 하기</button>
						<script type="text/javascript">
						function btnDeleteMate() {
							let cnt = $("input[name=nums]:checked").length;
							if(cnt === 0) {
								alert("삭제할 메이트를 먼저 선택하세요.");
								return false;
							}

							if(confirm("선택한 메이트를 삭제 하시겠습니까 ?")) {
								const f = document.listForm;
								f.action="${pageContext.request.contextPath}/mypage/deleteMate.do";
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