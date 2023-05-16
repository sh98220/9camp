<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<script type="text/javascript">
function memberOk() {
	const f = document.memberForm;
	let str;

	str = f.userPwd.value;
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.userPwd.focus();
		return;
	}

	if( str !== f.userPwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.userPwd.focus();
        return;
	}
	
    str = f.userName.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.userName.focus();
        return;
    }

    str = f.birth.value;
    if( !str ) {
        alert("생년월일를 입력하세요. ");
        f.birth.focus();
        return;
    }
    
    str = f.tel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.tel1.focus();
        return;
    }

    str = f.tel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.tel2.focus();
        return;
    }

    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.email1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email1.focus();
        return;
    }

    str = f.email2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.email2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/mypage/update_ok.do";
    f.submit();
}

function changeEmail() {
    const f = document.memberForm;
	    
    let str = f.selectEmail.value;
    if(str !== "direct") {
        f.email2.value = str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    }
    else {
        f.email2.value = "";
        f.email2.readOnly = false;
        f.email1.focus();
    }
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
			<h2><i class="fas fa-user"></i> ${title} </h2>
	    </div>
	    
	    <div class="body-main mx-auto">
			<form name="memberForm" method="post">
			<table class="table table-border table-form">
				<tr>
					<td>아&nbsp;이&nbsp;디</td>
					<td>
						<p>
							<input type="text" name="userId" id="userId" maxlength="10" class="form-control" value="${dto.userId}" 
								style="width: 50%;" 
								readonly='readonly'>
						</p>
					</td>
				</tr>
			
				<tr>
					<td>패스워드</td>
					<td>
						<p>
							<input type="password" name="userPwd" class="form-control" maxlength="10" style="width: 50%;">
						</p>
						<p class="help-block">패스워드는 5~10자 이내이며, 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</p>
					</td>
				</tr>
			
				<tr>
					<td>패스워드 확인</td>
					<td>
						<p>
							<input type="password" name="userPwd2" class="form-control" maxlength="10" style="width: 50%;">
						</p>
						<p class="help-block">패스워드를 한번 더 입력해주세요.</p>
					</td>
				</tr>
			
				<tr>
					<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
					<td>
						<input type="text" name="userName" maxlength="10" class="form-control" value="${dto.userName}" style="width: 50%;">
					</td>
				</tr>
			
				<tr>
					<td>생년월일</td>
					<td>
						<input type="date" name="birth" class="form-control" value="${dto.birth}" style="width: 50%;">
					</td>
				</tr>
			
				<tr>
					<td>이 메 일</td>
					<td>
						  <select name="selectEmail" class="form-select" onchange="changeEmail();">
								<option value="">선 택</option>
								<option value="naver.com"   ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
								<option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
								<option value="gmail.com"   ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
								<option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
								<option value="direct">직접입력</option>
						  </select>
						  <input type="text" name="email1" maxlength="30" class="form-control" value="${dto.email1}" style="width: 33%;"> @ 
						  <input type="text" name="email2" maxlength="30" class="form-control" value="${dto.email2}" style="width: 33%;" readonly="readonly">
					</td>
				</tr>
				
				<tr>
					<td>전화번호</td>
					<td>
						  <select name="tel1" class="form-select">
								<option value="">선 택</option>
								<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
								<option value="02"  ${dto.tel1=="02"  ? "selected='selected'" : ""}>02</option>
								<option value="031" ${dto.tel1=="031" ? "selected='selected'" : ""}>031</option>
								<option value="032" ${dto.tel1=="032" ? "selected='selected'" : ""}>032</option>
								<option value="033" ${dto.tel1=="033" ? "selected='selected'" : ""}>033</option>
								<option value="041" ${dto.tel1=="041" ? "selected='selected'" : ""}>041</option>
								<option value="042" ${dto.tel1=="042" ? "selected='selected'" : ""}>042</option>
								<option value="043" ${dto.tel1=="043" ? "selected='selected'" : ""}>043</option>
								<option value="044" ${dto.tel1=="044" ? "selected='selected'" : ""}>044</option>
								<option value="051" ${dto.tel1=="051" ? "selected='selected'" : ""}>051</option>
								<option value="052" ${dto.tel1=="052" ? "selected='selected'" : ""}>052</option>
								<option value="053" ${dto.tel1=="053" ? "selected='selected'" : ""}>053</option>
								<option value="054" ${dto.tel1=="054" ? "selected='selected'" : ""}>054</option>
								<option value="055" ${dto.tel1=="055" ? "selected='selected'" : ""}>055</option>
								<option value="061" ${dto.tel1=="061" ? "selected='selected'" : ""}>061</option>
								<option value="062" ${dto.tel1=="062" ? "selected='selected'" : ""}>062</option>
								<option value="063" ${dto.tel1=="063" ? "selected='selected'" : ""}>063</option>
								<option value="064" ${dto.tel1=="064" ? "selected='selected'" : ""}>064</option>
								<option value="070" ${dto.tel1=="070" ? "selected='selected'" : ""}>070</option>
						  </select>
						  <input type="text" name="tel2" maxlength="4" class="form-control" value="${dto.tel2}" style="width: 33%;"> -
						  <input type="text" name="tel3" maxlength="4" class="form-control" value="${dto.tel3}" style="width: 33%;">
					</td>
				</tr>
				
			</table>
			
			<table class="table">				
				<tr>
					<td align="center">
					    <button type="button" class="btn" name="btnOk" onclick="memberOk();"> 정보수정 </button>
					    <button type="reset" class="btn"> 다시입력 </button>
					    <button type="button" class="btn" 
					    	onclick="javascript:location.href='${pageContext.request.contextPath}/';"> 수정취소 </button>
					</td>
				</tr>
				
				<tr>
					<td align="center">
						<span class="msg-box">${message}</span>
					</td>
				</tr>
			</table>
			</form>
	    </div>
	</div>
	
</main>

<footer>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>