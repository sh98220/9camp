package com.freeboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class FreeBoardDAO {
	private Connection conn = DBConn.getConnection();
	
	// 데이터 추가
	public void insertFreeBoard(FreeBoardDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO CampChat(camChatNum, userId, camChatSubject, "
					+ " camChatContent, camChatHitCount, camChatRegDate) "
					+ " VALUES (campChat_seq.NEXTVAL, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getcamChatSubject());
			pstmt.setString(3, dto.getcamChatContent());

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
	
	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campchat";
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

	// 검색에서의 데이터 개수
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campchat b "
					+ " JOIN member1 m ON b.userId = m.userId ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("camChatregdate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(reg_date, 'YYYYMMDD') = ? ";
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
	
	// 게시물 리스트
	public List<FreeBoardDTO> listFreeBoard(int offset, int size) {
		List<FreeBoardDTO> list = new ArrayList<FreeBoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT camChatnum, username, camChatsubject, camChatcontent, camChathitcount, ");
			sb.append("       TO_CHAR(camChatregdate, 'YYYY-MM-DD') camChatregdate ");
			sb.append(" FROM campchat b ");
			sb.append(" JOIN member m ON b.userId = m.userId ");
			sb.append(" ORDER BY camChatnum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FreeBoardDTO dto = new FreeBoardDTO();

				dto.setcamChatNum(rs.getInt("camChatNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setcamChatSubject(rs.getString("camChatsubject"));
				dto.setcamChatContent(rs.getString("camChatcontent"));
				dto.setcamChatHitCount(rs.getInt("camChatHitCount"));
				dto.setcamChatRegDate(rs.getString("camChatRegDate"));

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
	
	public List<FreeBoardDTO> listFreeBoard(int offset, int size, String condition, String keyword) {
		List<FreeBoardDTO> list = new ArrayList<FreeBoardDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT camChatnum, username, camChatsubject, camChathitcount, ");
			sb.append("      TO_CHAR(camChatregdate, 'YYYY-MM-DD') reg_date ");
			sb.append(" FROM campchat b ");
			sb.append(" JOIN member m ON b.userId = m.userId ");
			if (condition.equals("all")) {
				sb.append(" WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(reg_date, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY camChatnum DESC ");
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
				FreeBoardDTO dto = new FreeBoardDTO();

				dto.setcamChatNum(rs.getInt("camChatNum"));
				dto.setUserName(rs.getString("userName"));
				dto.setcamChatSubject(rs.getString("camChatSubject"));
				dto.setcamChatHitCount(rs.getInt("camChatHitCount"));
				dto.setcamChatRegDate(rs.getString("camChatRegDate"));

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

	// 조회수 증가하기 
	public void updateHitCount(long camChatnum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE campChat SET camChathitCount=camChathitCount+1 WHERE camChatnum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatnum);
			
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

	// 해당 게시물 보기
	public FreeBoardDTO readBoard(long num) {
		FreeBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT camChatnum, b.userId, userName, camChatsubject, camChatcontent, "
					+ " camChatregdate, camChathitCount "
					+ " FROM campchat b "
					+ " JOIN member m ON b.userId=m.userId "
					+ " WHERE camChatnum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FreeBoardDTO();
				
				dto.setcamChatNum(rs.getInt("camChatNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setcamChatSubject(rs.getString("camChatsubject"));
				dto.setcamChatContent(rs.getString("camChatcontent"));
				dto.setcamChatHitCount(rs.getInt("camChatHitCount"));
				dto.setcamChatRegDate(rs.getString("camChatregdate"));
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
	
	// 게시물 수정
	public void updateFreeBoard(FreeBoardDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE campChat SET camChatsubject=?, camChatcontent=? WHERE camChatnum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getcamChatSubject());
			pstmt.setString(2, dto.getcamChatContent());
			pstmt.setLong(3, dto.getcamChatNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

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
	
	// 이전 글
	public FreeBoardDTO preReadFreeBoard(long camChatnum, String condition, String keyword) {
		FreeBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT camChatnum, camChatsubject ");
				sb.append(" FROM campChat b ");
				sb.append(" JOIN member m ON b.userId = m.userId ");
				sb.append(" WHERE camChatnum > ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(camChatsubject, ?) >= 1 OR INSTR(camChatcontent, ?) >= 1 ) ");
				} else if (condition.equals("camChatregdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND TO_CHAR(camChatregdate, 'YYYYMMDD') = ? ");
				} else {
					sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY camChatnum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, camChatnum);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT camChatnum, camChatsubject FROM campChat ");
				sb.append(" WHERE camChatnum > ? ");
				sb.append(" ORDER BY camChatnum ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, camChatnum);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new FreeBoardDTO();
				
				dto.setcamChatNum(rs.getInt("camChatnum"));
				dto.setcamChatSubject(rs.getString("camChatsubject"));
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


	// 게시물 삭제
	public void deleteFreeBoard(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM campChat WHERE camChatnum=?";
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

	
	
	
	
}
