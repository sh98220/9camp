package com.main;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.campInfo.CampInfoDAO;
import com.campInfo.CampInfoDTO;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		
		
		if(uri.indexOf("main.do") != -1) {
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
		} else if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		}
	}

	protected void loginFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//String path = "/WEB-INF/views/member/login.jsp";
	}
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
		// 검색
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
		if(condition == null) {
			condition = "all";
			keyword = "";
		}
		
		// GET 방식인 경우 디코딩
		if(req.getMethod().equalsIgnoreCase("GET")) {
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
		
		List<CampInfoDTO> list = null;
		if(keyword.length() == 0) {
			list = dao.listCampInfo(offset, size);
		} else {
			list = dao.listCampInfo(offset, size, condition, keyword);
		}
			
		String query = "";
		if(keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		String listUrl = cp + "/campInfo/list.do";
		String articleUrl = cp + "/campInfo/article.do?page=" + current_page;
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
	
		forward(req, resp, "/WEB-INF/views/campInfo/list.jsp");
	}
	

}
