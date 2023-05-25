package com.mate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MateDAOImpl implements MateDAO{
	private Connection conn = DBConn.getConnection();
	// 메이트 게시글

	@Override
	public void insertMate(MateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO campMate (cammateNum, caminfoNum, hostId, cammateSubject, cammateContent, "
					+ "cammateRegDate, cammateHitCount, cammateStartDate, cammateEndDate, cammateDues, campStyle) "
					+ "VALUES (campmate_seq.NEXTVAL, 32, ?, ?, ?, SYSDATE, 0, ?, ?, ?, ? )";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getHostId());
			pstmt.setString(2, dto.getCamMateSubject());
			pstmt.setString(3, dto.getCamMateContent());
			pstmt.setString(4, dto.getCamMateStartDate());
			pstmt.setString(5, dto.getCamMateEndDate());
			pstmt.setLong(6, dto.getCamMateDues());
			pstmt.setString(7, dto.getCamStyle());

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

	@Override
	public void updateMate(MateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE campmate SET cammateSubject=?, cammateContent=?, "
					+ "cammateStartDate=?, cammateEndDate=?, cammateDues=?, campstyle=? WHERE cammateNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getCamMateSubject());
			pstmt.setString(2, dto.getCamMateContent());
			pstmt.setString(3, dto.getCamMateStartDate());
			pstmt.setString(4, dto.getCamMateEndDate());
			pstmt.setLong(5, dto.getCamMateDues());
			pstmt.setString(6, dto.getCamStyle());
			pstmt.setLong(7, dto.getCamMateNum());

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

	@Override
	public void deleteMate(long cammateNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM campmate WHERE cammateNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, cammateNum);

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if( pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}


	}


	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campmate";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt(1);
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
		}

		return result;
	}


	@Override
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campmate c  "
					+ "JOIN member m ON c.userId = m.userId ";


			if(condition.equals("all")) {
				sql += "WHERE INSTR(cammateSubject, ?) >= 1 OR INSTR(cammateContent, ?) >= 1 ";
			} else if(condition.equals("cammateRegDate")){
				sql += "WHERE TO_CHAR(cammateRegDate,'YYYY-MM-DD') = ? ";
			} else if(condition.equals("campinfo")) {
				sql += "WHERE INSTR(camInfoSubject, ?) >= 1 ";
			} else {
				sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
			}


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,keyword);

			if(condition.equals("all")) {
				pstmt.setString(2,keyword);
			} 

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt(1);
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

		return result;

	}

	@Override
	public List<MateDTO> listMate(int offset, int size) {
		PreparedStatement pstmt = null;
		List<MateDTO> list = new ArrayList<>();
		MateDTO dto = null;
		String sql;
		ResultSet rs = null;
		try {
			sql = "SELECT cammateNum, hostId, userNickName, cammateSubject,"
					+ "TO_CHAR(cammateRegDate,'YYYY-MM-DD')cammateRegDate, cammateHitCount "
					+ "FROM campmate c JOIN member m ON m.userId = c.hostId "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new MateDTO();

				dto.setCamMateNum(rs.getLong("cammateNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setHostNickName(rs.getString("userNickName"));
				dto.setCamMateSubject(rs.getString("cammateSubject"));
				dto.setCamMateRegDate(rs.getString("cammateRegDate"));
				dto.setCamMateHitCount(rs.getInt("cammateHitCount"));

				list.add(dto);
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

		return list;
	}



	@Override
	public List<MateDTO> listMate(int offset, int size, String condition, String keyword) {
		PreparedStatement pstmt = null;
		List<MateDTO> list = new ArrayList<>();
		MateDTO dto = null;
		String sql;
		ResultSet rs = null;
		try {
			sql = "SELECT cammateNum, hostId, userNickName, camInfoSubject, cammateSubject, cammateContent, "
					+ "TO_CHAR(cammateStartDate,'YYYY-MM-DD')cammateStartDate, TO_CHAR(cammateEndDate,'YYYY-MM-DD')cammateEndDate, cammateDues, campStyle, "
					+ "TO_CHAR(cammateRegDate,'YYYY-MM-DD')cammateRegDate, cammateHitCount "
					+ "FROM campmate c "
					+ "JOIN member m ON m.userId = c.hostId "
					+ "JOIN campinfo i ON i.camInfoNum = c.camInfoNum ";


			if(condition.equals("all")) {
				sql += "WHERE INSTR(cammateSubject, ?) >= 1 OR INSTR(cammateContent, ?) >= 1 ";
			} else if(condition.equals("cammateRegDate")){
				sql += "WHERE TO_CHAR(cammateRegDate,'YYYY-MM-DD') = ? ";
			} else if(condition.equals("campinfo")) {
				sql += "WHERE INSTR(camInfoSubject, ?) >= 1 ";
			} else {
				sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";


			pstmt = conn.prepareStatement(sql);

			if(condition.equals("all")) {
				pstmt.setString(1,keyword);
				pstmt.setString(2,keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1,keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new MateDTO();

				dto.setCamMateNum(rs.getLong("cammateNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setHostNickName(rs.getString("userNickName"));
				dto.setCamMateSubject(rs.getString("cammateSubject"));
				dto.setCamMateRegDate(rs.getString("cammateRegDate"));
				dto.setCamMateHitCount(rs.getInt("cammateHitCount"));

				list.add(dto);
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

		return list;
	}


	@Override
	public MateDTO readMate(long camMateNum) {
		PreparedStatement pstmt = null;
		MateDTO dto = null;
		String sql;
		ResultSet rs = null;
		try {
			sql = "SELECT cammateNum, hostId, userNickName, camInfoSubject, cammateSubject, cammateContent, "
					+ "TO_CHAR(cammateStartDate,'YYYY-MM-DD')cammateStartDate, TO_CHAR(cammateEndDate,'YYYY-MM-DD')cammateEndDate, cammateDues, campStyle, "
					+ "TO_CHAR(cammateRegDate,'YYYY-MM-DD')cammateRegDate, cammateHitCount "
					+ "FROM campmate c "
					+ "JOIN member m ON m.userId = c.hostId "
					+ "JOIN campinfo i ON i.camInfoNum = c.camInfoNum "
					+ "WHERE camMateNum =?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, camMateNum);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new MateDTO();

				dto.setCamMateNum(rs.getLong("cammateNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setHostNickName(rs.getString("userNickName"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setCamMateSubject(rs.getString("cammateSubject"));
				dto.setCamMateContent(rs.getString("cammateContent"));
				dto.setCamMateStartDate(rs.getString("cammateStartDate"));
				dto.setCamMateEndDate(rs.getString("cammateEndDate"));
				dto.setCamMateDues(rs.getLong("cammateDues"));
				dto.setCamStyle(rs.getString("campStyle"));
				dto.setCamMateRegDate(rs.getString("cammateRegDate"));
				dto.setCamMateHitCount(rs.getInt("cammateHitCount"));
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

	public void updateHitCount(long camMateNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE campmate SET camMateHitCount = camMateHitCount+1 WHERE camMateNum=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, camMateNum);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

	}


	@Override
	public MateDTO preReadMate(long cammateNum, String keyword, String condition) {
		MateDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT cammateNum, camInfoSubject, cammateSubject FROM campmate c "
						+ "JOIN member m ON m.userId = c.hostId "
						+ "JOIN campinfo i ON i.camInfoNum = c.camInfoNum "
						+ "WHERE (cammateNum > ?) ";

				if(condition.equals("all")) {
					sql += "WHERE INSTR(cammateSubject, ?) >= 1 OR INSTR(cammateContent, ?) >= 1 ";
				} else if(condition.equals("cammateRegDate")){
					sql += "WHERE TO_CHAR(cammateRegDate,'YYYY-MM-DD') = ? ";
				} else if(condition.equals("campinfo")) {
					sql += "WHERE INSTR(camInfoSubject, ?) >= 1 ";
				} else {
					sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
				}

				sql += " ORDER BY cammateNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";


				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1,cammateNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3,keyword);
				} 

				rs = pstmt.executeQuery();

			} else {
				sql = "SELECT cammateNum, camInfoSubject, cammateSubject "
						+ " FROM campmate c "
						+ " JOIN member m ON c.hostId = m.userId "
						+ " JOIN campinfo i ON i.camInfoNum = c.camInfoNum "
						+ " WHERE (cammateNum > ?) "
						+ " ORDER BY cammateNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, cammateNum);
				rs = pstmt.executeQuery();

			}


			
			if(rs.next()) {
				dto = new MateDTO();

				dto.setCamMateNum(rs.getLong("cammateNum"));
				dto.setCamMateSubject(rs.getString("cammateSubject"));
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


	@Override
	public MateDTO nextReadMate(long cammateNum, String keyword, String condition) {
		MateDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT cammateNum, camInfoSubject, cammateSubject FROM campmate c "
						+ "JOIN member m ON m.userId = c.hostId "
						+ "JOIN campinfo i ON i.camInfoNum = c.camInfoNum "
						+ "WHERE (cammateNum < ?) ";

				if(condition.equals("all")) {
					sql += "WHERE INSTR(cammateSubject, ?) >= 1 OR INSTR(cammateContent, ?) >= 1 ";
				} else if(condition.equals("cammateRegDate")){
					sql += "WHERE TO_CHAR(cammateRegDate,'YYYY-MM-DD') = ? ";
				} else if(condition.equals("campinfo")) {
					sql += "WHERE INSTR(camInfoSubject, ?) >= 1 ";
				} else {
					sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
				}

				sql += " ORDER BY cammateNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";


				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1,cammateNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3,keyword);
				} 

				rs = pstmt.executeQuery();

			} else {
				sql = "SELECT cammateNum, camInfoSubject, cammateSubject "
						+ " FROM campmate c "
						+ " JOIN member m ON c.hostId = m.userId "
						+ " JOIN campinfo i ON i.camInfoNum = c.camInfoNum "
						+ " WHERE (cammateNum < ?) "
						+ " ORDER BY cammateNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, cammateNum);
				rs = pstmt.executeQuery();

			}
			
			if(rs.next()) {
				dto = new MateDTO();

				dto.setCamMateNum(rs.getLong("cammateNum"));
				dto.setCamMateSubject(rs.getString("cammateSubject"));
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




	// 메이트 지원 

	@Override
	public void insertMateApp(MateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO campmateapply (campAppNum, cammateNum, userId, camMateSubject, camMateAppContent, "
					+ "camMateAppDate, camMateAppGender, camMateAppAge, camMateAppConfirm, caminfoNum) "
					+ "VALUES(campMateApp_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, ?, 0, 32)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getCamMateNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getCamAppSubject());
			pstmt.setString(4, dto.getCamAppContent());
			pstmt.setString(5, dto.getCamAppGender());
			pstmt.setInt(6, dto.getCamAppAge());
			
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

	@Override
	public void updateMateApp(MateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE campmateapply SET camMateSubject=?, camMateAppContent=?, "
					+ "camMateAppGender=?, camMateAppAge=? WHERE campAppNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getCamAppSubject());
			pstmt.setString(2, dto.getCamAppContent());
			pstmt.setString(3, dto.getCamAppGender());
			pstmt.setInt(4, dto.getCamAppAge());
			pstmt.setLong(5, dto.getCamAppNum());
			
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

	@Override
	public void deleteMateApp(long camMateAppNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM campMateApply WHERE camMateAppNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camMateAppNum);
			
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


	@Override
	public int dataCountApp(long camMateNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campmateapply WHERE camMateNum=?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camMateNum);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt(1);
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
		}

		return result;
	}



	@Override
	public List<MateDTO> listMateApp(int offset, int size, long camMateNum) {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		List<MateDTO> list = new ArrayList<>();
		MateDTO dto = null;
		
		try {
			sql = "SELECT campAppNum, m.userId, userNickName, "
					+ "camMateAppContent, camMateSubject, camMateAppGender, camMateAppAge "
					+ "FROM campMateApply c "
					+ "JOIN member m ON m.userId=c.userId "
					+ "WHERE camMateNum=? "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camMateNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new MateDTO();
				
				dto.setCamAppNum(rs.getLong("campAppNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setCamAppContent(rs.getString("camMateAppContent"));
				dto.setCamAppSubject(rs.getString("camMateSubject"));
				dto.setCamAppGender(rs.getString("camMateAppGender"));
				dto.setCamAppAge(rs.getInt("camMateAppAge"));
				
				list.add(dto);	
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
		
		return list;
	}

	@Override
	public MateDTO readMateApp(long camAppNum) {
		MateDTO dto = null;
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs = null;
		
		try {
			sql = "SELECT campAppNum, m.userId, userNickName, "
					+ "camMateAppContent, camMateSubject, camMateAppGender, camMateAppAge "
					+ "FROM campMateApply c "
					+ "JOIN member m ON m.userId=c.userId "
					+ "WHERE campAppNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camAppNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MateDTO();
				
				dto.setCamAppNum(rs.getLong("campAppNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setCamAppContent(rs.getString("camMateAppContent"));
				dto.setCamAppSubject(rs.getString("camMateSubject"));
				dto.setCamAppGender(rs.getString("camMateAppGender"));
				dto.setCamAppAge(rs.getInt("camMateAppAge"));
				
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
			
			if(rs!= null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}


}
