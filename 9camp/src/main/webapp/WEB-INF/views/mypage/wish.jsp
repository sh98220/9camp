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

.table-list .wish { display: inline-block; padding:1px 3px; background: #ed4c00; color: #fff; }
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
			<h2><i class="fas fa-clipboard-list"></i> 나의 찜 목록 </h2>
			<button type="button" onclick="location.href='${pageContext.request.contextPath}/mypage/main.do'">뒤로 가기</button>
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
							<td>${dto.camInfoNum}</td>
							<td>${dto.camInfoSubject}</td>
							<td>${dto.camInfoRegDate}</td>
							<td>${dto.camInfoAddr}</td>
							<td>${dto.camThemaName}</td>
	
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