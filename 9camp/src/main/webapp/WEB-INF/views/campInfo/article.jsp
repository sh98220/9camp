<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>가자Goo캠핑</title>
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
    display: block;
    position: relative;
    width: 97%;
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
	width: 100px; height: 100px;
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

.img_b {
	width: 50%;
    height: 100%;
    float: left;
    background: #eee;
    border: 1px solid #dedede;
    box-sizing: border-box;
}

.img_b > img {
    object-fit: cover;
    width: 100%;
    height: 100%;
}

.s_title2 {
	width: 100%;
    margin-top: 30px;
    text-align: center;
    color: #fff;
    text-shadow: 2px 0px 3px #FF9800;
    padding-bottom: 20px;
}

.s_title2 > .camp_name {
    font-size: 35px;
    font-family: BM_dh;
    line-height: 40px;
    color: #FF9800 !important;
}

.s_title2 .camp_s_tt {
    font-size: 20px;
    clear: both;
    margin: 0;
    letter-spacing: -2px;
    color: #fff;
    padding: 5px 0 0 0;
}

.camp_tag {
    width: 100%;
    position: relative;
    height: auto;
    clear: both;
    margin: 20px 0 0 0;
    overflow: hidden;
    min-height: 32px;
}

.camp_tag p.tag_tt {
    display: block;
    float: left;
    padding: 0 15px;
    border: 1px solid #FF9800;
    border-radius: 100px;
    text-align: center;
    font-size: 15px;
    color: #FF9800;
    margin-right: 5px;
}

#cont_inner {
    width: 100%;
    position: relative;
    height: auto;
    clear: both;
    padding: 0px 0 0px 0;
    background: #fff;
}

.camp_info_box {
    position: relative;
    width: 100%;
    height: auto;
    overflow: hidden;
    clear: both;
    margin-bottom: 100px;
}
.camp_info_box .cont_tb {
    width: 45%;
    float: right;
    height: auto;
    overflow: hidden;
}

.camp_info_box .cont_tb table {
    width: 118%;
    font-size: 14px;
}

table {
    border-collapse: collapse;
}

.camp_info_box .cont_tb table tr th {
    padding: 11px 6px;
    border-bottom: 1px solid #c8c8c8;
    text-align: left;
    color: #000;
    line-height: 25px;
}

tbody {
    display: table-row-group;
    vertical-align: middle;
    border-color: inherit;
}

#sub_title_wrap2 {
    position: relative;
    width: 100%;
    height: auto;
    padding-bottom: 10px;
    clear: both;
    
}

@media (max-width: 1000px){
	#sub_title_wrap2 {
	    padding-top: 10px;
	}
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript">
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

//캠핑장 찜 여부
$(function(){
	$(".btnSendCampWish").click(function(){
		const $i = $(this).find("i");
		let isNoLike = $i.css("color") == "rgb(0, 0, 0)";
		let msg = isNoLike ? "찜 하시겠습니까 ?" : "찜을 취소하시겠습니까 ?";		
		
		if(! confirm(msg)){
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/campInfo/insertCampWish.do";
		let num = "${dto.camInfoNum}";
		let qs = "camInfoNum=" + num + "&isNoLike=" + isNoLike;
		
		const fn = function(data){
			let state = data.state;
			if(state === "true"){
				let color = "black";
				if( isNoLike ){
					color = "orange";
				}
				$i.css("color", color);
				
				let count = data.wishCount;
				$("#wishCount").text(count);
			} else if(state === "liked"){
				alert("찜하기는 한번만 가능합니다.");				
			}
		};
		
		ajaxFun(url, "post", qs, "json", fn);
	});
});
</script>

<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
function deletecampInfo() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    let query = "num=${dto.camInfoNum}&${query}";
	    let url = "${pageContext.request.contextPath}/campInfo/delete.do?" + query;
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

</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
		<div id="sub_title_wrap2">
		    <div class="layout">
				<div class="s_title2">
					<p class="camp_name">${dto.camInfoSubject}</p>
					<p class="camp_s_tt"> 운해와 야경이 일품인 휴양림속 힐링</p> 		
				</div>
				<div class="camp_tag">
					<p class="tag_tt">키워드</p>
					<p style="float: left">${dto.camKeyWord}</p>
					<p style="float: right; padding-right: 0px" >${dto.camInfoRegDate} &nbsp;&nbsp;|   조회수 &nbsp;&nbsp;${dto.camInfoHitCount} &nbsp;&nbsp;| 찜하기 수&nbsp;&nbsp; ${dto.wishCount}   &nbsp;&nbsp;| 찜하기 &nbsp;&nbsp;<button type="button" class="btn btnSendCampWish" title="좋아요"> <i class="fa-solid fa-star" style="color:${isUserWish?'orange':'black'}"></i></button>
					 </p>			
				</div>
		    </div>
		 </div>
	    
	    <div id="cont_inner">
	    	<div class="camp_info_box">
	    		<div class="img_b">
					<c:forEach var="vo" items="${listFile}">
						<img src="${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName}">
					</c:forEach>
	    		</div>
	    		<div class="cont_tb">
	    			<table class="table">
	    				<colgroup>
	    					<col style="width: 30%;">		
	    					<col style="width: 70%;">
	    				</colgroup>
	    				<tbody>
	    					<tr>
	    						<th scope="col" style = "border-top: 2px solid #FF9800;">주소</th>
	    						<td style = "border-top: 2px solid #000;">${dto.camInfoAddr}</td>
	    					</tr>
	    					<tr>
	    						<th scope="col">캠핑장테마</th>
	    							<td>${dto.camThemaName}</td>
	    					</tr>
	    				</tbody>
	    				
	    			
	    			</table>
	    		
	    		</div>
	    		
	    	</div>
	    </div>
	    
	    
	    
	    
	    
	    <div class="body-main mx-auto">
			<table class="table table-border table-article">
				<tbody>					
					<tr>
						<td colspan="2" height="200">
							사&nbsp;&nbsp;진 :
							<div class="img-box">
								<c:forEach var="vo" items="${listFile}">
									<img src="${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName}"
										onclick="imageViewer('${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName}');">
								</c:forEach>
							</div>
						</td>
					</tr>
				</tbody>
				
			
				
				
			</table>
			
			<table class="table">
				<tr>
					<td width="50%">
						<c:choose>
							<c:when test="${sessionScope.member.userId=='admin'}">
								<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/campInfo/update.do?num=${dto.camInfoNum}&page=${page}';">수정</button>
							</c:when>
						</c:choose>
				    	
						<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn" onclick="deletecampInfo();">삭제</button>
				    		</c:when>
				    	</c:choose>
					</td>
					<td align="right">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/campInfo/list.do?${query}';">리스트</button>
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