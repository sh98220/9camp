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

.table { width: 69%; border-spacing: 0; border-collapse: collapse; margin-left: 56%; margin-top: 10px;}
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #666; border-bottom: 1px solid #666; }
.table-border tbody > tr { border-bottom: 1px solid #ff5522; }
.td-border td { border: 1px solid #ced4da; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }

.table-list thead > tr:first-child { background: #f8f8f8; }
.table-list th, .table-list td { text-align: center; }

.table-list .mateList { display: inline-block; padding:1px 3px; background: #ed4c00; color: #fff; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .chk { width: 40px; color: #787878; }
.table-list .num { width: 60px; color: #787878; }
.table-list .subject { color: #787878; }
.table-list .thema { width: 100px; color: #787878; }
.table-list .date { width: 100px; color: #787878; }
.table-list .addr { width: 150px; color: #787878; }

.table-list input[type=checkbox] { vertical-align: middle; }

.container {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;

}

.page-navigation {
    clear: both;
    padding: 20px 0;
    text-align: center;
    margin-left: 80%;
}

/* body-container */
.body-container {
	min-height: 500px;
}

.body-title {
    display: block;
    position: relative;
    width: 36%;
    margin: 0 auto;
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
	padding-bottom: 40px;
}

.inner-page{
	display: block;
	padding-top: 35px;
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
			<h2><i class="fas fa-clipboard-list"></i> 나의 메이트 멤버 </h2>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/mypage/mateList.do'">뒤로 가기</button>
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
				
				<table class="table table-border table-list mateList">
					<thead>
						<tr>
							<th class="num">닉네임</th>
							<th class="gender">성별</th>
							<th class="age">나이</th>
							<th class="chk">
								<input type="checkbox" name="chkAll" id="chkAll">        
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="member" items="${list}" varStatus="status">
						<tr>				
							<td>${member.userNickName}</td>
							<td>${member.camMateAppGender}</td>
							<td>${member.camMateAppAge}</td>
							<td>
								<input type="checkbox" name="nums" value="${member.userId}">
							</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 멤버가 없습니다." : paging}
			</div>
			
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/mypage/mateApplyAdmin.do?page=${page}&num=${num}';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/mypage/mateApplyAdmin.do?page=${page}&num=${num}" method="post">
							<select name="condition" class="form-select">
								<option value="userNickName" ${condition=="userNickName"?"selected='selected'":"" }>닉네임</option>
								<option value="camMateAppGender"  ${condition=="camMateAppGender"?"selected='selected'":"" }>성별</option>
								<option value="camMateAppAge"  ${condition=="camMateAppAge"?"selected='selected'":"" }>나이</option>
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
								alert("삭제할 멤버를 먼저 선택하세요.");
								return false;
							}

							if(confirm("선택한 멤버를 삭제 하시겠습니까 ?")) {
								const f = document.listForm;
								f.action="${pageContext.request.contextPath}/mypage/deleteMateApply.do?page=${page}&num=${num}";
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