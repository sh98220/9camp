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
					+ " camPhoneNum, camNomalWeekDayPrice, camNomalWeekEndPrice, camPeakWeekDayPrice, camPeakWeekEndPrice, camFacility, camInfoLineContent ) "
					+ " VALUES (?, 'admin',  ?, ?, ?, 0, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
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
			pstmt.setString(13, dto.getCamInfoLineContent());
			
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
				sb.append("WHERE INSTR(camInfoSubject, ?) >= 1");
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
					+ "  camPhoneNum, camNomalWeekDayPrice, camNomalWeekEndPrice, camPeakWeekDayPrice, camPeakWeekEndPrice, camFacility, camInfoLineContent "
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
				dto.setCamInfoLineContent(rs.getString("camInfoLineContent"));
				
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
			sql = "UPDATE campInfo SET camInfoSubject=?, camInfoContent=?, camInfoAddr = ?, camThemaName = ?, camKeyWord = ?, camPhoneNum = ? , camNomalWeekDayPrice = ?, camNomalWeekEndPrice = ?, camPeakWeekDayPrice = ? , camPeakWeekEndPrice = ?, camFacility = ?, camInfoLineContent = ? WHERE camInfoNum=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getCamInfoSubject());
			pstmt.setString(2, dto.getCamInfoContent());
			pstmt.setString(3, dto.getCamInfoAddr());
			pstmt.setString(4, dto.getCamThemaName());
			pstmt.setString(5, dto.getCamKeyWord());
			pstmt.setString(6, dto.getCamPhoneNum());
			pstmt.setString(7, dto.getCamNomalWeekDayPrice());
			pstmt.setString(8, dto.getCamNomalWeekEndPrice());
			pstmt.setString(9, dto.getCamPeakWeekDayPrice());
			pstmt.setString(10, dto.getCamPeakWeekEndPrice());
			pstmt.setString(11, dto.getCamFacility());
			pstmt.setString(12, dto.getCamInfoLineContent());
			pstmt.setLong(13, dto.getCamInfoNum());
			
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
		
		// 사진 한장만 가지고 오고 List에 표시할 내용들 가져오기
		public List<CampInfoDTO> listPhoto(int offset, int size) {
			List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT c.camInfoNum, camInfosubject, camInfoPhotoName, camInfoAddr,camInfoAddr camInfoAddr1, camInfoContent, camPhoneNum, camInfoHitCount, camInfoLineContent, camKeyWord,  (SELECT COUNT(*) FROM campWish w WHERE w.camInfoNum = c.camInfoNum) AS wishCount ");
				sb.append(" FROM campInfo c ");
				sb.append(" LEFT OUTER JOIN ( ");
				sb.append("     SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM ( ");
				sb.append("        SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName, ");
				sb.append("            ROW_NUMBER() OVER (PARTITION BY camInfoNum ORDER BY camInfoPhotoNum ASC) AS RANK ");
				sb.append("          FROM campPhoto");
				sb.append("     ) WHERE rank = 1 ");
				sb.append(" ) i ON c.camInfoNum = i.camInfoNum ");
				sb.append(" ORDER BY camInfoNum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				
				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setInt(1, offset);
				pstmt.setInt(2, size);

				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					CampInfoDTO dto = new CampInfoDTO();
					
					dto.setCamInfoNum(rs.getLong("camInfoNum"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					dto.setCamInfoPhotoName(rs.getString("camInfoPhotoName"));
					dto.setCamInfoAddr(rs.getString("camInfoAddr"));
					dto.setCamInfoAddr1(rs.getString("camInfoAddr1"));
					dto.setCamInfoContent(rs.getString("camInfoContent"));;
					dto.setCamKeyWord(rs.getString("camKeyWord"));
					dto.setCamPhoneNum(rs.getString("camPhoneNum"));
					dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
					dto.setCamInfoLineContent(rs.getString("camInfoLineContent"));
					dto.setWishCount(rs.getInt("wishCount"));
					
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

		
		// 메인화면 검색
		public List<CampInfoDTO> listPhoto(int offset, int size , String condition, String keyword) {
			List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT c.camInfoNum, camInfosubject, camInfoPhotoName, camInfoAddr,camInfoAddr camInfoAddr1, camInfoContent, camPhoneNum, camInfoHitCount, camInfoLineContent, camKeyWord,  (SELECT COUNT(*) FROM campWish w WHERE w.camInfoNum = c.camInfoNum) AS wishCount ");
				sb.append(" FROM campInfo c ");
				sb.append(" LEFT OUTER JOIN ( ");
				sb.append("     SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM ( ");
				sb.append("        SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName, ");
				sb.append("            ROW_NUMBER() OVER (PARTITION BY camInfoNum ORDER BY camInfoPhotoNum ASC) AS RANK ");
				sb.append("          FROM campPhoto");
				sb.append("     ) WHERE rank = 1 ");
				sb.append(" ) i ON c.camInfoNum = i.camInfoNum ");
				if(condition.equals("all")) {
					sb.append("WHERE INSTR(camInfoSubject, ?) >= 1");
				} else {
					sb.append(" WHERE INSTR(" + condition + ", ?) >= 1 ");
				}
				sb.append(" ORDER BY camInfoNum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				
				pstmt = conn.prepareStatement(sb.toString());

				if (condition.equals("all")) {
					pstmt.setString(1, keyword);
					pstmt.setInt(2, offset);
					pstmt.setInt(3, size);
				} else {
					pstmt.setString(1, keyword);
					pstmt.setInt(2, offset);
					pstmt.setInt(3, size);
				}

				
				rs = pstmt.executeQuery();

				while (rs.next()) {
					CampInfoDTO dto = new CampInfoDTO();
					
					dto.setCamInfoNum(rs.getLong("camInfoNum"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					dto.setCamInfoPhotoName(rs.getString("camInfoPhotoName"));
					dto.setCamInfoAddr(rs.getString("camInfoAddr"));
					dto.setCamInfoAddr1(rs.getString("camInfoAddr1"));
					dto.setCamInfoContent(rs.getString("camInfoContent"));;
					dto.setCamKeyWord(rs.getString("camKeyWord"));
					dto.setCamPhoneNum(rs.getString("camPhoneNum"));
					dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
					dto.setCamInfoLineContent(rs.getString("camInfoLineContent"));
					dto.setWishCount(rs.getInt("wishCount"));
					
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
		// 키워드로 검색해서 리스트 출력하기
		public List<CampInfoDTO> listPhoto(String[] keys, int offset, int size) {

			List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {
				sb.append(" SELECT c.camInfoNum, camInfosubject, camInfoPhotoName, camInfoAddr,camInfoAddr camInfoAddr1, camInfoContent, camPhoneNum, camInfoHitCount, camInfoLineContent, camKeyWord,  (SELECT COUNT(*) FROM campWish w WHERE w.camInfoNum = c.camInfoNum) AS wishCount ");
				sb.append(" FROM campInfo c ");
				sb.append(" LEFT OUTER JOIN ( ");
				sb.append("     SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM ( ");
				sb.append("        SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName, ");
				sb.append("            ROW_NUMBER() OVER (PARTITION BY camInfoNum ORDER BY camInfoPhotoNum ASC) AS RANK ");
				sb.append("          FROM campPhoto");
				sb.append("     ) WHERE rank = 1 ");
				sb.append(" ) i ON c.camInfoNum = i.camInfoNum ");
				
				if( keys!=null && keys.length != 0) {
					String s = " WHERE ";
					for(int i=0; i<keys.length; i++) {
						s += " INSTR(camkeyword, ?) >= 1 OR ";
					}
					s = s.substring(0, s.length()-3);
					sb.append(s);
				}

				sb.append(" ORDER BY camInfoNum DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				
				pstmt = conn.prepareStatement(sb.toString());
				
				if(keys != null && keys.length != 0) {
					int n = keys.length;
					for(int i=0; i<keys.length; i++) {
						pstmt.setString(i+1, keys[i]);
					}
					pstmt.setInt(n+1, offset);
					pstmt.setInt(n+2, size);						
				} else {
					pstmt.setInt(1, offset);
					pstmt.setInt(2, size);					
				}
			
				rs = pstmt.executeQuery();

				while (rs.next()) {
					CampInfoDTO dto = new CampInfoDTO();
					
					dto.setCamInfoNum(rs.getLong("camInfoNum"));
					dto.setCamInfoSubject(rs.getString("camInfoSubject"));
					dto.setCamInfoPhotoName(rs.getString("camInfoPhotoName"));
					dto.setCamInfoAddr(rs.getString("camInfoAddr"));
					dto.setCamInfoAddr1(rs.getString("camInfoAddr1"));
					dto.setCamInfoContent(rs.getString("camInfoContent"));;
					dto.setCamKeyWord(rs.getString("camKeyWord"));
					dto.setCamPhoneNum(rs.getString("camPhoneNum"));
					dto.setCamInfoHitCount(rs.getInt("camInfoHitCount"));
					dto.setCamInfoLineContent(rs.getString("camInfoLineContent"));
					dto.setWishCount(rs.getInt("wishCount"));
					
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
			
			System.out.println(list.size());
			System.out.println(sb.toString());

			return list;
			
			
		}
		
		// 키워드로 검색 데이터 개수
		public int dataCount(String key[]) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0)\r\n"
						+ "FROM campInfo c\r\n"
						+ "LEFT OUTER JOIN (\r\n"
						+ "    SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName FROM (\r\n"
						+ "        SELECT camInfoPhotoNum, camInfoNum, camInfoPhotoName,\r\n"
						+ "            ROW_NUMBER() OVER (PARTITION BY camInfoNum ORDER BY camInfoPhotoNum ASC) AS RANK\r\n"
						+ "        FROM campPhoto\r\n"
						+ "    ) WHERE RANK = 1\r\n"
						+ ") i ON c.camInfoNum = i.camInfoNum\r\n"
						+ "WHERE INSTR(camkeyword, ?) >= 1\r\n"
						+ "ORDER BY c.camInfoNum DESC";
				

				pstmt = conn.prepareStatement(sql);
				
			//	pstmt.setString(1, key);
			
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

		// 캠핑장 검색 리스트
	      public List<CampInfoDTO> listCampInfo(String keyword) {
	         List<CampInfoDTO> list = new ArrayList<CampInfoDTO>();
	       //  PreparedStatement pstmt = null;
	        // ResultSet rs = null;
	        // StringBuilder sb = new StringBuilder();

	        try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}
	       
	        return list;
	        
	      }
	
	
}
