package com.main;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
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
			list(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CampInfoDAO dao = new CampInfoDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
		//	long num = Long.parseLong(req.getParameter("num"));
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
		// 검색
		String condition = req.getParameter("condition");
		String keyword = req.getParameter("keyword");
	
        String[] keys = req.getParameterValues("key"); // 키워드명 배열로 받기
       
 
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
		if (keyword.length() == 0 ) {
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
		
		List<CampInfoDTO> listimage = null;
		if(keyword.length() == 0) {
			if(keys == null || keys.length == 0) {
				listimage = dao.listPhoto(offset, size);
			} else {
				// 키워드로 검색
				listimage = dao.listPhoto(keys, offset, size);
			}
		} else {
			listimage = dao.listPhoto(offset, size, condition, keyword);
		}
		
		String query = "";
		if(keyword.length() != 0) {
			query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
		}
		
		
		for(CampInfoDTO dto : listimage) {
			dto.setCamInfoContent(dto.getCamInfoContent().substring(0, 50) + "...");
			dto.setCamInfoAddr(dto.getCamInfoAddr().substring(0, dto.getCamInfoAddr().indexOf(' ', dto.getCamInfoAddr().indexOf((' ') ) + 1 ) )  );
		}
	
		// 메인화면 -  이런 캠핑장은 어떄요?
		List<CampInfoDTO> images = new ArrayList<>();
		
		int n = 3;
		if(listimage.size() < n) {
			 n = listimage.size();
		}
		
		if(n > 0) {
			int []nn = new int[n];
			for(int i=0; i<n; i++) {
				nn[i] = (int) (Math.random() * listimage.size());
				for(int j=0; j<i; j++ ) {
					if(nn[i] == nn[j]) {
						i--;
						break;
					}
				}
			}
			
			for(int i=0; i<n; i++) {
				images.add(listimage.get(nn[i]));
			}
		}
		
		String listUrl = cp + "/campInfo/list.do";
		String articleUrl = cp + "/campInfo/article.do?page=" + current_page;
		if (query.length() != 0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}

		String paging = util.paging(current_page, total_page, listUrl);

		
		// 포워딩할 JSP에 전달할 속성
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("size", size);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("listimage", listimage);
		req.setAttribute("images", images);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
	}
	
	

}
