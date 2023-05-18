<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">

/* body-container */
.body-container {
	min-height: 500px;
	display: flex;
    justify-content: center;
    max-width: 1200px;
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
	min-width: 900px;
}

.inner-page{
	display: block;
	padding-top: 35px;
}

.table-list thead > tr:first-child { color: #4e4e4e; background: #f9f9f9; }
.table-list thead th { border-left: 1px solid #e4e5e7; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list thead th.chk { width: 40px; border-left: none; }
.table-list thead th.num { width: 60px; }
.table-list .subject {  }
.table-list .name { width: 100px; }
.table-list .date { width: 150px; }

.table-list td { border-left: 1px solid #e4e5e7; }
.table-list td.td-num { border-left: 0; }
.table-list td.td-content {
	padding: 0 10px;
	max-width: 500px;
}

.table-list td.td-content > a {
    text-overflow: ellipsis;
    max-width: 100%;
    overflow: hidden;
    white-space: nowrap;
    vertical-align: middle;
}

/* form-control */
.btn {
	border: 1px solid #999;
	padding: 5px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	vertical-align: middle;
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
	vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	border: 1px solid #999999; border-radius: 4px; background-color: #ffffff;
	padding: 4px 5px; 
	vertical-align: middle;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; font-size: 14px; }
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

/*커스텀*/
#msgMenuWrap {
	border-left: 1px solid #999;
	border-right: 1px solid #999;
	padding-top: 35px;
	margin-right: 50px;
	font-size: 18px;
}

#msgMenuWrap > .msgMenuBtn {
	padding: 5px 20px;
	cursor: pointer;
	border-top: 1px solid #999;
	border-bottom: 1px solid #999;
}

#msgMenuWrap > .msgMenuBtn:first-child {
	border-bottom: none;
	font-weight: bold;
}

#msgMenuWrap > .msgMenuBtn:hover {
	background: #333;
	color: #fff;
}

/*
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
*/
</style>


</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>



<main>
	<div class="container body-container">
		<div id="msgMenuWrap">
			<a href="${pageContext.request.contextPath}/message/listRecMsg.do" class="msgMenuBtn">받은쪽지함</a>
			<a href="${pageContext.request.contextPath}/message/listSendMsg.do" class="msgMenuBtn">보낸쪽지함</a>
		</div>

		<div class="body-main">
			<form name="listForm" method="post">
		    	<div class="body-title">
					<h2><i class="fa-regular fa-envelope"></i> 받은쪽지함 </h2>
		    	</div>
		    
				<table class="table">
					<tr>
						<td width="50%">
							<button type="button" class="btn" id="btnDeleteList">삭제</button>
						</td>
						<td width="50%" align="right">
							${dataCount}개(${page}/${total_page} 페이지)
						</td>
					</tr>
				</table>
				
				<table class="table table-border table-list">
					<thead>
						<tr>
							<th class="chk">
								<input type="checkbox" name="chkAll" id="chkAll">        
							</th>
							<th class="num">번호</th>
							<th class="subject">내용</th>
							<th class="name">보낸사람</th>
							<th class="date">날짜</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td class="td-num">
									<input type="checkbox" name="nums" value="${dto.msgNum}">
								</td>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left td-content">
									<a href="${articleUrl}&num=${dto.msgNum}">${dto.msgContent}</a>
								</td>
								<td>${dto.userNickName}</td>
								<td>${dto.msgRegDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
				
			<div class="page-navigation">
				${dataCount == 0 ? "받은 쪽지가 없습니다." : paging}
			</div>
				
			<table class="table">
				<tr>
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/listRecMsg.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					<td align="center">
						<form name="searchForm" action="${pageContext.request.contextPath}/message/listRecMsg.do" method="post">
							<select name="condition" class="form-select">
								<option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
								<option value="userNickName" ${condition=="userNickName"?"selected='selected'":"" }>보낸사람</option>
							</select>
							<input type="text" name="keyword" value="${keyword}" class="form-control">
							<button type="button" class="btn" onclick="searchList();">검색</button>
						</form>
					</td>
					<td align="right" width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/write.do';">쪽지쓰기</button>
					</td>
				</tr>
			</table>
	
		</div>
	</div>
	    
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<script type="text/javascript">
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
			alert("삭제할 쪽지를 먼저 선택하세요.");
			return false;
		}
		
		if(confirm("선택한 쪽지를 삭제 하시겠습니까 ?")) {
			const f = document.listForm;
			f.action="${pageContext.request.contextPath}/message/recDelete.do";
			f.submit();
		}
	});
});

function searchList() {
	const f = document.searchForm;
	f.submit();
}

function changeList() {
    const f = document.listForm;
    f.page.value="1";
    f.action="${pageContext.request.contextPath}/message/listRecMsg.do";
    f.submit();
}
</script>

</body>
</html>