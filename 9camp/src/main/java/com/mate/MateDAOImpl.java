package com.mate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MateDAOImpl implements MateDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public void insertMate(MateDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMate(MateDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	public int dataCount() {
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
	public int dataCount(String userId) {
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
	

	public int dataCount(String condition, String keyword, String userId) {
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
	@Override
	public List<MateDTO> listMate(int offset, int size, String userId) {
		List<MateDTO> list = new ArrayList<MateDTO>();
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
				MateDTO dto = new MateDTO();
				
				dto.setCamInfoNum(rs.getLong("camInfoNum"));
				dto.setUserId(rs.getString("userId"));
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


	@Override
	public List<MateDTO> listMate(int offset, int size, String condition, String keyword, String userId) {
		List<MateDTO> list = new ArrayList<MateDTO>();
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
				MateDTO dto = new MateDTO();
				
				dto.setCamInfoNum(rs.getLong("camInfoNum"));
				dto.setUserId(rs.getString("userId"));
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
	
	
	
	@Override
	public void deleteMate(long num, String userId) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMate(long[] nums, String[] userId) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
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

	@Override
	public MateDTO readMate(String userId, Long fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MateDTO> listMate() {
		// TODO Auto-generated method stub
		return null;
	}


}
