package com.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.MemberDTO;
import com.util.DBConn;

public class MessageDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<MemberDTO> listSender(String userId) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT userId, userNickName ");
			sb.append(" FROM member ");
			sb.append(" where userId = ?");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserNickName(rs.getString("userNickName"));
				
				list.add(dto);
			}
		} catch (Exception e) {
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
	
	public void sendMsg(MessageDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO message(msgNum, msgWriterId, msgSenderId, msgContent, msgRegDate, msgReadDate, msgRead, msgWriEnabled, msgSenEnabled) "
					+ " VALUES (message_seq.NEXTVAL, ?, ?, ?, SYSDATE, SYSDATE, 0, 1, 1)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getMsgWriterId());
			pstmt.setString(2, dto.getMsgSenderId());
			pstmt.setString(3, dto.getMsgContent());

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
	
	/*보낸쪽지함 쪽지 개수 카운트*/
	public int dataSendCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM message "
					+ " WHERE msgWriterId = ? AND msgWriEnabled=1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
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
		
		return result;
	}
	
	/*받은쪽지함 쪽지 개수 카운트*/
	public int dataRecCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) "
					+ " FROM message msg "
					+ " WHERE msg.msgSenderId = ? AND msgSenEnabled=1";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
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
		
		return result;
	}
	
	/*보낸쪽지함 검색조건 쪽지 개수 카운트*/
	public int dataSendCount(String userId, String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT NVL(COUNT(*),0) FROM message ");
			sb.append(" WHERE msgWriterId = ? AND msgWriEnabled=1 ");
			
			if(condition.equals("content")) {
				sb.append(" AND INSTR(msgContent, ?) >= 1 ");
			}
			
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
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
		
		return result;
	}
	
	/*받은쪽지함 검색조건 쪽지 개수 카운트*/
	public int dataRecCount(String userId, String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT NVL(COUNT(*),0) ");
			sb.append(" FROM message msg ");
			sb.append(" JOIN member mb ON msg.msgWriterId = mb.userId ");
			sb.append(" WHERE msg.msgSenderId = ? AND msgSenEnabled=1 ");
			
			if(condition.equals("content")) {
				sb.append(" AND INSTR(msgContent, ?) >= 1 ");
			} else if(condition.equals("userNickName")) {
				sb.append(" AND INSTR(userNickName, ?) >= 1 ");
			}
			
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, userId);
			pstmt.setString(2, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
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
		
		return result;
	}
	
	// 보낸쪽지함 리스트 출력
	public List<MessageDTO> listSendMsg(String userId, int offset, int size) {
		List<MessageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT msgNum, msgContent, msgRegDate, msgReadDate, msgRead, msgSenderId, userNickName "
					+ " FROM message msg "
					+ " JOIN member mb ON msg.msgSenderId = mb.userId "
					+ " WHERE msg.msgWriterId = ? AND msgWriEnabled=1 "
					+ " ORDER BY msgNum DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				MessageDTO dto = new MessageDTO();

				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setMsgReadDate(rs.getString("msgReadDate"));
				dto.setMsgRead(rs.getInt("msgRead"));
				dto.setMsgSenderId(rs.getString("msgSenderId"));
				dto.setUserNickName(rs.getString("userNickName"));

				list.add(dto);
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

		return list;
	}
	
	// 받은쪽지함 리스트 출력
	public List<MessageDTO> listRecMsg(String userId, int offset, int size) {
		List<MessageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT msgNum, msgContent, msgRegDate, msgWriterId, userNickName "
					+ " FROM message msg "
					+ " JOIN member mb ON msg.msgWriterId = mb.userId "
					+ " WHERE msg.msgSenderId = ? AND msgSenEnabled=1 "
					+ " ORDER BY msgNum DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				MessageDTO dto = new MessageDTO();

				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setMsgWriterId(rs.getString("msgWriterId"));
				dto.setUserNickName(rs.getString("userNickName"));

				list.add(dto);
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
		
		return list;
	}
	
	// 보낸쪽지함 검색 리스트 출력
	public List<MessageDTO> listSendMsg(String userId, int offset, int size, String condition, String keyword) {
		List<MessageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT msgNum, msgContent, msgRegDate, msgReadDate, msgRead, msgSenderId, userNickName ");
			sb.append(" FROM message msg ");
			sb.append(" JOIN member mb ON msg.msgSenderId = mb.userId ");
			sb.append(" WHERE msg.msgWriterId = ? AND msgWriEnabled=1 ");
			
			if(condition.equals("content")) {
				sb.append(" AND INSTR(msgContent, ?) >= 1 ");
			}
			
			sb.append(" ORDER BY msgNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);
			
			if(condition.equals("content")) {
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MessageDTO dto = new MessageDTO();

				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setMsgReadDate(rs.getString("msgReadDate"));
				dto.setMsgRead(rs.getInt("msgRead"));
				dto.setMsgSenderId(rs.getString("msgSenderId"));
				dto.setUserNickName(rs.getString("userNickName"));
				
				list.add(dto);
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
		
		return list;
	}
	
	// 받은쪽지함 검색 리스트 출력
	public List<MessageDTO> listRecMsg(String userId, int offset, int size, String condition, String keyword) {
		List<MessageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append("SELECT msgNum, msgContent, msgRegDate, msgRead, msgWriterId, userNickName ");
			sb.append(" FROM message msg ");
			sb.append(" JOIN member mb ON msg.msgWriterId = mb.userId ");
			sb.append(" WHERE msg.msgSenderId = ? AND msgSenEnabled=1 ");
			
			if(condition.equals("content")) {
				sb.append(" AND INSTR(msgContent, ?) >= 1 ");
			} else if(condition.equals("userNickName")) {
				sb.append(" AND INSTR(userNickName, ?) >= 1 ");
			}
			
			sb.append(" ORDER BY msgNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, userId);
			
			if(condition.equals("content") || condition.equals("userNickName")) {
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MessageDTO dto = new MessageDTO();

				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setMsgRead(rs.getInt("msgRead"));
				dto.setMsgSenderId(rs.getString("msgWriterId"));
				dto.setUserNickName(rs.getString("userNickName"));
				
				list.add(dto);
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

		return list;
	}
	
	// 받은쪽지 열람시 읽음 처리, 읽은 날짜 갱신
	public void updateRead(long num) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE message SET msgRead=1, msgReadDate=SYSDATE "
					+ " WHERE msgNum=? AND msgRead = 0";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, num);
			
			pstmt.executeUpdate();
		} catch (Exception e) {
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
	
	// 받은 쪽지 보기
	public MessageDTO readRecMsg(String userId, long num) {
		MessageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT msgNum, msgWriterId, msgContent, msgRegDate, userNickName "
					+ " FROM message msg "
					+ " JOIN member mb ON msg.msgWriterId = mb.userId "
					+ " WHERE msgSenderId = ? AND msgSenEnabled=1 AND msgNum = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			pstmt.setLong(2, num);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MessageDTO();
				
				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgWriterId(rs.getString("msgWriterId"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setUserNickName(rs.getString("userNickName"));
			}
			
		} catch (Exception e) {
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
	
	// 보낸 쪽지 보기
	public MessageDTO readSendMsg(String userId, long num) {
		MessageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT msgNum, msgSenderId, msgContent, msgRegDate, userNickName, msgReadDate, msgRead "
					+ " FROM message msg "
					+ " JOIN member mb ON msg.msgSenderId = mb.userId "
					+ " WHERE msgWriterId = ? AND msgSenEnabled=1 AND msgNum = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			pstmt.setLong(2, num);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new MessageDTO();

				dto.setMsgNum(rs.getLong("msgNum"));
				dto.setMsgWriterId(rs.getString("msgSenderId"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgRegDate(rs.getString("msgRegDate"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setMsgReadDate(rs.getString("msgReadDate"));
				dto.setMsgRead(rs.getInt("msgRead"));
			}

		} catch (Exception e) {
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
