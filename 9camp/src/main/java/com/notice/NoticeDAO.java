package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertNotice(NoticeDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		int seq;
		
		try {
			sql = "SELECT notice_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setNoticeNum(seq);
	
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO notice(noticeNum, userId, noticeSubject, noticeContent ,noticeHitcount, noticeRegdate) "
					+ " VALUES (?, ?, ?, ?, 0, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, dto.getNoticeNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getNoticeSubject());
			pstmt.setString(4, dto.getNoticeContent());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO noticephoto(noticePhotoNum, noticeNum, noticePhotoName) VALUES "
						+ " (noticePhoto_SEQ.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getNoticeNum());
					pstmt.setString(2, dto.getImageFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice";
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
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM notice n "
					+ " JOIN member m ON n.userId = m.userId ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(noticeSubject, ?) >= 1 OR INSTR(noticeContent, ?) >= 1 ";
			} else if (condition.equals("noticeRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(noticeRegDate, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
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
	
	public List<NoticeDTO> listNotice(int offset, int size) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT noticeNum, noticeSubject, noticeHitcount, n.userId, ");
			sb.append("       TO_CHAR(noticeRegDate, 'YYYY-MM-DD') noticeRegDate ");
			sb.append(" FROM notice n ");
			sb.append(" JOIN member m ON n.userId = m.userId ");
			sb.append(" ORDER BY noticeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNoticeSubject(rs.getString("noticeSubject"));
				dto.setNoticeHitCount(rs.getInt("noticeHitCount"));
				dto.setNoticeRegDate(rs.getString("noticeRegDate"));
				dto.setUserId(rs.getString("userId"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	public List<NoticeDTO> listNotice(int offset, int size, String condition, String keyword) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT noticeNum, username, noticeSubject, noticeHitCount, ");
			sb.append("      TO_CHAR(noticeRegDate, 'YYYY-MM-DD') noticeRegDate ");
			sb.append(" FROM Notice n ");
			sb.append(" JOIN member m ON n.userId = m.userId ");
			if (condition.equals("all")) {
				sb.append(" WHERE INSTR(noticeSubject, ?) >= 1 OR INSTR(noticeContent, ?) >= 1 ");
			} else if (condition.equals("noticeRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(noticeRegDate, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY noticeNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setNoticeSubject(rs.getString("noticeSubject"));
				dto.setNoticeHitCount(rs.getInt("noticeHitCount"));
				dto.setNoticeRegDate(rs.getString("noticeRegDate"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}
	
	public NoticeDTO readNotice(long num) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT n.noticeNum, n.userId, userName, noticeSubject, noticeContent, "
					+ " noticeRegDate, noticeHitCount "
					+ " FROM notice n "
					+ " JOIN member m ON n.userId=m.userId "
					+ " WHERE n.noticeNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setNoticeSubject(rs.getString("noticeSubject"));
				dto.setNoticeContent(rs.getString("noticeContent"));
				dto.setNoticeHitCount(rs.getInt("noticeHitCount"));
				dto.setNoticeRegDate(rs.getString("noticeRegDate"));
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
	
	public List<NoticeDTO> listPhotoFile(long num) {
		List<NoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT noticePhotoNum, noticeNum, noticePhotoName FROM noticePhoto WHERE noticeNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setNoticePhotoNum(rs.getInt("noticePhotoNum"));
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNoticePhotoName(rs.getString("noticePhotoName"));
				
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
	
	public void deleteNotice(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM notice WHERE noticeNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
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
	
	public void deleteNoticeFile(String mode, long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM NOTICEPHOTO WHERE noticeNum = ?";
			} else {
				sql = "DELETE FROM NOTICEPHOTO WHERE noticePhotoNum = ?";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

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
	
	public void updateNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET noticeSubject=?, noticeContent=? WHERE noticeNum=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getNoticeSubject());
			pstmt.setString(2, dto.getNoticeContent());
			pstmt.setLong(3, dto.getNoticeNum());

			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO noticephoto(noticePhotoNum, noticeNum, noticePhotoName) VALUES "
						+ " (NOTICEPHOTO_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getNoticeNum());
					pstmt.setString(2, dto.getImageFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}

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
	
	public NoticeDTO readNoticeFile(int noticePhotoNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT noticePhotoNum, noticeNum, noticePhotoName FROM noticePhoto WHERE noticePhotoNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, noticePhotoNum);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();

				dto.setNoticePhotoNum(rs.getInt("noticePhotoNum"));
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNoticePhotoName(rs.getString("noticePhotoName"));
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
	
	public NoticeDTO preReadNotice(long noticeNum, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT noticeNum, noticeSubject ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member m ON n.userId = m.userId ");
				sb.append(" WHERE noticeNum > ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(noticeSubject, ?) >= 1 OR INSTR(noticeContent, ?) >= 1 ) ");
				} else if (condition.equals("noticeRegDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND TO_CHAR(noticeRegDate, 'YYYYMMDD') = ? ");
				} else {
					sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY noticeNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, noticeNum);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT noticeNum, noticeSubject FROM notice ");
				sb.append(" WHERE noticeNum > ? ");
				sb.append(" ORDER BY noticeNum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, noticeNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNoticeSubject(rs.getString("noticeSubject"));
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
	
	public NoticeDTO nextReadNotice(long noticeNum, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT noticeNum, noticeSubject ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member m ON n.userId = m.userId ");
				sb.append(" WHERE noticeNum < ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(noticeSubject, ?) >= 1 OR INSTR(noticeContent, ?) >= 1 ) ");
				} else if (condition.equals("noticeRegDate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND TO_CHAR(noticeRegDate, 'YYYYMMDD') = ? ");
				} else {
					sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY noticeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, noticeNum);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT noticeNum, noticeSubject FROM notice ");
				sb.append(" WHERE noticeNum < ? ");
				sb.append(" ORDER BY noticeNum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, noticeNum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNoticeNum(rs.getInt("noticeNum"));
				dto.setNoticeSubject(rs.getString("noticeSubject"));
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
	
	public void deletePhotoFile(String mode, long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM sphotoFile WHERE num = ?";
			} else {
				sql = "DELETE FROM sphotoFile WHERE fileNum = ?";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

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
	
	public void updateHitCount(long noticeNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET noticeHitCount=noticeHitCount+1 WHERE noticeNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, noticeNum);
			
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
