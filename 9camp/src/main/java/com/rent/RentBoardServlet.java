package com.rent;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/rent/*")
public class RentBoardServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	
	private String pathname;

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
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "rentPhoto";
		
		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}

	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
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
			
			List<RentBoardDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listRentBoard(offset, size);
			} else {
				list = dao.listRentBoard(offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 페이징 처리
			String listUrl = cp + "/rent/list.do";
			String articleUrl = cp + "/rent/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
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
		
		forward(req, resp, "/WEB-INF/views/rent/list.jsp");
		
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/rent/write.jsp");
		
	}
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp+"/");
			return;
		}
		
		try {
			RentBoardDTO dto = new RentBoardDTO();
			
			dto.setHostId(info.getUserId());
			dto.setRentObject(req.getParameter("rentObject"));
			dto.setRentContent(req.getParameter("rentContent"));
			dto.setRentFee(Long.parseLong(req.getParameter("rentFee")));
			dto.setRentSubject(req.getParameter("rentSubject"));
			dto.setRentStartDate(req.getParameter("rentStartDate"));
			dto.setRentEndDate(req.getParameter("rentEndDate"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setRentPhotos(saveFiles);
			}
			
			dao.insertRent(dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/rent/list.do");
		
	}
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		
		try {
			long rentNum = Long.parseLong(req.getParameter("rentNum"));
			RentBoardDTO dto = dao.readRentBoard(rentNum);
			
			if(dto == null) {
				resp.sendRedirect(cp+"/rent/list.do?page="+page);
				return;
			}
			
			if(!dto.getHostId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/rent/list.do?page="+page);
				return;
			}
			
			List<RentBoardDTO> listFile = dao.listRentPhoto(rentNum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listFile", listFile);

			req.setAttribute("mode", "update");
			
			
			forward(req, resp, "/WEB-INF/views/rent/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/rent/list.do?page="+page);
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		String page = req.getParameter("page");
		
		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp+"/WEB-INF/views/list.do?page="+page);
			return;
		}
		
		try {
			RentBoardDTO dto = new RentBoardDTO();
			dto.setRentNum(Long.parseLong(req.getParameter("rentNum")));
			dto.setRentSubject(req.getParameter("rentCount"));
			dto.setRentSubject(req.getParameter("rentSubject"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setRentPhotos(saveFiles);
			}
			
			dao.updateRent(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/rent/list.do?page="+page);
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		String query = "page="+page;
		
		try {
			long rentNum = Long.parseLong(req.getParameter("rentNum"));
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
			
			dao.updateHitCount(rentNum);
			
			RentBoardDTO dto = dao.readRentBoard(rentNum);
			if(dto == null) {
				resp.sendRedirect(cp+"/rent/list.do?"+query);
				return;
			}
			
			dto.setRentContent(util.htmlSymbols(dto.getRentContent()));
			
			
			RentBoardDTO preRentDto = dao.preReadRentBoard(dto.getRentNum(), condition, keyword);
			RentBoardDTO nextRentDto = dao.nextReadRentBoard(dto.getRentNum(), condition, keyword);
			
			List<RentBoardDTO> listFile = dao.listRentPhoto(rentNum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("preRentDto", preRentDto);
			req.setAttribute("nextRentDto", nextRentDto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/rent/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/rent/list.do?"+query);
	}
	
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		
		try {
			long rentNum = Long.parseLong(req.getParameter("rentNum"));
			
			RentBoardDTO dto = dao.readRentBoard(rentNum);
			
			if(dto == null) {
				resp.sendRedirect(cp+"/rent/list.do?page="+page);
				return;
			}
			
			if(!dto.getHostId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/rent/list.do?page="+page);
				return;
			}
			
			List<RentBoardDTO> list = dao.listRentPhoto(rentNum);
			for(RentBoardDTO vo : list) {
				FileManager.doFiledelete(pathname, vo.getRentPhotoName());
			}
			
			dao.deleteRent(rentNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		resp.sendRedirect(cp+"/rent/list.do?page="+page);
		
	}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RentBoardDAO dao = new RentBoardDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			long rentNum = Long.parseLong(req.getParameter("rentNum"));
			long rentPhotoNum = Long.parseLong(req.getParameter("rentPhotoNum"));
			
		    RentBoardDTO dto = dao.readRentBoard(rentNum);
		    
		    if(dto == null) {
		    	resp.sendRedirect(cp + "/rent/list.do?page="+page);
		    	return;
		    }
		    
		    if(!dto.getHostId().equals(info.getUserId())) {
		    	resp.sendRedirect(cp + "/rent/list.do?page="+page);
		    	return;
		    }
		    
		    RentBoardDTO vo = dao.readPhoto(rentPhotoNum);
		    if(vo != null) {
		    	FileManager.doFiledelete(pathname,vo.getRentPhotoName());
		    	
		    	dao.deletePhotoFile(rentPhotoNum);
		    }
		    
		    resp.sendRedirect(cp + "/rent/update.do?rentNum="+rentNum);
		    return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/rent/list.do?page="+page);
		
	}
	
}
