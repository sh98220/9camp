<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="footerWrap">
	<!-- 로고 영역 -->
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
	<!-- //로고 영역 -->
	<!-- 로고 오른쪽 ul 영역 -->
	<ul class="f-menu">
		<li><a href="#">개인정보처리방침</a></li>
		<li>&nbsp;|&nbsp;</li>
		<li><a href="#">전자우편무단수집거부</a></li>
		<li>&nbsp;|&nbsp;</li>
		<li><a href="#">캠핑장 등록안내</a></li>
		<li>&nbsp;|&nbsp;</li>
		<li><a href="#">미등록야영장불법영업신고</a></li>
	</ul>
	<!-- //로고 오른쪽 ul 영역 -->
</div>