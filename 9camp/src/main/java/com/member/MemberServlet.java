package com.member;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");


		String uri = req.getRequestURI();

		// uri에 따른 작업 구분
		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		} else if (uri.indexOf("userIdForm.do") != -1) {
			userIdForm(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			deleteSubmit(req, resp);
		}
	}

	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 폼
		String path = "/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);		
	}

	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리
		// 세션객체. 세션 정보는 서버에 저장(로그인 정보, 권한등을 저장)
		HttpSession session = req.getSession();

		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();

		if(!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp+"/");
			return;
		}

		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		String mode = req.getParameter("loginmode");

		MemberDTO dto = null;
		if(mode.equals("1")) {
			dto = dao.loginMember(userId, userPwd);
		} else if (mode.equals("2")) {
			dto = dao.loginAdmin(userId, userPwd);
		}

		if(dto != null) {
			session.setMaxInactiveInterval(60*60);

			SessionInfo info = new SessionInfo();

			info.setUserId(dto.getUserId());
			info.setUserName(dto.getUserName());
			info.setUserNickName(dto.getUserNickName());

			session.setAttribute("member", info);

			resp.sendRedirect(cp + "/");
			return;
		}
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}


	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		session.removeAttribute("member");

		session.invalidate();

		resp.sendRedirect(cp + "/");

	}

	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}

	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("post")) {
			resp.sendRedirect(cp+"/");
			return;
		}

		String message = "";
		try {
			MemberDTO dto = new MemberDTO();

			dto.setUserId(req.getParameter("userId"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserPwd(req.getParameter("userPwd"));

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");

			dto.setUserTel(tel1+"-"+tel2+"-"+tel3);

			dto.setUserBirth(req.getParameter("userBirth"));
			dto.setUserNickName(req.getParameter("userNickName"));

			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");
			dto.setUserEmail(email1 + "@" + email2);

			dao.insertMember(dto);
			resp.sendRedirect(cp+"/");
			return;

		} catch (SQLException e) {
			if (e.getErrorCode() == 1)
				message = "아이디 중복으로 회원 가입이 실패 했습니다.";
			else if (e.getErrorCode() == 1400)
				message = "필수 사항을 입력하지 않았습니다.";
			else if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861)
				message = "날짜 형식이 일치하지 않습니다.";
			else
				message = "회원 가입이 실패 했습니다.";
		} catch (Exception e) {
			message = "회원 가입이 실패 했습니다.";
			e.printStackTrace();
		}

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);

		forward(req, resp, "/WEB-INF/views/member/member.jsp");

	}

	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
			resp.sendRedirect(cp + "/member/member.do");
			return;
		}

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");

	}	


	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 패스워드 확인 제출

		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			MemberDTO dto = dao.readMember(info.getUserId());
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			String userPwd = req.getParameter("userPwd");
			
			if (!dto.getUserPwd().equals(userPwd)) {
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				return;
			}

			// 회원정보수정 - 회원수정폼으로 이동
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/mypage/profileEdit.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원정보 수정 완료
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp+"/");
			return;
		}
		
		try {
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info==null) {
				resp.sendRedirect(cp+"/member/memeber.do");
				return;
			}
			
			MemberDTO dto = new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserPwd(req.getParameter("userPwd"));

			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");
			dto.setUserEmail(email1 + "@" + email2);

			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			dto.setUserTel(tel1 + "-" + tel2 + "-" + tel3);

			dto.setUserBirth(req.getParameter("userBirth"));
			dto.setUserNickName(req.getParameter("userNickName"));

			
			dao.updateMember(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/");

	}
	
	protected void deleteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			String userId = info.getUserId();
			
			MemberDTO dto = dao.readMember(userId);
			if(dto == null) {
				resp.sendRedirect(cp+"/");
				return;
			}
			
			dao.deleteMember(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/");
	}
	
	protected void userIdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/idcheck.jsp";
		forward(req, resp, path);
	}


	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();

		if (!req.getMethod().equalsIgnoreCase("POST")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
		
			MemberDTO dto = dao.readMember(req.getParameter("userId"));
			
			
			if (dto == null) {
				req.setAttribute("message", "존재하지 않는 아이디입니다.");
				return;
			} else if(dto != null) {
				String userName = req.getParameter("userName");
				if(!userName.equals(dto.getUserName())) {
					req.setAttribute("message", "회원 정보에 등록된 이름과 다릅니다.");
					return;
				}
			}

			// 회원정보수정 - 회원수정폼으로 이동
			req.setAttribute("title", "비밀번호 인증");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "submit");
			forward(req, resp, "/WEB-INF/views/member/idcheck_ok.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}



}
