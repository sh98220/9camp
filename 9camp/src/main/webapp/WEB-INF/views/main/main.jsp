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
    height: 100%;
    object-fit: cover;
   
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
   width: 100%;
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
   object-fit: cover;
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
   background-color: rgba(255, 196, 21, 0.8);
   padding: 15px;
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
                           <option value="camInfoSubject"  ${condition=="camInfoSubject"?"selected='selected'":"" }>캠핑장이름</option>
                           <option value="camThemaName"  ${condition=="camThemaName"?"selected='selected'":"" }>테마</option>
                           <option value="caminfoAddr"    ${condition=="camInfoAddr"?"selected='selected'":"" }>지역</option>
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
      <form name="keywordForm" action="${pageContext.request.contextPath}/campInfo/list.do" method="post">
         <ul class="keyword-ul">
            <li>
               <input type="checkbox" name="key" id="check1" value="반려견동반" >
                 <label for="check1">#반려견동반</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check2" value="바다가 보이는" >
                 <label for="check2">#바다가 보이는</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check3" value="친절한" >
                 <label for="check3">#친절한</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check4" value="생태교육">
                 <label for="check4">#생태교육</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check5" value=">힐링">
                 <label for="check5">#힐링</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check6" value="문화유적">
                 <label for="check6">#문화유적</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check7" value="커플">
                 <label for="check7">#커플</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check8" value="봄">
                 <label for="check8">#봄</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check9" value="그늘이 많은">
                 <label for="check9">#그늘이 많은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check10" value="별 보기 좋은">
                 <label for="check10">#별 보기 좋은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check11" value="익스트림">
                 <label for="check11">#익스트림</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check12" value="재미있는">
                 <label for="check12">#재미있는</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check13" value="여유있는">
                 <label for="check13">#여유있는</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check14" value="물놀이 하기 좋은">
                 <label for="check14">#물놀이 하기 좋은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check15" value="둘레길">
                 <label for="check15">#둘레길</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check16" value="물맑은">
                 <label for="check16">#물맑은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check17" value="가족">
                 <label for="check17">#가족</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check18" value="캠핑카">
                 <label for="check18">#캠핑카</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check19" value="깨끗한">
                 <label for="check19">#깨끗한</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check20" value="가을">
                 <label for="check20">#가을</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check21" value="자전거 타기 좋은">
                 <label for="check21">#자전거 타기 좋은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check22" value="수영장 있는">
                 <label for="check22">#수영장 있는</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check23" value="차대기 편한">
                 <label for="check23">#차대기 편한</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check24" value="여름">
                 <label for="check24">#여름</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check25" value="축제">
                 <label for="check25">#축제</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check26" value="계곡옆">
                 <label for="check26">#계곡옆</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check27" value="아이들 놀기 좋은">
                 <label for="check27">#아이들 놀기 좋은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check28" value="사이트 간격이 넓은">
                 <label for="check28">#사이트 간격이 넓은</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check29" value="온수 잘 나오는">
                 <label for="check29">#온수 잘 나오는</label>
            </li>
            <li>
               <input type="checkbox" name="key" id="check30" value="겨울">
                 <label for="check30">#겨울</label>
            </li>
         </ul>
         
         
        
         
          <div class="search-form"> 
            <button type="button" class="btn" onclick="sendOk();">검색</button>
            <button type="button" class="btn" id="reset-button">초기화</button>       
          </div>
          
      </form>
                
       <script type="text/javascript">
            function sendOk() {
            // 체크된 모든 체크박스 가져오기
            var checkedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
            
            if(checkedCheckboxes.length < 1) return;
            
            if(checkedCheckboxes.length > 5){
               alert("최대 5개");
               return;
            }
/*
           // 선택된 값을 저장할 변수 생성
           var selectedValues = '';
            
           // 체크된 체크박스를 순환하며 값을 문자열에 추가
            for (var i = 0; i < checkedCheckboxes.length; i++) {
               selectedValues += checkedCheckboxes[i].value + ',';
            }
            
           // 마지막 쉼표 제거
           selectedValues = selectedValues.slice(0, -1);

           
            console.log(selectedValues);
*/            
          

            document.keywordForm.submit();
          }
         
         </script>

          
          
          

   </div>
   <!-- //키워드 검색 -->

   
   <!-- 이달의 추천 캠핑장 -->
   <div id="rcm-cont">
      <div class="rcm-cont-div">
         <img src="${pageContext.request.contextPath}/resource/images/main/recommended.png" class="img_rcm">
         <h1>여기 캠핑장 어떠세요?</h1>
         <ul class="rcm-cont-ul">
            <c:forEach var="vo" items="${images}">
               <li style="position: relative; width: 30%; height: 310px; overflow: hidden;">
                  <span class="rcm-img">
                     <a href="${articleUrl}&num=${vo.camInfoNum}">
                        <img src="${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName}">
                     </a>
                  </span>
                  <span class="rcm-txt">
                     ${vo.camInfoLineContent}
                  </span>
               </li>
            </c:forEach>

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


</script>
   
</body>
</html>