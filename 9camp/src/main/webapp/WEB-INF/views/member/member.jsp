<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--  안녕  -->
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<style type="text/css">
:root {
	--primary-color: orange;
	--secondary-color: orange;
	--black: orange;
	--white: #ffffff;
	--gray: #efefef;
	--gray-2: #757575;
	--facebook-color: #4267B2;
	--google-color: #DB4437;
	--twitter-color: #1DA1F2;
	--insta-color: #E1306C;
}

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

html, body {
	height: 100vh;
	overflow: hidden;
}

.container {
	position: relative;
	min-height: 100vh;
	overflow: hidden;
}

.row {
	display: flex;
	flex-wrap: wrap;
	height: 100vh;
}

.col {
	width: 50%;
}

.align-items-center {
	display: flex;
	align-items: center;
	justify-content: center;
	text-align: center;
}

.form-wrapper {
	width: 100%;
	max-width: 28rem;
}

.form {
	padding: 1rem;
	background-color: var(--white);
	border-radius: 1.5rem;
	width: 100%;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	transform: scale(0);
	transition: .5s ease-in-out;
	transition-delay: 1s;
}

.input-group {
	position: relative;
	width: 100%;
	margin: 1rem 0;
}

.input-group i {
	position: absolute;
	top: 50%;
	left: 1rem;
	transform: translateY(-50%);
	font-size: 1.4rem;
	color: var(--gray-2);
}

.input-group input {
	width: 100%;
	padding: 1rem 1rem;
	font-size: 1rem;
	background-color: var(--gray);
	border-radius: .5rem;
	border: 0.125rem solid var(--white);
	outline: none;
}

fjg
.input-group input:focus {
	border: 0.125rem solid var(--primary-color);
}

.form button {
	cursor: pointer;
	width: 100%;
	padding: .6rem 0;
	border-radius: .5rem;
	border: none;
	background-color: var(--primary-color);
	color: var(--white);
	font-size: 1.2rem;
	outline: none;
}

.form p {
	margin: 1rem 0;
	font-size: .7rem;
}

.flex-col {
	flex-direction: column;
}

.social-list {
	margin: 2rem 0;
	padding: 1rem;
	border-radius: 1.5rem;
	width: 100%;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	transform: scale(0);
	transition: .5s ease-in-out;
	transition-delay: 1.2s;
}

.social-list>div {
	color: var(--white);
	margin: 0 .5rem;
	padding: .7rem;
	cursor: pointer;
	border-radius: .5rem;
	cursor: pointer;
	transform: scale(0);
	transition: .5s ease-in-out;
}

.social-list>div:nth-child(1) {
	transition-delay: 1.4s;
}

.social-list>div:nth-child(2) {
	transition-delay: 1.6s;
}

.social-list>div:nth-child(3) {
	transition-delay: 1.8s;
}

.social-list>div:nth-child(4) {
	transition-delay: 2s;
}

.social-list>div>i {
	font-size: 1.5rem;
	transition: .4s ease-in-out;
}

.social-list>div:hover i {
	transform: scale(1.5);
}

.facebook-bg {
	background-color: var(--facebook-color);
}

.google-bg {
	background-color: var(--google-color);
}

.twitter-bg {
	background-color: var(--twitter-color);
}

.insta-bg {
	background-color: var(--insta-color);
}

.pointer {
	cursor: pointer;
}

.container.sign-in .form.sign-in, .container.sign-in .social-list.sign-in,
	.container.sign-in .social-list.sign-in>div, .container.sign-up .form.sign-up,
	.container.sign-up .social-list.sign-up, .container.sign-up .social-list.sign-up>div
	{
	transform: scale(1);
}

.content-row {
	position: absolute;
	top: 0;
	left: 0;
	pointer-events: none;
	z-index: 6;
	width: 100%;
}

.text {
	margin: 4rem;
	color: var(--white);
}

.text h2 {
	font-size: 3.5rem;
	font-weight: 800;
	margin: -8rem 0;
	transition: 1s ease-in-out;
}

.text p {
	font-weight: 600;
	transition: 1s ease-in-out;
	transition-delay: .2s;
}

.img img {
	width: 30vw;
	transition: 1s ease-in-out;
	transition-delay: .4s;
}

.text.sign-in h2, .text.sign-in p, .img.sign-in img {
	transform: translateX(-250%);
}

.text.sign-up h2, .text.sign-up p, .img.sign-up img {
	transform: translateX(250%);
}

.container.sign-in .text.sign-in h2, .container.sign-in .text.sign-in p,
	.container.sign-in .img.sign-in img, .container.sign-up .text.sign-up h2,
	.container.sign-up .text.sign-up p, .container.sign-up .img.sign-up img
	{
	transform: translateX(0);
}

/* BACKGROUND */
.container::before {
	content: "";
	position: absolute;
	top: 0;
	right: 0;
	height: 100vh;
	width: 300vw;
	transform: translate(35%, 0);
	background-image: linear-gradient(-45deg, var(--primary-color) 0%,
		var(--secondary-color) 100%);
	transition: 1s ease-in-out;
	z-index: 6;
	box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
	border-bottom-right-radius: max(50vw, 50vh);
	border-top-left-radius: max(50vw, 50vh);
}

.container.sign-in::before {
	transform: translate(0, 0);
	right: 50%;
}

.container.sign-up::before {
	transform: translate(100%, 0);
	right: 50%;
}

.selectbox {
	width: 27%;
	padding: 1rem 1rem;
	font-size: 1rem;
	background-color: var(--gray);
	border-radius: .5rem;
	border: 0.125rem solid var(--white);
	outline: none;
	color: #757575;
}

.selectbox2 {
	width: 30%;
	padding: 1rem 1rem;
	font-size: 1rem;
	background-color: var(--gray);
	border-radius: .5rem;
	border: 0.125rem solid var(--white);
	outline: none;
	color: #757575;
}

/* RESPONSIVE */
@media only screen and (max-width: 425px) {
	.container::before, .container.sign-in::before, .container.sign-up::before
		{
		height: 100vh;
		border-bottom-right-radius: 0;
		border-top-left-radius: 0;
		z-index: 0;
		transform: none;
		right: 0;
	}

	/* .container.sign-in .col.sign-up {
        transform: translateY(100%);
    } */
	.container.sign-in .col.sign-in, .container.sign-up .col.sign-up {
		transform: translateY(0);
	}
	.content-row {
		align-items: flex-start !important;
	}
	.content-row .col {
		transform: translateY(0);
		background-color: unset;
	}
	.col {
		width: 100%;
		position: absolute;
		padding: 2rem;
		background-color: var(--white);
		border-top-left-radius: 2rem;
		border-top-right-radius: 2rem;
		transform: translateY(100%);
		transition: 1s ease-in-out;
	}
	.row {
		align-items: flex-end;
		justify-content: flex-end;
	}
	.form, .social-list {
		box-shadow: none;
		margin: 0;
		padding: 0;
	}
	.text {
		margin: 0;
	}
	.text p {
		display: none;
	}
	.text h2 {
		margin: .5rem;
		font-size: 2rem;
	}
}
</style>
<!-- 김성훈 -->

<script type="text/javascript">
function sendLogin() {
    const f = document.loginForm;

	let str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
}

function changeEmail() {
    const f = document.memberForm;
	    
    let str = f.selectEmail.value;
    if(str !== "direct") {
        f.email2.value = str; 
        f.email2.readOnly = true;
        f.email1.focus(); 
    } else {
        f.email2.value = "";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}
</script>

</head>
<body>
	<div id="container" class="container">
		<!-- FORM SECTION -->
		<div class="row">
			<!-- SIGN UP -->
			<div class="col align-items-center flex-col sign-up">
				<div class="form-wrapper align-items-center">
					<div class="form sign-up">

						<form name="memberForm">

							<div class="input-group">
								<i class='bx bxs-user'></i> <input type="text" name="userId"
									placeholder="아이디">
							</div>
							<div class="input-group">
								<i class='bx bxs-lock-alt'></i> <input type="password"
									name="userPwd" placeholder="비밀번호">
							</div>
							<div class="input-group">
								<i class=''></i> <input type="text" name="userName"
									placeholder="이름">
							</div>
							<div class="input-group">
								<input type="text" name="email1" maxlength="30"
									class="form-control" value="${dto.email1}" style="width: 33%;">
								@ <input type="text" name="email2" maxlength="30"
									class="form-control" value="${dto.email2}" style="width: 33%; color: #757575;"
									readonly="readonly"> 
									
									
									<select name="selectEmail" style="color: #757575;"
									class="selectbox" onchange="changeEmail();">
									<option value="">선 택</option>
									<option value="naver.com"
										${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버
										메일</option>
									<option value="hanmail.net"
										${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한
										메일</option>
									<option value="gmail.com"
										${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지
										메일</option>
									<option value="hotmail.com"
										${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫
										메일</option>
									<option value="direct">직접입력</option>
								</select>

							</div>

							<div class="input-group">
								<select name="tel1" class="selectbox2" style="color: #757575;">
									<option value="">선 택</option>
									<option value="010"
										${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
									<option value="02"
										${dto.tel1=="02"  ? "selected='selected'" : ""}>02</option>
									<option value="031"
										${dto.tel1=="031" ? "selected='selected'" : ""}>031</option>
									<option value="032"
										${dto.tel1=="032" ? "selected='selected'" : ""}>032</option>
									<option value="033"
										${dto.tel1=="033" ? "selected='selected'" : ""}>033</option>
									<option value="041"
										${dto.tel1=="041" ? "selected='selected'" : ""}>041</option>
									<option value="042"
										${dto.tel1=="042" ? "selected='selected'" : ""}>042</option>
									<option value="043"
										${dto.tel1=="043" ? "selected='selected'" : ""}>043</option>
									<option value="044"
										${dto.tel1=="044" ? "selected='selected'" : ""}>044</option>
									<option value="051"
										${dto.tel1=="051" ? "selected='selected'" : ""}>051</option>
									<option value="052"
										${dto.tel1=="052" ? "selected='selected'" : ""}>052</option>
									<option value="053"
										${dto.tel1=="053" ? "selected='selected'" : ""}>053</option>
									<option value="054"
										${dto.tel1=="054" ? "selected='selected'" : ""}>054</option>
									<option value="055"
										${dto.tel1=="055" ? "selected='selected'" : ""}>055</option>
									<option value="061"
										${dto.tel1=="061" ? "selected='selected'" : ""}>061</option>
									<option value="062"
										${dto.tel1=="062" ? "selected='selected'" : ""}>062</option>
									<option value="063"
										${dto.tel1=="063" ? "selected='selected'" : ""}>063</option>
									<option value="064"
										${dto.tel1=="064" ? "selected='selected'" : ""}>064</option>
									<option value="070"
										${dto.tel1=="070" ? "selected='selected'" : ""}>070</option>
								</select> <input type="text" name="tel2" maxlength="4"
									class="form-control" value="${dto.tel2}" style="width: 33%;">
								- <input type="text" name="tel3" maxlength="4"
									class="form-control" value="${dto.tel3}" style="width: 33%;">
							</div>
							<div class="input-group">
								<i class=''></i> <input type="text" placeholder="닉네임">
							</div>
							<div class="input-group">
								<i class=''></i> <input type="text" placeholder="생년월일">
							</div>

						</form>
						<button>Sign up</button>
						<p>
							<span> 계정이 이미 있으신가요? </span> <b onclick="toggle()"
								class="pointer"> Sign in here </b>
						</p>
					</div>
				</div>

			</div>
			<!-- END SIGN UP -->
			<!-- SIGN IN -->

			<div class="col align-items-center flex-col sign-in">
				<div class="form-wrapper align-items-center">
					<div class="form sign-in">
						<form name="loginForm" method="post">
							<div class="input-group">
								<i class='bx bxs-user'></i> <input name="userId" type="text"
									placeholder="아이디를 입력해주세요">
							</div>
							<div class="input-group">
								<i class='bx bxs-lock-alt'></i> <input name="userPwd"
									type="password" placeholder="비밀번호를 입력해주세요">
							</div>
							<button type="button" onclick="sendLogin();">Sign in</button>
							<p>
								<b><a href="${pageContext.request.contextPath}/member/userIdForm.do">비밀번호를 잊으셨나요?</a></b>
							</p>
							<p>
								<span> 계정이 없으신가요? </span> 
								<b onclick="toggle()" class="pointer">
									Sign up here </b>
							</p>
						</form>
					</div>
				</div>

				<div class="form-wrapper"></div>
			</div>

			<!-- END SIGN IN -->
		</div>
		<!-- END FORM SECTION -->
		<!-- CONTENT SECTION -->
		<div class="row content-row">
			<!-- SIGN IN CONTENT -->
			<div class="col align-items-center flex-col">
				<div class="text sign-in">
					<h2 style="font-size: 60px;">
						HELLO<br>CAMPER!
					</h2>


				</div>
				<div class="img sign-in"></div>
			</div>
			<!-- END SIGN IN CONTENT -->
			<!-- SIGN UP CONTENT -->
			<div class="col align-items-center flex-col">
				<div class="img sign-up"></div>
				<div class="text sign-up">
					<h2 style="margin: 0rem 0;">TO BE CAMPER</h2>

				</div>
			</div>
			<!-- END SIGN UP CONTENT -->
		</div>
		<!-- END CONTENT SECTION -->
	</div>

	<script type="text/javascript">
	let container = document.getElementById('container');

toggle = () => {
  container.classList.toggle('sign-in')
  container.classList.toggle('sign-up')
}

setTimeout(() => {
  container.classList.add('sign-in')
	}, 200)
</script>
</body>
</html>