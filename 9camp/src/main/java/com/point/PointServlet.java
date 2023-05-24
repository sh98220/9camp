package com.point;

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

@WebServlet("/point/*")
public class PointServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if(info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("complete.do") != -1) {
			complete(req, resp);
		} else if(uri.indexOf("complete_ok.do") != -1) {
			completesubmit(req, resp);
		} else if(uri.indexOf("withdraw.do") != -1) {
			withdraw(req, resp);
		} else if(uri.indexOf("withdraw_ok.do") != -1) {
			withdrawSubmit(req, resp);
		} else if(uri.indexOf("wd.do") != -1) {
			withdrawForm(req, resp);
		}

	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PointDAO dao = new PointDAO();
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
			
			
			
			List<PointDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listPoint(offset, size);
			} else {
				list = dao.listPoint(offset, size, condition, keyword);
			}
			
			
			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/point/list.do";
			String articleUrl = cp + "/point/article.do?page=" + current_page;
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
		forward(req, resp, "/WEB-INF/views/point/list.jsp");
		}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/point/write.jsp");
	}
	
	protected void complete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PointDAO dao = new PointDAO();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/point/list.do");
			return;
		}


		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			 
			
			PointDTO dto = new PointDTO();
			dto.setUserId(info.getUserId());
			dto.setAmount(Long.parseLong(req.getParameter("amount")));

	
			long updatedBalance = dao.updatePoint(dto);
			
			session.setAttribute("amount", dto.getAmount());
			session.setAttribute("userbalance", updatedBalance);
			session.setAttribute("prebalance", updatedBalance - dto.getAmount());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/point/complete_ok.do");
	}
	
	protected void completesubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			Long amount =  (Long)session.getAttribute("amount");
			Long balance =  (Long)session.getAttribute("userbalance");
			Long prebalance =  (Long)session.getAttribute("prebalance");
			session.removeAttribute("amount");
			session.removeAttribute("prebalance");
			
			
			req.setAttribute("amount", amount);
			req.setAttribute("balance", balance);
			req.setAttribute("prebalance", prebalance);
			
		} catch (Exception e) {
			
		}
		
		
		forward(req, resp, "/WEB-INF/views/point/complete.jsp");
		
	}

	protected void withdrawForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/views/point/withdraw.jsp");
	}
	
	protected void withdraw(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PointDAO dao = new PointDAO();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/point/list.do");
			return;
		}


		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			
			 
			
			PointDTO dto = new PointDTO();
			dto.setUserId(info.getUserId());
			dto.setAmount(Long.parseLong(req.getParameter("amount")));

	
			long updatedBalance = dao.withdrawPoint(dto);
			
			session.setAttribute("amount", dto.getAmount());
			session.setAttribute("userbalance", updatedBalance);
			session.setAttribute("prebalance",  updatedBalance + dto.getAmount());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/point/withdraw_ok.do");
	}
	
	protected void withdrawSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			Long amount =  (Long)session.getAttribute("amount");
			Long balance =  (Long)session.getAttribute("userbalance");
			Long prebalance =  (Long)session.getAttribute("prebalance");
			session.removeAttribute("amount");
			session.removeAttribute("prebalance");
			
			req.setAttribute("amount", amount);
			req.setAttribute("balance", balance);
			req.setAttribute("prebalance", prebalance);
			
		} catch (Exception e) {
			
		}
		
		
		forward(req, resp, "/WEB-INF/views/point/withdrawcomplete.jsp");
		
	}
	
	
}
	
	
