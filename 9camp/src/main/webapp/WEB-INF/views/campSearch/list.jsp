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
/*이달의 추천 캠핑장*/
#rcm-cont {
	background: #eee;
	padding: 50px 0;
	position: relative;
}

    .camp_item { /* 캠핑장 검색 - 리스트 박스 */
        display: flex;
        padding-bottom: 20px;
        margin-bottom: 20px;
        border: 1px solid skyblue;
        width: 1200px;
        height: 380px;
        margin-left: 120px;
        
        padding: 25px 20px;
        background-color: #F8FFFF;
    }
    
    .camp_item_box { /* 아이콘 박스 */
    	display: flex;
    	border: 1px solid #eee;
    	width: 800px;
    	height: 125px;
    	
    	margin-top: 30px;
    	background-color: white;
    	
    }

    .camp_item:not(:last-child) {
        border-bottom: 1px solid #ccc;
    }

    .img_box {
        width: 30%;
        margin-right: 20px;
    }

    .img_box img {
        width: 100%;
        height: auto;
        object-fit: cover;
        border-radius: 5px;
    }

    .camp_info {
        width: 70%;
    }

    .camp_info h4 {
        font-size: 20px;
        margin-bottom: 10px;
    }

    .camp_info p {
        font-size: 16px;
    }

    .camp_info a {
        display: block;
        margin-top: 10px;
        color: blue;
        text-decoration: underline;
    }





</style>
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
	
	<div id="cont_inner">
		<div class="sub_layout layout">
			<section id="section1" style="right: 0px">
					
				<h3 class="title2">
					총 <span class="em_org">3474개</span> 캠핑장이 검색되었습니다.
				</h3>
					
			</section>
		</div>
	</div>
	
	<div class="search_list_gr">
	<div class="select_box array_select">
		<label for="selectListOrdrTrget" class="skip">정렬하기</label> <select class="detail_select" id="selectListOrdrTrget" title="정렬하기">
			<option value="last_updusr_pnttm" >업데이트순</option>
			<option value="frst_register_pnttm" >등록일순</option>
			<option value="c_rdcnt" selected="selected">조회순</option>
			<option value="c_recomend_cnt" >추천순</option>
		</select>
	</div>
	<div class="select_map">
		<button type="button">지도로 보기</button>
		</div>
	</div>
	
	
	<!--리스트시작-->
		<div class="camp_search_list">
			<ul>
				<li>	
					<div class="img_box">
					</div>
				</li>
			</ul>	
		</div>
		
				
</header>

<main>
    <div class="camp_search_list">
        <ul>
            <li>
                <div class="camp_item">
                    <div class="img_box">
                        <img src="${pageContext.request.contextPath}/resource/images/camp_sacheon.jpg" alt="캠핑장1 이미지" style="width: 275px;">
                    </div>
                    <div class="camp_info">
						<p class="item_group">
							<span class="item_t01">관광사업자 등록업체</span>
							<span class="item_t02">리뷰수 0</span> <span class="item_t03">조회수 233846</span> <span class="item_t04">추천수 13</span>
						</p>
						<br>
                        <h4>[경상남도 사천시] 사천비토솔섬오토캠핑장</h4>
                        <p>일몰과 바다 전망이 장관인 솔섬오토캠핑장.</p>
                        <h5>솔섬오토캠핑장은 별주부전 테마파크가 있는 사천시 비토섬 인근 해안에 위치하고 있는 캠핑장이다. 바닥은 파쇄석으로 되어...</h5>
                        
                        <div class="camp_cont">
                        
                        <!--아이콘모음-->
						<div class="camp_item_box">
						<br>
								<ul>
									<li><i class="ico_volt"><img src="${pageContext.request.contextPath}/resource/images/lightning-bolt.png"> <span>전기</span></i></li>
									<li><i class="ico_wifi"><img src="${pageContext.request.contextPath}/resource/images/wi-fi.png" style="width: 30px; height: 30px;"> <span>와이파이</span></i></li>
								</ul>
							</div>
						<!--//아이콘모음-->
                        
						</div>
                    </div>
                </div>
            </li>
            
            <li>
                <div class="camp_item">
                    <div class="img_box">
                        <img src="${pageContext.request.contextPath}/resource/images/camp_sacheon.jpg" alt="캠핑장2 이미지" style="width: 275px;">
                    </div>
                    <div class="camp_info">
                    	<p class="item_group">
							<span class="item_t01">관광사업자 등록업체</span>
							<span class="item_t02">리뷰수 0</span> <span class="item_t03">조회수 233846</span> <span class="item_t04">추천수 13</span>
						</p>
						<br>
                        <h4>[경상남도 사천시] 사천비토솔섬오토캠핑장</h4>
                        <p>일몰과 바다 전망이 장관인 솔섬오토캠핑장.</p>
                        <h5>솔섬오토캠핑장은 별주부전 테마파크가 있는 사천시 비토섬 인근 해안에 위치하고 있는 캠핑장이다. 바닥은 파쇄석으로 되어...</h5>
                        <div class="camp_cont">
                        
                        <!--아이콘모음-->
						<div class="camp_item_box">
						<br>
								<ul>
									<li><i class="ico_volt"><img src="${pageContext.request.contextPath}/resource/images/lightning-bolt.png"> <span>전기</span></i></li>
									<li><i class="ico_wifi"><img src="${pageContext.request.contextPath}/resource/images/wi-fi.png" style="width: 30px; height: 30px;"> <span>와이파이</span></i></li>
								</ul>
							</div>
						<!--//아이콘모음-->
                        
						</div>
                    </div>
                </div>
            </li>
            <!-- 나머지 캠핑장도 위와 동일한 방식으로 추가 -->
        </ul>
    </div>

 
</main>

<footer>

</footer>


	
</body>
</html>