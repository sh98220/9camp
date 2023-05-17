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
	

	public int dataCount(String condition, String keyword, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) "
					+ " FROM campWish, member, campInfo "
					+ " WHERE campWish.userId = member.userId AND campInfo.camInfoNum = campWish.camInfoNum AND "
					+ " campWish.userId = ? AND ";
			
			if (condition.equals("all")) {
				sql += " INSTR(campInfo.camInfoSubject, ?) >= 1 OR INSTR(campInfo.camInfoContent, ?) >= 1 ";
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? ";
			} else {
				sql += " INSTR(" + condition + ", ?) >= 1 ";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			if (condition.equals("all")) {
				pstmt.setString(3, keyword);
			}

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

		public List<MyPageDTO> listWish(int offset, int size, String userId) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {	
				sb.append(" SELECT campWish.camInfoNum, campInfo.camInfoSubject, campInfo.camInfoRegDate, campInfo.camThemaName, campWish.userId, campInfo.camInfoAddr, campInfo.camInfoContent  "); 
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
					dto.setCamInfoAddr(rs.getString("camInfoAddr"));
					dto.setCamInfoContent(rs.getString("camInfoContent"));
					
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



		public List<MyPageDTO> listWish(int offset, int size, String condition, String keyword, String userId) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {	
				sb.append(" SELECT campWish.camInfoNum, campInfo.camInfoSubject, campInfo.camInfoRegDate, campInfo.camThemaName, campWish.userId, campInfo.camInfoAddr, campInfo.camInfoContent  "); 
				sb.append(" FROM campWish, campInfo, campInfoKeyword, keywordName, member "); 
				sb.append(" WHERE campInfoKeyword.keywordName = keywordName.keywordName AND "); 
				sb.append(" campInfoKeyword.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" campWish.camInfoNum = campInfo.camInfoNum AND ");   
				sb.append(" member.userId = campWish.userId AND ");  
				sb.append(" campWish.userId = ? AND ");
				
				
				if (condition.equals("all")) {
					sb.append(" INSTR(campInfo.camInfoSubject, ?) >= 1 OR INSTR(campInfo.camInfoContent, ?) >= 1 ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append(" TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? ");
				} else {
					sb.append(" INSTR(" + condition + ", ?) >= 1 ");
				}
			
				sb.append(" ORDER BY campWish.camInfoNum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
				
				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, userId);
				
				
				if (condition.equals("all")) {
					pstmt.setString(2, keyword);
					pstmt.setString(3, keyword);
					pstmt.setInt(4, offset);
					pstmt.setInt(5, size);
				} else {
					pstmt.setString(2, keyword);
					pstmt.setInt(3, offset);
					pstmt.setInt(4, size);
				}


				rs = pstmt.executeQuery();

				while (rs.next()) {
					MyPageDTO dto = new MyPageDTO();
					
					dto.setCamInfoNum(rs.getLong("camInfoNum"));
					dto.setUserId(rs.getString("userId"));
					dto.setCamThemaName(rs.getString("camThemaName"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					dto.setCamInfoRegDate(rs.getString("camInfoRegDate"));
					dto.setCamInfoAddr(rs.getString("camInfoAddr"));
					dto.setCamInfoContent(rs.getString("camInfoContent"));
					
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


		public void deleteWish(long[] nums, String userId) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
					sql = "DELETE FROM campWish WHERE userId = ? AND"
							+ " camInfoNum IN (";
					for (int i = 0; i < nums.length; i++) {
						sql += "?,";
					}
					sql = sql.substring(0, sql.length() - 1) + ")";

					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, userId);
					for (int i = 0; i < nums.length; i++) {
						pstmt.setLong(i + 2, nums[i]);
					}

					pstmt.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				} finally {
					if (pstmt != null) {
						try {
							pstmt.close();
						} catch (SQLException e2) {
						}
					}
				}

			}
		
		
		
}