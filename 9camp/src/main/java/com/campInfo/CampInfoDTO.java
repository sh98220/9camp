package com.campInfo;

public class CampInfoDTO {
	private long camInfoNum;
	private String adminId;
	private String camInfoSubject;
	private String camInfoContent;
	private String camInfoAddr;
	private int camInfoHitCount;
	private String camInfoRegDate;
	private String camThemaName;
	private int wishCount;
	
	public String getCamThemaName() {
		return camThemaName;
	}
	public void setCamThemaName(String camThemaName) {
		this.camThemaName = camThemaName;
	}
	public int getWishCount() {
		return wishCount;
	}
	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}
	public long getCamInfoNum() {
		return camInfoNum;
	}
	public void setCamInfoNum(long camInfoNum) {
		this.camInfoNum = camInfoNum;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getCamInfoSubject() {
		return camInfoSubject;
	}
	public void setCamInfoSubject(String camInfoSubject) {
		this.camInfoSubject = camInfoSubject;
	}
	public String getCamInfoContent() {
		return camInfoContent;
	}
	public void setCamInfoContent(String camInfoContent) {
		this.camInfoContent = camInfoContent;
	}
	public String getCamInfoAddr() {
		return camInfoAddr;
	}
	public void setCamInfoAddr(String camInfoAddr) {
		this.camInfoAddr = camInfoAddr;
	}
	public int getCamInfoHitCount() {
		return camInfoHitCount;
	}
	public void setCamInfoHitCount(int camInfoHitCount) {
		this.camInfoHitCount = camInfoHitCount;
	}
	public String getCamInfoRegDate() {
		return camInfoRegDate;
	}
	public void setCamInfoRegDate(String camInfoRegDate) {
		this.camInfoRegDate = camInfoRegDate;
	}

	
	
}
