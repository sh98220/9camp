package com.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	        sql = "INSERT INTO POINTRECORD(pointNum, userid, pointmode, pointdate, pointamount, balance2) "
	            + "VALUES(POINTRECORD_SEQ.NEXTVAL, ?, '입금', SYSDATE, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getUserId());
	        pstmt.setLong(2, dto.getAmount());
	        pstmt.setLong(3, updatedBalance);
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
	
	public long withdrawPoint(PointDTO dto) throws SQLException {
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

	        updatedBalance = currentBalance - dto.getAmount();
	        sql = "UPDATE member SET userpoint=? WHERE userid=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, updatedBalance);
	        pstmt.setString(2, dto.getUserId());
	        pstmt.executeUpdate();

	        pstmt.close();
	        pstmt = null;
	        
	        // 포인트 기록 추가
	        sql = "INSERT INTO POINTRECORD(pointNum, userid, pointmode, pointdate, pointamount, balance2) "
	            + "VALUES(POINTRECORD_SEQ.NEXTVAL, ?, '출금', SYSDATE, ?, ?)";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getUserId());
	        pstmt.setLong(2, dto.getAmount());
	        pstmt.setLong(3, updatedBalance);
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
	
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM pointrecord WHERE userId = ? OR 'admin' = ?";
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
	
	// 검색에서의 데이터 개수
	public int dataCount(String condition, String keyword, String userId) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM pointrecord b "
				    + "JOIN member m ON b.userId = m.userId "
				    + "WHERE (m.userId = ? OR 'admin' = ?) ";
				if (condition.equals("pointmode")) {
				    sql += "AND INSTR(pointmode, ?) >= 1";
				} else if (condition.equals("pointdate")) {
				    keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				    sql += "AND TO_CHAR(pointdate, 'YYYYMMDD') = ? ";
				} else if (condition.equals("userId")) {
				    sql += "AND INSTR(b.userId, ?) >= 1 ";
				}
			
	        pstmt = conn.prepareStatement(sql);

	        
	        pstmt.setString(1, userId);
	        pstmt.setString(2, userId);
	        pstmt.setString(3, keyword);

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
	                e.printStackTrace();
	            }
	        }

	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return result;
	}

	public List<PointDTO> listPoint(int offset, int size, String userId) {
		List<PointDTO> list = new ArrayList<PointDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT pointnum, pointmode, pointamount, balance2, ");
			sb.append("       TO_CHAR(pointdate, 'YYYY-MM-DD HH24:MI') pointdate, b.userId ");
			sb.append(" FROM pointrecord b ");
			sb.append(" JOIN member m ON b.userId = m.userId ");
			sb.append(" WHERE m.userId = ? OR 'admin' = ? ");
			sb.append(" ORDER BY b.pointnum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setInt(3, offset);
			pstmt.setInt(4, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PointDTO dto = new PointDTO();

				dto.setPointNum(rs.getLong("pointnum"));
				dto.setPointmode(rs.getString("pointmode"));
				dto.setPointAmount(rs.getLong("pointamount"));
				dto.setPointDate(rs.getString("pointdate"));
				dto.setBalance2(rs.getLong("balance2"));
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

	public List<PointDTO> listPoint(int offset, int size, String condition, String keyword, String userId) {
		List<PointDTO> list = new ArrayList<PointDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT pointnum, pointmode, pointamount, ");
			sb.append("       TO_CHAR(pointdate, 'YYYY-MM-DD HH24:MI') pointdate, b.userId ");
			sb.append("FROM pointrecord b ");
			sb.append("JOIN member m ON b.userid = m.userId ");
			sb.append("WHERE (m.userId = ? OR 'admin' = ?) ");
			if (condition.equals("pointmode")) {
			    sb.append("AND INSTR(pointmode, ?) >= 1 ");
			} else if (condition.equals("pointdate")) {
			    keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
			    sb.append("AND TO_CHAR(pointdate, 'YYYYMMDD') = ? ");
			} else if (condition.equals("userId")) {
			    sb.append("AND INSTR(b.userId, ?) >= 1 ");
			}
			
			sb.append(" ORDER BY pointNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());
			
				pstmt.setString(1, userId);
				pstmt.setString(2, userId);
				pstmt.setString(3, keyword);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PointDTO dto = new PointDTO();

				dto.setPointNum(rs.getLong("Pointnum"));
				dto.setPointmode(rs.getString("pointmode"));
				dto.setPointAmount(rs.getLong("pointamount"));
				dto.setPointDate(rs.getString("pointdate"));
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
}
