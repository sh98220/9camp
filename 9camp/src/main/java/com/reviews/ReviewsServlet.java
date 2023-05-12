package com.reviews;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/reviews/*")
public class ReviewsServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
				
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("download.do") != -1) {
			download(req, resp);
		}
		
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/reviews/list.jsp");
	}
	
	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
	}
	
	protected void insertLikeBoard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시글 좋아요
	}
	
	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 추가
	}
	
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 삭제
	}

	protected void insertReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글의 답글 추가
	}
	
	protected void listReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글의 답글 리스트
	}
	
	protected void deleteReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글의 답글 삭제
	}
	
	protected void countReplyAnswer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글의 답글 개수
	}
	
	
}
