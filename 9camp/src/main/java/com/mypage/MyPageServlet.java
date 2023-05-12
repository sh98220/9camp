package com.mypage;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/mypage/*")
public class MyPageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		//SessionInfo info = (SessionInfo) session.getAttribute("member");

		/*if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}*/
		
		// uri에 따른 작업 구분
		if (uri.indexOf("mypage.do") != -1) {
			forward(req, resp, "/WEB-INF/views/mypage/main.jsp");
		} else if (uri.indexOf("profileEdit.do") != -1) {
			profileEdit(req, resp);
		} else if (uri.indexOf("wishList.do") != -1) {
			wishList(req, resp);
		} else if (uri.indexOf("campMateList.do") != -1) {
			campMateList(req, resp);
		} 
	}

	protected void profileEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 프로필 수정
		forward(req, resp, "/WEB-INF/views/mypage/profileEdit.jsp");
	}
	
	protected void wishList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 위시 리스트
		forward(req, resp, "/WEB-INF/views/mypage/wishList.jsp");
	}
	
	protected void campMateList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑 메이트 리스트
		forward(req, resp, "/WEB-INF/views/mypage/campMateList.jsp");
	}
}
