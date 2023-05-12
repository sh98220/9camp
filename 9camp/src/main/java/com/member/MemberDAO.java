package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnetion();

	public MemberDTO loginMember(String userId, String userPwd) throws SQLException {
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
				dto.setUserPwd(rs.getString("userTel"));
				dto.setUserPwd(rs.getString("userBirth"));
				dto.setUserPwd(rs.getString("userNickName"));
				dto.setUserPwd(rs.getString("userEmail"));
				dto.setUserPwd(rs.getString("userRegDate"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserPwd(rs.getString("userUpdateDate"));
			}
			
			

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