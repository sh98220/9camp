package com.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class PointDAO {
	private Connection conn = DBConn.getConnection();

	
	public long updatePoint(PointDTO dto) throws SQLException {
	    PreparedStatement pstmt = null;
	    String sql;
	    long currentBalance = 0;
	    long updatedBalance = 0;
	    ResultSet rs = null;

	    try {
	        conn.setAutoCommit(false);

	        // 현재 잔액 조회
	        sql = "SELECT userpoint FROM member WHERE userid=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getUserId());
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            currentBalance = rs.getLong("userpoint");
	        }

	        pstmt.close();
	        pstmt = null;

	        // 잔액 합산하여 업데이트
	        updatedBalance = currentBalance + dto.getAmount();
	        sql = "UPDATE member SET userpoint=? WHERE userid=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, updatedBalance);
	        pstmt.setString(2, dto.getUserId());
	        pstmt.executeUpdate();

	        pstmt.close();
	        pstmt = null;
	        
	        // 포인트 기록 추가
	        sql = "INSERT INTO POINTRECORD(pointNum, userid, pointmode, pointdate, pointamount) "
	            + "VALUES(POINTRECORD_SEQ.NEXTVAL, ?, '입금', SYSDATE, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getUserId());
	        pstmt.setLong(2, dto.getAmount());
	        pstmt.executeUpdate();

	        conn.commit();
	    } catch (SQLException e) {
	        conn.rollback();
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	            }
	        }
	        // 트랜잭션 종료 후 자동 커밋으로 변경
	        conn.setAutoCommit(true);
	    } 
	    return updatedBalance;
	}
		
}
