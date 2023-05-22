<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
function login(){
	location.href = "${pageContext.request.contextPath}/member/login.do";
}

$(function(){
	countNoReadMsg();
});


//쪽지 개수
function countNoReadMsg() {
	let url = "${pageContext.request.contextPath}/message/countNoReadMsg.do";
	//let query = "count=" + count;
	
	const fn = function(data){
		let count = data.count;
		let selector = ".msg_count";
		$(selector).html(count);
	};
	
	$.ajax({
		type:"POST",		// 메소드(get, post, put, delete)
		url:url,			// 요청 받을 서버주소
		// data:query,			// 서버에 전송할 파라미터
		dataType:"json",	// 서버에서 응답하는 형식(json, xml, text)
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) { 
			jqXHR.setRequestHeader("AJAX", true); // 사용자 정의 헤더
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			//console.log(jqXHR.responseText);
		}
	});
}
</script>

	<!-- 메뉴바 위 -->
	<div class="top-cont">
		<div class="today-cont">
			<div class="today-txt">TODAY 캠핑러</div>
			<div><span>10,000</span>명</div>
		</div>
		
		<div class="logo-cont">
			<a href="${pageContext.request.contextPath}/main.do">
				<span class="logo">
					<i class="fa-solid fa-campground fa-bounce"></i>
				</span>
				<span class="logo-txt">
					가자Goo캠핑
				</span>
			</a>
		</div>
		
		<ul class="icon-cont">
			<c:if test="${not empty sessionScope.member}">
				<li class="point-li">
					<a href="${pageContext.request.contextPath}/point/list.do"><span class="point-tit">보유 포인트</span> <span class="point-val">100,000</span> Point</a>
				</li>
			</c:if>
			<c:if test="${not empty sessionScope.member}">	
				<li class="point-li bar">
					|
				</li>
			</c:if>
			<c:if test="${not empty sessionScope.member}">
				<li>
					<a href="#" class="top-icon ic_bell" title="알림">
						<i class="fa-sharp fa-regular fa-bell"></i>
					</a>
				</li>
			</c:if>
			<c:if test="${not empty sessionScope.member}">
				<li>
					<a href="${pageContext.request.contextPath}/member/logout.do" class="top-icon ic_logout" title="로그아웃">
						<i class="fa-solid fa-arrow-right-from-bracket"></i>
					</a>
				</li>
			</c:if>
			<c:if test="${not empty sessionScope.member}">
				<li>
					<a href="${pageContext.request.contextPath}/message/listRecMsg.do" class="top-icon ic_msg" title="쪽지">
						<i class="fa-regular fa-envelope"></i>
						<span class="msg_count">new</span>
					</a>
				</li>
			</c:if>
			<c:if test="${empty sessionScope.member}">
				<li>
					<a href="${pageContext.request.contextPath}/member/member.do" class="top-icon ic_login" title="로그인">
						<i class="fa-solid fa-arrow-right-from-bracket"></i>
					</a>
				</li>
			</c:if>
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
						<!-- 김성훈 수정 -->
							<a href="${pageContext.request.contextPath}/campInfo/list.do">캠핑장 검색</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/mapsearch/map.html">지도 검색</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">캠핑커뮤니티</a>
					<ul class="sub-menu">
						<li>
							<a href="${pageContext.request.contextPath}/reviews/list.do">전국캠핑자랑</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/mate/list.do">캠핑메이트</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/freeboard/list.do">자유게시판</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">캠핑마켓</a>
					<ul class="sub-menu">
						<li>
							<a href="${pageContext.request.contextPath}/auction/list.do">중고거래</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/rent/list.do">렌탈</a>
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
							<a href="${pageContext.request.contextPath}/member/pwd.do">정보수정</a>
						</li>
					</ul>
				</li>
				<li>
					<a href="#">고객센터</a>
					<ul class="sub-menu">
						<li>
							<a href="${pageContext.request.contextPath}/notice/list.do">공지사항</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/qna/list.do">Q &amp; A</a>
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