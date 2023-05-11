<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>

<style type="text/css">
input[type="checkbox"]{
  display: none;
}

label {
  border-radius: 10px;
  color: black;
  backgroundcolor: "#ff5522";
}
      
input[type="checkbox"] + label{
  margin-top: 10px;
  display: inline-block;
  padding: 10px;
  border: 3px solid #707070;
  position: relative;
  width: auto;
}

input[type="checkbox"]:checked + label {
  background-color: #ff5522;
}

.search-form {
  margin-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.search-form .btn{
  border-radius: 5px;
}

</style>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<body>

<header>

</header>

<main>
 <input type="checkbox" name="key" id="check1" value="101" >
        <label for="check1">#반려견동반</label>
 <input type="checkbox" name="key" id="check2" value="102" >
        <label for="check2">#바다가 보이는</label>
 <input type="checkbox" name="key" id="check3" value="103" >
        <label for="check3">#친절한</label>
 <input type="checkbox" name="key" id="check4" value="104">
        <label for="check4">#생태교육</label>
 <input type="checkbox" name="key" id="check5" value="105">
        <label for="check5">#힐링</label>
 <input type="checkbox" name="key" id="check6" value="106">
        <label for="check6">#문화유적</label>
 <input type="checkbox" name="key" id="check7" value="107">
        <label for="check7">#커플</label>
 <input type="checkbox" name="key" id="check8" value="108">
        <label for="check8">#봄</label>
 <input type="checkbox" name="key" id="check9" value="109">
        <label for="check9">#그늘이 많은</label>
 <input type="checkbox" name="key" id="check10" value="110">
        <label for="check10">#별 보기 좋은</label>
 <input type="checkbox" name="key" id="check11" value="111">
        <label for="check11">#익스트림</label>
 <input type="checkbox" name="key" id="check12" value="112">
        <label for="check12">#재미있는</label>
 <input type="checkbox" name="key" id="check13" value="113">
        <label for="check13">#여유있는</label>
 <input type="checkbox" name="key" id="check14" value="114">
        <label for="check14">#물놀이 하기 좋은</label>
 <input type="checkbox" name="key" id="check15" value="115">
        <label for="check15">#둘레길</label>
 <input type="checkbox" name="key" id="check16" value="116">
        <label for="check16">#물맑은</label>
 <input type="checkbox" name="key" id="check17" value="117">
        <label for="check17">#가족</label>
 <input type="checkbox" name="key" id="check18" value="118">
        <label for="check18">#캠핑카</label>
 <input type="checkbox" name="key" id="check19" value="119">
        <label for="check19">#깨끗한</label>
 <input type="checkbox" name="key" id="check20" value="120">
        <label for="check20">#가을</label>
 <input type="checkbox" name="key" id="check21" value="121">
        <label for="check21">#자전거 타기 좋은</label>
 <input type="checkbox" name="key" id="check22" value="122">
        <label for="check22">#수영장 있는</label>
 <input type="checkbox" name="key" id="check23" value="123">
        <label for="check23">#차대기 편한</label>
 <input type="checkbox" name="key" id="check24" value="124">
        <label for="check24">#여름</label>
 <input type="checkbox" name="key" id="check25" value="125">
        <label for="check25">#축제</label>
 <input type="checkbox" name="key" id="check26" value="126">
        <label for="check26">#계곡옆</label>
 <input type="checkbox" name="key" id="check27" value="127">
        <label for="check27">#아이들 놀기 좋은</label>
 <input type="checkbox" name="key" id="check28" value="128">
        <label for="check28">#사이트 간격이 넓은</label>
 <input type="checkbox" name="key" id="check29" value="129">
        <label for="check29">#온수 잘 나오는</label>
 <input type="checkbox" name="key" id="check30" value="130">
        <label for="check30">#겨울</label>
      
     <div class="search-form"> 
	     <form> 
			 <button type="button" class="btn" onclick="sendOk();">검색</button>
			 <button type="button" class="btn" id="reset-button">초기화</button>		 
	     </form>
	 </div>
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

</script>

<script type="text/javascript">
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