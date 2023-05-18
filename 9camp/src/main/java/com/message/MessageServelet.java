package com.message;

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

@WebServlet("/message/*")
public class MessageServelet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		// 세션 정보
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
				
		if(info == null) {
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;
		}
				
		if(uri.indexOf("listRecMsg.do") != -1) {
			listRecMsg(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("recArticle.do") != -1) {
			recArticle(req, resp);
		} else if(uri.indexOf("recDelete.do") != -1) {
			recDelete(req, resp);
		} else if(uri.indexOf("listSendMsg.do") != -1) {
			listSendMsg(req, resp);
		} else if(uri.indexOf("sendArticle.do") != -1) {
			sendArticle(req, resp);
		} else if(uri.indexOf("sendDelete.do") != -1) {
			sendDelete(req, resp);
		}
	}
	
	protected void listRecMsg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 받은쪽지함 리스트
		MessageDAO dao = new MessageDAO();
		MyUtil util = new MyUtil();

		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();

		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);

			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "content";
				keyword = "";
			}

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataRecCount(info.getUserId());
			} else {
				dataCount = dao.dataRecCount(info.getUserId(), condition, keyword);
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

			List<MessageDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listRecMsg(info.getUserId(), offset, size);
			} else {
				list = dao.listRecMsg(info.getUserId(), offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/message/listRecMsg.do";
			String articleUrl = cp + "/message/recArticle.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "&" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP로 넘길 속성
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
				
		forward(req, resp, "/WEB-INF/views/message/listRecMsg.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/message/writeMessage.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MessageDAO dao = new MessageDAO();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/message/listSendMsg.do");
			return;
		}
		
		try {
			MessageDTO dto = new MessageDTO();
			
			dto.setMsgContent(req.getParameter("msgContent"));
			dto.setMsgWriterId(info.getUserId());
			dto.setMsgSenderId(req.getParameter("msgSenderId"));
			
			dao.sendMsg(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/message/listSendMsg.do");
	}
	
	protected void recArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MessageDAO dao = new MessageDAO();
		MyUtil util = new MyUtil();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page=" + page;
		
		try {
			long msgNum = Long.parseLong(req.getParameter("msgNum"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "content";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			
			// 쪽지 읽음 표시
			dao.updateRead(msgNum);
			
			// 쪽지 가져오기
			MessageDTO dto = dao.readRecMsg(info.getUserId(), msgNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/message/listRecMsg.do?" + query);
				return;
			}
			dto.setMsgContent(util.htmlSymbols(dto.getMsgContent()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			forward(req, resp, "/WEB-INF/views/message/recArticle.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/message/listRecMsg.do?" + query);
	}
	
	protected void recDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void listSendMsg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 보낸쪽지함 리스트
		MessageDAO dao = new MessageDAO();
		MyUtil util = new MyUtil();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);

			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "content";
				keyword = "";
			}
			
			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataSendCount(info.getUserId());
			} else {
				dataCount = dao.dataSendCount(info.getUserId(), condition, keyword);
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

			List<MessageDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listSendMsg(info.getUserId(), offset, size);
			} else {
				list = dao.listSendMsg(info.getUserId(), offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 페이징 처리
			String listUrl = cp + "/message/listSendMsg.do";
			String articleUrl = cp + "/message/sendArticle.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "&" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP로 넘길 속성
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
		
		forward(req, resp, "/WEB-INF/views/message/listSendMsg.jsp");
	}
	
	protected void sendArticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MessageDAO dao = new MessageDAO();
		MyUtil util = new MyUtil();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page=" + page;
		
		try {
			long msgNum = Long.parseLong(req.getParameter("msgNum"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "content";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			
			// 쪽지 가져오기
			MessageDTO dto = dao.readSendMsg(info.getUserId(), msgNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/message/listSendMsg.do?" + query);
				return;
			}
			dto.setMsgContent(util.htmlSymbols(dto.getMsgContent()));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			
			forward(req, resp, "/WEB-INF/views/message/sendArticle.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/message/listSendMsg.do?" + query);
	}
	
	protected void sendDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
