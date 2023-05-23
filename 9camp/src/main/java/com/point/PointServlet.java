package com.point;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

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
		}

	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			session.setAttribute("balance", updatedBalance);
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
			Long balance =  (Long)session.getAttribute("balance");
			Long prebalance =  (Long)session.getAttribute("prebalance");
			session.removeAttribute("amount");
			session.removeAttribute("balance");
			session.removeAttribute("prebalance");
			
			req.setAttribute("amount", amount);
			req.setAttribute("balance", balance);
			req.setAttribute("prebalance", prebalance);
			
		} catch (Exception e) {
			
		}
		
		
		forward(req, resp, "/WEB-INF/views/point/complete.jsp");
		
	}
} 
