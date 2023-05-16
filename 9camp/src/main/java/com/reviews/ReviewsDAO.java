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
		ResultSet rs = null;
		int seq;
		
		try {		
			sql = "SELECT campreviews_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if (rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setCamRevnum(seq);
	
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO CAMPREVIEWS(camRevnum, camInfonum, userId, camRevsubject, camRevcontent, camRevhitcount, camRevregdate) "
					+ " VALUES (?, 1, ?, ?, ?, 0, SYSDATE)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getCamRevnum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getCamRevsubject());
			pstmt.setString(4, dto.getCamRevcontent());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO CAMPREVIEWSPHOTO(CAMREVPHOTONUM, camRevnum, CAMREVPHOTONAME) VALUES "
						+ " (CAMPREVIEWSPHOTO_SEQ.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getCamRevnum());
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
						+ " JOIN member m ON b.userId = m.userId ";
				if (condition.equals("all")) {
					sql += "  WHERE INSTR(camRevsubject, ?) >= 1 OR INSTR(camRevcontent, ?) >= 1 ";
				} else if (condition.equals("camRevregdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sql += "  WHERE TO_CHAR(camRevregdate, 'YYYYMMDD') = ? ";
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
				sb.append("      TO_CHAR(camRevregdate, 'YYYY-MM-DD') camRevregdate ");
				sb.append(" FROM campreviews b ");
				sb.append(" JOIN member m ON b.userId = m.userId ");
				if (condition.equals("all")) {
					sb.append(" WHERE INSTR(camRevsubject, ?) >= 1 OR INSTR(camRevcontent, ?) >= 1 ");
				} else if (condition.equals("camRevregdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append(" WHERE TO_CHAR(camRevregdate, 'YYYYMMDD') = ?");
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

		public List<ReviewsDTO> listPhotoFile(long num) {
			List<ReviewsDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT camRevphotonum, camRevnum, camRevphotoname FROM campreviewsphoto WHERE camRevnum = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					ReviewsDTO dto = new ReviewsDTO();

					dto.setCamRevphotonum(rs.getInt("camRevphotonum"));
					dto.setCamRevnum(rs.getInt("camRevnum"));
					dto.setCamRevphotoname(rs.getString("camRevphotoname"));
					
					list.add(dto);
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

			return list;
		}
		
		public void deleteReviews(long num) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				sql = "DELETE FROM CAMPREVIEWS WHERE camRevnum=?";
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
		
		public void deleteReviewsFile(String mode, long num) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				if (mode.equals("all")) {
					sql = "DELETE FROM CAMPREVIEWSPHOTO WHERE camRevnum = ?";
				} else {
					sql = "DELETE FROM CAMPREVIEWSPHOTO WHERE camRevphotonum = ?";
				}
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
					} catch (SQLException e2) {
					}
				}
			}
		}
		
		public void updateReviews(ReviewsDTO dto) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				sql = "UPDATE campReviews SET camRevsubject=?, camRevcontent=? WHERE camRevnum=?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getCamRevsubject());
				pstmt.setString(2, dto.getCamRevcontent());
				pstmt.setLong(3, dto.getCamRevnum());

				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;

				if (dto.getImageFiles() != null) {
					sql = "INSERT INTO campReviewsphoto(camRevphotonum, camRevnum, camRevphotoname) VALUES "
							+ " (CAMPREVIEWSPHOTO_seq.NEXTVAL, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					
					for (int i = 0; i < dto.getImageFiles().length; i++) {
						pstmt.setLong(1, dto.getCamRevnum());
						pstmt.setString(2, dto.getImageFiles()[i]);
						
						pstmt.executeUpdate();
					}
				}

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
			
			public ReviewsDTO readReviewsFile(int camRevphotonum) {
				ReviewsDTO dto = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql;

				try {
					sql = "SELECT camRevphotonum, camRevnum, camRevphotoname FROM campreviewsphoto WHERE camRevphotonum = ?";
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setLong(1, camRevphotonum);
					
					rs = pstmt.executeQuery();

					if (rs.next()) {
						dto = new ReviewsDTO();

						dto.setCamRevphotonum(rs.getInt("camRevphotonum"));
						dto.setCamRevnum(rs.getInt("camRevnum"));
						dto.setCamRevphotoname(rs.getString("camRevphotoname"));
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

			public ReviewsDTO preReadReviews(long camRevnum, String condition, String keyword) {
				ReviewsDTO dto = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StringBuilder sb = new StringBuilder();

				try {
					if (keyword != null && keyword.length() != 0) {
						sb.append(" SELECT camRevnum, camRevsubject ");
						sb.append(" FROM campreviews b ");
						sb.append(" JOIN member m ON b.userId = m.userId ");
						sb.append(" WHERE camRevnum > ? ");
						if (condition.equals("all")) {
							sb.append("   AND ( INSTR(camRevsubject, ?) >= 1 OR INSTR(camRevcontent, ?) >= 1 ) ");
						} else if (condition.equals("camRevregdate")) {
							keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
							sb.append("   AND TO_CHAR(camRevregdate, 'YYYYMMDD') = ? ");
						} else {
							sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
						}
						sb.append(" ORDER BY camRevnum ASC ");
						sb.append(" FETCH FIRST 1 ROWS ONLY ");

						pstmt = conn.prepareStatement(sb.toString());
						
						pstmt.setLong(1, camRevnum);
						pstmt.setString(2, keyword);
						if (condition.equals("all")) {
							pstmt.setString(3, keyword);
						}
					} else {
						sb.append(" SELECT camRevnum, camRevsubject FROM campreviews ");
						sb.append(" WHERE camRevnum > ? ");
						sb.append(" ORDER BY camRevnum ASC ");
						sb.append(" FETCH FIRST 1 ROWS ONLY ");

						pstmt = conn.prepareStatement(sb.toString());
						
						pstmt.setLong(1, camRevnum);
					}

					rs = pstmt.executeQuery();

					if (rs.next()) {
						dto = new ReviewsDTO();
						
						dto.setCamRevnum(rs.getInt("camRevnum"));
						dto.setCamRevsubject(rs.getString("camRevsubject"));
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

			// 다음글
			public ReviewsDTO nextReadReviews(long camRevnum, String condition, String keyword) {
				ReviewsDTO dto = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StringBuilder sb = new StringBuilder();

				try {
					if (keyword != null && keyword.length() != 0) {
						sb.append(" SELECT camRevnum, camRevsubject ");
						sb.append(" FROM campreviews b ");
						sb.append(" JOIN member m ON b.userId = m.userId ");
						sb.append(" WHERE camRevnum < ? ");
						if (condition.equals("all")) {
							sb.append("   AND ( INSTR(camRevsubject, ?) >= 1 OR INSTR(camRevcontent, ?) >= 1 ) ");
						} else if (condition.equals("camRevregdate")) {
							keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
							sb.append("   AND TO_CHAR(camRevregdate, 'YYYYMMDD') = ? ");
						} else {
							sb.append("   AND INSTR(" + condition + ", ?) >= 1 ");
						}
						sb.append(" ORDER BY camRevnum DESC ");
						sb.append(" FETCH FIRST 1 ROWS ONLY ");

						pstmt = conn.prepareStatement(sb.toString());
						
						pstmt.setLong(1, camRevnum);
						pstmt.setString(2, keyword);
						if (condition.equals("all")) {
							pstmt.setString(3, keyword);
						}
					} else {
						sb.append(" SELECT camRevnum, camRevsubject FROM campreviews ");
						sb.append(" WHERE camRevnum < ? ");
						sb.append(" ORDER BY camRevnum DESC ");
						sb.append(" FETCH FIRST 1 ROWS ONLY ");

						pstmt = conn.prepareStatement(sb.toString());
						
						pstmt.setLong(1, camRevnum);
					}

					rs = pstmt.executeQuery();

					if (rs.next()) {
						dto = new ReviewsDTO();
						
						dto.setCamRevnum(rs.getInt("camRevnum"));
						dto.setCamRevsubject(rs.getString("camRevsubject"));
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
			

			public void deletePhotoFile(String mode, long num) throws SQLException {
				PreparedStatement pstmt = null;
				String sql;

				try {
					if (mode.equals("all")) {
						sql = "DELETE FROM sphotoFile WHERE num = ?";
					} else {
						sql = "DELETE FROM sphotoFile WHERE fileNum = ?";
					}
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
						} catch (SQLException e2) {
						}
					}
				}
			}
			
			public void updateHitCount(long camRevnum) throws SQLException {
				PreparedStatement pstmt = null;
				String sql;

				try {
					sql = "UPDATE campreviews SET camRevhitCount=camRevhitCount+1 WHERE camRevnum=?";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setLong(1, camRevnum);
					
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
}
