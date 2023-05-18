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
	width: 700px;
	padding-bottom: 35px;
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
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding: 10px; }

.table-border thead { border-top: 2px solid #666; }
.table-border thead > tr { border-bottom: 1px solid #666; }
.table-border thead td.td-tit {
	font-weight: bold;
	width: 100px;
	text-align: center;
	background: #eee;
}

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

.note-read {
	min-width: 460px;
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
			<div class="body-title">
				<h2><i class="fa-regular fa-envelope"></i> 받은쪽지함 </h2>
		    </div>
		    <!-- note-read -->
			<div class="note-read">
				<table class="table table-border table-article">
					<thead>
						<tr>
							<td class="td-tit">보낸사람</td>
							<td class="td-cont">${dto.userNickName} (${dto.msgWriterId})</td>
						</tr>
						<tr>
							<td  class="td-tit">받은시간</td>
							<td class="td-cont">${dto.msgRegDate}</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.msgContent}
							</td>
						</tr>
					</tbody>
				</table>
			
				<table class="table">
					<tr>
						<td width="50%">
							<button type="button" class="btn" onclick="recDelete();">삭제</button>
						</td>
						<td align="right">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/listRecMsg.do?${query}';">리스트</button>
						</td>
					</tr>
				</table>
			</div>
			<!--// noter-read -->	
			
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<script type="text/javascript">
function recDelete() {
	if(confirm("쪽지를 삭제하시겠습니까 ? ")) {
		let query = "msgNum=${dto.msgNum}&${query}";
		let url = "${pageContext.request.contextPath}/message/recDelete.do?" + query;
	    location.href = url;
	}
}
</script>

</body>
</html>