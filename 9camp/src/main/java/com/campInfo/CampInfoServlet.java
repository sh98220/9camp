package com.campInfo;

import java.io.File;
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
@WebServlet("/campInfo/*")
public class CampInfoServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;
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
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "campInfo";
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		}  else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("insertCampWish.do") != -1) {
			insertCampWish(req, resp);
		}
		

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

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		
		List<CampInfoDTO> list = null;
		List<CampInfoDTO> list1 = null;
		
		list = dao.listAllKeyword();
		list1 = dao.listAllThemaName();
		
		
		req.setAttribute("list", list);	
		req.setAttribute("list1", list1);	
		
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/campInfo/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
	
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/campInfo/list.do");
			return;
		}
		
		try {
			CampInfoDTO dto = new CampInfoDTO();
			
			dto.setCamInfoSubject(req.getParameter("camInfoSubject"));
			dto.setCamInfoContent(req.getParameter("camInfoContent"));
			dto.setCamInfoAddr(req.getParameter("camInfoAddr"));
			dto.setCamKeyWord(req.getParameter("camKeyWord"));
			dto.setCamThemaName(req.getParameter("camThemaName"));
			
			dao.InsertCampInfo(dto);
			
			
			
			// 사진파일 업로드
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campInfo/list.do?");
	}
	
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		CampInfoDAO dao = new CampInfoDAO();
		
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

			// 조회수 증가시키기
			dao.updateHitCount(num);
			
			// 게시물 가져오기
			CampInfoDTO dto = dao.readCampInfo(num);
		
			if(dto == null) {
				resp.sendRedirect(cp + "/campInfo/list.do?" + query);
				return;
			}		
			
			dto.setCamInfoContent(util.htmlSymbols(dto.getCamInfoContent()));
			
			// 로그인 유저의 게시글 공감 여부
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			boolean isUserWish = dao.isUserCampWish(num, info.getUserId());
			
			
		   
			
			
			// 로그인 유저의 찜 여부
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("isUserWish", isUserWish);
			
			// 포워딩
			forward(req, resp, "/WEB-INF/views/campInfo/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campInfo/list.do?" + query);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
	
		try {
			long num = Long.parseLong(req.getParameter("num"));
			CampInfoDTO dto = dao.readCampInfo(num);
			
			if (dto == null) {
				resp.sendRedirect(cp + "/campInfo/list.do?page=" + page);
				return;
			}
			
			// 게심루은 관리자만 삭제 가능
			List<CampInfoDTO> list = null;
			List<CampInfoDTO> list1 = null;
			
			list = dao.listAllKeyword();
			list1 = dao.listAllThemaName();

			req.setAttribute("list1", list1);
			
			req.setAttribute("list", list);	
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/campInfo/write.jsp");
			return;
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campInfo/list.do?page=" + page);		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/campInfo/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			CampInfoDTO dto = new CampInfoDTO();
			dto.setCamInfoNum(Integer.parseInt(req.getParameter("camInfoNum")));
			dto.setCamInfoSubject(req.getParameter("camInfoSubject"));
			dto.setCamInfoContent(req.getParameter("camInfoContent"));
			dto.setCamInfoAddr(req.getParameter("camInfoAddr"));
			dto.setCamKeyWord(req.getParameter("camKeyWord"));
			dto.setCamThemaName(req.getParameter("camThemaName"));
			
			dao.updateCampInfo(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campInfo/list.do?page=" + page);
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			long num = Long.parseLong(req.getParameter("num"));
			
			CampInfoDTO dto = dao.readCampInfo(num);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/campInfo/list.do?page=" + page);
				return;
			}
			
			dao.deleteCampInfo(num);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/campInfo/list.do?page=" + page);
	}
	
	protected void insertCampWish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 공감 저장 : AJAX-JSON
		CampInfoDAO dao = new CampInfoDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		int wishCount = 0;
		
		try {
			int camInfoNum = Integer.parseInt(req.getParameter("camInfoNum"));
			String isNoLike = req.getParameter("isNoLike");
			
			if(isNoLike.equals("true")) {
				dao.insertCampWish(camInfoNum, info.getUserId()); // 공감
			} else {
				dao.deleteCampWish(camInfoNum, info.getUserId()); // 공감 취소
			}
			
			// 공감 개수
			wishCount = dao.countCampWish(camInfoNum);
			
			state = "true";
		} catch (SQLException e) {
			state = "liked";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		job.put("wishCount", wishCount);
		
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	
	
	
	
	
}
