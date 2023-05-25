package com.mate;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.campInfo.CampInfoDAO;
import com.campInfo.CampInfoDTO;
import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/mate/*")
public class MateServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp + "/member/member.do");
			return;
		}

		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("applyList.do") != -1) {
			applylist(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) { 
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("apply.do") != -1) {
			applyForm(req, resp);
		} else if(uri.indexOf("apply_ok.do") != -1) {
			applySubmit(req, resp);			
		} else if (uri.indexOf("applyArticle.do") != -1) { 
			applyarticle(req, resp);
		} else if (uri.indexOf("applyUpdate.do") != -1) {
			applyupdateForm(req, resp);
		} else if (uri.indexOf("applyUpdate_ok.do") != -1) {
			applyupdateSubmit(req, resp);
		} else if (uri.indexOf("applyDelete.do") != -1) {
			applydelete(req, resp);
		}  

	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();

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

			List<MateDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listMate(offset, size);
			} else {
				list = dao.listMate(offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/mate/list.do";
			String articleUrl = cp + "/mate/article.do?page=" + current_page;
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

		forward(req, resp, "/WEB-INF/views/mate/list.jsp");
	}

	protected void applylist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();

		MyUtil util = new MyUtil();

		String cp = req.getContextPath();

		try {
			long camMateNum = Long.parseLong(req.getParameter("camMateNum"));
			
			String page = req.getParameter("page");
			
			int current_page = 1;
			
			if(page != null) {
				current_page = Integer.parseInt(page);
			}


			int dataCount = dao.dataCountApp(camMateNum);


			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;

			List<MateDTO> list = dao.listMateApp(offset, size, camMateNum);
			
			// 페이징 처리
			String listUrl = cp + "/mate/applyList.do";
			String articleUrl = cp + "/mate/applyArticle.do?page=" + current_page;

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/mate/applylist.jsp");

	}

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/mate/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/mate/list.do");
			return;
		}

		try {
			MateDTO dto = new MateDTO();

			dto.setHostId(info.getUserId());
			dto.setHostNickName(info.getUserNickName());
			dto.setCamMateSubject(req.getParameter("camMateSubject"));
			dto.setCamMateContent(req.getParameter("camMateContent"));

			String[] styles = req.getParameterValues("campstyle");
			String style = "";
			for(String s : styles ) {
				style += s + ",";
			}

			dto.setCamStyle(style.substring(0, style.length()-1));
			dto.setCamMateDues(Long.parseLong(req.getParameter("camMateDues")));
			dto.setCamMateStartDate(req.getParameter("camMateStartDate"));
			dto.setCamMateEndDate(req.getParameter("camMateEndDate"));

			dao.insertMate(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page="+page;

		MateDAO dao = new MateDAOImpl();

		try {
			long camMateNum = Long.parseLong(req.getParameter("camMateNum"));

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

			dao.updateHitCount(camMateNum);

			MateDTO dto = dao.readMate(camMateNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/list.do?" + query);
				return;
			}

			MateDTO preReadDto =  dao.preReadMate(dto.getCamMateNum(), keyword, condition);
			MateDTO nextReadDto = dao.nextReadMate(dto.getCamMateNum(), keyword, condition);

			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);

			forward(req, resp, "/WEB-INF/views/mate/article.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/mate/list.do?" + query);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		String page = req.getParameter("page");

		MateDAO dao = new MateDAOImpl();

		try {
			long camMateNum = Long.parseLong(req.getParameter("camMateNum"));

			MateDTO dto = dao.readMate(camMateNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/mate/list.do?page=" + page);
				return;
			}

			if(!dto.getHostId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/mate/list.do?page=" + page);
				return;
			}


			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/mate/write.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/list.do?page="+page);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		String page = req.getParameter("page");

		MateDAO dao = new MateDAOImpl();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/mate/list.do");
			return;
		}

		try {
			MateDTO dto = new MateDTO();

			dto.setHostId(info.getUserId());
			dto.setHostNickName(info.getUserNickName());
			dto.setCamMateNum(Long.parseLong(req.getParameter("camMateNum")));
			dto.setCamMateSubject(req.getParameter("camMateSubject"));
			dto.setCamMateContent(req.getParameter("camMateContent"));

			String[] styles = req.getParameterValues("campstyle");
			String style = "";
			for(String s : styles ) {
				style += s + ",";
			}

			dto.setCamStyle(style.substring(0, style.length()-1));
			dto.setCamMateDues(Long.parseLong(req.getParameter("camMateDues")));
			dto.setCamMateStartDate(req.getParameter("camMateStartDate"));
			dto.setCamMateEndDate(req.getParameter("camMateEndDate"));

			dao.updateMate(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/list.do?page="+page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");


		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		try {
			long camMateNum = Long.parseLong(req.getParameter("camMateNum"));

			MateDTO dto = dao.readMate(camMateNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/list.do?page="+page);
				return;
			}

			if(!dto.getHostId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/mate/list.do?page="+page);
				return;
			}

			dao.deleteMate(camMateNum);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/list.do?page="+page);
	}
	
	
	
	
	
	// 지원 폼

	protected void applyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		try {
			long camMateNum = Long.parseLong(req.getParameter("camMateNum"));
			MateDTO dto = dao.readMate(camMateNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/list.do?page="+page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/mate/applywrite.jsp");
	}

	protected void applySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new  MateDAOImpl();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp+"/");
			return;
		}

		try {
			MateDTO dto = new MateDTO();

			dto.setUserId(info.getUserId());
			dto.setUserNickName(info.getUserNickName());
			dto.setCamMateNum(Long.parseLong(req.getParameter("camMateNum")));
			dto.setCamAppSubject(req.getParameter("camAppSubject"));
			dto.setCamAppContent(req.getParameter("camAppContent"));
			dto.setCamAppAge(Integer.parseInt(req.getParameter("camAppAge")));
			dto.setCamAppGender(req.getParameter("camMateAppGender"));

			dao.insertMateApp(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/applyList.do");
	}

	protected void applyarticle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();
		MyUtil util = new MyUtil();

		String cp = req.getContextPath();
		String page = req.getParameter("page");


		try {
			long camAppNum = Long.parseLong(req.getParameter("camAppNum"));
			
			MateDTO dto = dao.readMateApp(camAppNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
				return;
			}

			dto.setCamAppContent(util.htmlSymbols(dto.getCamAppContent()));


			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

			forward(req, resp, "/WEB-INF/views/mate/applyarticle.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
	}

	protected void applyupdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();

		String cp = req.getContextPath();
		String page = req.getParameter("page");

		try {
			long camMateAppNum = Long.parseLong(req.getParameter("camMateAppNum"));
			MateDTO dto = dao.readMateApp(camMateAppNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
				return;
			}


			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");


			forward(req, resp, "/WEB-INF/views/mate/applywrite.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/WEB-INF/views/mate/applyList.do");
	}

	protected void applyupdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();
		String page = req.getParameter("page");

		MateDAO dao = new MateDAOImpl();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/mate/list.do");
			return;
		}

		try {
			MateDTO dto = new MateDTO();

			dto.setUserId(info.getUserId());
			dto.setUserNickName(info.getUserNickName());
			dto.setCamAppSubject(req.getParameter("camAppSubject"));
			dto.setCamAppContent(req.getParameter("camAppContent"));
			dto.setCamAppAge(Integer.parseInt(req.getParameter("camAppAge")));
			dto.setCamAppGender(req.getParameter("camMateAppGender"));

			dao.updateMateApp(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
	}

	protected void applydelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MateDAO dao = new MateDAOImpl();
		String cp = req.getContextPath();
		String page = req.getParameter("page");

		try {
			long camMateAppNum = Long.parseLong(req.getParameter("camMateAppNum"));

			MateDTO dto = dao.readMate(camMateAppNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
				return;
			}

			dao.deleteMate(camMateAppNum);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/mate/applyList.do?page="+page);
	}

	protected void searchCamp (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		
		try {
			String search = req.getParameter("search");
			
			List<CampInfoDTO> list = dao.listCampInfo(search);
			
			JSONObject job = new JSONObject();
			job.put("list", list);
			
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}






}
