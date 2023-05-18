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
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/mypage/main.do'">뒤로 가기</button>
	    </div>
	    
	    <div class="body-main mx-auto">
	        <form name="listForm" method="post" action="${pageContext.request.contextPath}/mypage/myMateList.do">
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