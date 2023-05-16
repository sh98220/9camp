package com.mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/mypage/*")
public class MyPageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		// uri에 따른 작업 구분
		if (uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/mypage/main.jsp");
		//} else if (uri.indexOf("pwdCheck.do") != -1) {
			//profileEditForm(req, resp);
		} else if (uri.indexOf("profile.do") != -1) {
			editProfile(req, resp);
		} else if (uri.indexOf("wish.do") != -1) {
			wishList(req, resp);
		} else if (uri.indexOf("mate.do") != -1) {
			campMateList(req, resp);
		} 
	}

	
	protected void editProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 프로필 수정
		MyPageDAO dao = new MyPageDAO();
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/member.do");
				return;
			}

			// DB에서 해당 회원 정보 가져오기
			MyPageDTO dto = dao.readMember(info.getUserId());
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			forward(req, resp, "/WEB-INF/views/mypage/profile.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}
	
	protected void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료
		MyPageDAO dao = new MyPageDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/member.do");
				return;
			}

			MyPageDTO dto = new MyPageDTO();

			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));

			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");
			dto.setUserEmail(email1 + "@" + email2);

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);

			//dao.updateMember(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}
	
	
	
	protected void wishList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 찜 리스트
		MyPageDAO dao = new MyPageDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");


		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			// 전체데이터 개수
			int dataCount = dao.dataCount(info.getUserId());

			// 전체페이지수
			int size = 12;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<MyPageDTO> list = dao.listMyPage(offset, size, info.getUserId());

			// 페이징 처리
			String listUrl = cp + "/mypage/wish.do";
			String articleUrl = cp + "/mypage/wish.do?page=" + current_page;
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 list.jsp에 넘길 값
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/mypage/wish.jsp");
	}
	
	protected void campMateList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑 메이트 리스트
		forward(req, resp, "/WEB-INF/views/mypage/mate.jsp");
	}


}
