package com.campInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;


public class CampInfoDAO {
	private Connection conn = DBConn.getConnection();
	
	public void InsertCampInfo(CampInfoDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		int seq;
		
		try {
			sql = "SELECT campInfo_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setCamInfoNum(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO campInfo(camInfoNum, userId, camInfoSubject, camInfoContent, camInfoAddr, camInfoHitCount, camInfoRegDate, camThemaName, camKeyword ,"
					+ " camPhoneNum, camNomalWeekDayPrice, camNomalWeekEndPrice, camPeakWeekDayPrice, camPeakWeekEndPrice, camFacility ) "
					+ " VALUES (?, 'admin',  ?, ?, ?, 0, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, dto.getCamInfoNum());
			pstmt.setString(2, dto.getCamInfoSubject());
			pstmt.setString(3, dto.getCamInfoContent());
			pstmt.setString(4, dto.getCamInfoAddr());
			pstmt.setString(5, dto.getCamThemaName());
			pstmt.setString(6, dto.getCamKeyWord());
			pstmt.setString(7, dto.getCamPhoneNum());
			pstmt.setString(8, dto.getCamNomalWeekDayPrice());
			pstmt.setString(9, dto.getCamNomalWeekEndPrice());
			pstmt.setString(10, dto.getCamPeakWeekDayPrice());
			pstmt.setString(11, dto.getCamPeakWeekEndPrice());
			pstmt.setString(12, dto.getCamFacility());
			
			pstmt.executeUpdate();	
			
			pstmt.close();
			pstmt = null;
			
			if(dto.getImageFiles() != null) {
				sql = "INSERT INTO campPhoto(camInfoPhotoNum, camInfoNum, camInfoPhotoName)"
						+ "VALUES (campPhoto_SEQ.NEXTVAL, ? , ?)";
				pstmt = conn.prepareStatement(sql);
				
				for(int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getCamInfoNum());
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
	
	// 데이터 개수
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
			
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campInfo";
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
	
	// 검색 데이터 개수
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campInfo ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(camInfosubject, ?) >= 1 OR INSTR(camInfoContent, ?) >= 1 ";
			} else if (condition.equals("camInfoRegDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(camInfoRegDate, 'YYYYMMDD') = ? ";
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
	
	// 캠핑장 리스트
	public List<CampInfoDTO> listCampInfo(int offset, int size) {
		List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT camInfoNum, camInfoSubject, camInfoContent, camInfoAddr, ");
			sb.append("		camInfoHitCount, TO_CHAR(camInfoRegDate, 'YYYY-MM-DD') camInfoRegDate, camThemaName ");
			sb.append(" FROM campInfo ");
			sb.append(" ORDER BY camInfoNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, offset);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			// 키워드 명 추가 필요
			
			while(rs.next()) {
				CampInfoDTO dto = new CampInfoDTO();
				
				dto.setCamInfoNum(rs.getInt("camInfoNum"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setCamInfoContent(rs.getString("camInfoContent"));
				dto.setCamInfoAddr(rs.getString("camInfoAddr"));
				dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
				dto.setCamInfoRegDate(rs.getString("camInfoRegDate"));
				dto.setCamThemaName(rs.getString("camThemaName"));
				
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
	
	// 캠핑장 검색 리스트
	public List<CampInfoDTO> listCampInfo(int offset, int size, String condition, String keyword) {
		List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT camInfoNum, camInfoSubject, camInfoContent, camInfoAddr, ");
			sb.append("		camInfoHitCount, TO_CHAR(camInfoRegDate, 'YYYY-MM-DD') camInfoRegDate, camThemaName ");
			sb.append(" FROM campInfo ");
			if(condition.equals("all")) {
				sb.append("WHERE INSTR(camInfoSubject, ?) >= 1 OR INSTR(camInfoContent, ?) >= 1 ");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY camInfoNum DESC ");
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
			
			while(rs.next()) {
				CampInfoDTO dto = new CampInfoDTO();
				
				dto.setCamInfoNum(rs.getInt("camInfoNum"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setCamInfoContent(rs.getString("camInfoContent"));
				dto.setCamInfoAddr(rs.getString("camInfoAddr"));
				dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
				dto.setCamInfoRegDate(rs.getString("camInfoRegDate"));
				dto.setCamThemaName(rs.getString("camThemaName"));
				
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
	
	// 글 쓸 내용 읽어오기 
	public CampInfoDTO readCampInfo(long num) {
		CampInfoDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT c.camInfoNum, camInfoSubject, camInfoContent, camInfoAddr, "
					+ " camInfoHitCount, TO_CHAR(camInfoRegDate, 'YYYY-MM-DD') camInfoRegDate, camThemaName, NVL(wishCount, 0) wishCount, camKeyWord, "
					+ "  camPhoneNum, camNomalWeekDayPrice, camNomalWeekEndPrice, camPeakWeekDayPrice, camPeakWeekEndPrice, camFacility "
					+ " FROM campInfo c "
					+ " LEFT OUTER JOIN("
					+ " SELECT camInfoNum, COUNT(*) wishCount"
					+ " FROM campwish "
					+ " GROUP BY camInfoNum ) "
					+ " wc ON c.camInfoNum = wc.camInfoNum "
					+ " WHERE c.camInfoNum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new CampInfoDTO();
				
				dto.setCamInfoNum(rs.getLong("camInfoNum"));
				dto.setCamInfoSubject(rs.getString("camInfoSubject"));
				dto.setCamInfoContent(rs.getString("camInfoContent"));
				dto.setCamInfoAddr(rs.getString("camInfoAddr"));
				dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
				dto.setCamInfoRegDate(rs.getString("camInfoRegDate"));
				dto.setCamThemaName(rs.getString("camThemaName"));
				dto.setWishCount(rs.getInt("wishCount"));	
				dto.setCamKeyWord(rs.getString("camKeyWord"));
				dto.setCamPhoneNum(rs.getString("camPhoneNum"));
				dto.setCamNomalWeekDayPrice(rs.getString("camNomalWeekDayPrice"));
				dto.setCamNomalWeekEndPrice(rs.getString("camNomalWeekEndPrice"));
				dto.setCamPeakWeekDayPrice(rs.getString("camPeakWeekDayPrice"));
				dto.setCamPeakWeekEndPrice(rs.getString("camPeakWeekEndPrice"));
				dto.setCamFacility(rs.getString("camFacility"));
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
	
	// 캠핑 전체 키워드 얻어오기
	public List<CampInfoDTO> listAllKeyword() {
		List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT keyWordName ");
			sb.append(" FROM keyWordName ");
			
			pstmt = conn.prepareStatement(sb.toString());
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CampInfoDTO dto = new CampInfoDTO();
				
				dto.setKeyWordName(rs.getString("keyWordName"));
				
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

	// 캠핑 키워드 리스트로 얻어오기
	public List<CampInfoDTO> listKeyword(long num) {
		List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT camInfoNum, keyWordName ");
			sb.append(" FROM campInfoKeyWord ");
			sb.append(" WHERE camInfoNum = ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CampInfoDTO dto = new CampInfoDTO();
				
				dto.setKeyWordName(rs.getString("keyWordName"));
				
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

	
	// 캠핑리스트 삭제
	public void deleteCampInfo(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM campInfo WHERE camInfoNum=?";
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
	
	// 게시글 수정
	public void updateCampInfo(CampInfoDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE campInfo SET camInfoSubject=?, camInfoContent=?, camInfoAddr = ?, camThemaName = ?, camKeyWord = ? camPhoneNum = ? , camNomalWeekDayPrice = ?, camNomalWeekEndPrice = ?, camPeakWeekDayPrice = ? , camPeakWeekEndPrice = ?, camFacility = ? WHERE camInfoNum=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getCamInfoSubject());
			pstmt.setString(2, dto.getCamInfoContent());
			pstmt.setString(3, dto.getCamInfoAddr());
			pstmt.setString(4, dto.getCamThemaName());
			pstmt.setString(5, dto.getCamKeyWord());
			pstmt.setLong(6, dto.getCamInfoNum());
			pstmt.setString(7, dto.getCamPhoneNum());
			pstmt.setString(8, dto.getCamNomalWeekDayPrice());
			pstmt.setString(9, dto.getCamNomalWeekEndPrice());
			pstmt.setString(10, dto.getCamPeakWeekDayPrice());
			pstmt.setString(11, dto.getCamPeakWeekEndPrice());
			pstmt.setString(12, dto.getCamFacility());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			

			if (dto.getImageFiles() != null) {
				sql = "INSERT INTO campPhoto(camInfoPhotoNum, camInfoNum, camInfoPhotoName) VALUES "
						+ " (CAMPPHOTO_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getImageFiles().length; i++) {
					pstmt.setLong(1, dto.getCamInfoNum());
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
	
	// 조회수 증가
	public void updateHitCount(long camInfoNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE campInfo SET camInfoHitCount=camInfoHitCount+1 WHERE camInfoNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camInfoNum);
			
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
	
	// 찜 담기
	public void insertCampWish(long camInfoNum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO campWish(camInfoNum, userId) VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camInfoNum);
			pstmt.setString(2, userId);
			
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
	
	// 캠핑장 리스트 찜 삭제
	public void deleteCampWish(long camInfoNum, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM campWish WHERE camInfoNum = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camInfoNum);
			pstmt.setString(2, userId);
			
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
	
	// 캠핑리스트 찜 개수
	public int countCampWish(long camInfoNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM campWish WHERE camInfoNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camInfoNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	// 로그인 유저의 게시글 공감 유무
	public boolean isUserCampWish(long camInfoNum, String userId) {
		boolean result = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT camInfoNum, userId FROM campWish WHERE camInfoNum = ? AND userId = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, camInfoNum);
			pstmt.setString(2, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = true;
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
		
		return result;
	}

	// 사진 파일 리스트
	public List<CampInfoDTO> listPhotoFile(long num) {
		List<CampInfoDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM campPhoto WHERE camInfoNum = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CampInfoDTO dto = new CampInfoDTO();

				dto.setCamInfoPhotoNum(rs.getInt("camInfoPhotoNum"));
				dto.setCamInfoNum(rs.getInt("camInfoNum"));
				dto.setCamInfoPhotoName(rs.getString("camInfoPhotoName"));
				
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
	
	// 사진 파일 삭제
	public void deleteCampInfoFile(String mode, long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (mode.equals("all")) {
				sql = "DELETE FROM CAMPPHOTO WHERE camInfoNum = ?";
			} else {
				sql = "DELETE FROM CAMPPHOTO WHERE camInfoPhotonum = ?";
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
		
		// 사진 읽어오기
		public CampInfoDTO readCampInfoFile(int camInfoPhotoNum) {
			CampInfoDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM campPhoto WHERE camInfoPhotoNum = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setLong(1, camInfoPhotoNum);
				
				rs = pstmt.executeQuery();

				if (rs.next()) {
					dto = new CampInfoDTO();

					dto.setCamInfoPhotoNum(rs.getInt("camInfoPhotoNum"));
					dto.setCamInfoNum(rs.getInt("camInfoNum"));
					dto.setCamInfoPhotoName(rs.getString("camInfoPhotoName"));
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
		
		public void deleteCampPhotoFile(String mode, long num) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				if (mode.equals("all")) {
					sql = "DELETE FROM campPhoto WHERE camInfoNum = ?";
				} else {
					sql = "DELETE FROM campPhoto WHERE camInfoPhotoNum = ?";
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
	
}
