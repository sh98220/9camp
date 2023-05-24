<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>전국캠핑자랑</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
.body-main {
	max-width: 700px;
	padding-top: 15px;
}


.body-main {
	max-width: 700px;
}

/* form-control */
.btn {
	color: #333;
	border: 1px solid #999;
	background-color: #eee;
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



.text-left { text-align: left; }
.text-right { text-align: right; }
.text-center { text-align: center; }

.clear { clear: both; }
.clear:after { content:''; display:block; clear: both; }

.mx-auto { margin-left: auto; margin-right: auto; }

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

.body-main {
	display: block;
	padding-bottom: 15px;
}

.inner-page{
	display: block;
	padding-top: 35px;
}

.table-list thead > tr:first-child { background: skyblue; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px; color: #ff5522; }
.table-list .subject { color: #ff5522; }
.table-list .name { width: 100px; color: #ff5522; }
.table-list .date { width: 100px; color: #ff5522; }
.table-list .hit { width: 70px; color: #ff5522; }
.table-list .file { width: 50px; color: #ff5522; }

.table-form td { padding: 7px 0; }
.table-form p { line-height: 200%; }
.table-form tr:first-child { border-top: 2px solid #666;  }
.table-form tr > td:first-child { width: 110px; text-align: center; background: #eee; }
.table-form tr > td:nth-child(2) { padding-left: 10px; }

.table-form input[type=text], .table-form input[type=file], .table-form textarea {
	border: 1px solid #999;
	width: 96%;
}
	
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
	
.img-box {
	max-width: 600px;
	padding: 5px;
	box-sizing: border-box;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}
.img-box img {
	width: 37px; height: 37px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

.modal-btn-box { text-align:left; }
.modal-btn-box button {
	display: inline-block;
	width: 134px;
	height: 28px;
	background-color: #ffffff;
	border: 1px solid #e1e1e1;
	cursor: pointer;
}

.popup-wrap {
	background-color: rgba(0,0,0,.3);
	justify-content: center;
	align-items: center;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	display: none;
	padding: 15px;
}
.popup {
	width: 100%;
	max-width: 400px;
	background-color: #ffffff;
	border-radius: 10px;
	overflow: hidden;
	background-color: orange;
	box-shadow: 5px 10px 10px 1px rgba(0,0,0,.3);
}
.popup-head {
	width: 100%;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
}
.head-title {
	font-size: 33px;
	font-weight: 700;
    text-align: center;
}
.popup-body {
	width:100%;
	background-color:#ffffff;
}
.popup-content{
  width:100%;
  padding:30px;
}

.popup-foot{
	width: 100%;
	height: 50px;
}
.pop-btn{
	display:inline-flex;
	width:50%;
	height:100%;
	float:left;
	justify-content:center;
	align-items:center;
	color:#ffffff;
	cursor:pointer;
}
.pop-btn.confirm {
	border-right:1px solid #ffffff;
}


</style>

<script type="text/javascript">
function sendOk() {
    const f = document.campInfoForm;
	let str;
	
    str = f.camInfoSubject.value.trim();
    if(!str) {
        alert("캠핑장 이름을 입력하세요. ");
        f.camInfoSubject.focus();
        return;
    }

    str = f.camInfoAddr.value.trim();
    if(!str) {
        alert("주소를 입력하세요. ");
        f.camInfoAddr.focus();
        return;
    }
    
    str = f.camInfoContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.camInfoContent.focus();
        return;
    }
    
    
    
    str = f.camThemaName.value.trim();
    if(!str) {
    	alert("테마명을 입력하세요");
    	f.camThemaName.focus();
    	return;
    }
    
    str = f.camKeyWord.value.trim();

    str = f.camPhoneNum.value.trim();
    if(!str) {
    	alert("전화번호를 입력하세요");
    	f.camPhoneNum.focus();
    	return;
    }
    
    str = f.camNomalWeekDayPrice.value.trim();
    if(!str) {
    	alert("평상시 평일 가격을 입력하세요");
    	f.camNomalWeekDayPrice.focus();
    	return;
    }
    
    str = f.camNomalWeekEndPrice.value.trim();
    if(!str) {
    	alert("평상시 주말 가격을 입력하세요");
    	f.camNomalWeekEndPrice.focus();
    	return;
    }
    
    str = f.camPeakWeekDayPrice.value.trim();
    if(!str) {
    	alert("성수기 평일 가격을 입력하세요");
    	f.camPeakWeekDayPrice.focus();
    	return;
    }
    
    str = f.camPeakWeekEndPrice.value.trim();
    if(!str) {
    	alert("성수기 주말 가격을 입력하세요");
    	f.camPeakWeekEndPrice.focus();
    	return;
    }
    
    str = f.camInfoLineContent.value.trim();
    if(!str) {
    	alert("한줄소개를 해주세요!");
    	f.camInfoLineContent.focus();
    	return;
    }
    
    
    
    let mode = "${mode}";
    if( (mode === "write") && (!f.selectFile.value) ) {
        alert("이미지 파일을 추가 하세요. ");
        f.selectFile.focus();
        return;
    }

    
    f.action = "${pageContext.request.contextPath}/campInfo/${mode}_ok.do";
    f.submit();
}


<c:if test="${mode == 'update'}">
	function deleteFile(camInfoPhotoNum) {
		let cnt = $(".img-box").find("img").length;
		if(cnt == 1){
			alert('이미지가 한개면 삭제할 수 없습니다.')
			return;
		}
		
		if(confirm('이미지를 삭제하시겠습니까 ?')){
			let query = "camInfoNum=${dto.camInfoNum}&camInfoPhotoNum=" + camInfoPhotoNum + "&page=${page}";
			let url = "${pageContext.request.contextPath}/campInfo/deleteFile.do";
			location.href = url + "?" + query;
		}
		
	}
</c:if>



</script>
</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>
	
<main>
	<div class="container body-container">
	    <div class="body-title">
			<h2><i class="fas fa-graduation-cap"></i> 캠핑장등록 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="campInfoForm" method="post" enctype="multipart/form-data">
				<table class="table table-border table-form">
					<tr> 
						<td>캠핑장</td>
						<td> 
							<input type="text" name="camInfoSubject" maxlength="100" class="form-control" value="${dto.camInfoSubject}">
						</td>
					</tr>

					<tr> 
						<td>주소</td>
						<td> 
							<input type="text" name="camInfoAddr" maxlength="100" class="form-control" value="${dto.camInfoAddr}">						
						</td>
					</tr>
					
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td> 
							<textarea name="camInfoContent" class="form-control">${dto.camInfoContent}</textarea>
						</td>
					</tr>
			
			
					<tr> 
						<td valign="top">한줄소개</td>
						<td> 
							<textarea name="camInfoLineContent" class="form-control">${dto.camInfoLineContent}</textarea>
						</td>
					</tr>	
						
					<tr>
						<td>사&nbsp;&nbsp;&nbsp;&nbsp;진</td>
						<td> 
							<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="form-control">
						</td>		
					</tr>
					
					<tr>
						<td>테&nbsp;&nbsp;&nbsp;&nbsp;마</td>
						<td> 
							<i class="fa-solid fa-fan"></i>봄<input type="radio" name="camThemaName" maxlength="100" class="form-control" value="봄">
							<i class="fa-solid fa-umbrella-beach"></i>여름<input type="radio" name="camThemaName" maxlength="100" class="form-control" value="여름">
							<i class="fa-solid fa-wheat-awn"></i>가을<input type="radio" name="camThemaName" maxlength="100" class="form-control" value="가을">
							<i class="fa-solid fa-snowflake"></i>겨울<input type="radio" name="camThemaName" maxlength="100" class="form-control" value="겨울">
						</td>		
					</tr>
					
					<tr>
						<td>전화번호</td>
						<td> 
							<input type="text" name="camPhoneNum" maxlength="100" class="form-control" value="${dto.camPhoneNum}">
						</td>		
					</tr>
					
					<tr>
						<td>평상시평일</td>
						<td> 
							<input type="text" name="camNomalWeekDayPrice" maxlength="100" class="form-control" value="${dto.camNomalWeekDayPrice}">
						</td>		
					</tr>
					
					<tr>
						<td>평상시주말</td>
						<td> 
							<input type="text" name="camNomalWeekEndPrice" maxlength="100" class="form-control" value="${dto.camNomalWeekEndPrice}">
						</td>		
					</tr>
					
					<tr>
						<td>성수기평일</td>
						<td> 
							<input type="text" name="camPeakWeekDayPrice" maxlength="100" class="form-control" value="${dto.camPeakWeekDayPrice}">
						</td>		
					</tr>
					
					<tr>
						<td>성수기주말</td>
						<td> 
							<input type="text" name="camPeakWeekEndPrice" maxlength="100" class="form-control" value="${dto.camPeakWeekEndPrice}">
						</td>		
					</tr>
					
					<tr>
						<td>캠핑장시설</td>
						<td> 
							<input type="text" name="camFacility" maxlength="100" class="form-control" value="${dto.camFacility}">
						</td>		
					</tr>
					
					<tr>
						<td>키워드</td>
					
						<td><!-- 검색 버튼 -->
						    <div class="container">
								<div class="modal-btn-box">
									<input type="text" name="camKeyWord" id= "selected-keywords" value="${dto.camKeyWord}" readonly="readonly" style= "width: 30%;">
									<button type="button" id="modal-open" >키워드 선택하기</button>  
								</div>
							  
								<div class="popup-wrap" id="popup">
									<div class="popup">
										<div class="popup-head">
											<span class="head-title">키워드 추가하기</span>
							      		</div>
										<div class="popup-body">
											<div class="popup-content">
												<c:forEach var="dto" items="${list}" varStatus="status">
													<input type="checkbox" name="keyWordName" value="${dto.keyWordName}">${dto.keyWordName}
												</c:forEach>
											</div>
										</div>
										<div class="popup-foot">
											<span class="pop-btn confirm" id="modal-confirm">확인</span>
											<span class="pop-btn close" id="modal-close">창 닫기</span>
										</div>			
									</div>
								</div>
							
							</div>   				
   						</td>	
						
					</tr>
					
				
					<c:if test="${mode == 'update' }">
						<tr>
							<td>등록이미지</td>
							<td>
								<div class="img-box">
									<c:forEach var="vo" items="${listFile}">
										<img src="${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName}"
											onclick="deleteFile('${vo.camInfoPhotoNum}');">
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:if>
					
			
				</table>
					
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendOk();">${mode=="update"?"수정완료":"등록완료"}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/campInfo/list.do';">${mode=="update"?"수정취소":"등록취소"}</button>
							<c:if test="${mode=='update' }">
								<input type="hidden" name="camInfoNum" value="${dto.camInfoNum}">
								<input type="hidden" name="page" value="${page}">
							</c:if>							
						</td>
					</tr>
				</table>
		
			</form>

	    </div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<script type="text/javascript">
$(function(){
	 $("#modal-confirm").click(function() {

	    var selectedKeywords = [];
	    $("input[name=keyWordName]:checked").each(function() {
	      selectedKeywords.push($(this).val());
	    });
	 
	    $("#selected-keywords").val('#' + selectedKeywords.join("#"));
	  
	    modalClose();
	 });
	
	 
	 
	$("#modal-open").click(function(){
		$("#popup").css('display','flex').hide().fadeIn();
	});
	
	$("#modal-close").click(function(){
		modalClose();
	});
	
	function modalClose(){
		$("#popup").fadeOut();
	}
});


</script>

</body>
</html>