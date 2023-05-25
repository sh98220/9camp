<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지도 검색</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.container {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
    padding-bottom: 35px;
}

.clear::after {
	clear: both;
	content: "";
	display: block;
}

/* body-container */
.body-container {
	min-height: 500px;
}

.body-title {
    color: #424951;
    padding-top: 35px;
    padding-bottom: 7px;
    margin: 0 0 40px 0;
    border-bottom: 2px solid #eee;
}

.body-title h2 {
    font-size: 24px;
    min-width: 300px;
    color: #ff5522;
    font-weight: 700;
    padding-bottom: 10px;
    display: inline-block;
    margin: 0 0 -7px 0;
    border-bottom: 3px solid #ff5522;
}

.body-title h2 i {
	
}

.body-main {
	max-width: 1200px;
	padding-bottom: 35px;
	margin: 0 auto;
}

/*조회 개수*/
.sc_result_txt {
	margin-bottom: 20px;
}

.sc_result_txt h2 {
	font-weight: normal;
	font-size: 22px;
}

.sc_result_txt h2 > span.camp_count {
	color: #eb831d;
}

/*회색*/
.search_list_gr {
    width: 100%;
    height: auto;
    padding: 15px 20px 15px 0;
    box-sizing: border-box;
    min-height: 31px;
    background: #f4f4f4;
    border-top: 1px solid #dbdbdb;
    border-bottom: 2px solid #000000;
    overflow: hidden;
    margin-bottom: 30px;
}

.search_list_gr .select_box {
    width: 160px;
    height: 31px;
    line-height: 31px;
    border: 0;
    margin: 0 10px 0 20px;
    float: left;
}

.select_box select.detail_select {
    width: 100%;
    height: 31px;
    min-height: 31px;
    line-height: 31px;
    padding: 0 10px;
    border: 1px solid #c7c7c7;
    font-size: 14px;
    appearance: none;
    color: #626262;
    cursor: pointer;
}

.search_list_gr .select_map {
    float: right;
}

.select_map button {
    padding: 0 10px;
    height: 31px;
    background: #4a4e57;
    text-align: center;
    color: #fff;
    cursor: pointer;
    border: none;
}

/*map*/
.map_cont {
	width: 100%;
	overflow: hidden;
}

.map_cont img {
	max-width: 100%;
}

.map_wrap {position:relative;overflow:hidden;width:75%;height:600px; float: left;}
.radius_border{border:1px solid #919191;border-radius:5px;}     
.custom_zoomcontrol {position:absolute;top:10px;right:10px;width:36px;height:80px;overflow:hidden;z-index:1;background-color:#f5f5f5;} 
.custom_zoomcontrol span {display:block;width:36px;height:40px;text-align:center;cursor:pointer;}     
.custom_zoomcontrol span img {width:15px; height:15px; border:none; display: inline-block; margin-top: 12px;}             
.custom_zoomcontrol span:first-child{border-bottom:1px solid #bfbfbf;}

/*지도 리스트*/
.map_list {
    width: 25%;
    height: 600px;
    background: #fafbfe;
    float: left;
}

.map_list > ul {
    height: 560px;
    overflow-y: scroll;
    width: 100%;
}

.map_list > ul > li {
    padding: 15px 5%;
    border-bottom: 1px solid #d9deeb;
    cursor: pointer;
}

.map_list li > div {
    width: 100%;
    margin: 0 auto;
}

.map_list li > div > h2 {
    font-size: 18px;
    color: #000;
    width: 100%;
    padding-bottom: 5px;
}

.map_list li > div > p {
	font-size: 14px;
}

.map_list li > div > p > i {
	color: #888;
}

/*페이징*/
.map_paging {
    display: block;
    width: 100%;
    height: 40px;
    overflow: hidden;
    text-align: center;
    clear: both;
    border: 1px solid #ddd;
    background-color: #f8f8f8;
    clear: both;
    color: #626262;
}

.map_paging ul {
	overflow: hidden;
}

.map_paging ul li {
    width: 33%;
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    float: left;
}

.map_paging ul li.prev {
    background-color: #fff;
    border-right: 1px solid #ddd;
}

.map_paging ul li.next {
    background-color: #fff;
    border-left: 1px solid #ddd;
}

.map_paging ul li a {
	color: #626262;
}

.map_paging ul li .cp_span {
	color: #455db4;
	font-weight: bold;
}

.map_paging ul li.noclick {
	color: rgba(0,0,0,.25);
}

/*지도 클릭 오버레이*/
.wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left; overflow: hidden; font-size: 16px; line-height: 1.5;}
.wrap * {padding: 0;margin: 0;}
.wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
.wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
.info .title {padding: 7px 0 7px 10px; min-height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
.info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
.info .close:hover {cursor: pointer;}
.info .body {overflow: hidden; font-size: 14px; color: #333;}
.info .desc {margin: 13px 10px 0 13px;}
.desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
.desc .tel {}
.info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
.info .link {color: #5085BB;}

.desc i {color: #888;}

.wrap a:hover { text-decoration: underline; }


</style>

</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>

<main>
	<div class="container body-container">
		<div class="body-main">
			<div class="body-title">
				<h2><i class="fa-solid fa-map-location-dot"></i> 지도 검색 </h2>
		    </div>
		    
		    <div class="sc_result_txt">
		    	<h2>총 <span class="camp_count">${dataCount}</span>개 캠핑장이 검색되었습니다.</h2>
		    </div>
		    
		    <div class="search_list_gr">
		    	<div class="select_box">
		    		<select class="detail_select">
		    			<option>등록일순</option>
		    			<option>조회순</option>
		    		</select>
		    	</div>
		    	<div class="select_map">
		    		<button type="button" onclick="location.href='${pageContext.request.contextPath}/campInfo/list.do'">리스트로 보기</button>
		    	</div>
		    </div>
		    
		    <div class="map_cont clear">
		    	<!-- 지도 영역 -->
		    	<div class="map_wrap">
				    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div> 
				    <!-- 지도 확대, 축소 컨트롤 div 입니다 -->
				    <div class="custom_zoomcontrol radius_border"> 
				        <span onclick="zoomIn()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대"></span>  
				        <span onclick="zoomOut()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소"></span>
				    </div>
				</div>
				<!-- // 지도 영역 -->
				<!-- 캠핑장 리스트 -->
				<div class="map_list">
					<ul>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<li id="li${status.index}" class="camp_list" data-index=${status.index} data-num="${dto.camInfoNum}" data-name="${dto.camInfoSubject}" data-addr="${dto.camInfoAddr}" data-tel="${dto.camPhoneNum}">
								<div>
									<h2>${dto.camInfoSubject}</h2>
									<p><i class="fa-solid fa-location-dot"></i> ${dto.camInfoAddr}</p>
									<p><i class="fa-solid fa-phone"></i> ${dto.camPhoneNum}</p>
								</div>
							</li>
						</c:forEach>
					</ul>
					<!-- 페이징 -->
					<div class="map_paging">
						<!--
						<ul>
							<li class="prev">
								<a href="${pageContext.request.contextPath}/mapCamp/mapSearch.do?page=${page+1}">PREV</a>
							</li>
							<li>
								<span class="cp_span">page</span>/total_page
							</li>
							<li class="next">
								<a href="#">NEXT</a>
							</li>
						</ul>
						-->
						${dataCount == 0 ? 0 : paging}
					</div>
					<!-- //페이징 -->
				</div>
				<!-- // 캠핑장 리스트 -->
		    </div>
		    
		    
		</div>
	</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4a45ff266f6b4910cc81e1ac775e16d1&libraries=services,clusterer,drawing"></script>

<script type="text/javascript">
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(34.987387, 127.9661896), // 지도의 중심좌표
        level: 13 // 지도의 확대 레벨
    };  

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

//지도타입 컨트롤의 지도 또는 스카이뷰 버튼을 클릭하면 호출되어 지도타입을 바꾸는 함수입니다
function setMapType(maptype) { 
    var roadmapControl = document.getElementById('btnRoadmap');
    var skyviewControl = document.getElementById('btnSkyview'); 
    if (maptype === 'roadmap') {
        map.setMapTypeId(kakao.maps.MapTypeId.ROADMAP);    
        roadmapControl.className = 'selected_btn';
        skyviewControl.className = 'btn';
    } else {
        map.setMapTypeId(kakao.maps.MapTypeId.HYBRID);    
        skyviewControl.className = 'selected_btn';
        roadmapControl.className = 'btn';
    }
}

// 지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomIn() {
    map.setLevel(map.getLevel() - 1);
}

// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomOut() {
    map.setLevel(map.getLevel() + 1);
}

var listData = '${jList}';
var campList = JSON.parse(listData);

var campNumList = new Array();
var campNameList = new Array();
var campAddrList = new Array();
var campTelList = new Array();

for(let k in campList){    
    let $obj = campList[k];
    let campNum  =  $obj.campNum;
    let campName =  $obj.campName;
    let campAddr =  $obj.campAddr;
    let campTel =  $obj.campTel;
    campNumList.push(campNum);
    campNameList.push(campName);
    campAddrList.push(campAddr);
    campTelList.push(campTel);
}

function mapReady() {
	//주소-좌표 변환 객체를 생성합니다
	let geocoder = new kakao.maps.services.Geocoder();

	// for loop
	campAddrList.forEach(function(addr, index){
	    geocoder.addressSearch(addr, function(result, status) {
	        if (status === kakao.maps.services.Status.OK) {
	            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	            
	            let marker = new kakao.maps.Marker({
	                position: coords,
	                title: campNameList[index]
	            });
	            
	            var markers = [];
	            markers.push(marker);
	            
	            for (var i = 0; i < markers.length; i++) {
	                markers[i].setMap(map);
	            }
	            
	         	let content = '<div class="wrap wrap'+campNumList[index]+'">' + 
	    	                 '    <div class="info">' + 
	    	                 '        <div class="title">' +
	    	                 			  '<a href="${pageContext.request.contextPath}/campInfo/article.do?num='+campNumList[index]+'">' +
	    	                 			  campNameList[index] + 
	    	                 			  '</a>' +
	    	                 '            <div class="close" title="닫기"></div>' + 
	    	                 '        </div>' + 
	    	                 '        <div class="body">' + 
	    	                 '            <div class="desc">' + 
	    	                 '                <div class="ellipsis"><i class="fa-solid fa-location-dot"></i>&nbsp;' + campAddrList[index] + '</div>' + 
	    	                 '                <div class="tel ellipsis"><i class="fa-solid fa-phone"></i>&nbsp;' + campTelList[index] + '</div>' +  
	    	                 '            </div>' + 
	    	                 '        </div>' + 
	    	                 '    </div>' +    
	    	                 '</div>';
	    	                 
	         	// 마커 위에 커스텀오버레이를 표시합니다
	         	// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
	         	let overlay = new kakao.maps.CustomOverlay({
	             	content: content,
	             	map: map,
	             	position: marker.getPosition()       
	         	});
	         	
	         	overlay.setMap(null);

	         	// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
	         	kakao.maps.event.addListener(marker, 'click', function() {
	             	overlay.setMap(map);
	         	});
	         	
	         	$("body").on("click", ".close", function(){
	         		overlay.setMap(null);
	         	});
	         	
	         	$("body").on("click", ".camp_list", function(){
	         		let num = $(this).attr("data-index");
	        		let campNum = $(this).attr("data-num");
	        		let campName = $(this).attr("data-name");
	        		let campAddr = $(this).attr("data-addr");
	        		let campTel = $(this).attr("data-tel");
	        		
					// mapListClick(campNum, campName, campAddr, campTel);
	        		
	        	});
	         	
	        } 
	    });
	});
}

$(function(){
	mapReady();
});
</script>

<script type="text/javascript">
/*
$(function(){
	$("body").on("click", ".camp_list", function(){
		let campNum = $(this).attr("data-num");
		let campName = $(this).attr("data-name");
		let campAddr = $(this).attr("data-addr");
		let campTel = $(this).attr("data-tel");
		
		mapListClick(campNum, campName, campAddr, campTel);
	});
	
});
*/

function mapMarker(campNum, campName, campAddr, campTel) {
	let geocoder = new kakao.maps.services.Geocoder();
	
	geocoder.addressSearch(campAddr, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            
            map.setCenter(coords);
            
        } 
    });
}

function mapListClick(campNum, campName, campAddr, campTel) {
	let url = "${pageContext.request.contextPath}/mapCamp/mapSearch.do";
	let query = "campNum=" + campNum + "&campName=" + campName + "&campAddr=" + campAddr + "&campTel=" + campTel;
	
	const fn = function(data){
		let campNum = data.campNum;
		let campName = data.campName;
		let campAddr = data.campAddr;
		let campTel = data.campTel;
		mapMarker(campNum, campName, campAddr, campTel);
	};
	
	$.ajax({
		type:"POST",		
		url:url,			
		data:query,			
		dataType:"json",	
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) { 
			jqXHR.setRequestHeader("AJAX", true); 
		},
		error:function(jqXHR) {
			if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			//console.log(jqXHR.responseText);
		}
	});
}
</script>

</body>
</html>