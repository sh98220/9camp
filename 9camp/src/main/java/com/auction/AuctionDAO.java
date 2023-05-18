package com.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class AuctionDAO {
	private Connection conn = DBConn.getConnection();
		
	public void insertAuction(AuctionDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		int seq;
		
		try {		
			sql = "SELECT auction_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setAuctionNum(seq);
	
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO AUCTION(auctionnum, auctionsaleId, auctionObject, auctionStartAmount, auctionRegdate, auctionEnddate, auctionContent, auctionEnabled) "
					+ " VALUES (?, ?, ?, ?, SYSDATE, ?, ?, 0)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getAuctionNum());
			pstmt.setString(2, dto.getAuctionSaleId());
			pstmt.setString(3, dto.getAuctionObject());
			pstmt.setLong(4, dto.getAuctionStartamount());
			pstmt.setString(5, dto.getAuctionEnddate());
			pstmt.setString(6, dto.getAuctionContent());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO AUCTIONPHOTO(auctionphotonum, auctionnum, auctionphotoname) VALUES "
						+ " (AUCTIONPHOTO_SEQ.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getAuctionNum());
					pstmt.setString(2, dto.getImageFiles()[i]);
					
					pstmt.executeUpdate();
				}
			}
			
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
}
