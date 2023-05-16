<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!-- 메뉴바 위 -->
	<div class="top-cont">
		<div class="today-cont">
			<div class="today-txt">TODAY 캠핑러</div>
			<div><span>10,000</span>명</div>
		</div>
		
		<div class="logo-cont">
			<a href="${pageContext.request.contextPath}/main.do">
				<span class="logo">
					<i class="fa-solid fa-campground"></i>
				</span>
				<span class="logo-txt">
					가자Goo캠핑
				</span>
			</a>
		</div>
		
		<ul class="icon-cont">
				<li class="point-li">
					<c:if test="${not empty sessionScope.member}">
						<a href="#"><span class="point-tit">보유 포인트</span> <span class="point-val">100,000</span> Point</a>
					</c:if>
				</li>
				<li class="point-li bar">
					<c:if test="${not empty sessionScope.member}">	
						|
					</c:if>
				</li>
			<li>
				<c:if test="${not empty sessionScope.member}">
					<a href="#" class="top-icon ic_bell" title="알림">
						<i class="fa-sharp fa-regular fa-bell"></i>
					</a>
				</c:if>
			</li>
			<li>
				<c:if test="${not empty sessionScope.member}">
					<a href="${pageContext.request.contextPath}/member/logout.do" class="top-icon ic_logout" title="로그아웃">
						<i class="fa-solid fa-arrow-right-from-bracket"></i>
					</a>
				</c:if>
			</li>
			<li>
				<c:if test="${not empty sessionScope.member}">
					<a href="${pageContext.request.contextPath}/message/listRecMsg.do" class="top-icon ic_msg" title="쪽지">
						<i class="fa-regular fa-envelope"></i>
					</a>
				</c:if>
			</li>
			<li>
				<c:if test="${empty sessionScope.member}">
					<a href="${pageContext.request.contextPath}/member/member.do" class="top-icon ic_login" title="로그인">
						<i class="fa-solid fa-arrow-right-from-bracket"></i>
					</a>
				</c:if>
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
						<!-- 김성훈 수정 -->
							<a href="#">캠핑장 검색</a>
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
							<a href="#">공지사항</a>
						</li>
						<li>
							<a href="#">Q &amp; A</a>
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