package com.reviews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewsDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertReviews(ReviewsDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO CAMPREVIEWS(camRevnum, camInfonum, userId, camRevsubject, camRevcontent, camRevhitcount, camRevregdate) "
					+ " VALUES (campreviews_seq.NEXTVAL, 1, ?, ?, ?, 0, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getCamRevsubject());
			pstmt.setString(3, dto.getCamRevcontent());
			
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
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campreviews";
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
				sql = "SELECT NVL(COUNT(*), 0) FROM campreviews b "
						+ " JOIN member1 m ON b.userId = m.userId ";
				if (condition.equals("all")) {
					sql += "  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
				} else if (condition.equals("camRevregdate")) {
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

		public List<ReviewsDTO> listReviews(int offset, int size) {
			List<ReviewsDTO> list = new ArrayList<ReviewsDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT camRevnum, username, camRevsubject, camRevhitcount, ");
				sb.append("       TO_CHAR(camRevregdate, 'YYYY-MM-DD') camRevregdate ");
				sb.append(" FROM campreviews b ");
				sb.append(" JOIN member m ON b.userId = m.userId ");
				sb.append(" ORDER BY camRevnum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);

				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					ReviewsDTO dto = new ReviewsDTO();

					dto.setCamRevnum(rs.getInt("camRevnum"));
					dto.setUserName(rs.getString("userName"));
					dto.setCamRevsubject(rs.getString("camRevsubject"));
					dto.setCamRevhitcount(rs.getInt("camRevhitcount"));
					dto.setCamRevregdate(rs.getString("camRevregdate"));

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

		public List<ReviewsDTO> listReviews(int offset, int size, String condition, String keyword) {
			List<ReviewsDTO> list = new ArrayList<ReviewsDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT camRevnum, username, camRevsubject, camRevhitcount, ");
				sb.append("      TO_CHAR(camRevregdate, 'YYYY-MM-DD') reg_date ");
				sb.append(" FROM campreviews b ");
				sb.append(" JOIN member m ON b.userId = m.userId ");
				if (condition.equals("all")) {
					sb.append(" WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append(" WHERE TO_CHAR(reg_date, 'YYYYMMDD') = ?");
				} else {
					sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY camRevnum DESC ");
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
					ReviewsDTO dto = new ReviewsDTO();

					dto.setCamRevnum(rs.getInt("camRevnum"));
					dto.setUserName(rs.getString("userName"));
					dto.setCamRevsubject(rs.getString("camRevsubject"));
					dto.setCamRevhitcount(rs.getInt("camRevhitcount"));
					dto.setCamRevregdate(rs.getString("camRevregdate"));

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
		
		public ReviewsDTO readReviews(long num) {
			ReviewsDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT camRevnum, b.userId, userName, camRevsubject, camRevcontent, "
						+ " camRevregdate, camRevhitCount "
						+ " FROM campreviews b "
						+ " JOIN member m ON b.userId=m.userId "
						+ " WHERE camRevnum = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					dto = new ReviewsDTO();
					
					dto.setCamRevnum(rs.getInt("camRevnum"));
					dto.setUserId(rs.getString("userId"));
					dto.setUserName(rs.getString("userName"));
					dto.setCamRevsubject(rs.getString("camRevsubject"));
					dto.setCamRevcontent(rs.getString("camRevcontent"));
					dto.setCamRevhitcount(rs.getInt("camRevhitCount"));
					dto.setCamRevregdate(rs.getString("camRevregdate"));
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


}
