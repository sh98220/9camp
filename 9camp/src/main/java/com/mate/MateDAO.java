package com.mate;

import java.sql.SQLException;
import java.util.List;

public interface MateDAO {
	public void insertMate(MateDTO dto) throws SQLException;
	public void updateMate(MateDTO dto) throws SQLException;
	public void deleteMate(long num, String userId) throws SQLException;
	public void deleteMate(long[] nums, String[] userId) throws SQLException;
	public void deleteMate(long[] nums, String userId) throws SQLException;
	
	public int dataCount(String condition, String keyword, String userId);
	public int dataCount(String userId);
	public int dataCount();
	public List<MateDTO> listMate();
	public List<MateDTO> listMate(int offset, int size, String condition, String keyword, String userId);
	public List<MateDTO> listMate(int offset, int size, String userId);
	public MateDTO readMate(String userId, Long fileName);
}
