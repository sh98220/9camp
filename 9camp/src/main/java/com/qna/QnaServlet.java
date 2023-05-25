package com.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet{
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
			resp.sendRedirect(cp + "/member/member.do");
			return;
		}

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "qna";

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
		} else if(uri.indexOf("reply.do") != -1) {
			replyForm(req, resp);
		} else if(uri.indexOf("reply_ok.do") != -1) {
			replySubmit(req, resp);			
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		}

	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
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

			List<QnaDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listQna(offset, size);
			} else {
				list = dao.listQna(offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/qna/list.do";
			String articleUrl = cp + "/qna/article.do?page=" + current_page;
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

		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/qna/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		QnaDAO dao = new QnaDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}

		try {
			QnaDTO dto = new QnaDTO();

			dto.setUserId(info.getUserId());
			dto.setUserNickName(req.getParameter("userNickName"));
			dto.setQnaSubject(req.getParameter("qnaSubject"));
			dto.setQnaContent(req.getParameter("qnaContent"));
			dto.setQnaOrChange(req.getParameter("qnaOrChange"));
			dto.setQnaPwd(req.getParameter("qnaPwd"));

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setQnasaveFiles(saveFiles);
				dto.setQnaoriginalFiles(originalFiles);
			}

			dao.insertQna(dto, "write");

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/qna/list.do");

	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page="+page;

		QnaDAO dao = new QnaDAO();

		try {
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));

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

			dao.updateHitCount(qnaNum);

			QnaDTO dto = dao.readQna(qnaNum);
			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?" + query);
				return;
			}

			QnaDTO preQnaDto =  dao.preReadQna(dto.getQnaNum(), keyword, condition);
			QnaDTO nextQnaDto = dao.nextReadQna(dto.getQnaNum(), keyword, condition);

			List<QnaDTO> listFile = dao.listQnaFile(qnaNum);

			req.setAttribute("dto", dto);
			req.setAttribute("preQnaDto", preQnaDto);
			req.setAttribute("nextQnaDto", nextQnaDto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("query", query);
			req.setAttribute("page", page);

			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/list.do?" + query);


	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		QnaDAO dao = new QnaDAO();
		
		try {
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));
			
			QnaDTO dto = dao.readQna(qnaNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}
			
			if(!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}
			
			List<QnaDTO> listFile = dao.listQnaFile(qnaNum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("listFile", listFile);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		QnaDAO dao = new QnaDAO();
		
		if(!req.getMethod().equalsIgnoreCase("post")) {
			resp.sendRedirect(cp + "/qna/list.do?page=" + page);
			return;
		}
				
		try {
			
			QnaDTO dto = new QnaDTO();
			
			dto.setUserId(info.getUserId());
			dto.setUserNickName(req.getParameter("userNickName"));
			dto.setQnaNum(Long.parseLong(req.getParameter("qnaNum")));
			dto.setQnaSubject(req.getParameter("qnaSubject"));
			dto.setQnaContent(req.getParameter("qnaContent"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setQnasaveFiles(saveFiles);
				dto.setQnaoriginalFiles(originalFiles);
			}
			
			dao.updateQna(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	}

	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));
			
			QnaDTO dto = dao.readQna(qnaNum);
			if (dto == null) {
				resp.sendRedirect(cp + "/qna/list.do?page=" + page);
				return;
			}
			
			String s = "[" + dto.getQnaSubject() + "] 에 대한 답변입니다.\n";
			dto.setQnaContent(s);
			
			req.setAttribute("mode", "reply");
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
		
			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
		
	}

	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp + "/qna/list.do?page=" + page);
			return;
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/board/list.do");
			return;
		}
		
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setUserId(info.getUserId());
			dto.setUserNickName(info.getUserNickName());
			dto.setQnaSubject(req.getParameter("qnaSubject"));
			dto.setQnaContent(req.getParameter("qnaContent"));
			dto.setGroupNum(Long.parseLong(req.getParameter("groupNum")));
			dto.setOrderNum(Integer.parseInt(req.getParameter("orderNum")));
			dto.setDepth(Integer.parseInt(req.getParameter("depth")));
			dto.setParent(Long.parseLong(req.getParameter("parent")));

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setQnasaveFiles(saveFiles);
				dto.setQnaoriginalFiles(originalFiles);
			}

			dao.insertQna(dto, "reply");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
		
	}

	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		QnaDAO dao = new QnaDAO();

		String cp = req.getContextPath();
		String page = req.getParameter("page");

		try {
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));
			long qnaFileNum = Long.parseLong(req.getParameter("qnaFileNum"));

			QnaDTO dto = dao.readQna(qnaNum);
			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?page=" + page);
				return;
			}

			QnaDTO vo = dao.readFile(qnaFileNum);

			if(vo != null) {
				FileManager.doFiledelete(pathname,vo.getQnasaveFilename());

				dao.deleteFile(qnaFileNum);
			}
			resp.sendRedirect(cp + "/qna/update.do?page="+page+"&qnaNum="+qnaNum);
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp+"/qna/list.do?page="+page);

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");

		try {
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));

			QnaDTO dto = dao.readQna(qnaNum);

			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?page="+page);
				return;
			}

			List<QnaDTO> list = dao.listQnaFile(qnaNum);
			for(QnaDTO vo : list) {
				FileManager.doFiledelete(pathname, vo.getQnasaveFilename());
			}
			
			dao.deleteQna(qnaNum);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?page="+page);

	}

	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		boolean b = false;

		try {
			long qnaFileNum = Long.parseLong(req.getParameter("qnaFileNum"));

			QnaDTO dto = dao.readFile(qnaFileNum);
			if(dto != null) {
				b = FileManager.doFiledownload(dto.getQnasaveFilename(), dto.getQnaoriginalFilename(), pathname, resp);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}
	}
	
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		long qnaNum = Long.parseLong(req.getParameter("qnaNum"));
		
		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
			resp.sendRedirect(cp + "/qna/member.do");
			return;
		}
		
		req.setAttribute("qnaNum", qnaNum);
		forward(req, resp, "/WEB-INF/views/qna/pwd.jsp");

	}	


	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 제출

		QnaDAO dao = new QnaDAO();
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			
			long qnaNum = Long.parseLong(req.getParameter("qnaNum"));
			
			QnaDTO dto = dao.readQna(qnaNum);

			String userPwd = req.getParameter("userPwd");
			
			if (!dto.getQnaPwd().equals(userPwd)) {
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/qna/pwd.jsp");
				return;
			}

			resp.sendRedirect(cp + "/qna/article.do?qnaNum="+qnaNum);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
		
	}


}
