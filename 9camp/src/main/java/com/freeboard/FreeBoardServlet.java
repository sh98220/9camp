package com.freeboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/freeboard/*")
public class FreeBoardServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("insertFreeBoardLike.do") != -1) {
			insertFreeBoardLike(req, resp);
		
		} else if(uri.indexOf("insertReply.do") != -1) {
			// 댓글 등록
			insertReply(req, resp);
		} else if(uri.indexOf("listReply.do") != -1) {
			// 댓글 리스트
			listReply(req, resp);
		} else if(uri.indexOf("deleteReply.do") != -1) {
			// 댓글 삭제
			deleteReply(req, resp);
		}	
		
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeBoardDAO dao = new FreeBoardDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
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

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<FreeBoardDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listFreeBoard(offset, size);
			} else {
				list = dao.listFreeBoard(offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/freeboard/list.do";
			String articleUrl = cp + "/freeboard/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
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
		forward(req, resp, "/WEB-INF/views/freeboard/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/freeboard/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreeBoardDAO dao = new FreeBoardDAO();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/freeboard/list.do");
			return;
		}
		
		try {
			FreeBoardDTO dto = new FreeBoardDTO();
			
			dto.setUserId(info.getUserId()); 
			dto.setcamChatSubject(req.getParameter("camChatSubject"));
			dto.setcamChatContent(req.getParameter("camChatContent"));
			
			
			
			dao.insertFreeBoard(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/freeboard/list.do?");
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 보기
		FreeBoardDAO dao = new FreeBoardDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

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

			// 조회수 증가
			dao.updateHitCount(num);

			// 게시물 가져오기
			FreeBoardDTO dto = dao.readBoard(num);
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/freeboard/list.do?" + query);
				return;
			}
			dto.setcamChatContent(util.htmlSymbols(dto.getcamChatContent()));
			
			// 로그인 유저의 게시글 공감 여부
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			boolean isUserLike = dao.isUserFreeBoardLike(num, info.getUserId());

			// 이전글 다음글
			FreeBoardDTO preReadDto = dao.preReadFreeBoard( dto.getcamChatNum(), condition, keyword);
			FreeBoardDTO nextReadDto = dao.nextReadFreeBoard( dto.getcamChatNum(), condition, keyword);

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			req.setAttribute("isUserLike", isUserLike);

			// 포워딩
			forward(req, resp, "/WEB-INF/views/freeboard/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freeboard/list.do?" + query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정폼
		FreeBoardDAO dao = new FreeBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");

		try {
			long num = Long.parseLong(req.getParameter("num"));
			FreeBoardDTO dto = dao.readBoard(num);

			if (dto == null) {
				resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/freeboard/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정완료
		FreeBoardDAO dao = new FreeBoardDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/freeboard/list.do");
			return;
		}

		String page = req.getParameter("page");

		try {
			FreeBoardDTO dto = new FreeBoardDTO();
			dto.setcamChatNum(Integer.parseInt(req.getParameter("camChatNum")));
			dto.setcamChatSubject(req.getParameter("camChatSubject"));
			dto.setcamChatContent(req.getParameter("camChatContent"));

			dto.setUserId(info.getUserId());
			
			dao.updateFreeBoard(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
	
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 삭제
		FreeBoardDAO dao = new FreeBoardDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			long num = Long.parseLong(req.getParameter("num"));

			FreeBoardDTO dto = dao.readBoard(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
				return;
			}

			if (!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
				return;
			}
			
			dao.deleteFreeBoard(num);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freeboard/list.do?page=" + page);
	
	}
	
	
	protected void insertFreeBoardLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 공감 저장 : AJAX-JSON
		FreeBoardDAO dao = new FreeBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		int freeboardLikeCount = 0;
		
		try {
			int camChatnum = Integer.parseInt(req.getParameter("camChatNum"));
			String isNoLike = req.getParameter("isNoLike");
			
			if(isNoLike.equals("true")) {
				dao.insertFreeBoardLike(camChatnum, info.getUserId()); // 공감
			} else {
				dao.deleteFreeBoardLike(camChatnum, info.getUserId()); // 공감 취소
			}
			
			// 공감 개수
			freeboardLikeCount = dao.countFreeBoardLike(camChatnum);
			
			state = "true";
		} catch (SQLException e) {
			state = "liked";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		job.put("freeboardLikeCount", freeboardLikeCount);
		
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	
	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 추가
		FreeBoardDAO dao = new FreeBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		try {
			FreeBoardReplyDTO dto = new FreeBoardReplyDTO();
			
			long num = Long.parseLong(req.getParameter("camChatNum"));
			dto.setCamChatNum(num);
			dto.setUserId(info.getUserId());
			dto.setCamChatRepContent(req.getParameter("camChatRepContent"));
		
		dao.insertReply(dto);
		
		state = "true";
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 삭제
		FreeBoardDAO dao = new FreeBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String state = "false";
		
		try {
			int camChatRepNum = Integer.parseInt(req.getParameter("camChatRepNum"));
			dao.deleteReply(camChatRepNum, info.getUserId());
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 리스트
		FreeBoardDAO dao = new FreeBoardDAO();
		MyUtil util = new MyUtil();
		
		try {
			long camChatNum = Long.parseLong(req.getParameter("camChatNum"));
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if(pageNo != null) {
				current_page = Integer.parseInt(pageNo);
			}
			
			int size = 5;
			int total_page = 0;
			int replyCount = 0;
			
			replyCount = dao.dataCountReply(camChatNum);
			total_page = util.pageCount(replyCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<FreeBoardReplyDTO> listReply = dao.listReply(camChatNum, offset, size);
			
			for(FreeBoardReplyDTO dto : listReply) {
				dto.setCamChatRepContent(dto.getCamChatRepContent().replaceAll("\n", "<br>"));
			}
			
			String paging = util.pagingMethod(current_page, total_page, "listPage");
			
			req.setAttribute("listReply", listReply);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("replyCount", replyCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			forward(req, resp, "/WEB-INF/views/freeboard/listReply.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
	
	}
	
}
