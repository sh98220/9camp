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
				sql = "INSERT INTO rentalPhoto(rentalPhotoNum, rentNum, rentPhotoName) VALUES "
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
	
	public int dataCount(String conditioin, String keyword) {
		int result = 0;
		
		return result;
	}

	public void updateRent(RentBoardDTO dto) throws SQLException {

	}

	public void deleteRent(long rentNum) throws SQLException {

	}


	public List<RentBoardDTO> listRentBoard(int offset, int size) {
		List<RentBoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			//sql = "SELECT S"
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
		return list;

	}
	public List<RentBoardDTO> listRentBoard(int offset, int size, String condition, String keyword) {
		List<RentBoardDTO> list = new ArrayList<>();

		return list;
	}

	public RentBoardDTO readRentBoard(long rentNum) {
		RentBoardDTO dto = null;

		return dto;
	}

	public RentBoardDTO preReadRentBoard(long rentNum) {
		RentBoardDTO dto = null;

		return dto;
	}

	public RentBoardDTO nextReadRentBoard(long rentNum) {
		RentBoardDTO dto = null;

		return dto;
	}

}
