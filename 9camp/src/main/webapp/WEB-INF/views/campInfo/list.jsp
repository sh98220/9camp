<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>캠핑메이트</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">

.body-main {
	max-width: 700px;
}

/* form-control */
.btn {
	border: 1px solid #999;
	padding: 5px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:active, .btn:focus, .btn:hover {
	background-color: #333;
	color:#fff;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: not-allowed;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
}


.form-control {
	border: 1px solid #999999; border-radius: 4px; background-color: #ffffff;
	padding: 5px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	border: 1px solid #999999; border-radius: 4px; background-color: #ffffff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #666; border-bottom: 1px solid #666; }
.table-border tbody > tr { border-bottom: 1px solid #ff5522; }
.td-border td { border: 1px solid #ced4da; }

tr.hover:hover { cursor: pointer; background: #f5fffa; }



.text-left { text-align: left; }
.text-right { text-align: right; }
.text-center { text-align: center; }

.clear { clear: both; }
.clear:after { content:''; display:block; clear: both; }

.mx-auto { margin-left: auto; margin-right: auto; }


.btnConfirm {
	background-color:#507cd1; border:none;
	width: 100%; padding: 15px 0;
	font-size: 15px; color:#ffffff; font-weight: 700;  cursor: pointer; vertical-align: baseline;
}

.container {
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;

}

/* body-container */
.body-container {
	min-height: 500px;
}

.body-title {
    color: #424951;
    padding-top: 35px;
    padding-bottom: 7px;
    margin: 0 0 25px 0;
    border-bottom: 2px solid #eee;
}

.body-title h2 {
    font-size: 24px;
    min-width: 300px;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
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
	display: block;
	padding-bottom: 15px;
}

.inner-page{
	display: block;
	padding-top: 35px;
}

.table-list thead > tr:first-child { color: #4e4e4e; }
.table-list th, .table-list td { text-align: center; }
.table-list .left { text-align: left; padding-left: 5px; }

.table-list .num { width: 60px; }
.table-list .subject {  }
.table-list .name { width: 100px; }
.table-list .date { width: 100px; }
.table-list .hit { width: 70px; }
.table-list .file { width: 50px; }

@media (min-width: 576px) {
	.container {
	    max-width: 540px;
	}
}
@media (min-width: 768px) {
	.container {
	    max-width: 720px;
	}
}
@media (min-width: 992px) {
	.container {
		max-width: 1010px;
	}
}
@media (min-width: 1200px) {
	.container {
	    max-width: 1100px;
	}
}

#cont_inner {
    width: 100%;
    position: relative;
    height: auto;
    clear: both;
    padding: 15px 0 95px 0;
    margin: 0px 0 0px 0;
    background: #fff;
}

@media (max-width: 1219px){
	.layout {
	    display: block;
	    position: relative;
	    width: 97%;
	    margin: 0 auto;
	}
}

.paginate {
    clear: both;
    text-align: center;
    white-space: nowrap;
    font-size: 20px;
    margin-top: -19px;
}

#section1 {
    position: relative;
    overflow: hidden;
    width: 100%;
}

#cont_inner .title_w {
    width: 100%;
    height: 50px;
    border-bottom: 1px solid #dedede;
    margin-bottom: 30px;
    padding-top: 20px;
}

.title_w.mb_0 {
    margin-bottom: 0 !important;
    border-bottom: 0px !important;
}

#cont_inner .title_w h2.title2 {
    display: inline-block;
    font-size: 22px;
    font-weight: normal;
    color: #000;
    padding: 0;
    float: left;
}

.em_org {
    color: #eb831d !important;
}

#contents {
    clear: both;
    position: relative;
    color: #626262;
    letter-spacing: -0.5px;
}

.search_list_gr {
    position: relative;
    width: 100%;
    height: auto;
    padding: 15px 20px 15px 0;
    box-sizing: border-box;
    min-height: 31px;
    background: #f4f4f4;
    border-top: 1px solid #dbdbdb;
    border-bottom: 2px solid #000000;
    overflow: hidden;
}

div.select_box {
    position: relative;
    width: 160px;
    height: 31px;
    line-height: 31px;
    background: url(/img/2018/layout/arr2.png) no-repeat right 10px center;
    border: 0;
    margin: 10px 0;
}

.array_select {
    margin: 0 10px 0 20px !important;
    float: left !important;
}

.select_map {
    float: right;
}


.camp_search_list {
    position: relative;
    width: 100%;
    height: auto;
    clear: both;
}

.camp_search_list .c_list.update {
    background: #f8fcfe;
}

.camp_search_list .c_list {
    position: relative;
    padding: 25px 20px;
    height: auto;
    overflow: hidden;
    border-bottom: 1px solid #dbdbdb;
 }

.camp_search_list .c_list .img_box {
    width: 285px;
    height: 195px;
    position: relative;
    float: left;
}

.camp_search_list .c_list .img_box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.img_box {
    background:  #f8fcfe
}

.camp_cont {
    width: 700px;
    height: auto;
    float: right;
}


.camp_cont .item_group {
    position: relative;
    margin-bottom: -15px;
    height: auto;
    clear: both;
    overflow: hidden;
    width: 40%;
}

.camp_cont .item_group > span:first-child {
    margin: 0;
}

.camp_cont .item_group .item_t01 {
    background: #547ac0;
}

.camp_cont .item_group .item_t02 {
    background: #d6394c;
}

.camp_cont .item_group .item_t03 {
    background: #444444;
    margin-left: -1px;
}

.camp_cont .item_group > span {
    display: inline-block;
    padding: 0 8px;
    float: left;
    font-size: 12px;
    color: #fff;
    height: 17px;
    line-height: 17px;
    margin: 0 3px;
}
.camp_tt {
    text-align: left;
    clear: both;
    margin-top: 22px;
    display: block;
    padding-bottom: 5px;
}

.camp_txt {
    font-size: 14px;
    line-height: 20px;
    text-align: left;
    display: block;
    margin-top: 5px;
    clear: both;
    margin-bottom: 25px;
}

.camp_key {
    font-size: 14px;
    line-height: 20px;
    text-align: left;
    display: block;
    margin-top: -15px;
    clear: both;
    margin-bottom: 25px;
}

.camp_info01 {
    text-align: left;
    list-style: none;
    overflow: hidden;
    width: 100%;
    height: auto;
    box-sizing: border-box;
    margin-left: -19px;
}

.camp_info01 li {
    float: left;
    font-size: 14px;
    color: #000;
}

.camp_info01 li.addr {
    padding-left: 25px;
    margin-right: 20px;
}

.camp_info01 li.call_num {
    padding-left: 25px;
}

</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery-ui.min.js"></script>
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
	<div class="container body-container">
	    <div id="cont_inner">
	    	<div class="sub_layout layout">
	    		<section id="section1" style="right: 0px">
	    			<header class="title _w mb_0">
	    				<h2 class="title2">
	    					총
	    					<span class="em_org">${dataCount}개</span>
	    					캠핑장이 검색되었습니다.
	    				</h2>		
	    		 	</header>	
	    		</section>
	    	
	    	<div id="contents">
	    		<div class="search_list_gr">
	    			<div class="select_box array_select">
					  <select class="detail_select" id="sortSelect" title="">
					    <option value="updatedAt">업데이트순</option>
					    <option value="views">조회순</option>
					    <option value="likes">추천순</option>    				
					  </select>	    			
					</div>
	    			<div class="select_map">
	    				<button type="button" onclick="location.href='${pageContext.request.contextPath}/mapCamp/mapSearch.do';">지도로보기</button>
	    			</div>	    			
	    		</div>	    	
	    	</div>
	    	
	    	<div class="camp_search_list">
	    		<ul>
	    			<c:forEach var="vo" items="${listimage}">
	    		
		    			<li>
		    				<div class="c_list update">
		    					<div class="img_box">
		    						<a href="${articleUrl}&num=${vo.camInfoNum}">
												<img src="${pageContext.request.contextPath}/uploads/campInfo/${vo.camInfoPhotoName};">
									</a>		   						 
		    					</div>
		    					
		    					<div class="camp_cont">
		    						<p class="item_group">
		    							<span class="item_t01">관광사업자</span>
		    							<span class="item_t02">조회수 ${vo.camInfoHitCount}</span>
		    							<span class="item_t03">찜 ${vo.wishCount}</span>
		    						</p>
			    					<h2 class="camp_tt" style="font-size: 21px;">
			    					<a href="${articleUrl}&num=${vo.camInfoNum}">[ ${vo.camInfoAddr} ] ${vo.camInfoSubject}</a>			
			    					</h2>
			    					
			    					<span class="camp_stt" style="font-size: 18px;">${vo.camInfoLineContent}</span>
			    					
			    					<span class="camp_txt" style="font-size: 13px;">
			    						<a href="${articleUrl}&num=${vo.camInfoNum}">${vo.camInfoContent}</a>			
			    					 </span>
			    					 
			    					 <span class="camp_key" style="font-size: 16px;">
			    						${vo.camKeyWord}			
			    					 </span>
			    					
		    						<ul class="camp_info01">
		    							<li class="addr"><i class="fa-solid fa-location-dot" style="font-size: 18px; color: orange; margin-right: 3px;"></i>${vo.camInfoAddr1}</li>
		    							<li class="call_num"><i class="fa-solid fa-phone" style="font-size: 16px; color: orange; margin-right: 3px;"></i>${vo.camPhoneNum}</li>
		    					
		    					
		    						</ul>
		    					
		    					
		    					</div>
		    				
		    				</div>
		    			
		    			
		    			
		    			</li>
	    			</c:forEach>
	    		
	    		
	    		
	    		
	    		</ul>
	    	</div>
	    	
	    	
	    	</div>
	    
	    
	    
	    </div>
			
			<div class="page-navigation">
				${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
			</div>
			
			<table class="table">			
				<tr>	
					<td width="100">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/campInfo/list.do';" title="새로고침"><i class="fa-solid fa-arrow-rotate-right"></i></button>
					</td>
					
					
					<td align="right" width="100">
						<c:choose>
				    		<c:when test="${sessionScope.member.userId=='admin'}">
				 			   <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/campInfo/write.do';">글올리기</button>	
				 			</c:when>
				   		</c:choose>
					</td>
				</tr>
			</table>
	    
	    

	    </div>
	
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

</body>
</html>