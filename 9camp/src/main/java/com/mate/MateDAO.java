package com.mate;

import java.sql.SQLException;

import java.util.List;

public interface MateDAO {
	public void insertMate(MateDTO dto) throws SQLException;
	public void updateMate(MateDTO dto) throws SQLException;
	public void deleteMate(long cammateNum) throws SQLException;
	
	public void insertMateApp(MateDTO dto) throws SQLException;
	public void updateMateApp(MateDTO dto) throws SQLException;
	public void deleteMateApp(long camMateAppNum) throws SQLException;
	
	public int dataCount(String condition, String keyword);
	public int dataCount();
	
	public int dataCountApp(long camMateNum);

	public List<MateDTO> listMate(int offset, int size, String condition, String keyword);
	public List<MateDTO> listMate(int offset, int size);
	public List<MateDTO> listMateApp(int offset, int size, long camMateNum);
	
	public MateDTO readMate(long camMateNum);
	public MateDTO readMateApp (long camAppNum);
	
	public void updateHitCount(long camMateNum) throws SQLException;
	
	public MateDTO preReadMate(long qnaNum, String keyword, String condition);
	public MateDTO nextReadMate(long qnaNum, String keyword, String condition);
	
	
}
