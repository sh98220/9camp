<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>가자구캠핑</title>

</head>

<style type="text/css">
* {margin: 0; padding: 0; box-sizing: border-box;}

.section_01 {
	width: 100%;
	height: 300px;
	background-color: rgb(255, 165, 0);
}

</style>
<body>

<main>
	<h3>
		<span class="skip">캠핑장 정보검색</span>
	</h3>
	
	<div class="section_01">
		<div class="layout" style="margin: 0 auto; width: 600px; margin-top: 15px;" >
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
							style="width: 60%; height: 40px; border-radius: 5px; padding-left: 10px;">
						</div>
						
						<br>
						
						<div class="form1_2">
							<p class="tt"  style="color: white; margin-bottom: 5px;">지역별</p>
							<label class="skip" for="c_do"></label> 
							
							<select name="c_do" id="c_do" class="select_01" title="검색할 지역을 선택하세요." 
												style="width: 30%; height: 40px; border-radius: 5px; padding-left: 10px;">
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
														style="width: 30%; height: 40px; border-radius: 5px; padding-left: 10px;">
								<option value="">전체/시/군</option>
								</select>
						</div>
						
						<br>
						
						<div class="form1_2">
							<p class="tt"  style="color: white; margin-bottom: 5px;">테마별</p>
							<label class="skip" for="searchLctCl"></label> 
							
							<select name="searchLctCl" id="searchLctCl" class="select_03" title="검색할 테마를 선택하세요."
														style="width: 30%; height: 40px; border-radius: 5px; padding-left: 10px;">
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
								background-color: black; color: white; border-radius: 5px;"> 검색
							</button>
							
						</div>
					</div>
					<!--//검색박스-->
						
				</div>
			</section>
		</div>
	</div>
</main>
</body>
</html>