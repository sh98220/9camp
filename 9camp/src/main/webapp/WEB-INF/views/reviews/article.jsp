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
a {display: inline-block;}

.body-main {
	max-width: 700px;
	padding-top: 15px;
}


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
    border-bottom: 3px solid #eee;
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

/* 모달대화상자 */
.ui-widget-header { /* 타이틀바 */
	background: none;
	border: none;
	border-bottom: 1px solid #ccc;
	border-radius: 0;
}
.ui-dialog .ui-dialog-title {
	padding-top: 5px; padding-bottom: 5px;
}
.ui-widget-content { /* 내용 */
   /* border: none; */
   border-color: #ccc; 
}

.table-article tr > td { padding-left: 5px; padding-right: 5px; }

.img-box {
	max-width: 700px;
	padding: 5px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 200px; height: 200px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

.photo-layout img { width: 570px; height: 450px; }

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

.nav-link:hover { color: red; }

/*작성자 이름 부분*/
.writer_info {
	overflow: visible;
	position: relative;
	display: inline-block;
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
	display: block;
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
	height: 200%;
	display: none;
}

</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
	function deleteReviews() {
	    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
		    let query = "num=${dto.camRevnum}&${query}";
		    let url = "${pageContext.request.contextPath}/reviews/delete.do?" + query;
	    	location.href = url;
	    }
	}
</c:if>

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

function login(){
	location.href = "${pageContext.request.contextPath}/member/login.do";
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

// 게시글 공감 여부
$(function(){
	$(".btnSendReviewsLike").click(function(){
		const $i = $(this).find("i");
		let isNoLike = $i.css("color") == "rgb(0, 0, 0)";
		let msg = isNoLike ? "게시글에 공감하십니까 ?" : "게시글 공감을 취소하시겠습니까 ?";		
		
		if(! confirm(msg)){
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/reviews/insertReviewsLike.do";
		let num = "${dto.camRevnum}";
		let qs = "camRevnum=" + num + "&isNoLike=" + isNoLike;
		
		const fn = function(data){
			let state = data.state;
			if(state === "true"){
				let color = "black";
				if( isNoLike ){
					color = "blue";
				}
				$i.css("color", color);
				
				let count = data.reviewsLikeCount;
				$("#reviewsLikeCount").text(count);
			} else if(state === "liked"){
				alert("좋아요는 한번만 가능합니다.");				
			}
		};
		
		ajaxFun(url, "post", qs, "json", fn);
	});
});

//댓글 리스트 및 페이징
$(function(){
	listPage(1);
});

function listPage(page) {
	let url = "${pageContext.request.contextPath}/reviews/listReply.do";
	let qs = "camRevnum=${dto.camRevnum}&pageNo="+page;
	let selector = "#listReply";
	
	const fn = function(data){
		$(selector).html(data);
	}
	
	ajaxFun(url, "get", qs, "text", fn);
	//	ajaxFun(url, "get", qs, "html", fn); // 가능
	
}

// 댓글 등록
$(function(){
	$(".btnSendReply").click(function(){
		let num = "${dto.camRevnum}";
		const $tb = $(this).closest("table");
		let content = $tb.find("textarea").val().trim();
		
		if(! content) {
			$tb.find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		let url = "${pageContext.request.contextPath}/reviews/insertReply.do";
		let qs = "camRevnum="+num+"&camRevRepcontent="+content;
		
		const fn = function(data){
			$tb.find("textarea").val("");
			
			let state = data.state;
			if(state === "true"){
				listPage(1);
			} else {
				alert("댓글을 추가하지 못했습니다.");
			}
		}
		
		ajaxFun(url, "post", qs, "json", fn);
		
	});
});

//댓글 삭제
$(function(){
	$("#listReply").on("click", ".deleteReply", function(){
		if(! confirm("게시글을 삭제하시겠습니까 ? ")){
			return false;
		}
		
		let camRevRepnum = $(this).attr("data-camRevRepnum");
		let page = $(this).attr("data-pageNo");
		
		let url = "${pageContext.request.contextPath}/reviews/deleteReply.do";
		let qs = "camRevRepnum="+camRevRepnum;
		
		const fn = function(data){
			listPage(page);
		};
		
		ajaxFun(url, "post", qs, "json", fn);
	});
});


</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div id="open_layer"></div>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-graduation-cap"></i> 전국캠핑자랑	 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<thead>
					<tr>
						<td colspan="2" align="center">
							${dto.camRevsubject}
						</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td width="50%">
							작성자 :
							<div class="writer_info">
								<a style="cursor: pointer;">
									${dto.userNickName}
								</a>
								<div id="writer_modal">
									<ul class="writer_submenu">
										<li>
											<a href="${pageContext.request.contextPath}/message/write.do?msgRecId=${dto.userId}">쪽지보내기</a>
										</li>
									</ul>
								</div>
							</div>
						</td>
						<td align="right">
							${dto.camRevregdate} | 조회 ${dto.camRevhitcount}
						</td>
					</tr>
					
					<tr>
						<td width="50%">
							캠핑장 : <a href="#"></a> 우아아아아아아아아아앙캠핑장
						</td>
					</tr>
					
					<tr>
						<td colspan="2" valign="top" height="100">
							${dto.camRevcontent}
						</td>
					</tr>
		
					<tr>
						<td colspan="2" height="200">
							사&nbsp;&nbsp;진 :
							<div class="img-box">
								<c:forEach var="vo" items="${listFile}">
									<img src="${pageContext.request.contextPath}/uploads/reviews/${vo.camRevphotoname}"
										onclick="imageViewer('${pageContext.request.contextPath}/uploads/reviews/${vo.camRevphotoname}');">
								</c:forEach>
							</div>
						</td>
					</tr>
		
					<tr>
						<td colspan="2" align="center" style="border-bottom: 20px; ">
							<button type="button" class="btn btnSendReviewsLike" title="좋아요"> <i class="fas fa-thumbs-up" style="color:${isUserLike?'blue':'black'}"></i>&nbsp;&nbsp;<span id="reviewsLikeCount">${dto.reviewsLikeCount}</span></button>
						</td>
					</tr>
		
					<tr>
						    <td colspan="2">
						        이전글 :
						        <c:choose>
						            <c:when test="${empty preReadDto}">
						                <span style="font-weight: bold;">제일 처음 게시글입니다.</span>
						            </c:when>
						            <c:otherwise>
						                <a class="nav-link" href="${pageContext.request.contextPath}/reviews/article.do?${query}&num=${preReadDto.camRevnum}">${preReadDto.camRevsubject}</a>
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
					                 <a class="nav-link"  href="${pageContext.request.contextPath}/reviews/article.do?${query}&num=${nextReadDto.camRevnum}">${nextReadDto.camRevsubject}</a>
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
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/reviews/update.do?num=${dto.camRevnum}&page=${page}';">수정</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn" disabled="disabled">수정</button>
							</c:otherwise>
						</c:choose>
				    	
						<c:choose>
				    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="deleteReviews();">삭제</button>
				    		</c:when>
				    		<c:otherwise>
				    			<button type="button" class="btn" disabled="disabled">삭제</button>
				    		</c:otherwise>
				    	</c:choose>
					</td>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/reviews/list.do?${query}';">리스트</button>
					</td>
				</tr>
			</table>
	       <div class="reply">
				<form name="replyForm" method="post">
					<div class='form-header'>
						<span class="bold">댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가해 주세요.</span>
					</div>
					
					<table class="table reply-form">
						<tr>
							<td>
								<textarea class='form-control' name="camRevRepcontent"></textarea>
							</td>
						</tr>
						<tr>
						   <td align='right'>
								<button type='button' class='btn btnSendReply'>댓글 등록</button>
							</td>
						 </tr>
					</table>
				</form>
				
				<div id="listReply"></div>
			</div>
	    </div>
	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<div class="dialog-photo">
      <div class="photo-layout"></div>
</div>

<script type="text/javascript">
//작성자 클릭시 쪽지보내기 뜨기
$(function(){
	$("body").on("click", ".writer_info", function(){
		const $modal = $(this).find(".writer_submenu");
		
		let isVisible = $modal.is(':visible');
		
		$("#open_layer").show();
		$modal.show();
	});
	
});

// 바디 클릭시 쪽지보내기 닫기
$(function(){
	$("#open_layer").click(function(){
		$("#open_layer").hide();
		$(".writer_submenu").hide();
	});
});
</script>


</body>
</html>