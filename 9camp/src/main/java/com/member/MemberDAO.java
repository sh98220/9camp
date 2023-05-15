package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	}

	public void deleteMember(String userId, String userPwd) throws SQLException {

	}
     
	/*
	public MemberDTO readMember(String userId) {

	}

	public MemberDTO readMember(String userNickName) {

	}

	public List<MemberDTO> listMember() {

	}

	public List<MemberDTO> listMember(String userName) {

	}
	*/

}