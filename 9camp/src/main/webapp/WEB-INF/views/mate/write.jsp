<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<title>캠핑메이트</title>
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
    border-bottom: 2px solid #eee;;
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
	padding-bottom: 15px;
}

.inner-page{
	display: block;
	padding-top: 35px;
}

/* 캠핑스타일  */
#keyword-cont .keyword-ul > li {
	display: inline-block;
	margin: 3px;
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

/* 모달창 디자인 */
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
	background-color: #264db5;
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
	border-right:1px solid #3b5fbf;
}
	
</style>

<script type="text/javascript">
window.onload = function(){
	var now_utc = Date.now()
	var timeOff = new Date().getTimezoneOffset()*60000;
	var today = new Date(now_utc-timeOff).toISOString().split("T")[0];

	document.getElementById("dateFrom").setAttribute("min", today);

	document.getElementById("dateTo").setAttribute("min", today);

	}


function sendOk() {
    const f = document.boardForm;
	let str;
	
    str = f.camMateSubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.camMateSubject.focus();
        return;
    }

    str = f.camMateContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.camMateContent.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/mate/${mode}_ok.do";
    f.submit();
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
			<h2><i class="fas fa-graduation-cap"></i> 캠핑메이트 </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="boardForm" method="post" enctype="multipart/form-data">
				<table class="table table-border table-form">
					<tr> 
						<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
						<td> 
							<input type="text" name="camMateSubject" maxlength="100" class="form-control" value="${dto.camMateSubject}">
						</td>
					</tr>
					
					<tr> 
						<td>작성자</td>
						<td> 
							 <p>${sessionScope.member.userName}</p> 
						</td>
					</tr>
						
					<tr> 
						<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
						<td> 
							<textarea name="camMateContent" class="form-control">${dto.camMateContent}</textarea>
						</td>
					</tr>
				
					<tr>				
						<td>캠핑스타일</td>
						<td>
							<div id="keyword-cont">
									<ul class="keyword-ul">
										<li>
											<input type="checkbox" name="campstyle" id="check1" value="술을 많이 마시는" >
							        		<label for="check1">#술을 많이 마시는</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check2" value="조용한" >
							        		<label for="check2">#조용한</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check3" value="시끌벅적한" >
							        		<label for="check3">#시끌벅적한</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check4" value="술을 마시지 않는">
							        		<label for="check4">#술을 마시지 않는</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check5" value="사람이 많은">
							        		<label for="check5">#사람이 많은</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check6" value="단둘이">
							        		<label for="check6">#단둘이</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="육식파">
							        		<label for="check7">#육식파</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="채식파">
							        		<label for="check7">#채식파</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="해물파">
							        		<label for="check7">#해물파</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="즉흥적">
							        		<label for="check7">#즉흥적</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="계획적">
							        		<label for="check7">#계획적</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="내향적">
							        		<label for="check7">#내향적</label>
										</li>
										<li>
											<input type="checkbox" name="campstyle" id="check7" value="외향적">
							        		<label for="check7">#외향적</label>
										</li>
									</ul>
								</div>
						</td>					
					</tr>
					
					<tr>
					    <td>기&nbsp;&nbsp;&nbsp;&nbsp;간</td>
					    <td>
					        <p style="display: inline-block;">출발날짜&nbsp;&nbsp;</p><input type="date" name="camMateStartDate" id="dateFrom">&nbsp;&nbsp;
					        <p  style="display: inline-block;">도착날짜&nbsp;&nbsp;</p><input type="date" name="camMateEndDate" id="dateTo">&nbsp;&nbsp;
					   		<button type="button" id="btnSearch">확인</button>
					    </td>					   
					</tr>
					<tr>
						<td>회&nbsp;&nbsp;&nbsp;&nbsp;비</td>
						<td>
							<input type="text" name="camMateDues" placeholder="ex) 50,000원" value="${dto.camMateDues}"> 
						</td>
					</tr>
				</table>
	
				<table class="table">
					<tr> 
						<td align="center">
							<button type="button" class="btn" onclick="sendOk();">${mode=="update"?"수정완료":"등록완료"}</button>
							<button type="reset" class="btn">다시입력</button>
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/mate/list.do';">${mode=="update"?"수정취소":"등록취소"}</button>
							<c:if test="${mode=='update' }">
								<input type="hidden" name="camMateNum" value="${dto.camMateNum}">
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

<script>
//날짜 사이의 기간을 계산하는 함수

$('#btnSearch').click(function(){

    var dateFrom = document.getElementById('dateFrom');
    var dateTo = document.getElementById('dateTo');
    var today = new Date();      

    dateFrom = new Date(dateFrom.value);
    var fromYear = dateFrom.getFullYear();
    var fromMonth = dateFrom.getMonth() + 1;
    var fromDay = dateFrom.getDate();

    dateFrom = fromYear +'-'+ fromMonth +'-'+fromDay; 

    dateTo = new Date(dateTo.value);
    var toYear  = dateTo.getFullYear();
    var toMonth = dateTo.getMonth() + 1;
    var toDay   = dateTo.getDate();
    
    dateTo = toYear +'-'+ toMonth +'-'+toDay;

    //오늘날짜 날짜 형식으로 지정
    var todayYear  = today.getFullYear();
    var todayMonth = today.getMonth() + 1;       
    var todayDay   = today.getDate();     
    today = todayYear +'-'+ todayMonth +'-'+todayDay;  


    if(dateFrom >= today && dateTo >= dateFrom){
       alert("유효한 날짜입니다.");
       dateFrom +" ~ "+dateTo
       return true;
    } else {
       alert("유효하지 않은 날짜입니다.");
    }
  });
  
  
function calculateDuration() {
    var depDate = new Date(document.getElementById("dep").value);
    var ariDate = new Date(document.getElementById("ari").value);

    // 출발날짜와 도착날짜를 모두 선택한 경우에만 기간을 계산하고 표시
    if (depDate && ariDate) {
        var duration = ariDate - depDate;
        var days = Math.floor(duration / (1000 * 60 * 60 * 24)); // 밀리초를 일로 변환

        // NaN이거나 음수인 경우 0으로 설정
        if (isNaN(days) || days < 0) {
            days = 0;
        }

        document.getElementById("result").innerHTML = "기간 : " + days + "박" + (days + 1) + "일";
	    } else {
	        document.getElementById("result").innerHTML = ""; // 선택하지 않은 경우, 기간을 표시하지 않음
	    }
	}

	// 출발날짜 입력값이 변경될 때마다 기간 계산 함수 호출
	document.getElementById("dep").addEventListener("change", function() {
	    if (document.getElementById("ari").value) {
	        calculateDuration();
	    }
	});
	
	// 도착날짜 입력값이 변경될 때마다 기간 계산 함수 호출
	document.getElementById("ari").addEventListener("change", function() {
	    if (document.getElementById("dep").value) {
	        calculateDuration();
	    }
	});
 
	$(function(){
		$("#modal-confirm").click(function(){
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
	
	$(function() {
		$(".searchbt").click(function() {
			
			let search = document.querySelector("#search")
			let keyword = search.value;
			
			if(!keyword) {
				search.focus();
				return false;
			}
			
			
			let qs = "keyword="+keyword;
			
			let url = "${pageContext.request.contextPath}/mate/searchCamp.do";
			
			const fn = function(data) {
				let campinfo  = data.list;
				for(let item of data.list){
					let camInfoNum = item.camInfoNum;
					let camInfoSubject = item.camInfoSubject;
					let camInfoAddr = item.camInfoAddr;
					let camInfoContent = item.camInfoContent;
					
					out += "<tr>";
					out += "<td width='50%'>";
					out += "    <span style='font-weight: 600'>"+camInfoSubject+"</span>";
					out += " </td>";
					out += "   <td width='50%' align='right' > ";
					out += "     "+camInfoAddr;
					out += "    </td>";
					out += "</tr>";
					out += "<tr>";
					out += "<td colspan='2' valign='top'>" + camInfoContent + "</td>";
					out += "</tr>";
					
					console.log(out);

					//$(".resultLayout").html(out);
				
				} 
			}
			ajaxFun(url, "post", qs, "json", fn);
		});
		
	});

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

  
</body>
</html>