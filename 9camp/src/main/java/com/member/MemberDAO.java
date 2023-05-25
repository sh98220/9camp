package com.member;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


public class MemberDAO {
	private Connection conn = DBConn.getConnection();

	public MemberDTO loginMember(String userId, String userPwd) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;

		try {
			sql = "SELECT userId, userName, userPwd, userTel, userBirth, userNickName, userEmail, "
					+ "userRegDate, userPoint, userUpdateDate "
					+ "FROM member WHERE userId= ? AND userPwd=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new MemberDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));
			}
			
			
			pstmt.close();
			rs.close();
			
			sql = "";
			sql = "SELECT userId, restContent, TO_CHAR(restEndDate, 'YYYY-MM-DD') restEndDate "
					+ " FROM restrictedMember "
					+ " WHERE userId = ? ";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
	
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();

				dto.setRestId(rs.getString("userId"));
				dto.setRestContent(rs.getString("restContent"));
				dto.setRestDate(rs.getString("restEndDate"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}

			}
		}

		return dto;
	}
	
	public void insertMember(MemberDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO member (userId, userName, userPwd, userTel, userBirth, userNickName, "
					+ "userEmail, userRegDate, userPoint, userUpdateDate) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE, 0, SYSDATE)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserName());
			pstmt.setString(3, dto.getUserPwd());
			pstmt.setString(4, dto.getUserTel());
			pstmt.setString(5, dto.getUserBirth());
			pstmt.setString(6, dto.getUserNickName());
			pstmt.setString(7, dto.getUserEmail());

			pstmt.executeUpdate();


		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	public void updateMember(MemberDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE member SET userName=?, userPwd=?, userTel=?, "
					+ "userBirth=?, userNickName=?, userEmail=? WHERE userId=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserTel());
			pstmt.setString(4, dto.getUserBirth());
			pstmt.setString(5, dto.getUserNickName());
			pstmt.setString(6, dto.getUserEmail());
			pstmt.setString(7, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

	}

	public MemberDTO readMember(String userId){
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT userId, userName, userPwd, userTel, userBirth, userNickName, userEmail, userRegDate, userPoint, userUpdateDate "
					+ "FROM member WHERE userId=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserTel(rs.getString("userTel"));
				if(dto.getUserTel() != null) {
					String[] ss = dto.getUserTel().split("-");
					if(ss.length == 3) {
						dto.setTel1(ss[0]);
						dto.setTel2(ss[1]);
						dto.setTel3(ss[2]);
					}
				}

				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				if(dto.getUserEmail() != null) {
					String[] ss = dto.getUserEmail().split("@");
					if(ss.length == 2) {
						dto.setEmail1(ss[0]);
						dto.setEmail2(ss[1]);
					}
				}

				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;

	}

	/*
	public MemberDTO readMember(String userNickName) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT userId, userName, userPwd, userTel, userBirth, userNickName, userEmail, userRegDate, userPoint, userUpdateDate "
					+ "FROM member WHERE userNickName=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userNickName);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserTel(rs.getString("userTel"));


				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	 */

	public void deleteMember(String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM member WHERE userId=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}





	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		MemberDTO dto = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT userId, userName, userPwd, userTel, "
					+ "TO_CHAR(userBirth,'YYYY-MM-DD')userBirth, userNickName, userEmail, "
					+ "TO_CHAR(userRegDate,'YYYY-MM-DD')userRegDate, userPoint, TO_CHAR(userUpdateDate,'YYYY-MM-DD')userUpdateDate "
					+ "FROM member ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new MemberDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserRegDate(rs.getString("userRegData"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));

				list.add(dto);
			}

		} catch (Exception e) {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;

	}

	public List<MemberDTO> listMember(String userName) {
		List<MemberDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		MemberDTO dto = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT userId, userName, userPwd, userTel, "
					+ "TO_CHAR(userBirth,'YYYY-MM-DD')userBirth, userNickName, userEmail, "
					+ "TO_CHAR(userRegDate,'YYYY-MM-DD')userRegDate, userPoint, TO_CHAR(userUpdateDate,'YYYY-MM-DD')userUpdateDate "
					+ "FROM member WHERE userName=?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userName);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new MemberDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserRegDate(rs.getString("userRegData"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));

				list.add(dto);
			}

		} catch (Exception e) {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		return list;
	}
	
}