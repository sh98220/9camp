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
		} else if (uri.indexOf("wish.do") != -1) {
			wish(req, resp);
		} else if (uri.indexOf("mateList.do") != -1) {
			mateList(req, resp); //내가 관리 중인 캠핑 메이트 리스트
		} else if (uri.indexOf("mateApplyAdmin.do") != -1) {
			mateApplyAdmin(req, resp);
		} else if (uri.indexOf("mateWait.do") != -1) {
			mateWait(req, resp);
		} else if (uri.indexOf("deleteWish.do") != -1) {
			deleteWish(req, resp);
		} else if (uri.indexOf("deleteMate.do") != -1) {
			deleteMate(req, resp);
		} else if (uri.indexOf("deleteMateApply.do") != -1) {
			deleteMateApply(req, resp);
		} else if (uri.indexOf("deleteMateWait.do") != -1) {
			deleteMateWait(req, resp);
		} else if (uri.indexOf("confirmMateApply.do") != -1) {
			confirmMateApply(req, resp);
		} else if (uri.indexOf("adminList.do") != -1) {
			adminList(req, resp);
		}
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
				dataCount = dao.dataCountWish(info.getUserId());
			} else {
				dataCount = dao.dataCountWish(condition, keyword, info.getUserId());
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
	
	protected void mateList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 캠핑 메이트 관리 리스트
		
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
						dataCount = dao.dataCountMate(info.getUserId());
					} else {
						dataCount = dao.dataCountMate(condition, keyword, info.getUserId());
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
						list = dao.listMate(offset, size, info.getUserId());
					} else {
						list = dao.listMate(offset, size, condition, keyword, info.getUserId());
					}


					String query = "";
					if (keyword.length() != 0) {
						query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
					}
					

					
					// 페이징 처리
					String listUrl = cp + "/mypage/mateList.do";
					String AdminUrl = cp + "/mypage/mateApplyAdmin.do?page=" + current_page;
					String waitUrl = cp + "/mypage/mateWait.do?page=" + current_page;
					if (query.length() != 0) {
						listUrl += "?" + query;
						AdminUrl += "&" + query;
						waitUrl += "&" + query;
					}
					

					String paging = util.paging(current_page, total_page, listUrl);


					// 포워딩할 list.jsp에 넘길 값
					req.setAttribute("list", list);
					req.setAttribute("page", current_page);
					req.setAttribute("total_page", total_page);
					req.setAttribute("dataCount", dataCount);
					req.setAttribute("size", size);
					req.setAttribute("AdminUrl", AdminUrl);
					req.setAttribute("waitUrl", waitUrl);
					req.setAttribute("paging", paging);
					req.setAttribute("condition", condition);
					req.setAttribute("keyword", keyword);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/mypage/mateList.jsp");
	}
	
	private void mateApplyAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page=" + page;
		MyPageDAO dao = new MyPageDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		try {
			long num = Long.parseLong(req.getParameter("num"));

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			//메이트 멤버 가져오기
			MyPageDTO dto = dao.readMateApply(num, info.getUserId());
			if (dto == null) {
				resp.sendRedirect(cp + "/mypage/mateList.do?" + query);
	
				return;
			}

			

			dto.setCamMateAppContent(dto.getCamMateAppContent().replaceAll("\n", "<br>"));

			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("num", num);

			forward(req, resp, "/WEB-INF/views/mypage/mateApplyAdmin.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateList.do?" + query);
	}
		
	
	
	protected void mateWait(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page=" + page;
		MyPageDAO dao = new MyPageDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		try {
			long num = Long.parseLong(req.getParameter("num"));

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			//메이트 멤버 가져오기
			MyPageDTO dto = dao.readMateWait(num, info.getUserId());
			if (dto == null) {
				resp.sendRedirect(cp + "/mypage/mateList.do?" + query);
	
				return;
			}

			

			dto.setCamMateAppContent(dto.getCamMateAppContent().replaceAll("\n", "<br>"));

			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("num", num);

			forward(req, resp, "/WEB-INF/views/mypage/mateWait.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateList.do?" + query);
	}
	

	protected void deleteWish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page=" + page;

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

	
	protected void deleteMate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page=" + page;

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


			// 메이트 삭제
			dao.deleteMate(nums, info.getUserId());

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateList.do?" + query);
	}
	
	protected void deleteMateApply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String num = req.getParameter("num");
		String query = "page=" + page + "&num=" + num;

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		try {
			if (keyword != null && keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			String[] nn = req.getParameterValues("nums");

			MyPageDAO dao = new MyPageDAO();


			// 메이트 삭제
			dao.deleteMateApply(nn, num);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateApplyAdmin.do?" + query);
	}

	protected void deleteMateWait(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String num = req.getParameter("num");
		String query = "page=" + page + "&num=" + num;

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		try {
			if (keyword != null && keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			String[] nn = req.getParameterValues("nums");

			MyPageDAO dao = new MyPageDAO();


			// 신청서 삭제
			dao.deleteMateApply(nn, num);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateWait.do?" + query);
		
	}
	


	private void confirmMateApply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
		
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String num = req.getParameter("num");
		String query = "page=" + page + "&num=" + num;

		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");

		try {
			if (keyword != null && keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			String[] nn = req.getParameterValues("nums");

			MyPageDAO dao = new MyPageDAO();


			// 신청서 수락
			dao.confirmMateApply(nn, num);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mypage/mateWait.do?" + query);
		
	}
	
	private void adminList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		MyPageDAO dao = new MyPageDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			// admin 아니면 조회 불가
			if (!info.getUserId().equals("admin")) {
				resp.sendRedirect(cp + "/mypage/main.do");
				return;
			}
			
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
				dataCount = dao.dataCountMember();
			} else {
				dataCount = dao.dataCountMember(condition, keyword);
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
				//list = dao.listMember(offset, size, column, order);
			} else {
				list = dao.listMember(offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			
			
			// 페이징 처리
			String listUrl = cp + "/mypage/adminList.do";
			String articleUrl = cp + "/mypage/adminList.do?page=" + current_page;
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
		forward(req, resp, "/WEB-INF/views/mypage/adminList.jsp");
	}
	
}
