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
/*메인 배너*/
#main_ban {
	background: url(${pageContext.request.contextPath}/resource/images/main/main_ban01.jpg) no-repeat center center;
	background-size: 100% 100%;
	position: relative;
	min-height: 470px;
	cursor: pointer;
}

#main_ban .main_bg_txt {
	width: 650px;
	position: absolute;
	top: 13%;
	left: 5%;
}

#main_ban .main_bg_txt > img {
	width: 100%;
}

#main_ban .main_bg_btn {
	display: inline-block;
	padding: 5px 14px;
	position: absolute;
	bottom: 17%;
	left: 5%;
	background: #ff5522;
	color: #fff;
	border-radius: 50px;
	font-size: 32px;
	font-weight: bold;
}

#main_ban .main_bg_btn:hover {
	background: #000;
}

/*이달의 추천 캠핑장*/
#rcm-cont {
	width: 100%;
	background: #f0f1f5;
	padding: 50px 0;
	position: relative;
}

#rcm-cont .rcm-cont-div {
	width: 90%;
	max-width: 1200px;
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
    flex-wrap: wrap;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li {
	position: relative;
	width: 30%;
	height: 350px;
	overflow: hidden;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li > a {
	color: #fff;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-img {
	width: 100%;
	height: 100%;
	display: block;
	transition: transform ease-in 0.5s;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li:hover .rcm-img {
	transform: scale(1.2);
	transition: transform ease-in 0.5s;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-img > img {
	width: 100%;
	height: 350px;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li .rcm-txt {
	position: absolute;
	bottom: 0;
	width: 100%;
	background: rgba(1,1,1,1);
	padding: 10px;
	display: block;
	transition: transform ease-in 0.5s;
}

#rcm-cont .rcm-cont-div .rcm-cont-ul > li:hover .rcm-txt {
	background: rgba(1,1,1,0.6);
	transition: transform ease-in 0.5s;
	/*height: 50%;*/
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
	text-align: center;
}

#keyword-cont .keyword-ul > li {
	display: inline-block;
	margin: 3px;
}

#keyword-cont input[type="checkbox"]{
  display: none;
}

#keyword-cont label {
  border-radius: 5px;
  border: 1px solid #707070;
  display: inline-block;
  padding: 5px 10px;
  position: relative;
  cursor: pointer;
  font-size: 18px;
  background: #efefef;
}

#keyword-cont label:hover {
	background-color: #c7c4c4;
}
      
#keyword-cont input[type="checkbox"] + label{
  
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
  padding: 5px 10px;
  background-color: #333;
  margin: 5px;
  font-size: 20px;
  color: #fff;
  cursor: pointer;
}

/*메인 검색 - 민찬*/
#main_search {
	width: 100%;
	background: #f0f1f5;
	overflow: hidden;
}
#main_search .section_01 {
	width: 100%;
	padding: 100px 0;
	background: url(${pageContext.request.contextPath}/resource/images/main/main_search_bg03_3.jpg) no-repeat center center;
	position: relative;
	min-height: 570px;
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
	background: rgb(44, 55, 13, 0.8);
	padding: 50px;
	border-radius: 20px;
}

#main_search .section_01 .btnSearch {
	width: 80px; height: 40px;
	background: #fffe5b;
	border-radius: 5px; 
	font-weight: bold; 
	border: none; 
	cursor: pointer;
}

#main_search .section_01 .btnSearch:hover {
	background: #000;
	color: #fff;
}

@media (max-width: 1920px) {
	#main_search .section_01 {
		
	}
}

.btn1 {
  border: none;
  display: block;
  text-align: center;
  cursor: pointer;
  text-transform: uppercase;
  outline: none;
  overflow: hidden;
  position: relative;
  color: #000;
  font-weight: 700;
  font-size: 15px;
  background-color: #fff;
  padding: 17px 60px;
  margin: 12px auto;
  box-shadow: 0 5px 15px rgba(0,0,0,0.20);

}

.btn1 span {
  position: relative; 
  z-index: 1;
}

.btn1:after {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  height: 490%;
  width: 140%;
  background: #FFC107;
  -webkit-transition: all .5s ease-in-out;
  transition: all .5s ease-in-out;
  -webkit-transform: translateX(-98%) translateY(-25%) rotate(45deg);
  transform: translateX(-98%) translateY(-25%) rotate(45deg);
}

.btn1:hover:after {
  -webkit-transform: translateX(-9%) translateY(-25%) rotate(45deg);
  transform: translateX(-9%) translateY(-25%) rotate(45deg);
}

</style>
<script type="text/javascript">
function searchList() {
	const f = document.searchForm;
	f.submit();
}
</script>
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</header>

<main>
	<!-- 메인 배너 -->
	<div id="main_ban">
		<div class="main_bg_txt">
			<img alt="" src="${pageContext.request.contextPath}/resource/images/main/main_bg_txt.png">
		</div>
		<a href="#main_search" class="main_bg_btn">
			검색하기 &gt;
		</a>
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
						<form name="searchForm" action="${pageContext.request.contextPath}/campInfo/list.do" method="post">
						
								<select name="condition" class="form-select" class="form-control" style="width: 16%; height: 35px; border-radius: 3px;">
									<option value="all"      ${condition=="all"?"selected='selected'":"" }>모든내용</option>
									<option value="camInfoSubject"  ${condition=="camInfoSubject"?"selected='selected'":"" }>제목</option>
									<option value="camInfoContent"  ${condition=="camInfoContent"?"selected='selected'":"" }>내용</option>
									<option value="camThemaName"  ${condition=="camThemaName"?"selected='selected'":"" }>테마</option>
									<option value="caminfoAddr" 	${condition=="camInfoAddr"?"selected='selected'":"" }>지역</option>
								</select>
								
						
								
								<input type="text" name="keyword" value="${keyword}" class="form-control" style="width: 83%; height: 35px; border-radius: 3px; padding-left: 10px;" placeholder="내용을 입력해주세요.">
								<input type="hidden" name="category" value="${category}">
								<button type="button" class="btn1" onclick="searchList();"><span>SEARCH</span></button>
							<!--검색박스-->
						
						<!--//검색박스-->
						</form>
					</div>
				</section>
			</div>
		</div>
	</div>
	<!-- //메인 검색창 -->
	
	<!-- 키워드 검색 -->
	<div id="keyword-cont">
		<h1 class="center">키워드로 검색</h1>
		<form name="keywordForm" action="${pageContext.request.contextPath}/campInfo/list.do" method="get">
			<ul class="keyword-ul">
				<li>
					<input type="checkbox" name="key" id="check1" value="#반려견동반" >
	        		<label for="check1">#반려견동반</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check2" value="#바다가 보이는" >
	        		<label for="check2">#바다가 보이는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check3" value="#친절한" >
	        		<label for="check3">#친절한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check4" value="#생태교육">
	        		<label for="check4">#생태교육</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check5" value=">#힐링">
	        		<label for="check5">#힐링</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check6" value="#문화유적">
	        		<label for="check6">#문화유적</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check7" value="#커플">
	        		<label for="check7">#커플</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check8" value="#봄">
	        		<label for="check8">#봄</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check9" value="#그늘이 많은">
	        		<label for="check9">#그늘이 많은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check10" value="#별 보기 좋은">
	        		<label for="check10">#별 보기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check11" value="#익스트림">
	        		<label for="check11">#익스트림</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check12" value="#재미있는">
	        		<label for="check12">#재미있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check13" value="#여유있는">
	        		<label for="check13">#여유있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check14" value="#물놀이 하기 좋은">
	        		<label for="check14">#물놀이 하기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check15" value="#둘레길">
	        		<label for="check15">#둘레길</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check16" value="#물맑은">
	        		<label for="check16">#물맑은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check17" value="#가족">
	        		<label for="check17">#가족</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check18" value="#캠핑카">
	        		<label for="check18">#캠핑카</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check19" value="#깨끗한">
	        		<label for="check19">#깨끗한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check20" value="#가을">
	        		<label for="check20">#가을</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check21" value="#자전거 타기 좋은">
	        		<label for="check21">#자전거 타기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check22" value="#수영장 있는">
	        		<label for="check22">#수영장 있는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check23" value="#차대기 편한">
	        		<label for="check23">#차대기 편한</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check24" value="#여름">
	        		<label for="check24">#여름</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check25" value="#축제">
	        		<label for="check25">#축제</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check26" value="#계곡옆">
	        		<label for="check26">#계곡옆</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check27" value="#아이들 놀기 좋은">
	        		<label for="check27">#아이들 놀기 좋은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check28" value="#사이트 간격이 넓은">
	        		<label for="check28">#사이트 간격이 넓은</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check29" value="#온수 잘 나오는">
	        		<label for="check29">#온수 잘 나오는</label>
				</li>
				<li>
					<input type="checkbox" name="key" id="check30" value="#겨울">
	        		<label for="check30">#겨울</label>
				</li>
			</ul>
	      
	      
	     
	      
	 		<div class="search-form"> 
				<button type="button" class="btn" onclick="sendOk();">검색</button>
				<button type="button" class="btn" id="reset-button">초기화</button>		 
	 		</div>
	 		
	 		
	 		 <script type="text/javascript">
		      	function sendOk() {
		    	  let form = document.getElementById("keywordForm");
		    	  let selectedKeywords = [];
		    	  let array = "";
		    	  let cnt = $("input[name=key]:checked").length;
		    	  let cv = $("input[name=key]:checked").val();
		    	  
		    	  
		    	  $("input[name=key]:checked").each(function(){
		    		 	let chk = $(this).val();
		    		 	selectedKeywords.push(chk);
		    		 	array += chk;
		    	  });
		    	  
		    	  console.log(array);
		    	  if(cnt === 0){
		    		  alert('키워드를 하나 이상 선택하세요.');
		    		  return false;
		    	  }
		    	  
		    	  if(confirm("선택한 키워드를 검색 하시겠습니까 ?")) {
						const f = document.keywordForm;
						//f.action="${pageContext.request.contextPath}/campInfo/list.do?key=" + encodeURIComponent(selectedKeywordsString);
						f.action= "${pageContext.request.contextPath}/campInfo/list.do?key=" + encodeURIComponent(array);
						f.submit();
					}
		    	 
		    	}
	      
	      </script>
	 		
	 		
	 		
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
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
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

/**/
 
 $('document').ready(function() {
 var area0 = ["시/도 선택","서울특별시","인천광역시","대전광역시","광주광역시","대구광역시","울산광역시","부산광역시","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도"];
  var area1 = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
   var area2 = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
   var area3 = ["대덕구","동구","서구","유성구","중구"];
   var area4 = ["광산구","남구","동구",     "북구","서구"];
   var area5 = ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
   var area6 = ["남구","동구","북구","중구","울주군"];
   var area7 = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
   var area8 = ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"];
   var area9 = ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"];
   var area10 = ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"];
   var area11 = ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"];
   var area12 = ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"];
   var area13 = ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"];
   var area14 = ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"];
   var area15 = ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"];
   var area16 = ["서귀포시","제주시","남제주군","북제주군"];

 

 // 시/도 선택 박스 초기화

 $("select[name^=sido]").each(function() {
  $selsido = $(this);
  $.each(eval(area0), function() {
   $selsido.append("<option value='"+this+"'>"+this+"</option>");
  });
  $selsido.next().append("<option value=''>구/군 선택</option>");
 });

 

 // 시/도 선택시 구/군 설정

 $("select[name^=sido]").change(function() {
  var area = "area"+$("option",$(this)).index($("option:selected",$(this))); // 선택지역의 구군 Array
  var $gugun = $(this).next(); // 선택영역 군구 객체
  $("option",$gugun).remove(); // 구군 초기화

  if(area == "area0")
   $gugun.append("<option value=''>구/군 선택</option>");
  else {
   $.each(eval(area), function() {
    $gugun.append("<option value='"+this+"'>"+this+"</option>");
   });
  }
 });


});

</script>
	
</body>
</html>