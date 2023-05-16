package com.mypage;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
			profile(req, resp);
		} else if (uri.indexOf("wish.do") != -1) {
			wish(req, resp);
		} else if (uri.indexOf("mate.do") != -1) {
			campMateList(req, resp);
		} else if (uri.indexOf("deleteWish.do") != -1) {
			deleteWish(req, resp);
		}
	}



	protected void profile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
	

	protected void wish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			// 전체데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount(info.getUserId());
			} else {
				dataCount = dao.dataCount(condition, keyword, info.getUserId());
			}
			
			// 전체페이지수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			
			
			List<MyPageDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listWish(offset, size, info.getUserId());
			} else {
				list = dao.listWish(offset, size, condition, keyword, info.getUserId());
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			
			
			// 페이징 처리
			String listUrl = cp + "/mypage/wish.do";
			String articleUrl = cp + "/mypage/wish.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 list.jsp에 넘길 값
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
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
	
	protected void deleteWish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "size=" + size + "&page=" + page;

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		try {
			if (keyword != null && keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			String[] nn = req.getParameterValues("nums");
			long nums[] = null;
			nums = new long[nn.length];
			for (int i = 0; i < nn.length; i++) {
				nums[i] = Long.parseLong(nn[i]);
			}

			MyPageDAO dao = new MyPageDAO();


			// 게시글 삭제
			dao.deleteWish(nums, info.getUserId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/wish.do?" + query);
	}

}
