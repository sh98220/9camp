<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>통계</title>
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
			<h2><i class="fa-regular fa-envelope"></i> 가입 날짜 통계 </h2>
	    </div>
	    

		<form name="msgForm" method="post" action="${pageContext.request.contextPath}/mypage/stats.do">
			<input type="hidden" name="page" value="${page}">
			<div id="normalMode">
				<div>
					<label for="date">시작 날짜
 					<input type="date" name="startDate"
         				id="startDate"
        				value="${startDate}" oninput="onInputHandler()">
					</label>
				</div>

				<div>
					<label for="date">마지막 날짜
 					<input type="date" name="endDate"
         				id="endDate"
        				value="${endDate}" oninput="onInputHandler()">
					</label>
				</div>
					<script>

						let date = document.querySelector("#startDate");

						const onInputHandler = () => {
						    let val = date.value.replace(/\-/g, "");
						    let leng = val.length;
						    let result = '';

						    if(leng < 6) result = val;
						    else if(leng < 8){
						        result += val.substring(0,4);
						        result += "-";
						        result += val.substring(4);
						    } else{
						        result += val.substring(0,4);
						        result += "-";
						        result += val.substring(4,6);
						        result += "-";
						        result += val.substring(6);
						    }
						    date.value = result;
						}

						const checkValidDate = (value) => {
						    let result = true;
						    try {
						        let date = value.split("-");
						        let y = parseInt(date[0], 10),
						            m = parseInt(date[1], 10),
						            d = parseInt(date[2], 10);

						        let dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
						        result = dateRegex.test(d+'-'+m+'-'+y);
						    } catch (err) {
						        result = false;
						    }
						    return result;
						}
						
					</script>
					<script>
						let date = document.querySelector("#endDate");

						const onInputHandler = () => {
						    let val = date.value.replace(/\-/g, "");
						    let leng = val.length;
						    let result = '';

						    if(leng < 6) result = val;
						    else if(leng < 8){
						        result += val.substring(0,4);
						        result += "-";
						        result += val.substring(4);
						    } else{
						        result += val.substring(0,4);
						        result += "-";
						        result += val.substring(4,6);
						        result += "-";
						        result += val.substring(6);
						    }
						    date.value = result;
						}

						const checkValidDate = (value) => {
						    let result = true;
						    try {
						        let date = value.split("-");
						        let y = parseInt(date[0], 10),
						            m = parseInt(date[1], 10),
						            d = parseInt(date[2], 10);

						        let dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
						        result = dateRegex.test(d+'-'+m+'-'+y);
						    } catch (err) {
						        result = false;
						    }
						    return result;
						}
						
					</script>
				
			</div>
			<div>
				<label for="dataCount">총 : ${dataCount} 개</label>
			</div>
				
				<table class="table table-border table-list">
					<thead>
						<tr>
							<th class="id">아이디</th>
							<th class="name">이름</th>
							<th class="nickName">닉네임</th>
							<th class="tel">전화번호</th>
							<th class="birth">생일</th>
							<th class="email">이메일</th>
							<th class="point">포인트</th>
							<th class="regDate">생성일</th>
							<th class="updateDate">수정일</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="member" items="${list}" varStatus="status">
						<tr>
							<td>${member.userId}</td>
							<td>${member.userName}</td>
							<td>${member.userNickName}</td>
							<td>${member.userTel}</td>
							<td>${member.userBirth}</td>
							<td>${member.userEmail}</td>
							<td>${member.userPoint}</td>
							<td>${member.userRegDate}</td>
							<td>${member.userUpdateDate}</td>				
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 유저가 없습니다." : paging}
				</div>
			
			
				<button type="button" class="btn" onclick="sendOk();"> 확인 </button>
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/main.do';"> 취소 </button>
				
			</form>
			

	</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

<script type="text/javascript">

function sendOk() {
	const f = document.msgForm;
	let str;
	
	str1 = f.startDate.value.trim();
	str2 = f.endDate.value.trim();
	if(!str1 || !str2){
		alert("날짜를 입력하세요.")
		return;
	}
	

	
	if(str1 > str2){
		alert("날짜 설정이 잘못 되었습니다.")
		return;
	}
	
	
    f.action = "${pageContext.request.contextPath}/mypage/stats.do";
    f.submit();
}

</script>

</body>
</html>