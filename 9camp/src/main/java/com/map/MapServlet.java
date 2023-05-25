package com.map;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.campInfo.CampInfoDTO;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/mapCamp/*")
public class MapServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("mapSearch.do") != -1) {
			mapSearch(req, resp);
		}
	}
	
	protected void mapSearch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MapDAO dao = new MapDAO();
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
			
			JSONArray jArray = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
                JSONObject jObject = new JSONObject();//배열 내에 들어갈 json
                jObject.put("campNum", list.get(i).getCamInfoNum());
                jObject.put("campName", list.get(i).getCamInfoSubject());
                jObject.put("campAddr", list.get(i).getCamInfoAddr());
                jObject.put("campTel", list.get(i).getCamPhoneNum());
                jArray.put(jObject);
            }
				
			String query = "";
			if(keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			String listUrl = cp + "/mapCamp/mapSearch.do";
			String articleUrl = cp + "/campInfo/article.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging2(current_page, total_page, listUrl);
			
			//long num = Long.parseLong(req.getParameter("num"));
			//CampInfoDTO campDto = dao.readCampInfo(num);
		
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
			
			req.setAttribute("jList", jArray.toString());
			//req.setAttribute("campDto", campDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/mapCamp/mapSearch.jsp");
	}

}
