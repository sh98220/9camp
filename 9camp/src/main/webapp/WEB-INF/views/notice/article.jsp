<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>공지사항</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
.body-main {
	max-width: 700px;
	padding-top: 15px;
}

a { display: inline-block;}

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
.table-border tbody > tr { border-bottom: 1px solid #666; }
.td-border td { border: 1px solid #ced4da; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }

.photo-layout img { width: 570px; height: 450px; }



.text-left { text-align: left; }
.text-right { text-align: right; }
.text-center { text-align: center; }

.clear { clear: both; }
.clear:after { content:''; display:block; clear: both; }

.mx-auto { margin-left: auto; margin-right: auto; }

.noticeImage{
	width:200px;
	height: 200px
}

.img-box{
	max-width: 700px;
	min-height: 120px;
	padding: 10px;
	box-sizing: border-box;
	border:1px solid #ccc;
	display:flex;
	flex-direction: row;
	flex-wrap: nowrap;
	overflow-x: auto;
	justify-content: flex-start;
}
.img-box img {
	width: 200px; height: 200px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

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

.table-article tr>td { padding-left: 5px; padding-right: 5px; }
.reply { clear: both; padding: 20px 0 10px; }
.reply .bold { font-weight: 600; }

.reply .form-header { padding-bottom: 7px; }
.reply-form  tr>td { padding: 2px 0 2px; }
.reply-form textarea { width: 100%; height: 75px; }
.reply-form button { padding: 8px 25px; }

.reply .reply-info { padding-top: 25px; padding-bottom: 7px; }
.reply .reply-info  .reply-count { color: #3EA9CD; font-weight: 700; }

.reply .reply-list tr>td { padding: 7px 5px; }
.reply .reply-list .bold { font-weight: 600; }

.reply .deleteReply, .reply .deleteReplyAnswer { cursor: pointer; }
.reply .notifyReply { cursor: pointer; }

.reply-list .list-header { border: 1px solid #ccc; background: #f8f8f8; }
.reply-list tr>td { padding-left: 7px; padding-right: 7px; }

</style>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
	function deleteBoard() {
	    if(confirm("게시글을 삭제 하시겠습니까 ? ")) {
		    let query = "num=${dto.noticeNum}&${query}";
		    let url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
	    	location.href = url;
	    }
	}
</c:if>
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>

<script type="text/javascript">
function login(){
	location.href = "${pageContext.request.contextPath}/member/login.do";
}

function imageViewer(img) {
	const viewer = $(".photo-layout");
	let s="<img src='"+img+"'>";
	viewer.html(s);
	
	$(".dialog-photo").dialog({
		title:"이미지",
		width: 600,
		height: 530,
		modal: true
	});
}


function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,		// 메소드(get, post, put, delete)
		url:url,			// 요청 받을 서버주소
		data:query,			// 서버에 전송할 파라미터
		dataType:dataType,	// 서버에서 응답하는 형식(json, xml, text)
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) { 
			jqXHR.setRequestHeader("AJAX", true); // 사용자 정의 헤더
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			console.log(jqXHR.responseText);
		}
	});
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
			<h2><i class="fas fa-graduation-cap"></i> 공지사항 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<thead>
					<tr>
						<td colspan="2" align="center">
							${dto.noticeSubject}
						</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td width="50%">
							이름 : 관리자
						</td>
						<td align="right">
							${dto.noticeRegDate} | 조회 ${dto.noticeHitCount}
						</td>
					</tr>
					
					<tr>
						<td colspan="2" valign="top" height="200">
							${dto.noticeContent}
						</td>
					</tr>
					
					<c:choose>
						<c:when test="${! empty listFile}">	
							<tr>
								<td colspan="2" height="200">
									사&nbsp;&nbsp;진 :
									<div class="img-box">
										<c:forEach var="vo" items="${listFile}">
											<img class = "noticeImage" src="${pageContext.request.contextPath}/uploads/notice/${vo.noticePhotoName}"
												onclick="imageViewer('${pageContext.request.contextPath}/uploads/notice/${vo.noticePhotoName}');">
										</c:forEach>
									</div>
								</td>
							</tr>
						</c:when>	
						<c:otherwise>
						</c:otherwise>
					</c:choose>
					
					<tr>
						    <td colspan="2">
						        이전글 :
						        <c:choose>
						            <c:when test="${empty preReadDto}">
						                <span style="font-weight: bold;">제일 처음 게시글입니다.</span>
						            </c:when>
						            <c:otherwise>
						                <a class="nav-link" href="${pageContext.request.contextPath}/notice/article.do?${query}&num=${preReadDto.noticeNum}">${preReadDto.noticeSubject}</a>
						            </c:otherwise>
						        </c:choose>
						    </td>
						</tr>
					<tr>
						<td colspan="2">
							다음글 :
							<c:choose>
		           				 <c:when test="${empty nextReadDto}">
		              					 <span style="font-weight: bold;">제일 마지막 게시글입니다.</span>
         					     </c:when>
					             <c:otherwise>
					                 <a class="nav-link"  href="${pageContext.request.contextPath}/notice/article.do?${query}&num=${nextReadDto.noticeNum}">${nextReadDto.noticeSubject}</a>
					             </c:otherwise>
					         </c:choose>
						</td>
					</tr>
				</tbody>
			</table>
			
			<table class="table">
				<tr>
					<td width="50%">
						<c:choose>
							<c:when test="${sessionScope.member.userId==dto.userId}">
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/update.do?num=${dto.noticeNum}&page=${page}';">수정</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" disabled="disabled">수정</button>
							</c:otherwise>
						</c:choose>
				    	
						<c:choose>
				    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
				    		</c:when>
				    		<c:otherwise>
				    			<button type="button" class="btn" disabled="disabled">삭제</button>
				    		</c:otherwise>
				    	</c:choose>
					</td>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/list.do?${query}';">리스트</button>
					</td>
				</tr>
			</table>
    	       
	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>


<div class="dialog-photo">
      <div class="photo-layout"></div>
</div>

</body>
</html>

