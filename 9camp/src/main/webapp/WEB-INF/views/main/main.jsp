<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가자구캠핑</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v6.4.0/css/all.css">
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>

<style type="text/css">
/*여진*/
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;400&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap');

* {margin: 0; padding: 0; box-sizing: border-box;}

body {
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 16px;
}

img {border: 0; display: block;}

ul, li{list-style: none;}
a {text-decoration: none; color: #333; display: block;}
header, main, footer {width: 100%;}

/*사용자 지정 공통*/
.hidden {
	display: none;
}

/*헤더 상단*/
.top-cont {
	max-width: 1200px; 
	margin: 0 auto; 
	padding: 14px 0; 
	display: flex;
    justify-content: space-between;
    align-items: center;
    }

/*로고 영역*/
.top-cont .logo-cont {
	overflow: hidden; 
	display: flex;
    justify-content: space-between;
    align-items: center;
}
.top-cont .logo-cont .logo{
	background: #ff5522;
	border-radius: 50%;
	width: 40px;
	height: 40px;
	text-align: center;
	color: #fff;
	font-size: 24px;
	line-height: 32px;
	display: inline-block;
	vertical-align: middle;
}
.top-cont .logo-cont .logo-txt {
	font-family: 'Black Han Sans', sans-serif;
	font-weight: bold;
	font-size: 36px;
	margin-left: 5px;
	color: #ff5522;
	padding-top: 5px;
	display: inline-block;
	vertical-align: middle;
}

/*오늘 방문자 영역*/
.top-cont .today-cont {
	padding: 0 10px;
	border-left: 2px solid #ff5522;
	border-right: 2px solid #ff5522;
}

.top-cont .today-cont .today-txt {
	font-weight: bold;
	color: #ff5522;
}

/*포인트, 아이콘 영역*/
.top-cont .icon-cont {
	display: flex; 
	justify-content: center;
	align-items: center; 
}

.top-cont .icon-cont > li {
	margin-right: 10px;
}

.top-cont .icon-cont > li.point-li.bar {
	color: #666;
}

.top-cont .icon-cont > li.point-li a {
	color: #333;
}

.top-cont .icon-cont > li.point-li .point-tit {
	background: #ff5522;
	border-radius: 50px;
	padding: 3px 10px;
	color: #fff;
}

.top-cont .icon-cont a.top-icon {
	display: inline-block;
}

.top-cont .icon-cont a.top-icon i {
	color: #ff5522;
	font-size: 24px;
	vertical-align: middle;
}

.top-cont .icon-cont li:last-child {
	margin-right: 0;
}

/*nav*/
#header-menu > nav { 
	/*background: #ff5522;*/
	background: linear-gradient(90deg, #ff5522, #ff7c0f, #ff9c00, #ff7c0f, #ff5522);
}
#header-menu > nav > ul.main-menu {
	width: 80%;
	max-width: 1200px;
	margin: 0 auto;
	display: flex; 
	justify-content: center;
	align-items: center; 
	color: #fff; 
	text-align: center; 
	font-size: 18px; 
	font-weight: bold;
}

#header-menu > nav > ul.main-menu > li {
	position: relative;
}

#header-menu > nav > ul.main-menu > li > a {
	border-right: 1px solid rgba(0,0,0,0.15);
	color: #fff;
	display: block;
	padding: 14px 40px;
	text-align: center;
}

#header-menu > nav > ul.main-menu > li:first-child > a {
	border-left: 1px solid rgba(0,0,0,0.15);
}

/*서브메뉴*/
.main-menu .sub-menu {
	position: absolute;
	background: #182952;
	opacity: 0;
	visibility: hidden;
	transition: all 0.15s ease-in;
	width: 100%;
}

.main-menu .sub-menu > li {
	padding: 13px 28px;
	border-bottom: 1px solid rgba(255,255,255,0.1);
}

.main-menu .sub-menu > li >  a {
	font-size: 16px;
	color: rgba(255,255,255,0.9);
	text-decoration: none;
}

.main-menu > li:hover .sub-menu {
	opacity: 1;
	visibility: visible;
}

.main-menu .sub-menu > li:hover {
	background: #008793;
}

/*이달의 추천 캠핑장*/
#rcm-cont {
	background: #eee;
	padding: 50px 0;
	position: relative;
}

#rcm-cont .rcm-cont-div {
	width: 1200px;
	margin: 0 auto;
}

#rcm-cont .rcm-cont-div .img_rcm {
	max-width: 500px;
	position: absolute;
	top: -80px;
	left: 5%;
}

#rcm-cont .rcm-cont-div h1 {
	text-align: center;
	margin-bottom: 50px;
	font-size: 60px;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul {
	display: flex;
    justify-content: space-between;
    align-items: center;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li {
	position: relative;
	width: 30%;
	height: 350px;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li > a {
	color: #fff;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-img {
	width: 100%;
	height: 100%;
	display: block;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-img > img {
	width: 100%;
	height: 350px;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-txt {
	position: absolute;
	bottom: 0;
	width: 100%;
	background: #000;
	padding: 10px;
	display: block;
}

/*키워드 검색 - 정현*/
#keyword-cont {
	width: 100%;
	background: #fff;
	padding: 50px 0;
	padding-bottom: 10%;
}

#keyword-cont .keyword-ul {
	width: 90%;
	margin: 0 auto;
}

#keyword-cont .keyword-ul > li {
	display: inline-block;
}

#keyword-cont input[type="checkbox"]{
  display: none;
}

#keyword-cont label {
  border-radius: 10px;
  color: black;
  backgroundcolor: "#ff5522";
}
      
#keyword-cont input[type="checkbox"] + label{
  margin-top: 10px;
  display: inline-block;
  padding: 10px;
  border: 3px solid #707070;
  position: relative;
  width: auto;
}

#keyword-cont input[type="checkbox"]:checked + label {
  background-color: #ff5522;
}

#keyword-cont .search-form {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#keyword-cont .search-form .btn{
  border-radius: 5px;
  display: inline-block;
}

</style>


<body>

<header>
	<!-- 메뉴바 위 -->
	<div class="top-cont">
		<div class="today-cont">
			<div class="today-txt">TODAY 캠핑러</div>
			<div><span>10,000</span>명</div>
		</div>
		
		<div class="logo-cont">
			<a href="#">
				<span class="logo">
					<i class="fa-solid fa-campground"></i>
				</span>
				<span class="logo-txt">
					가자GOO캠핑
				</span>
			</a>
		</div>
		
		<ul class="icon-cont">
			<li class="point-li">
				<a href="#"><span class="point-tit">보유 포인트</span> <span class="point-val">100,000</span> Point</a>
			</li>
			<li class="point-li bar">
				|
			</li>
			<li class="hidden">
				<a href="#" class="top-icon ic_bell" title="알림">
					<i class="fa-sharp fa-regular fa-bell"></i>
				</a>
			</li>
			<li class="hidden">
				<a href="#" class="top-icon ic_logout" title="로그아웃">
					<i class="fa-solid fa-arrow-right-from-bracket"></i>
				</a>
			</li>
			<li class="hidden">
				<a href="#" class="top-icon ic_msg" title="쪽지">
					<i class="fa-regular fa-envelope"></i>
				</a>
			</li>
			<li>
				<a href="#" class="top-icon ic_login" title="로그인">
					<i class="fa-solid fa-arrow-right-from-bracket"></i>
				</a>
			</li>
			<li>
				<a href="#" class="top-icon ic_join" title="회원가입">
					<i class="fa-solid fa-user-plus"></i>
				</a>
			</li>
			<li>
				<a href="#" class="top-icon ic_setting" title="설정">
					<i class="fa-solid fa-gear"></i>
				</a>
			</li>
		</ul>
	</div>
	<!-- //메뉴바 위 -->
	
	<!-- 메뉴바 -->
	<div id="header-menu">
		<nav>
			<ul class="main-menu">
				<li>
					<a href="#">가자Goo캠핑</a>
					<ul class="sub-menu">
						<li>
							<a href="#">캠핑장 검색</a>
						</li>
						<li>
							<a href="#">지도 검색</a>
						</li>
					</ul>
				</li>
				<li><a href="#">공지사항</a></li>
				<li>
					<a href="#">캠핑커뮤니티</a>
					<ul class="sub-menu">
						<li>
							<a href="#">전국캠핑자랑</a>
						</li>
						<li>
							<a href="#">캠핑메이트</a>
						</li>
						<li>
							<a href="#">자유게시판</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">캠핑마켓</a>
					<ul class="sub-menu">
						<li>
							<a href="#">중고거래</a>
						</li>
						<li>
							<a href="#">렌탈</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">마이페이지</a>
					<ul class="sub-menu">
						<li>
							<a href="#">찜 목록</a>
						</li>
						<li>
							<a href="#">내 글보기</a>
						</li>
						<li>
							<a href="#">정보수정</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">고객센터</a>
					<ul class="sub-menu">
						<li>
							<a href="#">Q&amp;A</a>
						</li>
						<li>
							<a href="#">정보수정요청</a>
						</li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
	<!-- //메뉴바 -->
</header>

<main>
	<!-- 메인 배너 -->
	<div id="main_ban">
		<img src="${pageContext.request.contextPath}/resource/images/main_ban.jpg" style="width: 100%;">
	</div>
	<!-- //메인 배너 -->
	
	<!-- 키워드 검색 -->
	<div id="keyword-cont">
		<form id="keyword-form" method="post">
			<ul class="keyword-ul">
				<li>
					<input type="checkbox" name="key" id="check1" value="101" >
	        		<label for="check1">#반려견동반</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check2" value="102" >
	        		<label for="check2">#바다가 보이는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check3" value="103" >
	        		<label for="check3">#친절한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check4" value="104">
	        		<label for="check4">#생태교육</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check5" value="105">
	        		<label for="check5">#힐링</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check6" value="106">
	        		<label for="check6">#문화유적</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check7" value="107">
	        		<label for="check7">#커플</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check8" value="108">
	        		<label for="check8">#봄</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check9" value="109">
	        		<label for="check9">#그늘이 많은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check10" value="110">
	        		<label for="check10">#별 보기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check11" value="111">
	        		<label for="check11">#익스트림</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check12" value="112">
	        		<label for="check12">#재미있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check13" value="113">
	        		<label for="check13">#여유있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check14" value="114">
	        		<label for="check14">#물놀이 하기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check15" value="115">
	        		<label for="check15">#둘레길</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check16" value="116">
	        		<label for="check16">#물맑은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check17" value="117">
	        		<label for="check17">#가족</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check18" value="118">
	        		<label for="check18">#캠핑카</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check19" value="119">
	        		<label for="check19">#깨끗한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check20" value="120">
	        		<label for="check20">#가을</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check21" value="121">
	        		<label for="check21">#자전거 타기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check22" value="122">
	        		<label for="check22">#수영장 있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check23" value="123">
	        		<label for="check23">#차대기 편한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check24" value="124">
	        		<label for="check24">#여름</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check25" value="125">
	        		<label for="check25">#축제</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check26" value="126">
	        		<label for="check26">#계곡옆</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check27" value="127">
	        		<label for="check27">#아이들 놀기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check28" value="128">
	        		<label for="check28">#사이트 간격이 넓은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check29" value="129">
	        		<label for="check29">#온수 잘 나오는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check30" value="130">
	        		<label for="check30">#겨울</label>
				</li>
			</ul>
	      
	 		<div class="search-form"> 
				<button type="button" class="btn" onclick="sendOk();">검색</button>
				<button type="button" class="btn" id="reset-button">초기화</button>		 
	 		</div>
		</form>
	</div>
	<!-- //키워드 검색 -->
	
	<!-- 이달의 추천 캠핑장 -->
	<div id="rcm-cont">
		<div class="rcm-cont-div">
			<img src="${pageContext.request.contextPath}/resource/images/recommended.png" class="img_rcm">
			<h1>이달의 추천 캠핑장</h1>
			<ul class="rcm-cont-ul">
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/camp_img01.jpg">
						</span>
						<span class="rcm-txt">
							바다와 파도소리, 밤이면 별이 쏟아지는 해변에서 즐기는 감성캠핑. 그곳으로 떠나요~~
						</span>
					</a>
				</li>
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/camp_img01.jpg">
						</span>
						<span class="rcm-txt">
							바다와 파도소리, 밤이면 별이 쏟아지는 해변에서 즐기는 감성캠핑. 그곳으로 떠나요~~
						</span>
					</a>
				</li>
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/camp_img01.jpg">
						</span>
						<span class="rcm-txt">
							바다와 파도소리, 밤이면 별이 쏟아지는 해변에서 즐기는 감성캠핑. 그곳으로 떠나요~~
						</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- //이달의 추천 캠핑장 -->
 
</main>

<footer>
</footer>

<script type="text/javascript">
$("input[name=key]").click(function(){
	var arTest = [];

	$("input[name=key]:checked").each(function(){
		arTest.push($(this).val());
	});
	console.log("체크된 값 total : " + arTest);
});

const resetButton = document.getElementById("reset-button");
const checkboxes = document.querySelectorAll('input[type="checkbox"]');

resetButton.addEventListener("click", function() {
	checkboxes.forEach(function(checkbox) {
		checkbox.checked = false;
	});
});
</script>
</body>
</html>