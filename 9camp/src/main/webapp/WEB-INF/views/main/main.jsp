<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>가자Goo캠핑</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
/*여진*/
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
	font-size: 48px;
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
	padding-top: 50px;
	padding-bottom: 5%;
}
#keyword-cont h1 {
	margin-top: 20px;
    font-size: 48px;
    margin-bottom: 30px;
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
  background-color: #c7c4c4;
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
  padding: 10px;
  background-color: #333;
  margin: 5px;
  font-size: 20px;
  color: #fff;
}

/*메인 검색 - 민찬*/
#main_search {
	width: 100%;
	background: #56b286;
}
#main_search .section_01 {
	width: 100%;
	padding: 100px 0;
	background-image: url("${pageContext.request.contextPath}/resource/images/main/main_search_bg.png");
	background-size: 50%;
	background-position: 15% center;
	background-repeat: no-repeat;
	position: relative;
	height: 570px;
}

#main_search .section_01 h1 {
	color: #fffe5b;
	font-size: 48px;
}

#main_search .section_01 .layout {
	width: 600px;
	position: absolute;
	right: 10%;
	top: 12%;
	background: rgb(0, 0, 0, 0.6);
	padding: 50px;
	border-radius: 20px;
}
</style>
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<!-- 메인 배너 -->
	<div id="main_ban">
		<img src="${pageContext.request.contextPath}/resource/images/main/main_ban.jpg" style="width: 100%;">
	</div>
	<!-- //메인 배너 -->
	
	<!-- 메인 검색창 -->
	<div id="main_search">
		<div class="section_01">
			<div class="layout">
				<h1>
					<span class="skip">캠핑장 정보검색</span>
				</h1>
				<section id="layer_search">
					<!--타이틀-->
					<div class="m_title">
						<br>
					</div>
					
					<!--//타이틀-->
					<div class="search_box">
						
						<!--검색박스-->
						<div class="search_box_form">
						
							<div class="form1_1">
								<p class="tt" style="color: white; margin-bottom: 5px;">키워드검색</p>
								
								<label class="skip" for="searchKrwd_f"></label> 
								<input type="text" name="userId" id="userId" class="form-control" 
								style="width: 100%; height: 40px; border-radius: 5px; padding-left: 10px;">
							</div>
							
							<br>
							
							<div class="form1_2">
								<p class="tt"  style="color: white; margin-bottom: 5px;">지역별</p>
								<label class="skip" for="c_do"></label> 
								
								<select name="c_do" id="c_do" class="select_01" title="검색할 지역을 선택하세요." 
													style="width: 40%; height: 40px; border-radius: 5px; padding-left: 10px;">
									<option value="">전체/도</option>
									<option value="1">서울시</option>
									<option value="2">부산시</option>
									<option value="3">대구시</option>
									<option value="4">인천시</option>
									<option value="5">광주시</option>
									<option value="6">대전시</option>
									<option value="7">울산시</option>
									<option value="8">세종시</option>
									<option value="9">경기도</option>
									<option value="10">강원도</option>
									<option value="11">충청북도</option>
									<option value="12">충청남도</option>
									<option value="13">전라북도</option>
									<option value="14">전라남도</option>
									<option value="15">경상북도</option>
									<option value="16">경상남도</option>
									<option value="17">제주도</option>
									</select> 
									
									<label class="skip" for="c_signgu" style="color: white;"></label> <!-- 시군별 검색 -->
									
									<select name="c_signgu" id="c_signgu" class="select_02" title="검색할 지역을 선택하세요."
															style="width: 40%; height: 40px; border-radius: 5px; padding-left: 10px;">
									<option value="">전체/시/군</option>
									</select>
							</div>
							
							<br>
							
							<div class="form1_2">
								<p class="tt"  style="color: white; margin-bottom: 5px;">테마별</p>
								<label class="skip" for="searchLctCl"></label> 
								
								<select name="searchLctCl" id="searchLctCl" class="select_03" title="검색할 테마를 선택하세요."
															style="width: 40%; height: 40px; border-radius: 5px; padding-left: 10px;">
									<option value="">전체테마</option>
									<option value="47">바다</option>
									<option value="48">강</option>
									<option value="49">호수</option>
									<option value="50">숲</option>
									<option value="51">계곡</option>
									<option value="52">산</option>
									<option value="53">도심</option>
									<option value="54">섬</option>
								</select>
								
								<button type="submit" class="btnSearch_black01" style=" width: 80px; height: 40px;
									background-color: #fffe5b; color: #000; border-radius: 5px; font-weight: bold;"> 검색
								</button>
								
							</div>
						</div>
						<!--//검색박스-->
							
					</div>
				</section>
			</div>
		</div>
	</div>
	<!-- //메인 검색창 -->
	
	<!-- 키워드 검색 -->
	<div id="keyword-cont">
		<h1 class="center">키워드로 검색</h1>
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
			<img src="${pageContext.request.contextPath}/resource/images/main/recommended.png" class="img_rcm">
			<h1>이달의 추천 캠핑장</h1>
			<ul class="rcm-cont-ul">
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/main/camp_img01.jpg">
						</span>
						<span class="rcm-txt">
							바다와 파도소리, 밤이면 별이 쏟아지는 해변에서 즐기는 감성캠핑. 그곳으로 떠나요~~
						</span>
					</a>
				</li>
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/main/camp_img01.jpg">
						</span>
						<span class="rcm-txt">
							바다와 파도소리, 밤이면 별이 쏟아지는 해변에서 즐기는 감성캠핑. 그곳으로 떠나요~~
						</span>
					</a>
				</li>
				<li>
					<a href="#">
						<span class="rcm-img">
							<img src="${pageContext.request.contextPath}/resource/images/main/camp_img01.jpg">
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