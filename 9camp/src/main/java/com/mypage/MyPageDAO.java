package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mate.MateDTO;

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
	
	
	public int dataCountWish() {
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
	public int dataCountWish(String userId) {
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
	

	public int dataCountWish(String condition, String keyword, String userId) {
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
		
		
		public int dataCountMate() {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM campMate";
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
		public int dataCountMate(String userId) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) "
						+ " FROM campMate, member, campInfo "
						+ " WHERE campMate.userId = member.userId AND campInfo.camInfoNum = campMate.camInfoNum AND "
						+ " campMate.userId = ?";
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
		

		public int dataCountMate(String condition, String keyword, String userId) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) "
						+ " FROM campMate, member, campInfo "
						+ " WHERE campMate.userId = member.userId AND campInfo.camInfoNum = campMate.camInfoNum AND "
						+ " campMate.userId = ? AND ";
				
				if (condition.equals("all")) {
					sql += " INSTR(campMate.camMateSubject, ?) >= 1 OR INSTR(campMate.camMateContent, ?) >= 1 ";
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
		//캠핑 메이트 리스트

		public List<MyPageDTO> listMate(int offset, int size, String userId) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {	
				sb.append(" SELECT campMate.camMateNum, campMate.camInfoNum, campMate.userId, campMate.camMateSubject, "
						+ " campMate.camMateContent, campMate.camMateStartDate, campMate.camMateEndDate, campMate.camMateDues, campInfo.camInfoSubject ");
				sb.append(" FROM campMate, campInfo, member "); 
				sb.append(" WHERE campMate.camInfoNum = campInfo.camInfoNum AND "); 
				sb.append(" campMate.userId = member.userId AND ");   
				sb.append(" campMate.userId = ? ");
				sb.append(" ORDER BY campMate.camMateNum DESC ");
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
					dto.setCamMateNum(rs.getLong("camMateNum"));
					dto.setCamMateSubject(rs.getString("camMateSubject"));
					dto.setCamMateContent(rs.getString("camMateContent"));
					dto.setCamMateStartDate(rs.getString("camMateStartDate"));
					dto.setCamMateEndDate(rs.getString("camMateEndDate"));
					dto.setCamMateDues(rs.getInt("camMateDues"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					
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


		public List<MyPageDTO> listMate(int offset, int size, String condition, String keyword, String userId) {
			List<MyPageDTO> list = new ArrayList<MyPageDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {	
				sb.append(" SELECT campMate.camMateNum, campMate.camInfoNum, campMate.userId, campMate.camMateSubject, "
						+ " campMate.camMateContent, campMate.camMateStartDate, campMate.camMateEndDate, campMate.camMateDues, campInfo.camInfoSubject ");
				sb.append(" FROM campMate, campInfo, member "); 
				sb.append(" WHERE campMate.camInfoNum = campInfo.camInfoNum AND "); 
				sb.append(" campMate.userId = member.userId AND ");   
				sb.append(" campMate.userId = ? AND ");

				if (condition.equals("all")) {
					sb.append(" INSTR(campMate.camMateSubject, ?) >= 1 OR INSTR(campMate.camMateContent, ?) >= 1 ");
				} //else if (condition.equals("reg_date")) {
					//keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					//sb.append(" TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? "); }
				 else {
					sb.append(" INSTR(" + condition + ", ?) >= 1 ");
				}
			
				sb.append(" ORDER BY campMate.camMateNum DESC ");
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
					dto.setCamMateNum(rs.getLong("camMateNum"));
					dto.setCamMateSubject(rs.getString("camMateSubject"));
					dto.setCamMateContent(rs.getString("camMateContent"));
					dto.setCamMateStartDate(rs.getString("camMateStartDate"));
					dto.setCamMateEndDate(rs.getString("camMateEndDate"));
					dto.setCamMateDues(rs.getInt("camMateDues"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					
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
		
		
		

		public void deleteMate(long num, String userId) throws SQLException {
			// TODO Auto-generated method stub
			
		}


		public void deleteMate(long[] nums, String[] userId) throws SQLException {
			// TODO Auto-generated method stub
			
		}



		public void deleteMate(long[] nums, String userId) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;
			
			
			try {
					sql = "DELETE FROM campMate WHERE userId = ? AND"
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

	public List<MateDTO> listMate() {
			// TODO Auto-generated method stub
		return null;
	}


	public MyPageDTO readMateApply(long num) {
		MyPageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT campMateApply.camMateNum, campMateApply.userid, campMateApply.camMateAppContent,  "
					+ " campMateApply.camMateAppDate, campMateApply.camMateAppGender, campMateApply.camMateAppAge, "
					+ " campMateApply.camMateAppConfirm, member.userNickname " +
					" FROM campMate, campMateApply, member " +
					" WHERE campMate.camMateNum = campMateApply.camMateNum  AND " +
					" member.userId = campMateApply.userid AND " +
					" campMateApply.camMateNum= ? " ;
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new MyPageDTO();

				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCamMateAppContent(rs.getString("camMateAppContent"));
				dto.setCamMateAppDate(rs.getString("camMateAppDate"));
				dto.setCamMateAppGender(rs.getString("camMateAppGender"));
				dto.setCamMateAppAge(rs.getInt("camMateAppAge"));
				dto.setCamMateAppConfirm(rs.getString("camMateAppConfirm"));
				dto.setUserNickName(rs.getString("userNickname"));
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

		return dto;
	}
	
	public void deleteMateApply(String[] nn, String num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		
		try {
				sql = "DELETE FROM campMateApply "
						+ " WHERE camMateNum = ? AND "
						+ " userId IN (";
				for (int i = 0; i < nn.length; i++) {
					sql += "?,";
				}
				sql = sql.substring(0, sql.length() - 1) + ")";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, num);
				for (int i = 0; i < nn.length; i++) {
					pstmt.setString(i + 2, nn[i]);
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