<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지보내기</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
#writeMessageWrap {
	max-width: 700px;
	margin: 0 auto;
	padding-top: 35px;
	min-height: 500px;
}

#writeMessageWrap .body-title {
    color: #424951;
    padding-top: 35px;
    padding-bottom: 7px;
    margin: 0 0 25px 0;
    border-bottom: 2px solid #eee;
}

#writeMessageWrap .body-title h2 {
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

#writeMessageWrap .btn {
	padding: 5px 10px;
	font-size: 14px;
	color: #333; font-weight: 500;
	border: 1px solid #999; border-radius: 4px;
	background-color: #fff;
	cursor:pointer;
	vertical-align: middle;
}
#writeMessageWrap .btn:active, #writeMessageWrap .btn:focus, #writeMessageWrap .btn:hover {
	color:#fff;
	background-color: #333;
}
#writeMessageWrap .btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: default;
	opacity: .65;
}

/**/
#writeMessageWrap .tf_tit input#who {
	border: 1px solid #adafaa;
	padding: 5px;
	height: 30px;
	vertical-align: middle;
}

#writeMessageWrap .btn-modal {
	display: inline-block;
	vertical-align: middle;
}

#writeMessageWrap .btn-modal:hover {
	
}

#writeMessageWrap .writing_area {
	width: 100%;
}

#writeMessageWrap .writing_area {
    clear: both;
    border: 1px solid #adafaa;
    height: 200px;
    position: relative;
    padding: 5px 8px;
    background-color: #fff;
    margin: 10px 0;
}

#writeMessageWrap .writing_area textarea {
	width: 100%;
	height: 100%;
	border: none;
}

/*모달*/
#modal-open {
	vertical-align: middle;
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

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<div id="writeMessageWrap">
		<div class="body-title">
			<h2><i class="fa-regular fa-envelope"></i> 쪽지보내기 </h2>
	    </div>
	    
		<form name="msgForm" method="post">
			<div id="normalMode">
				<div class="tf_tit">
					<label for="who" class="recipient">받는사람</label>
					<input type="text" id="who" name="msgSenderId" value="${msgRecId}" placeholder="아이디 입력">
					<!--<button type="button" id="modal-open" class="btn">검색</button>-->  
				</div>
				<div class="writing_area">
					<textarea id="writeNote" name="msgContent" style="resize:none;" rows="5" cols="55" title="쪽지 내용을 입력해 주세요."></textarea>
				</div>
			</div>
			<button type="button" class="btn" onclick="sendOk();"> 보내기 </button>
			<button type="button" class="btn" onclick="history.back();"> 취소 </button>
		</form> 
	</div>
  
	<div class="popup-wrap" id="popup">
		<div class="popup">
			<div class="popup-head">
				<span class="head-title">제목</span>
      		</div>
			<div class="popup-body">
				<div class="popup-content">
					<p> 팝업 내용 입니다.</p>
				</div>
			</div>
			<div class="popup-foot">
				<span class="pop-btn confirm" id="modal-confirm">확인</span>
				<span class="pop-btn close" id="modal-close">창 닫기</span>
			</div>			
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<script type="text/javascript">
/*
$(function () {
	$("#who").keyup(function(event) {		
		const searchWord = $("#who").val();
		$.ajax({
   			url:"autocomplete.jsp"
   			,dataType:"json"
   			,type:"get"
   			,data:searchWord
   			,cache:false 
   			,success:function(data){  //["JAMES","JONES"]
   				//alert(data);
   				$( "#who" ).autocomplete({
   			      source: data //검색한 값이 소스로 들어가도록
   			    });
   			}
		    ,error: function() {
				//alert("에러");
			}
   		});//ajax
		
		
	}); //keyup
});
*/

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

function sendOk() {
	const f = document.msgForm;
	let str;
	
	str = f.msgSenderId.value.trim();
	if(!str) {
	    alert("받는 사람 아이디를 입력하세요. ");
	    f.msgSenderId.focus();
		return;
	}
	
    str = f.msgContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.msgContent.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/message/write_ok.do";
    f.submit();
}




</script>

</body>
</html>