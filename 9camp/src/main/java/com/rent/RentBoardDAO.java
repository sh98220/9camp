package com.rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RentBoardDAO {
	private Connection conn = DBConn.getConnection();

	public void insertRent(RentBoardDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		long seq = 0;

		try {
			sql = "SELECT rentNum_seq.NEXTVAL FROM dual";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			seq = 0;
			if(rs.next()) {
				seq = rs.getLong(1);
			}
			dto.setRentNum(seq);

			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;


			sql = "INSERT INTO rental(rentNum, hostId, rentSubject, rentObject, rentContent, rentFee, "
					+ " rentRegDate, rentHitCount, rentStartDate, rentEndDate) VALUES( ?, ?, ?, ?, ?, ?, SYSDATE, 0, ?, ?) ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, dto.getRentNum());
			pstmt.setString(2, dto.getHostId());
			pstmt.setString(3, dto.getRentSubject());
			pstmt.setString(4, dto.getRentObject());
			pstmt.setString(5, dto.getRentContent());
			pstmt.setLong(6, dto.getRentFee());
			pstmt.setString(7, dto.getRentStartDate());
			pstmt.setString(8, dto.getRentEndDate());

			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			if (dto.getRentPhotos() != null) {
				sql = "INSERT INTO rentPhoto(rentPhotoNum, rentNum, rentPhotoName) VALUES "
						+ " (rentPhotoNum_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);

				for (int i = 0; i < dto.getRentPhotos().length; i++) {
					pstmt.setLong(1, dto.getRentNum());
					pstmt.setString(2, dto.getRentPhotos()[i]);

					pstmt.executeUpdate();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
	}

	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM rental";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getInt(1);
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

		return result;
	}

	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;


		try {
			sql = "SELECT COUNT(*) FROM rental r  "
					+ "JOIN member m ON m.userId = r.hostId ";

			if(condition.equals("all")) {
				sql += "WHERE INSTR(rentSubject, ?) >= 1 OR INSTR(rentContent, ?) >= 1 ";
			} else if(condition.equals("reg_date")){
				sql += "WHERE TO_CHAR(rentRegDate,'YYYY-MM-DD') = ? ";
			} else if(condition.equals("object")) {
				sql += "WHERE INSTR(rentObject, ?) >= 1 ";
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
		}finally {
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

	public void updateRent(RentBoardDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql ="UPDATE rental SET rentSubject=?, rentContent=? WHERE rentNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getRentSubject());
			pstmt.setString(2, dto.getRentContent());
			pstmt.setLong(3, dto.getRentNum());

			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			if(dto.getRentPhotos()!= null) {
				sql = "INSERT INTO rentPhoto(rentNum, rentPhotoNum, rentPhotoName) VALUES (?, rentPhotoNum_seq, ?)";

				pstmt = conn.prepareStatement(sql);

				for (int i = 0; i < dto.getRentPhotos().length; i++) {
					pstmt.setLong(1, dto.getRentNum());
					pstmt.setString(2, dto.getRentPhotos()[i]);

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

	public void deleteRent(long rentNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM rentPhoto WHERE rentNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, rentNum);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			sql = "DELETE FROM rental WHERE rentNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, rentNum);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
	}


	public List<RentBoardDTO> listRentBoard(int offset, int size) {
		List<RentBoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		RentBoardDTO dto = null;

		try {
			sql = "SELECT rentNum, hostId, rentSubject, TO_CHAR(rentRegDate,'YYYY-MM-DD')rentRegDate , rentHitCount, userNickName "
					+ " FROM rental r "
					+ " JOIN member m ON r.hostId = m.userId "
					+ "ORDER BY rentNum DESC "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setRentSubject(rs.getString("rentSubject"));
				dto.setRentRegDate(rs.getString("rentRegDate"));
				dto.setRentHitCount(rs.getInt("rentHitCount"));
				dto.setUserNickName(rs.getString("userNickName"));

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
	public List<RentBoardDTO> listRentBoard(int offset, int size, String condition, String keyword) {
		List<RentBoardDTO> list = new ArrayList<>();
		RentBoardDTO dto = null;
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT rentNum, hostId, rentSubject, TO_CHAR(rentRegDate,'YYYY-MM-DD')rentRegDate , rentHitCount "
					+ " FROM rental r "
					+ " JOIN member m ON r.hostId = m.userId ";


			if(condition.equals("all")) {
				sql += "WHERE INSTR(rentSubject, ?) >= 1 OR INSTR(rentContent, ?) >= 1 ";
			} else if(condition.equals("reg_date")){
				sql += "WHERE TO_CHAR(rentRegDate,'YYYY-MM-DD') = ? ";
			} else if(condition.equals("object")) {
				sql += "WHERE INSTR(rentObject, ?) >= 1 ";
			} else {
				sql += "WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			sql += " ORDER BY rentNum DESC "
					+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

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
			} else if(condition.equals("object")) {
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
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setRentSubject(rs.getString("rentSubject"));
				dto.setRentHitCount(rs.getInt("rentHitCount"));
				dto.setRentRegDate(rs.getString("rentRegDate"));

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

	// 조회수 증가하기
	public void updateHitCount(long rentNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE rental SET rentHitCount=rentHitCount+1 WHERE rentNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, rentNum);

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

	public RentBoardDTO readRentBoard(long rentNum) {
		PreparedStatement pstmt = null;
		RentBoardDTO dto = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT rentNum, hostId, rentSubject, rentObject, rentContent, rentFee, userNickName, "
					+ "TO_CHAR(rentRegDate,'YYYY-MM-DD')rentRegDate, rentHitCount, "
					+ "TO_CHAR(rentStartDate,'YYYY-MM-DD')rentStartDate, TO_CHAR(rentEndDate,'YYYY-MM-DD')rentEndDate "
					+ "FROM rental r "
					+ "JOIN member m ON r.hostId = m.userId "
					+ "WHERE rentNum=? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, rentNum);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setHostId(rs.getString("hostId"));
				dto.setRentSubject(rs.getString("rentSubject"));
				dto.setRentObject(rs.getString("rentObject"));
				dto.setRentContent(rs.getString("rentContent"));
				dto.setRentFee(rs.getLong("rentFee"));
				dto.setRentRegDate(rs.getString("rentRegDate"));
				dto.setRentHitCount(rs.getInt("rentHitCount"));
				dto.setRentStartDate(rs.getString("rentStartDate"));
				dto.setRentEndDate(rs.getString("rentEndDate"));
				dto.setUserNickName(rs.getString("userNickName"));
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

	public RentBoardDTO preReadRentBoard(long rentNum, String condition, String keyword) {
		RentBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT rentNum, rentSubject FROM rental r "
						+ "JOIN member m ON m.userId = r.hostId "
						+ "WHERE (rentNum > ?) ";

				if(condition.equals("all")) {
					sql += " AND INSTR(rentSubject, ?) >= 1 OR INSTR(rentContent, ?) >= 1 ";
				} else if(condition.equals("reg_date")){
					sql += "AND TO_CHAR(rentRegDate,'YYYY-MM-DD') = ? ";
				} else if(condition.equals("object")) {
					sql += "AND INSTR(rentObject, ?) >= 1 ";
				} else {
					sql += "AND INSTR(" + condition + ", ?) >= 1 ";
				}

				sql += " ORDER BY rentNum DESC "
						+ "FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rentNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}

			} else {
				sql = "SELECT rentNum, rentSubject "
						+ " FROM rental r "
						+ " JOIN member m ON r.hostId = m.userId "
						+ " WHERE (rentNum > ?) "
						+ " ORDER BY rentNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rentNum);

			}

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setRentSubject(rs.getString("rentSubject"));
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

	public RentBoardDTO nextReadRentBoard(long rentNum, String condition, String keyword) {
		RentBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			if(keyword != null && keyword.length() != 0) {
				sql = "SELECT rentNum, rentSubject FROM rental r "
						+ "JOIN member m ON m.userId = r.hostId "
						+ "WHERE (rentNum < ?) ";


				sql += " ORDER BY rentNum DESC "
						+ "FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rentNum);
				pstmt.setString(2, keyword);

				if(condition.equals("all")) {
					pstmt.setString(3, keyword);
				}

			} else {
				sql = "SELECT rentNum, rentSubject "
						+ " FROM rental r "
						+ " JOIN member m ON r.hostId = m.userId "
						+ " WHERE (rentNum < ?) "
						+ " ORDER BY rentNum DESC "
						+ " FETCH FIRST 1 ROWS ONLY ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, rentNum);

			}

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setRentSubject(rs.getString("rentSubject"));
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

	public List<RentBoardDTO> listRentPhoto(long rentNum){
		List<RentBoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		RentBoardDTO dto = null;
		String sql;
		ResultSet rs = null;

		try {
			sql = "SELECT rentNum, rentPhotoNum, rentPhotoName "
					+ "FROM rentPhoto WHERE rentNum =?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, rentNum);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setRentPhotoNum(rs.getLong("rentPhotoNum"));
				dto.setRentPhotoName(rs.getString("rentPhotoName"));

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

	public RentBoardDTO readPhoto(long rentPhotoNum) {
		RentBoardDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;


		try {
			sql = "SELECT rentNum, rentPhotoNum, rentPhotoName "
					+ "FROM rentPhoto WHERE rentPhotoNum=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, rentPhotoNum);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new RentBoardDTO();

				dto.setRentNum(rs.getLong("rentNum"));
				dto.setRentPhotoNum(rs.getLong("rentPhotoNum"));
				dto.setRentPhotoName(rs.getString("rentPhotoName"));

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
	
	public void deletePhotoFile(long rentPhotoNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM rentPhoto WHERE rentPhotoNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, rentPhotoNum);
			
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
