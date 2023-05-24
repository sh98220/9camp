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

	// 아이디를 이용하여 개수 구하기
	public int dataCountWish(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0)  FROM campWish, member, campInfo "
					+ " WHERE campWish.userId = member.userId AND campInfo.camInfoNum = campWish.camInfoNum AND "
					+ " (campWish.userId = ? OR 'admin' = ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);

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
			sql = "SELECT NVL(COUNT(*), 0) FROM campWish, member, campInfo "
					+ " WHERE campWish.userId = member.userId AND campInfo.camInfoNum = campWish.camInfoNum AND "
					+ " (campWish.userId = ? OR 'admin' = ?) AND ";

			if (condition.equals("all")) {
				sql += " INSTR(campInfo.camInfoSubject, ?) >= 1 OR INSTR(campInfo.camInfoContent, ?) >= 1 ";
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? ";
			} else if (condition.equals("userId")) {
				sql += " INSTR(campWish.userId, ?) >= 1 ";
			} else {
				sql += " INSTR(" + condition + ", ?) >= 1 ";
			}
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, keyword);
			if (condition.equals("all")) {
				pstmt.setString(4, keyword);
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
			sb.append(" FROM campWish, campInfo, member ");
			sb.append(" WHERE campWish.camInfoNum = campInfo.camInfoNum AND ");
			sb.append(" member.userId = campWish.userId AND ");
			sb.append(" (campWish.userId = ? OR 'admin' = ?) ");
			sb.append(" ORDER BY campWish.camInfoNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, size);

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
			sb.append(" FROM campWish, campInfo, member ");
			sb.append(" WHERE campWish.camInfoNum = campInfo.camInfoNum AND ");
			sb.append(" member.userId = campWish.userId AND ");
			//sb.append(" keywordName.keywordName = campInfo.camKeyword AND ");
			sb.append(" (campWish.userId = ? OR 'admin' = ?) AND ");

			if (condition.equals("all")) {
				sb.append(" INSTR(campInfo.camInfoSubject, ?) >= 1 OR INSTR(campInfo.camInfoContent, ?) >= 1 ");
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? ");
			}  else if (condition.equals("userId")) {
				sb.append(" INSTR(campWish.userId , ?) >= 1 ");
			} else {
				sb.append(" INSTR(" + condition + ", ?) >= 1 ");
			}

			sb.append(" ORDER BY campWish.camInfoNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			
			if (condition.equals("all")) {
				pstmt.setString(3, keyword);
				pstmt.setString(4, keyword);
				pstmt.setInt(5, offset);
				pstmt.setInt(6, size);
			} else {
				pstmt.setString(3, keyword);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, size);
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
			sql = "DELETE FROM campWish WHERE (userId = ? OR 'admin' = ? ) AND camInfoNum IN (";
			for (int i = 0; i < nums.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length() - 1) + ")";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			for (int i = 0; i < nums.length; i++) {
				pstmt.setLong(i + 3, nums[i]);
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

	// 아이디를 이용하여 개수 구하기
	public int dataCountMate(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(distinct campMate.camMateNum) " + 
					" FROM campMate, member, campInfo, campMateApply "
					+ " WHERE campMate.hostId = member.userId AND campInfo.camInfoNum = campMate.camInfoNum AND campMate.camMateNum = campMateApply.camMateNum "
					+ " AND (campMate.hostId = ? OR campMateApply.userId = ? OR 'admin' = ? )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);

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
			sql = "SELECT COUNT(distinct campMate.camMateNum) " + " FROM campMate, member, campInfo, campMateApply "
					+ " WHERE campMate.hostId = member.userId AND campInfo.camInfoNum = campMate.camInfoNum AND campMate.camMateNum = campMateApply.camMateNum "
					+ " AND (campMate.hostId = ? OR campMateApply.userId = ? OR 'admin' = ?)";

			if (condition.equals("all")) {
				sql += " AND INSTR(LOWER(campMate.camMateSubject), LOWER(?)) >= 1 OR INSTR(LOWER(campMate.camMateContent), LOWER(?)) >= 1 ";
			} else if (condition.equals("camMateNum")) {
				sql += " AND campMate.camMateNum = ? ";
			} else if (condition.equals("userNickName")) {
				sql += " AND INSTR(LOWER(member.userNickName), LOWER(?)) >= 1 ";
			} else {
				sql += " AND INSTR(" + condition + ", ?) >= 1 ";
			}

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setString(4, keyword);
			if (condition.equals("all")) {
				pstmt.setString(5, keyword);
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
	// 캠핑 메이트 리스트

	public List<MyPageDTO> listMate(int offset, int size, String userId) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					" SELECT DISTINCT campMate.camMateNum, campMate.camInfoNum, campMate.hostId, campMate.camMateSubject, member.userNickName, ");
			sb.append(
					" campMate.camMateStartDate, campMate.camMateEndDate, campMate.camMateDues, campMate.campStyle, campInfo.camInfoSubject ");
			sb.append(" FROM campMate, campInfo, member,campMateApply ");
			sb.append(" WHERE campMate.camInfoNum = campInfo.camInfoNum AND ");
			sb.append(" campMate.hostId = member.userId AND ");
			sb.append(" (campMate.hostId = ? OR campMateApply.userId = ? OR 'admin' = ? ) ");
			sb.append(" ORDER BY campMate.camMateNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setInt(4, offset);
			pstmt.setInt(5, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamInfoNum(rs.getLong("camInfoNum"));
				dto.setUserId(rs.getString("hostId"));
				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setCamMateSubject(rs.getString("camMateSubject"));
				dto.setCamMateStartDate(rs.getString("camMateStartDate"));
				dto.setCamMateEndDate(rs.getString("camMateEndDate"));
				dto.setCamMateDues(rs.getInt("camMateDues"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setCampStyle(rs.getString("campStyle"));

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
			sb.append(
					" SELECT campMate.camMateNum, campMate.camInfoNum, campMate.hostId, campMate.camMateSubject, member.userNickName, ");
			sb.append(
					" campMate.camMateStartDate, campMate.camMateEndDate, campMate.camMateDues, campInfo.camInfoSubject ");
			sb.append(" FROM campMate, campInfo, member,campMateApply ");
			sb.append(" WHERE campMate.camInfoNum = campInfo.camInfoNum AND ");
			sb.append(" campMate.hostId = member.userId AND ");
			sb.append(" (campMate.hostId = ? OR campMateApply.userId = ? OR 'admin' = ? ) AND ");

			if (condition.equals("all")) {
				sb.append(" INSTR(LOWER(campMate.camMateSubject), LOWER(?)) >= 1 OR INSTR(LOWER(campMate.camMateContent), LOWER(?)) >= 1 ");
			} // else if (condition.equals("reg_date")) {
				// keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				// sb.append(" TO_CHAR(campInfo.camInfoRegDate, 'YYYYMMDD') = ? "); }
			else if (condition.equals("camMateNum")) {
				sb.append(" campMate.camMateNum = ? ");
			} else if (condition.equals("userNickName")) {
				sb.append(" INSTR(LOWER(member.userNickName), LOWER(?)) >= 1 ");
			} else {
				sb.append(" INSTR(LOWER(" + condition + "), LOWER(?)) >= 1 ");
			}

			sb.append(" ORDER BY campMate.camMateNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			if (condition.equals("all")) {
				pstmt.setString(4, keyword);
				pstmt.setString(5, keyword);
				pstmt.setInt(6, offset);
				pstmt.setInt(7, size);
			} else {
				pstmt.setString(4, keyword);
				pstmt.setInt(5, offset);
				pstmt.setInt(6, size);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamInfoNum(rs.getLong("camInfoNum"));
				dto.setUserId(rs.getString("hostId"));
				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setCamMateSubject(rs.getString("camMateSubject"));
				dto.setCamMateStartDate(rs.getString("camMateStartDate"));
				dto.setCamMateEndDate(rs.getString("camMateEndDate"));
				dto.setCamMateDues(rs.getInt("camMateDues"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setUserNickName(rs.getString("userNickName"));

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

	public void deleteMate(long[] nums, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM campMate WHERE hostId = ? AND camInfoNum IN (";
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

	// 아이디를 이용하여 개수 구하기
	public int dataCountMateApply(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT Count(*) FROM campMateApply,  campMate, member "
					+ " WHERE campMate.camMateNum = campMateApply.camMateNum AND member.userId = campMateApply.userId "
					+ " AND camMateAppConfirm = '1' AND "
					+ " (campMate.hostId = ? AND campMateApply.userId = ? OR 'admin' = ?) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);

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

	public int dataCountMateApply(String condition, String keyword, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT Count(*) " + " FROM campMateApply, campMate, member "
					+ " WHERE campMate.camMateNum = campMateApply.camMateNum  AND member.userId = campMateApply.userId AND "
					+ " camMateAppConfirm = '1' AND "
					+ " (campMate.hostId = ? AND " + " campMateApply.userId = ? OR 'admin' = ?) ";

			if (condition.equals("userNickName")) {
				sql += " AND INSTR(LOWER(member.userNickName), LOWER(?)) >= 1 ";
			} else if (condition.equals("camMateAppContent")) {
				sql += " AND INSTR(LOWER(campMateApply.camMateAppContent), LOWER(?)) >= 1 ";
			} else if (condition.equals("camMateAppGender")) {
				sql += " AND LOWER(campMateApply.camMateAppGender) = LOWER(?) ";
			} else {
				sql += " AND campMateApply.camMateAppAge = ? ";
			}

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setString(4, keyword);

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

	public List<MyPageDTO> readMateApply(int offset, int size, long num, String userId) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT campMateApply.camMateNum, campMateApply.userid, campMateApply.camMateAppContent,  "
					+ " campMateApply.camMateAppDate, campMateApply.camMateAppGender, campMateApply.camMateAppAge, "
					+ " campMateApply.camMateAppConfirm, member.userNickname "
					+ " FROM campMate, campMateApply, member "
					+ " WHERE campMate.camMateNum = campMateApply.camMateNum  AND "
					+ " member.userId = campMateApply.userid AND " + " campMateApply.camMateAppConfirm = '1' AND "
					+ " (campMateApply.camMateNum= ? AND campMate.hostId = ? OR 'admin' = ?) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, num);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCamMateAppContent(rs.getString("camMateAppContent"));
				dto.setCamMateAppDate(rs.getString("camMateAppDate"));
				dto.setCamMateAppGender(rs.getString("camMateAppGender"));
				dto.setCamMateAppAge(rs.getInt("camMateAppAge"));
				dto.setCamMateAppConfirm(rs.getString("camMateAppConfirm"));
				dto.setUserNickName(rs.getString("userNickname"));

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

	public List<MyPageDTO> readMateApply(int offset, int size, long num, String condition, String keyword,
			String userId) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT campMateApply.camMateNum, campMateApply.userid, campMateApply.camMateAppContent,  ");
			sb.append(" campMateApply.camMateAppDate, campMateApply.camMateAppGender, campMateApply.camMateAppAge, ");
			sb.append(" campMateApply.camMateAppConfirm, member.userNickname ");
			sb.append(" FROM campMate, campMateApply, member ");
			sb.append(" WHERE campMate.camMateNum = campMateApply.camMateNum  AND ");
			sb.append(" member.userId = campMateApply.userid AND ");
			sb.append(" campMateApply.camMateAppConfirm = '1' AND ");
			sb.append(" (campMateApply.camMateNum= ? AND campMate.hostId = ? OR 'admin' = ? ) AND ");

			if (condition.equals("userNickName")) {
				sb.append(" INSTR(LOWER(member.userNickName), LOWER(?)) >= 1  ");
			} else if (condition.equals("camMateAppContent")) {
				sb.append(" INSTR(LOWER(" + condition + "), LOWER(?)) >= 1 ");
			} else if (condition.equals("camMateAppGender")) {
				sb.append(" lower(campMateApply.camMateAppGender) = lower(?) ");
			} else {
				sb.append(" campMateApply.camMateAppAge = ? ");
			}

			sb.append(" ORDER BY campMate.camMateNum DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setLong(1, num);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setString(4, keyword);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCamMateAppContent(rs.getString("camMateAppContent"));
				dto.setCamMateAppDate(rs.getString("camMateAppDate"));
				dto.setCamMateAppGender(rs.getString("camMateAppGender"));
				dto.setCamMateAppAge(rs.getInt("camMateAppAge"));
				dto.setCamMateAppConfirm(rs.getString("camMateAppConfirm"));
				dto.setUserNickName(rs.getString("userNickname"));

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

	public void deleteMateApply(String[] nn, String num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM campMateApply " + " WHERE camMateNum = ? AND " + " userId IN (";
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

	// 아이디를 이용하여 개수 구하기
	public int dataCountMateWait(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT Count(*) FROM campMateApply,  campMate, member "
					+ " WHERE campMate.camMateNum = campMateApply.camMateNum AND member.userId = campMateApply.userId "
					+ " AND camMateAppConfirm = '0' AND "
					+ " (campMate.hostId = ? AND campMateApply.userId = ? OR 'admin' = ?) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
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

	public int dataCountMateWait(String condition, String keyword, String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT Count(*) FROM campMateApply, campMate, member "
					+ " WHERE campMate.camMateNum = campMateApply.camMateNum AND member.userId = campMateApply.userId "
					+ " AND camMateAppConfirm = '0' AND "
					+ " (campMate.hostId = ? AND campMateApply.userId = ? OR 'admin' = ?) ";

			if (condition.equals("userNickName")) {
				sql += " AND INSTR(LOWER(member.userNickName), LOWER(?)) >= 1  ";
			} else if (condition.equals("camMateAppContent")) {
				sql += " AND INSTR(LOWER(campMateApply.camMateAppContent), ?) >= 1 ";
			} else if (condition.equals("camMateAppGender")) {
				sql += " AND LOWER(campMateApply.camMateAppGender) = LOWER(?) ";
			} else {
				sql += " AND campMateApply.camMateAppAge = ? ";
			}

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setString(4, keyword);

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

	public List<MyPageDTO> readMateWait(int offset, int size, long num, String userId) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT campMateApply.camMateNum, campMateApply.userid, campMateApply.camMateAppContent,  ");
			sb.append(" campMateApply.camMateAppDate, campMateApply.camMateAppGender, campMateApply.camMateAppAge, ");
			sb.append(" campMateApply.camMateAppConfirm, member.userNickname ");
			sb.append(" FROM campMate, campMateApply, member ");
			sb.append(" WHERE campMate.camMateNum = campMateApply.camMateNum  AND ");
			sb.append(" member.userId = campMateApply.userId AND ");
			sb.append(" campMateApply.camMateAppConfirm = '0' AND ");
			sb.append(" (campMateApply.camMateNum= ? AND campMate.hostId = ? OR 'admin' = ? ) ");
			sb.append(" ORDER BY campMate.camMateNum DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setLong(1, num);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCamMateAppContent(rs.getString("camMateAppContent"));
				dto.setCamMateAppDate(rs.getString("camMateAppDate"));
				dto.setCamMateAppGender(rs.getString("camMateAppGender"));
				dto.setCamMateAppAge(rs.getInt("camMateAppAge"));
				dto.setCamMateAppConfirm(rs.getString("camMateAppConfirm"));
				dto.setUserNickName(rs.getString("userNickname"));

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

	public List<MyPageDTO> readMateWait(int offset, int size, long num, String condition, String keyword,
			String userId) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT campMateApply.camMateNum, campMateApply.userid, campMateApply.camMateAppContent,  ");
			sb.append(" campMateApply.camMateAppDate, campMateApply.camMateAppGender, campMateApply.camMateAppAge, ");
			sb.append(" campMateApply.camMateAppConfirm, member.userNickname ");
			sb.append(" FROM campMate, campMateApply, member ");
			sb.append(" WHERE campMate.camMateNum = campMateApply.camMateNum  AND ");
			sb.append(" member.userId = campMateApply.userid AND ");
			sb.append(" campMateApply.camMateAppConfirm = '0' AND ");
			sb.append(" (campMateApply.camMateNum= ? AND campMate.hostId = ? OR 'admin' = ? ) AND ");

			if (condition.equals("userNickName")) {
				sb.append(" INSTR(LOWER(member.userNickName), LOWER(?)) >= 1  ");
			} else if (condition.equals("camMateAppContent")) {
				sb.append(" INSTR(LOWER(" + condition + "), ?) >= 1 ");
			} else if (condition.equals("camMateAppGender")) {
				sb.append(" lower(campMateApply.camMateAppGender) = lower(?) ");
			} else {
				sb.append(" campMateApply.camMateAppAge = ? ");
			}

			sb.append(" ORDER BY campMate.camMateNum DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setLong(1, num);
			pstmt.setString(2, userId);
			pstmt.setString(3, userId);
			pstmt.setString(4, keyword);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setCamMateNum(rs.getLong("camMateNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setCamMateAppContent(rs.getString("camMateAppContent"));
				dto.setCamMateAppDate(rs.getString("camMateAppDate"));
				dto.setCamMateAppGender(rs.getString("camMateAppGender"));
				dto.setCamMateAppAge(rs.getInt("camMateAppAge"));
				dto.setCamMateAppConfirm(rs.getString("camMateAppConfirm"));
				dto.setUserNickName(rs.getString("userNickname"));

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

	public void confirmMateApply(String[] nn, String num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE campMateApply SET camMateAppConfirm = 1  WHERE camMateNum = ? AND "
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

	public List<MyPageDTO> listMember(int offset, int size) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					" SELECT member.userId, userName, userTel, TO_CHAR(TO_DATE(userBirth), 'YYYY-MM-DD') userBirth, userNickName, userEmail, userPoint, "
					+ "TO_CHAR(TO_DATE(userRegDate), 'YYYY-MM-DD') userRegDate, TO_CHAR(TO_DATE(userUpdateDate), 'YYYY-MM-DD') userUpdateDate, NVL(TO_CHAR(TO_DATE(restEndDate), 'YYYY-MM-DD'),' ') restEndDate ");
			sb.append(" FROM member ");
			sb.append(" LEFT OUTER JOIN restrictedMember on member.userId = restrictedMember.userId ");
			sb.append(" ORDER BY userRegDate DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));
				dto.setRestEndDate(rs.getString("restEndDate"));

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

	public List<MyPageDTO> listMember(int offset, int size, String condition, String keyword) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					" SELECT userId, userName, userTel, TO_CHAR(TO_DATE(userBirth), 'YYYY-MM-DD') userBirth, userNickName, userEmail, userPoint, TO_CHAR(TO_DATE(userRegDate), 'YYYY-MM-DD') userRegDate, TO_CHAR(TO_DATE(userUpdateDate), 'YYYY-MM-DD') userUpdateDate ");
			sb.append(" FROM member WHERE ");

			if (condition.equals("userRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" TO_CHAR(TO_DATE(userRegDate), 'YYYYMMDD') = ? ");
			} else if (condition.equals("userUpdateDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" TO_CHAR(TO_DATE(userUpdateDate), 'YYYYMMDD') = ? ");
			} else {
				sb.append(" INSTR(LOWER(" + condition + "), LOWER(?)) >= 1 ");
			}
			sb.append(" ORDER BY userRegDate DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));

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

	public int dataCountMember() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT NVL(COUNT(*), 0) FROM member ");

			pstmt = conn.prepareStatement(sb.toString());

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

	public int dataCountMember(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT NVL(COUNT(*), 0) FROM member WHERE ");

			if (condition.equals("userRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" TO_CHAR(TO_DATE(userRegDate), 'YYYYMMDD') = ? ");
			} else if (condition.equals("userUpdateDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" TO_CHAR(TO_DATE(userUpdateDate), 'YYYYMMDD') = ? ");
			} else {
				sb.append(" INSTR(LOWER(" + condition + "), LOWER(?)) >= 1 ");
			}

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);

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

	public void deleteUser(String[] userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM member " + " WHERE userId IN (";
			for (int i = 0; i < userId.length; i++) {
				sql += "?,";
			}

			sql = sql.substring(0, sql.length() - 1) + ")";

			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < userId.length; i++) {
				pstmt.setString(i + 1, userId[i]);
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
	
	
	
	public MyPageDTO confine(String userId) {
		MyPageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append(" SELECT restrictedmember.userId, restEndDate ");
			sb.append(" FROM restrictedmember, member ");
			sb.append(" WHERE restrictedmember.userId = member.userId AND restrictedmember.userId = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new MyPageDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setRestEndDate(rs.getString("restEndDate"));
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
	

	//테이블에 데이터가 있으면 해당 데이터 수정, 없으면 새로운 데이터 추가
	public void cofineMember(String userId, String content, String endDate) throws SQLException {
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		
		
		try {
			conn.setAutoCommit(false);
			
			sb.append(" MERGE INTO restrictedMember ");
			sb.append(" USING DUAL ON (userId = ?) ");
			sb.append(" WHEN MATCHED THEN ");
			sb.append(" UPDATE SET restContent = ?, reststartDate = SYSDATE, restEndDate = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') WHERE userId = ? ");
			sb.append(" WHEN NOT MATCHED THEN ");
			sb.append(" INSERT (userId, restContent, restStartDate, restEndDate) VALUES (?, ?, sysdate, TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS')) ");


			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, content);
			pstmt.setString(3, endDate);
			pstmt.setString(4, userId);
			pstmt.setString(5, userId);
			pstmt.setString(6, content);
			pstmt.setString(7, endDate);
			
			pstmt.executeQuery();
			pstmt.close();
			
			
			
			
			sb.setLength(0);
			sb.append(" INSERT INTO message ");
			sb.append(" (msgNum, msgWriterId, msgSenderId , msgContent, msgRegDate, msgWriEnabled , msgSenEnabled , msgread, msgreaddate) ");
			sb.append(" values (message_seq.nextval, 'admin', ?, '관리자 메시지입니다. " + endDate + "까지 정지됩니다.' , sysdate, 1, 1, 0, sysdate) ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);
			pstmt.executeQuery();
			conn.commit();
			conn.setAutoCommit(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
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
	
	
	public void deleteConfine(String[] userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM restrictedMember " + " WHERE userId IN (";
			for (int i = 0; i < userId.length; i++) {
				sql += "?,";
			}

			sql = sql.substring(0, sql.length() - 1) + ")";

			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < userId.length; i++) {
				pstmt.setString(i + 1, userId[i]);
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
	
	
	
	
	public int dataCountStatsSwitch(String startDate, String endDate) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT COUNT(*) ");
			sb.append(" FROM member ");
			sb.append(" WHERE TRUNC(userRegDate) >= ? AND TRUNC(userRegDate) <= ? ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);


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
	
	
	
	
	
	
	
	public List<MyPageDTO> statsSwitch(String startDate, String endDate, int offset, int size) {
		List<MyPageDTO> list = new ArrayList<MyPageDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(
					" SELECT member.userId, userName, userTel, TO_CHAR(TO_DATE(userBirth), 'YYYY-MM-DD') userBirth, userNickName, userEmail, userPoint, "
					+ "TO_CHAR(TO_DATE(userRegDate), 'YYYY-MM-DD') userRegDate, TO_CHAR(TO_DATE(userUpdateDate), 'YYYY-MM-DD') userUpdateDate ");
			sb.append(" FROM member ");
			sb.append(" WHERE TRUNC(userRegDate) >= TO_DATE(?, 'YYYY-MM-DD')  AND TRUNC(userRegDate) <= TO_DATE(?, 'YYYY-MM-DD') ");
			sb.append(" ORDER BY userRegDate DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyPageDTO dto = new MyPageDTO();

				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setUserPoint(rs.getLong("userPoint"));
				dto.setUserRegDate(rs.getString("userRegDate"));
				dto.setUserUpdateDate(rs.getString("userUpdateDate"));

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
