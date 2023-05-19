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
			sql = "SELECT NVL(COUNT(*), 0) FROM campchat c "
					+ " JOIN member m ON c.userId = m.userId ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(camChatSubject, ?) >= 1 OR INSTR(camChatContent, ?) >= 1 ";
			} else if (condition.equals("camChatregdate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(camChatRegDate, 'YYYYMMDD') = ? ";
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
			sb.append(" SELECT camChatNum, usernickname, username, camChatSubject, camChatHitCount, ");
			sb.append("      TO_CHAR(camChatRegDate, 'YYYY-MM-DD') camChatRegDate ");
			sb.append(" FROM campchat c ");
			sb.append(" JOIN member m ON c.userId = m.userId ");
			if (condition.equals("all")) {
				sb.append(" WHERE INSTR(camChatSubject, ?) >= 1 OR INSTR(camChatContent, ?) >= 1 ");
			} else if (condition.equals("camChatRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(camChatRegDate, 'YYYYMMDD') = ?");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY camChatNum DESC ");
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
			sql = "SELECT b.camChatnum, b.userId, userName, camChatsubject, camChatcontent, "
					+ " camChatregdate, camChathitCount, NVL(freeboardLikeCount, 0) freeboardLikeCount "
					+ " FROM campchat b "
					+ " JOIN member m ON b.userId=m.userId "
					+ " LEFT OUTER JOIN ("
					+ " 	 SELECT camChatnum, COUNT(*) freeboardLikeCount FROM campchatlike"
					+ "		 GROUP BY camChatnum"
					+ " ) bc ON b.camChatnum = bc.camChatnum"
					+ " WHERE b.camChatnum = ? ";
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
				
				dto.setFreeboardLikeCount(rs.getInt("freeboardLikeCount"));
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

	// 다음 글
	public FreeBoardDTO nextReadFreeBoard(long camChatnum, String condition, String keyword) {
		FreeBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT camChatnum, camChatsubject ");
				sb.append(" FROM campChat b ");
				sb.append(" JOIN member m ON b.userId = m.userId ");
				sb.append(" WHERE camChatnum < ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(camChatsubject, ?) >= 1 OR INSTR(camChatcontent, ?) >= 1 ) ");
				} else if (condition.equals("camChatregdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND TO_CHAR(camChatregdate, 'YYYYMMDD') = ? ");
				} else {
					sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY camChatnum DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, camChatnum);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT camChatnum, camChatsubject FROM campChat ");
				sb.append(" WHERE camChatnum < ? ");
				sb.append(" ORDER BY camChatnum DESC ");
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

	// 게시물의 공감 추가
	public void insertFreeBoardLike(long camChatnum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO campchatlike(camChatNum, userId) VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatnum);
			pstmt.setString(2, userId);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	
	// 게시글 공감 삭제
	public void deleteFreeBoardLike(long camChatnum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM campchatlike WHERE camChatNum = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatnum);
			pstmt.setString(2, userId);
			
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
	
	// 게시물의 공감 개수
	public int countFreeBoardLike(long camChatnum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campChatlike WHERE camChatnum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatnum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
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
		
		return result;
	}
	
	// 로그인 유저의 게시글 공감 유무
	public boolean isUserFreeBoardLike(long camChatnum, String userId) {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT camChatnum, userId FROM campchatLike WHERE camChatnum = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatnum);
			pstmt.setString(2, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return result;
	}
	
	// 게시물에 댓글 추가
	public void insertReply(FreeBoardReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO campChatReply(camChatRepNum, camChatNum, userId, camChatRepContent, camChatRepRegDate) "
					+ " VALUES (CAMPCHATREPLY_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getCamChatNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getCamChatRepContent());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
	}
	
	// 게시물의 댓글 개수
	public int dataCountReply(long camChatNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campChatReply WHERE camChatNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
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
		
		return result;
	}
	
	// 게시물 댓글 리스트
	public List<FreeBoardReplyDTO> listReply(long camChatnum, int offset, int size) {
		List<FreeBoardReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT r.camChatRepNum, r.userId, userName, camChatnum, camChatRepContent, r.camChatRepRegDate ");
			sb.append(" FROM campChatReply r ");
			sb.append(" JOIN member m ON r.userId = m.userId ");
			sb.append(" WHERE camChatnum = ?");
			sb.append(" ORDER BY r.camChatRepNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, camChatnum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FreeBoardReplyDTO dto = new FreeBoardReplyDTO();
				
				dto.setCamChatRepNum(rs.getLong("camChatRepNum"));
				dto.setCamChatNum(rs.getLong("camChatNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setCamChatRepContent(rs.getString("camChatRepContent"));
				dto.setCamChatRepRegDate(rs.getString("camChatRepRegDate"));
				
				list.add(dto);
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
		
		return list;
	}
	
	// 게시물 댓글 보기
	public FreeBoardReplyDTO readReply(long camChatRepnum) {
		FreeBoardReplyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT camChatRepNum, camChatNum, r.userId, userName, camChatRepContent ,r.camChatRepRegDate "
					+ " FROM campChatReply r JOIN member m ON r.userId=m.userId  "
					+ " WHERE camChatRepNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatRepnum);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new FreeBoardReplyDTO();
				
				dto.setCamChatRepNum(rs.getLong("camChatRepNum"));
				dto.setCamChatNum(rs.getLong("camChatNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setCamChatRepContent(rs.getString("camChatRepContent"));
				dto.setCamChatRepRegDate(rs.getString("camChatRepRegdate"));
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
	
	
	// 게시물의 댓글 삭제
	public void deleteReply(long camChatRepNum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		if(! userId.equals("admin")) {
			FreeBoardReplyDTO dto = readReply(camChatRepNum);
			if(dto == null || (! userId.equals(dto.getUserId()))) {
				return;
			}
		}
		
		try {
			sql = "DELETE FROM campChatReply "
					+ " WHERE camChatRepNum = ?  ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camChatRepNum);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		
	}
}
