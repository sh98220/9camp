package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	public void insertQna(QnaDTO dto, String mode) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		long seq = 0;
		
		try {
			sql = "SELECT qnaNum_seq.NEXTVAL FROM dual";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
		
			if(rs.next()) {
				seq = rs.getLong(1);
			}
			
			dto.setQnaNum(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			if(mode.equals("write")) {
				dto.setGroupNum(seq);
				dto.setOrderNum(0);
			} else if(mode.equals("reply")) {
				updateOrderNo(dto.getGroupNum(), dto.getOrderNum());
			}
			
			sql = "INSERT INTO qna (qnaNum, userId, qnaSubject, qnaContent, qnaRegDate, qnaHitCount, qnaGroupNum, qnaOrderNum) "
					+ "VALUES(?, ?, ?, ?, SYSDATE, 0, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getQnaNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getQnaSubject());
			pstmt.setString(4, dto.getQnaContent());
			pstmt.setLong(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getOrderNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if(dto.getQnasaveFiles() != null) {
				sql = "INSERT INTO qnaFile(qnaNum, qnaFileNum, qnasaveFileName, qnaoriginalFileName) VALUES (?, qnaFileNum_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for(int i = 0; i < dto.getQnasaveFiles().length; i++) {
					pstmt.setLong(1, dto.getQnaNum());
					pstmt.setString(2, dto.getQnasaveFiles()[i]);
					pstmt.setString(2, dto.getQnaoriginalFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}
			

			if (dto.getQnaPhotos() != null) {
				sql = "INSERT INTO qnaPhoto(qnaPhotoNum, qnaNum, qnaPhotoName) VALUES "
						+ " (qnaPhotoNum_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				for (int i = 0; i < dto.getQnaPhotos().length; i++) {
					pstmt.setLong(1, dto.getQnaPhotoNum());
					pstmt.setString(2, dto.getQnaPhotos()[i]);

					pstmt.executeUpdate();
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		

	}
	
	public void updateOrderNo(long qnaGroupNum, int qnaOrderNum) throws SQLException {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE qna SET qnaOrderNum = qnaOrderNum+1 WHERE qnaGroupNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1,qnaGroupNum);
			
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
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna";
			
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
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna q  "
					+ "JOIN member m ON q.userId = r.hostId ";

			if(condition.equals("all")) {
				sql += "WHERE INSTR(qnaSubject, ?) >= 1 OR INSTR(qnaContent, ?) >= 1 ";
			} else if(condition.equals("qnaRegDate")){
				sql += "WHERE TO_CHAR(qnaRegDate,'YYYY-MM-DD') = ? ";
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

	public void updateQna(QnaDTO dto) throws SQLException {

	}

	public void deleteQna(long qnaNum) throws SQLException {

	}
	
	public List<QnaDTO> listQna(int offset, int size){
		List<QnaDTO> list = new ArrayList<>();
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT qnaNum, a.userId, qnaSubject, qnaContent, TO_CHAR(qnaRegDate, 'YYYY-MM-DD')qnaRegDate, qnaGroupNum, qnaOrderNum, qnaHitCount "
					+ "FROM qna q JOIN member m ON m.userId = q.userId "
					+ "ORDER BY qnaGroupNum DESC, qnaOrderNum ASC  "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new QnaDTO();
				
				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
				dto.setQnaContent(rs.getString("qnaContent"));
				dto.setQnaRegDate(rs.getString("qnaRegDate"));
				dto.setGroupNum(rs.getLong("qnaGroupNum"));
				dto.setOrderNum(rs.getInt(size));
				dto.setQnaHitCount(rs.getInt("qnaHitCount"));
				
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

	public List<QnaDTO> listQna(int offset, int size, String condition, String keyword){
		List<QnaDTO> list = new ArrayList<>();
		QnaDTO dto = null;
		
		return list;
	}
	
	public QnaDTO readQna(long qnaNum) {
		QnaDTO dto = null;
		
		return dto;
	}

	public QnaDTO preReadQna(long qnaNum) {
		QnaDTO dto = null;
		
		return dto;
	}
	
	public QnaDTO nextReadQna(long qnaNum) {
		QnaDTO dto = null;
		
		return dto;
	}
	
	public void updateHitCount(long qnaNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE qna SET qnaHitCount=qnaHitCount+1 WHERE qnaNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, qnaNum);
			
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
	
	public List<QnaDTO> listQnaPhoto(long qnaNum) {
		List<QnaDTO> list = new ArrayList<>();
		
		return list;
	}
	
	public QnaDTO readPhoto(long qnaNum) {
		QnaDTO dto = null;
		
		return dto;
	}
	
	
	public void deletePhoto(long qnaPhotoNum) {
		
	}

	public List<QnaDTO> listQnaFile(long qnaNum) {
		List<QnaDTO> list = new ArrayList<>();
		
		return list;
	}
	
	public QnaDTO readFile(long qnaNum) {
		QnaDTO dto = null;
		
		return dto;
	}
	
	
	public void deleteFile(long qnaFileNum) {
		
	}
	
}
