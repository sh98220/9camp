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
.table { width: 167%; border-spacing: 0; border-collapse: collapse; margin-left: -234px; }
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
    margin-left : -222px;
    width: 162%;
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
				$("input[name=userId]").prop("checked", true);
			} else {
				$("input[name=userId]").prop("checked", false);
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
			<h2><i class="fas fa-clipboard-list"></i> 모든 유저 목록 </h2>
			<button type="button" class="btn" style="float:right" onclick="location.href='${pageContext.request.contextPath}/mypage/main.do'">뒤로 가기</button>
	    </div>
	    
	    <div class="body-main mx-auto">
	        <form name="listForm" method="post" action="${pageContext.request.contextPath}/mypage/adminList.do">
				<table class="table">
					<tr>
						<td align="right">
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="condition" value="${condition}">
							<input type="hidden" name="keyword" value="${keyword}">
							<input type="hidden" name="userId" value="${userId}">
						</td>
					</tr>
				</table>
				
				<table class="table table-border table-list">
					<thead>
						<tr>
							<th class="id">아이디</th>
							<th class="name">이름</th>
							<th class="nickName">닉네임</th>
							<th class="tel">전화번호</th>
							<th class="birth">생일</th>
							<th class="email">이메일</th>
							<th class="point">포인트</th>
							<th class="regDate">생성일</th>
							<th class="updateDate">수정일</th>
							<th class="restEndDate">정지 날짜</th>
							<th class="confine">정지 주기</th>
							<th class="chk">
								<input type="checkbox" name="chkAll" id="chkAll">
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="member" items="${list}" varStatus="status">
						<tr>
							<td>${member.userId}</td>
							<td>${member.userName}</td>
							<td>${member.userNickName}</td>
							<td>${member.userTel}</td>
							<td>${member.userBirth}</td>
							<td>${member.userEmail}</td>
							<td>${member.userPoint}</td>
							<td>${member.userRegDate}</td>
							<td>${member.userUpdateDate}</td>
							<td>${member.restEndDate}</td>
							<td><button type="button" class="btn" onclick="location.href='${ConfineUrl}&userId=${member.userId}'">정지 시키기</button></td>
							
							<td>
								<input type="checkbox" name="userId" value="${member.userId}">
							</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 유저가 없습니다." : paging}
			</div>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/mypage/adminList.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/mypage/adminList.do" method="post">
							<select name="condition" class="form-select">
								<!-- <option value="all" ${condition=="all"?"selected='selected'":"" }>제목+내용</option> -->
								<option value="userId" ${condition=="userId"?"selected='selected'":"" }>아이디</option>
								<option value="userName"  ${condition=="userName"?"selected='selected'":"" }>이름</option>
								<option value="userNickName"  ${condition=="userNickName"?"selected='selected'":"" }>닉네임</option>
								<option value="userRegDate"  ${condition=="userRegDate"?"selected='selected'":"" }>생성일</option>
								<option value="userUpdateDate"  ${condition=="userUpdateDate"?"selected='selected'":"" }>수정일</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">

							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					
					<td align="right" width="100">
						<button type="button" class="btn" onclick="btnDeleteUser();">삭제 하기</button>
						<button type="button" class="btn" onclick="btnDeleteConfine();">정지 해제</button>
						
						<script type="text/javascript">
						function btnDeleteUser() {
							let cnt = $("input[name=userId]:checked").length;
							if(cnt === 0) {
								alert("삭제할 유저를 먼저 선택하세요.");
								return false;
							}

							if(confirm("선택한 유저를 삭제 하시겠습니까 ?")) {
								const f = document.listForm;
								f.action="${pageContext.request.contextPath}/mypage/deleteUser.do";
								f.submit();
							}
						}
						
						function btnDeleteConfine() {
							let cnt = $("input[name=userId]:checked").length;
							if(cnt === 0) {
								alert("해제할 유저를 먼저 선택하세요.");
								return false;
							}

							if(confirm("선택한 유저를 정지 해제 하시겠습니까 ?")) {
								const f = document.listForm;
								f.action="${pageContext.request.contextPath}/mypage/deleteConfine.do";
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