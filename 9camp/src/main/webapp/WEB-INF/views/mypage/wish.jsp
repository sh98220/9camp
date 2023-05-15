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
.table-list .name { width: 100px; color: #787878; }
.table-list .date { width: 100px; color: #787878; }
.table-list .hit { width: 70px; color: #787878; }

.table-list input[type=checkbox] { vertical-align: middle; }
</style>

<script type="text/javascript">
function changeList() {
    const f = document.listForm;
    f.page.value="1";
    f.action="${pageContext.request.contextPath}/mypage/wish.do";
    f.submit();
}

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
		
		$("#btnDeleteList").click(function(){
			let cnt = $("input[name=nums]:checked").length;
			if(cnt === 0) {
				alert("삭제할 찜을 먼저 선택하세요.");
				return false;
			}
			
			if(confirm("선택한 찜을 삭제 하시겠습니까 ?")) {
				const f = document.listForm;
				f.action="${pageContext.request.contextPath}/mypage/deleteList.do";
				f.submit();
			}
		});
	});

function allDelete(){
	if(confirm("모든 찜을 삭제하시겠습니까 ?")){
		
	};
	
}
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
	    </div>
	    
	    <div class="body-main mx-auto">
	        <form name="listForm" method="post">
				<table class="table">
					<tr>
						<td width="50%">
							<button type="button" class="btn" id="btnDeleteList">삭제</button>
						</td>
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
							<th class="subject">제목</th>
							<th class="name">작성자</th>
							<th class="date">작성일</th>
							<th class="hit">조회수</th>
							
								<th class="chk">
									<input type="checkbox" name="chkAll" id="chkAll">        
								</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${listWish}">
							<tr>
								<td>
									<input type="checkbox" name="nums" value="${dto.num}">
								</td>
								<td><span class="wish">나의 찜 목록</span></td>
								<td class="left">
									<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
								</td>
								<td>${dto.userName}</td>
								<td>${dto.reg_date}</td>
								<td>${dto.hitCount}</td>
							</tr>
						</c:forEach>
						
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" name="nums" value="${dto.num}">
								</td>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
								</td>
								<td>${dto.userName}</td>
								<td>${dto.reg_date}</td>
								<td>${dto.hitCount}</td>
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
								<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
								<option value="thema" ${condition=="thema"?"selected='selected'":"" }>테마</option>
								<option value="reg_date"  ${condition=="reg_date"?"selected='selected'":"" }>등록일</option>
								<option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
								<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							<input type="hidden" name="size" value="${size}">
							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<td align="right" width="100">
						<button type="button" class="btn" onclick="allDelete();">전체 삭제</button>
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