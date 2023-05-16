package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.util.DBConn;

public class MyPageDAO {
	
	private Connection conn = DBConn.getConnection();

	//회원 정보 수정
	public MyPageDTO readMember(String userId) {
		MyPageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT userId, userName, userPwd,");
			sb.append("      userTel, TO_CHAR(userBirth, 'YYYY-MM-DD') userBirth, userNickName,");
			sb.append("     userEmail, userPoint ");
			sb.append("  FROM member");
			sb.append("  WHERE userId = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MyPageDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserBirth(rs.getString("birth"));
				dto.setUserNickName(rs.getString("nickName"));
				dto.setUserTel(rs.getString("userTel"));
				if(dto.getUserTel() != null) {
					String[] ss = dto.getUserTel().split("-");
					if(ss.length == 3) {
						dto.setUserTel1(ss[0]);
						dto.setUserTel2(ss[1]);
						dto.setUserTel3(ss[2]);
					}
				}
				dto.setUserEmail(rs.getString("userEmail"));
				if(dto.getUserEmail() != null) {
					String[] ss = dto.getUserEmail().split("@");
					if(ss.length == 2) {
						dto.setUserEmail1(ss[0]);
						dto.setUserEmail2(ss[1]);
					}
				}
				dto.setUserPoint(rs.getLong("userPoint"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}	
	
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campWish";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	//아이디를 이용하여 개수 구하기
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM campWish, member, campInfo "
					+ " WHERE campWish.userId = member.userId AND campInfo.camInfoNum = campWish.camInfoNum AND "
					+ " campWish.userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	

		
		// 게시물 리스트
		public List<MyPageDTO> listWish(int offset, int size) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT num, n.userId, userName, subject, ");
				sb.append("       hitCount, reg_date ");
				sb.append(" FROM Wish n ");
				sb.append(" JOIN member1 m ON n.userId = m.userId ");
				sb.append(" ORDER BY num DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					MyPageDTO dto = new MyPageDTO();

					//dto.setNum(rs.getLong("num"));
					dto.setUserId(rs.getString("userId"));
					dto.setUserName(rs.getString("userName"));
					//dto.setSubject(rs.getString("subject"));
					//dto.setHitCount(rs.getInt("hitCount"));
					//dto.setReg_date(rs.getString("reg_date"));

					list.add(dto);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return list;
		}


		// 
		public List<MyPageDTO> listWish() {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {				
				sb.append(" SELECT count(*) "); 
				sb.append(" FROM campWish, campInfo, campInfoKeyword, keywordName, member "); 
				sb.append(" WHERE campInfoKeyword.keywordName = keywordName.keywordName AND "); 
				sb.append(" campInfoKeyword.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" campWish.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" member.userId = campWish.userId; AND ");  
				sb.append(" ");


				pstmt = conn.prepareStatement(sb.toString());

				rs = pstmt.executeQuery();

				while (rs.next()) {
					MyPageDTO dto = new MyPageDTO();

					//dto.setNum(rs.getLong("num"));
					dto.setUserId(rs.getString("userId"));
					dto.setUserName(rs.getString("userName"));
					//dto.setSubject(rs.getString("subject"));
					//dto.setHitCount(rs.getInt("hitCount"));
					//dto.setReg_date(rs.getString("reg_date"));

					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return list;
		}


		public List<MyPageDTO> listMyPage(int offset, int size, String userId) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {	
				sb.append(" SELECT campWish.camInfoNum, campInfo.camInfoSubject, campInfo.camInfoRegDate, campInfo.camThemaName, campWish.userId  "); 
				sb.append(" FROM campWish, campInfo, campInfoKeyword, keywordName, member "); 
				sb.append(" WHERE campInfoKeyword.keywordName = keywordName.keywordName AND "); 
				sb.append(" campInfoKeyword.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" campWish.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" member.userId = campWish.userId AND ");  
				sb.append(" campWish.userId = ? ");
				sb.append(" ORDER BY campWish.camInfoNum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, userId);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					MyPageDTO dto = new MyPageDTO();
					
					dto.setCamInfoNum(rs.getLong("camInfoNum"));
					dto.setUserId(rs.getString("userId"));
					dto.setCamThemaName(rs.getString("camThemaName"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					dto.setCamInfoRegDate(rs.getString("camInfoRegDate"));
					
					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return list;
		}

}