package com.auction;

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
@WebServlet("/auction/*")
public class AuctionServlet extends MyUploadServlet {
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
		pathname = root + "uploads" + File.separator + "auction";
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("auctionRecamount.do") != -1) {
			auctionRecamount(req, resp);
		} else if(uri.indexOf("auctionRecamount_ok.do") != -1) {
			auctionRecamountSubmit(req,resp);
		}
		
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
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
			
			
			
			List<AuctionDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listAuction(offset, size);
			} else {
				list = dao.listAuction(offset, size, condition, keyword);
			}
			
			
			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/auction/list.do";
			String articleUrl = cp + "/auction/article.do?page=" + current_page;
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
		forward(req, resp, "/WEB-INF/views/auction/list.jsp");
		}
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/auction/write.jsp");
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/auction/list.do");
			return;
		}
		
		
		try {
			AuctionDTO dto = new AuctionDTO();
			
			dto.setAuctionSaleId(info.getUserId()); 
			dto.setAuctionSubject(req.getParameter("auctionSubject"));
			dto.setAuctionContent(req.getParameter("auctionContent"));
			dto.setAuctionObject(req.getParameter("auctionObject"));
			dto.setAuctionEnddate(req.getParameter("auctionEnddate"));
			dto.setAuctionStartamount(Long.parseLong(req.getParameter("auctionStartamount")));			

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				dto.setImageFiles(saveFiles);
				
			}
			
			dao.insertAuction(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/auction/list.do?");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 보기
		AuctionDAO dao = new AuctionDAO();
		
		
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			long num = Long.parseLong(req.getParameter("num"));
			String keyword = req.getParameter("keyword");

			if (keyword != null) {
			    keyword = URLDecoder.decode(keyword, "utf-8");
			} else {
			    keyword = "";
			}


			if (keyword.length() != 0) {
				query += "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}


			// 게시물 가져오기
			AuctionDTO dto = dao.readAuction(num);
			if (dto == null ) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/auction/list.do?" + query);
				return;
			}
			dto.setAuctionContent(util.htmlSymbols(dto.getAuctionContent()));

			List<AuctionDTO> listFile = dao.listPhotoFile(num);
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("listFile", listFile);
			

			// 포워딩 
			forward(req, resp, "/WEB-INF/views/auction/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/auction/list.do?" + query);
}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");

		try {
			long auctionNum = Long.parseLong(req.getParameter("auctionNum"));
			long auctionPhotonum = Long.parseLong(req.getParameter("auctionPhotonum"));
			
			AuctionDTO dto = dao.readAuction(auctionNum);

			if (dto == null) {
				resp.sendRedirect(cp + "/auction/list.do?page=" + page);
				return;
			}

			if (!info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp + "/auction/list.do?page=" + page);
				return;
			}
			
			AuctionDTO vo = dao.readAuctionFile(auctionPhotonum);
			if(vo != null) {
				FileManager.doFiledelete(pathname, vo.getAuctionPhotoname());
				
				dao.deleteAuctionFile("one", auctionPhotonum);
			}

			resp.sendRedirect(cp + "/auction/update.do?num=" + auctionNum + "&page=" + page);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/auction/list.do?page=" + page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		
		String page = req.getParameter("page");

		try {
			long num = Long.parseLong(req.getParameter("num"));

			AuctionDTO dto = dao.readAuction(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/auction/list.do?page=" + page);
				return;
			}

			if (!dto.getAuctionSaleId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/auction/list.do?page=" + page);
				return;
			}

			List<AuctionDTO> listFile = dao.listPhotoFile(num);
			for (AuctionDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getAuctionPhotoname());
			}
			dao.deleteAuctionFile("all", num);

			dao.deleteAuction(num);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/auction/list.do?page=" + page);
	}
	
	protected void auctionRecamount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
		
		HttpSession session= req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/auction/list.do");
			return;
		}
		
		try {
			AuctionDTO dto = new AuctionDTO();
			
			dto.setAuctionNum(Long.parseLong(req.getParameter("auctionNum")));
			dto.setAuctionUserId(info.getUserId()); 
			dto.setAuctionRecamount(Long.parseLong(req.getParameter("auctionRecamount")));			
				
			dao.insertAuctionRecamount(dto);
			resp.sendRedirect(cp + "/auction/article.do?num="+dto.getAuctionNum()+"&page="+page);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//resp.sendRedirect(cp + "/auction/list.do?page=" + page);
	}
	
	protected void auctionRecamountSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AuctionDAO dao = new AuctionDAO();
		
		
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			long num = Long.parseLong(req.getParameter("num"));

			// 게시물 가져오기
			AuctionDTO dto = dao.readAuction(num);
			if (dto == null ) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/auction/list.do?" + query);
				return;
			}
			dto.setAuctionContent(util.htmlSymbols(dto.getAuctionContent()));

			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			

			// 포워딩 
			forward(req, resp, "/WEB-INF/views/auction/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/auction/article.do?" + query);
	}
}
	
	
