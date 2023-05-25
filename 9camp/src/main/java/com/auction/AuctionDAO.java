package com.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			
			sql = "INSERT INTO AUCTION(auctionnum, auctionSubject, auctionsaleId, auctionObject, auctionStartAmount, auctionRegdate, auctionEnddate, auctionContent, auctionEnabled, auctionFinalamount) "
				      + "VALUES (?, ?, ?, ?, ?, SYSDATE, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), ?, 0, 0)";

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, dto.getAuctionNum());
				pstmt.setString(2, dto.getAuctionSubject());
				pstmt.setString(3, dto.getAuctionSaleId());
				pstmt.setString(4, dto.getAuctionObject());
				pstmt.setLong(5, dto.getAuctionStartamount());
				
				pstmt.setString(6, dto.getAuctionEnddate());

				pstmt.setString(7, dto.getAuctionContent());
			
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
	
	public long insertAuctionRecamount(AuctionDTO dto) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

	    long auctionRecamount = 0;
	    long userPoint = 0;
	    
		try {
	        conn.setAutoCommit(false);
			
	        // 데이터 입력하기 전 가장 큰 값을 가진 금액 조회
	        sql = "SELECT MAX(auctionRecamount) auctionRecamount "
	        		+ "FROM auctionrecord "
	        		+ "WHERE auctionnum = ?";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, dto.getAuctionNum());
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	        	auctionRecamount = rs.getLong("auctionRecamount");
	        }

	        rs.close();
	        pstmt.close();
	        rs = null;
	        pstmt = null;
	        
	        if(auctionRecamount >= dto.getAuctionRecamount()) {
	        	return 0L;
	        }
	        
	        // 이전 입찰자의 포인트를 입찰전으로 변경
			sql = "UPDATE member SET userpoint = userpoint + ? WHERE userid= "
	        		+ "(SELECT auctionuserID "
	        		+ "FROM auctionrecord "
					+ "WHERE auctionnum = ? "
					+ "ORDER BY auctionrecamount DESC "
					+ "FETCH FIRST 1 ROW ONLY) ";					
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, auctionRecamount);
			pstmt.setLong(2, dto.getAuctionNum());

			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt=null;
			
	        // 참가자 잔액 가져오기
	        sql = "SELECT userpoint FROM member WHERE userId = ? ";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, dto.getAuctionUserId());
	        rs = pstmt.executeQuery();
	        
	        if(rs.next()) {
	        	userPoint = rs.getLong("userpoint");
	        }
	        rs.close();
	        pstmt.close();
	        rs = null;
	        pstmt = null;

	        if(userPoint < dto.getAuctionRecamount()) {
	        	return 0L;
	        }
	        
			// 입력한 auctionRecamount 인서트
			sql = "INSERT INTO AUCTIONRECORD(auctionnum, auctionuserId, auctionRecamount, auctionRecdate, auctionConfirm) "
					+ " VALUES (?, ?, ?, SYSDATE, 0)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getAuctionNum());
			pstmt.setString(2, dto.getAuctionUserId());
			pstmt.setLong(3, dto.getAuctionRecamount());
			
			pstmt.executeUpdate();
			pstmt.close();

			
			sql = "UPDATE AUCTION SET auctionfinalamount = ? WHERE auctionnum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, dto.getAuctionRecamount());
			pstmt.setLong(2, dto.getAuctionNum());
			pstmt.executeUpdate();
			pstmt.close();
			
			
			sql = "UPDATE member SET userpoint=userpoint - ? WHERE userid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, dto.getAuctionRecamount());
			pstmt.setString(2, dto.getAuctionUserId());
			pstmt.executeUpdate();
			
		    conn.commit();
		    
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			conn.setAutoCommit(true);
		}
		return dto.getAuctionRecamount();
	}
	

	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM auction";
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
				sql = "SELECT NVL(COUNT(*), 0) FROM auction b "
						+ " JOIN member m ON b.auctionSaleId = m.userId ";
				if (condition.equals("all")) {
					sql += "  WHERE INSTR(auctionSubject, ?) >= 1 OR INSTR(auctioncontent, ?) >= 1 ";
				} else if (condition.equals("auctionRegdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sql += "  WHERE TO_CHAR(auctionRegdate, 'YYYYMMDD') = ? ";
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

		public List<AuctionDTO> listAuction(int offset, int size) {
			List<AuctionDTO> list = new ArrayList<AuctionDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT auctionnum, usernickname, username, auctionsubject, auctionObject,  ");
				sb.append("       TO_CHAR(auctionregdate, 'YYYY-MM-DD HH24:MI') auctionregdate, ");
				sb.append("       TO_CHAR(auctionEnddate, 'YYYY-MM-DD HH24:MI') auctionEnddate ");
				sb.append(" FROM auction b ");
				sb.append(" JOIN member m ON b.auctionSaleId = m.userId ");
				sb.append(" ORDER BY auctionnum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);

				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					AuctionDTO dto = new AuctionDTO();

					dto.setAuctionNum(rs.getLong("Auctionnum"));
					dto.setUserName(rs.getString("userName"));
					dto.setUserNickName(rs.getString("userNickName"));
					dto.setAuctionSubject(rs.getString("auctionsubject"));
					dto.setAuctionObject(rs.getString("auctionObject"));
					dto.setAuctionRegdate(rs.getString("auctionRegdate"));
					dto.setAuctionEnddate(rs.getString("auctionEnddate"));

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

		public List<AuctionDTO> listAuction(int offset, int size, String condition, String keyword) {
			List<AuctionDTO> list = new ArrayList<AuctionDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT auctionnum, usernickname, username, auctionsubject, auctionObject,  ");
				sb.append("       TO_CHAR(auctionregdate, 'YYYY-MM-DD HH24:MI') auctionregdate, ");
				sb.append("       TO_CHAR(auctionEnddate, 'YYYY-MM-DD HH24:MI') auctionEnddate ");
				sb.append(" FROM auction b ");
				sb.append(" JOIN member m ON b.auctionSaleId = m.userId ");
				if (condition.equals("all")) {
					sb.append(" WHERE INSTR(auctionsubject, ?) >= 1 OR INSTR(auctioncontent, ?) >= 1 ");
				} else if (condition.equals("auctionRegdate")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append(" WHERE TO_CHAR(auctionRegdate, 'YYYYMMDD') = ?");
				} else {
					sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY auctionNum DESC ");
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
					AuctionDTO dto = new AuctionDTO();

					dto.setAuctionNum(rs.getLong("Auctionnum"));
					dto.setUserName(rs.getString("userName"));
					dto.setUserNickName(rs.getString("userNickName"));
					dto.setAuctionSubject(rs.getString("auctionsubject"));
					dto.setAuctionObject(rs.getString("auctionObject"));
					dto.setAuctionRegdate(rs.getString("auctionRegdate"));
					dto.setAuctionEnddate(rs.getString("auctionEnddate"));


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
		
		public AuctionDTO readAuction(long num) {
			AuctionDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT b.auctionnum, b.auctionSaleId, userNickName, userName, auctionSubject, auctionContent, "
						+ " TO_CHAR(auctionRegdate, 'YYYY-MM-DD HH24:MI') auctionregdate, NVL(auctionRecAmount, 0) auctionRecAmount,"
						+ " TO_CHAR(auctionEnddate, 'YYYY-MM-DD HH24:MI') auctionEnddate, auctionObject, auctionStartamount, auctionFinalamount "
						+ " FROM auction b "
						+ " JOIN member m ON b.auctionSaleId=m.userId "
						+ " LEFT OUTER JOIN ("
						+ "    SELECT auctionnum, NVL(MAX(auctionRecAmount), 0) auctionRecAmount FROM auctionRecord   GROUP BY auctionnum"
						+"  ) m ON b.auctionnum = m.auctionnum"
						+ " WHERE b.auctionnum = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					dto = new AuctionDTO();
					
					long startAmount = rs.getLong("auctionStartamount");
				    String formattedStartAmount = String.format("%,d", startAmount);
				    dto.setAuctionStartamountFormatted(formattedStartAmount);
					
					dto.setAuctionNum(rs.getLong("auctionNum"));
					dto.setAuctionSaleId(rs.getString("auctionSaleId"));
					dto.setUserName(rs.getString("userName"));
					dto.setUserNickName(rs.getString("userNickName"));
					dto.setAuctionSubject(rs.getString("auctionSubject"));
					dto.setAuctionContent(rs.getString("auctionContent"));
					dto.setAuctionRegdate(rs.getString("auctionRegdate"));
					dto.setAuctionEnddate(rs.getString("auctionEnddate"));
					dto.setAuctionObject(rs.getString("auctionObject"));
					dto.setAuctionStartamount(rs.getLong("auctionStartamount"));
					dto.setAuctionFinalamount(rs.getLong("auctionFinalamount"));
					dto.setAuctionRecamount(rs.getLong("auctionRecAmount"));
					
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

		public List<AuctionDTO> listPhotoFile(long num) {
			List<AuctionDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT auctionphotonum, auctionnum, auctionphotoname FROM auctionphoto WHERE auctionnum = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, num);
				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					AuctionDTO dto = new AuctionDTO();

					dto.setAuctionPhotonum(rs.getLong("auctionphotonum"));
					dto.setAuctionNum(rs.getLong("auctionNum"));
					dto.setAuctionPhotoname(rs.getString("auctionphotoname"));
					
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
		
		public void deleteAuction(long num) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				sql = "DELETE FROM AUCTION WHERE auctionnum=?";
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
		
		public void deleteAuctionFile(String mode, long num) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				if (mode.equals("all")) {
					sql = "DELETE FROM AUCTIONPHOTO WHERE auctionNum = ?";
				} else {
					sql = "DELETE FROM AUCTIONPHOTO WHERE auctionPhotonum = ?";
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
		
		public AuctionDTO readAuctionFile(long auctionPhotonum) {
			AuctionDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT auctionPhotonum, auctionnum, auctionphotoname FROM auctionphoto WHERE auctionPhotonum = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, auctionPhotonum);
				
				rs = pstmt.executeQuery();

				if (rs.next()) {
					dto = new AuctionDTO();

					dto.setAuctionPhotonum(rs.getLong("auctionPhotonum"));
					dto.setAuctionNum(rs.getLong("auctionNum"));
					dto.setAuctionPhotoname(rs.getString("auctionPhotoname"));
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
		
		public void updateAuction(AuctionDTO dto) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				sql = "UPDATE auction SET auctionsubject=?, auctioncontent=?, auctionobject=?, auctionEnddate=? WHERE auctionnum=?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, dto.getAuctionSubject());
				pstmt.setString(2, dto.getAuctionContent());
				pstmt.setString(3, dto.getAuctionObject());
				pstmt.setString(4, dto.getAuctionEnddate());
				pstmt.setLong(5, dto.getAuctionNum());

				pstmt.executeUpdate();
				
				pstmt.close();
				pstmt = null;

				if (dto.getImageFiles() != null) {
					sql = "INSERT INTO auctionphoto(auctionphotonum, auctionnum, auctionphotoname) VALUES "
							+ " (AUCTIONPHOTO_seq.NEXTVAL, ?, ?)";
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
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}
		}
		public long finish(AuctionDTO dto) throws SQLException{
			PreparedStatement pstmt = null;
			String sql;
		    
			try {
				sql = "UPDATE member SET userpoint=userpoint + ? WHERE userid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, dto.getAuctionRecamount());
				pstmt.setString(2, dto.getUserId());
				pstmt.executeUpdate();
			    
		
			} finally {
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				
			}
			return dto.getAuctionRecamount();
		}
		

		}
