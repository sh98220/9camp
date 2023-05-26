package com.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mail.Mail;
import com.mail.MailSender;
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
		} else if (uri.indexOf("pwdFind.do")!= -1) {
			pwdFindForm(req, resp);
		} else if (uri.indexOf("pwdFind_ok.do")!= -1) {
			pwdFindSubmit(req, resp);
		} else if (uri.indexOf("complete.do")!= -1) {
			complete(req, resp);
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

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");

		
		
		
		MemberDTO dto = dao.loginMember(userId, userPwd);
		
			if(dto != null) {
				
	
				
				if(dto.getRestId() != null) {
					String msg = dto.getRestId() + "님은 " + dto.getRestDate() + " 까지 로그인 할 수 없습니다. 사유 : " + dto.getRestContent();
					req.setAttribute("message", msg);
					forward(req, resp, "/WEB-INF/views/member/member.jsp");
					return;
				}
				
				
				
				session.setMaxInactiveInterval(60*60);
	
				SessionInfo info = new SessionInfo();
	
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setUserNickName(dto.getUserNickName());
				info.setUserPoint(dto.getUserPoint());
				
	
				session.setAttribute("member", info);
				session.setAttribute("userbalance", dto.getUserPoint());
				
				resp.sendRedirect(cp + "/");
				return;
			
		}
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		return;
	}


	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		session.removeAttribute("member");
		session.removeAttribute("userbalance");
		
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
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();
		if(!req.getMethod().equalsIgnoreCase("post")) {
			resp.sendRedirect(cp+"/");
			return;
		}
		String message ="";	
		
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
			
			session.setAttribute("userName", dto.getUserName());
			resp.sendRedirect(cp+"/member/complete.do?mode=member");
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

		resp.sendRedirect(cp + "/");

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
	
	protected void pwdFindForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		String cp = req.getContextPath();

		if(info != null) {
			resp.sendRedirect(cp+"/");
			return;
		}

		forward(req, resp, "/WEB-INF/views/member/pwdFind.jsp");
	}



	protected void pwdFindSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String cp = req.getContextPath();

		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/");
			return;
		}

		String userId = req.getParameter("userId");

		try {
			MemberDAO dao = new MemberDAO();
			MemberDTO dto = dao.readMember(userId);

			if(dto == null) {
				String s = "등록된 아이디가 아닙니다.";
				req.setAttribute("message", s);
				forward(req, resp, "/WEB-INF/views/member/pwdFind.jsp");
				return;
			} else if (dto.getUserEmail()==null||dto.getUserEmail().equals("")) {
				String s = "이메일을 등록하지 않았습니다. 관리자에게 문의하세요.";
				req.setAttribute("message", s);
				forward(req, resp, "/WEB-INF/views/member/pwdFind.jsp");
				return;
			}

			// 임시 패스워드 생성
			String pwd = generatePwd();
			
			// 메일로 전송
			String msg = dto.getUserName()+"님의 새로 발급된 임시 비밀번호는 <span style='color:blue;'><b>" + pwd + "</b></span> 입니다. <br>" 
			+ "로그인 후 반드시 패스워드를 변경하시기 바랍니다.";
			
			Mail mail = new Mail();
			MailSender sender = new MailSender();
			mail.setReceiverEmail(dto.getUserEmail());
			mail.setSenderEmail("9campforcamper@gmail.com"); //메일 설정 이메일 입력
			mail.setSenderName("관리자");
			mail.setSubject("임시 패스워드 발급");
			mail.setContent(msg);
			
			boolean b = sender.mailSend(mail);
			if(b) {
				// 테이블의 패스워드 변경
				dto.setUserPwd(pwd);
				dao.updateMember(dto);
			} else {
				req.setAttribute("message", "이메일 전송이 실패했습니다.");
				forward(req, resp, "/WEB-INF/views/member/pwdFind.jsp");
				return;
			}
			
			session.setAttribute("userName", dto.getUserName());
			resp.sendRedirect(cp+"/member/complete.do?mode=pf");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp +"/");
	}

	protected void complete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userName = (String)session.getAttribute("userName");
		session.removeAttribute("userName");

		String cp = req.getContextPath();

		String mode = req.getParameter("mode");
		if(mode == null) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String msg = "";
		String title = "";
		msg = "<span style='color:blue;'>" + userName + "</span> 님에게 <br>";
		if(mode.equals("member")) {
			title = "회원 가입";

			msg += "회원가입을 축하합니다";
			msg += "로그인 후 차별화된 서비스를 이용하시기 바랍니다.";
		} else if(mode.equals("pf")) {
			title = "비밀번호 찾기";

			msg += "임시 비밀번호를 메일로 전송했습니다.<br>";
			msg += "로그인 후 비밀번호를 변경해주세요.";
		} else {
			resp.sendRedirect(cp + "/");
			return;
		}

		req.setAttribute("title", title);
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/complete.jsp");

	}
	
	// 10자리 임시 패스워드 작성
	private String generatePwd() {
		StringBuilder sb = new StringBuilder();
		
		Random rd = new Random();
		String s = "!@#$%^&*~-+=ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopgrstuvwxyz0123456789";
		for(int i=0; i<10; i++) {
			int n = rd.nextInt(s.length());
			sb.append(s.substring(n, n+1));
		}
		
		return sb.toString();
	}



}
