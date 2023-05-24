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
				
				dto.setDepth(dto.getDepth()+1);
				dto.setOrderNum(dto.getOrderNum()+1);
			}

			sql = "INSERT INTO qna (qnaNum, userId, qnaSubject, qnaContent, qnaRegDate, qnaHitCount, qnaGroupNum, qnaOrderNum, qnadepth, qnaparent ) "
					+ "VALUES(?, ?, ?, ?, SYSDATE, 0, ?, ?, ?, ? ) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, dto.getQnaNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getQnaSubject());
			pstmt.setString(4, dto.getQnaContent());
			pstmt.setLong(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getOrderNum());
			pstmt.setInt(7, dto.getDepth());
			pstmt.setLong(8, dto.getParent());

			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			if(dto.getQnasaveFiles() != null) {
				sql = "INSERT INTO qnaFile (qnaNum, qnaFileNum, qnasaveFileName, qnaoriginalFileName) VALUES (?, qnaFileNum_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				for(int i = 0; i < dto.getQnasaveFiles().length; i++) {
					pstmt.setLong(1, dto.getQnaNum());
					pstmt.setString(2, dto.getQnasaveFiles()[i]);
					pstmt.setString(3, dto.getQnaoriginalFiles()[i]);

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
			sql = "UPDATE qna SET qnaOrderNum = qnaOrderNum+1 WHERE qnaGroupNum = ? AND qnaOrderNum > ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1,qnaGroupNum);
			pstmt.setInt(2, qnaOrderNum);

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
					+ "JOIN member m ON q.userId = m.userId ";

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
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE qna SET qnaSubject=?, qnaContent=? WHERE qnaNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQnaSubject());
			pstmt.setString(2, dto.getQnaContent());
			pstmt.setLong(3, dto.getQnaNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if(dto.getQnasaveFiles() != null) {
				sql = "INSERT INTO qnaFile (qnaNum, qnaFileNum, qnasaveFileName, qnaoriginalFileName) VALUES (?, qnaFileNum_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				for(int i = 0; i < dto.getQnasaveFiles().length; i++) {
					pstmt.setLong(1, dto.getQnaNum());
					pstmt.setString(2, dto.getQnasaveFiles()[i]);
					pstmt.setString(3, dto.getQnaoriginalFiles()[i]);

					pstmt.executeUpdate();
				}
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
	}

	public void deleteQna(long qnaNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM qnaFile WHERE qnaNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaNum);

			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			sql = "DELETE FROM qna WHERE qnaNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaNum);

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

	public List<QnaDTO> listQna(int offset, int size){
		List<QnaDTO> list = new ArrayList<>();
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT qnaNum, q.userId, userNickName, qnaSubject, TO_CHAR(qnaRegDate, 'YYYY-MM-DD')qnaRegDate, qnaHitCount, qnadepth, qnaOrderNum, qnaGroupNum "
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
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
				dto.setQnaRegDate(rs.getString("qnaRegDate"));
				dto.setQnaHitCount(rs.getInt("qnaHitCount"));
				dto.setDepth(rs.getInt("qnadepth"));
				dto.setOrderNum(rs.getInt("qnaOrderNum"));
				dto.setGroupNum(rs.getLong("qnaGroupNum"));

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
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT qnaNum, q.userId, userNickName, qnaSubject, TO_CHAR(qnaRegDate, 'YYYY-MM-DD')qnaRegDate, qnaHitCount, qnadepth, qnaOrderNum, qnaGroupNum "
					+ "FROM qna q JOIN member m ON m.userId = q.userId ";


			if(condition.equals("all")) {
				sql += " WHERE INSTR(qnaSubject, ?) >= 1 OR INSTR(qnaContent, ?) >= 1 ";
			} else if(condition.equals("reg_date")){
				sql += " WHERE TO_CHAR(qnaRegDate,'YYYY-MM-DD') = ? ";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			sql += " ORDER BY qnaGroupNum DESC, qnaOrderNum ASC  "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";

			pstmt = conn.prepareStatement(sql);


			if(condition.equals("all")) {
				pstmt.setString(1,keyword);
				pstmt.setString(2,keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else if(condition.equals("reg_date")){
				pstmt.setString(1,keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			} else {
				pstmt.setString(1,keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new QnaDTO();

				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
				dto.setQnaRegDate(rs.getString("qnaRegDate"));
				dto.setQnaHitCount(rs.getInt("qnaHitCount"));
				dto.setDepth(rs.getInt("qnadepth"));
				dto.setOrderNum(rs.getInt("qnaOrderNum"));
				dto.setGroupNum(rs.getLong("qnaGroupNum"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}


		return list;
	}

	public QnaDTO readQna(long qnaNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT qnaNum, q.userId, userNickName, qnaSubject, qnaContent, qnaHitCount, qnaRegDate, qnadepth, qnaOrderNum, qnaGroupNum "
					+ "FROM qna q JOIN member m ON m.userId=q.userId WHERE qnaNum = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaNum);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new QnaDTO();

				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
				dto.setQnaContent(rs.getString("qnaContent"));
				dto.setQnaHitCount(rs.getInt("qnaHitCount"));
				dto.setQnaRegDate(rs.getString("qnaRegDate"));
				dto.setDepth(rs.getInt("qnadepth"));
				dto.setOrderNum(rs.getInt("qnaOrderNum"));
				dto.setGroupNum(rs.getLong("qnaGroupNum"));

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

		return dto;
	}

	public QnaDTO preReadQna(long qnaNum, String keyword, String condition) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT qnaNum, qnaSubject "
						+ "FORM qna q "
						+ "JOIN member m ON m.userId = q.userId "
						+ "WHERE ( qnaNum > ? ) ";

				if(condition.equals("all")) {
					sql += " WHERE INSTR(qnaSubject, ?) >= 1 OR INSTR(qnaContent, ?) >= 1 ";
				} else if(condition.equals("reg_date")){
					sql += " WHERE TO_CHAR(qnaRegDate,'YYYY-MM-DD') = ? ";
				} else {
					sql += " WHERE INSTR(" + condition + ", ?) >= 1 ";
				}

				sql += " ORDER BY qnaGroupNum DESC, qnaOrderNum ASC  "
						+ " FETCH FIRST 1 ROWS ONLY";

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, qnaNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}

			} else {
				sql = " SELECT qnaNum, qnaSubject FROM qna q JOIN member m ON m.userId = q.userId WHERE ( qnaNum > ? ) "
						+ " ORDER BY qnaGroupNum DESC, qnaOrderNum ASC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, qnaNum);
			}

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new QnaDTO();

				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
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

	public QnaDTO nextReadQna(long qnaNum, String keyword, String condition) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT qnaNum, qnaSubject "
						+ "FORM qna q "
						+ "JOIN member m ON m.userId = q.userId "
						+ "WHERE ( qnaNum < ? ) ";

				if(condition.equals("all")) {
					sql += " WHERE INSTR(qnaSubject, ?) >= 1 OR INSTR(qnaContent, ?) >= 1 ";
				} else if(condition.equals("reg_date")){
					sql += " WHERE TO_CHAR(qnaRegDate,'YYYY-MM-DD') = ? ";
				} else {
					sql += " WHERE INSTR(" + condition + ", ?) >= 1 ";
				}

				sql += " ORDER BY qnaGroupNum DESC, qnaOrderNum ASC  "
						+ " FETCH FIRST 1 ROWS ONLY";

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, qnaNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}

			} else {
				sql = " SELECT qnaNum, qnaSubject FROM qna q JOIN member m ON m.userId = q.userId WHERE ( qnaNum < ? ) "
						+ " ORDER BY qnaGroupNum DESC, qnaOrderNum ASC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, qnaNum);
			}

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new QnaDTO();

				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setQnaSubject(rs.getString("qnaSubject"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
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


	public List<QnaDTO> listQnaFile(long qnaNum) {
		List<QnaDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT qnaFileNum, qnaNum, qnaoriginalFileName, qnasaveFileName FROM qnaFile WHERE qnaNum=? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaNum);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setQnaFileNum(rs.getLong("qnaFileNum"));
				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setQnaoriginalFilename(rs.getString("qnaoriginalFileName"));
				dto.setQnasaveFilename(rs.getString("qnasaveFileName"));

				list.add(dto);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
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

	public QnaDTO readFile(long qnaFileNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT qnaFileNum, qnaNum, qnaoriginalFileName, qnasaveFileName FROM qnaFile WHERE qnaFileNum=? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaFileNum);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new QnaDTO();

				dto.setQnaFileNum(rs.getLong("qnaFileNum"));
				dto.setQnaNum(rs.getLong("qnaNum"));
				dto.setQnaoriginalFilename(rs.getString("qnaoriginalFileName"));
				dto.setQnasaveFilename(rs.getString("qnasaveFileName"));

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


	public void deleteFile(long qnaFileNum) {

		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "DELETE FROM qnaFile WHERE qnaFileNum = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, qnaFileNum);

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

}
