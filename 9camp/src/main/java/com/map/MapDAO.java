package com.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campInfo.CampInfoDTO;
import com.util.DBConn;

public class MapDAO {
	private Connection conn = DBConn.getConnection();
	
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
				sb.append(" SELECT camInfoNum, camInfoSubject, camInfoContent, camInfoAddr, camPhoneNum,  ");
				sb.append("		camInfoHitCount, TO_CHAR(camInfoRegDate, 'YYYY-MM-DD') camInfoRegDate, camThemaName ");
				sb.append(" FROM campInfo ");
				sb.append(" ORDER BY camInfoNum ASC ");
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
					dto.setCamPhoneNum(rs.getString("camPhoneNum"));
					
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
				sb.append(" SELECT camInfoNum, camInfoSubject, camInfoContent, camInfoAddr, camPhoneNum, ");
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
					dto.setCamPhoneNum(rs.getString("camPhoneNum"));
					
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
		
		// 리스트 클릭시 표시할 내용
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
}
