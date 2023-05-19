<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class='reply-info'>
	<span class='reply-count'>댓글 ${replyCount}개</span>
	<span>[목록, ${pageNo}/${total_page} 페이지]</span>
</div>

<table class='table reply-list'>
	<c:forEach var="vo" items="${listReply}">	
		<tr class='list-header'>
			<td width='50%'>
				<span class='bold'>${vo.userName}</span>
			</td>
			<td width='50%' align='right'>
				<span>${vo.camChatRepRegDate}</span> |
				
				<c:choose>
					<c:when test="${sessionScope.member.userId=='admin' || sessionScope.member.userId == vo.userId}">
						<span class='deleteReply' data-camChatRepNum='${vo.camChatRepNum}' data-pageNo='${pageNo}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span class="notifyReply">신고</span>
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
		<tr>
			<td colspan='2' valign='top'>${vo.camChatRepContent}</td>
		</tr>

	</c:forEach>
</table>

<div class="page-navigation">
	${paging}
</div>