package com.reviews;

public class ReplyDTO {
	private long camRevRepnum;
	private long camRevnum;
	private String userId;
	private String userName;
	private String camRevRepcontent;
	private String camRevRepregdate;
	
	
	public long getCamRevRepnum() {
		return camRevRepnum;
	}
	public void setCamRevRepnum(long camRevRepnum) {
		this.camRevRepnum = camRevRepnum;
	}
	public long getCamRevnum() {
		return camRevnum;
	}
	public void setCamRevnum(long camRevnum) {
		this.camRevnum = camRevnum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCamRevRepcontent() {
		return camRevRepcontent;
	}
	public void setCamRevRepcontent(String camRevRepcontent) {
		this.camRevRepcontent = camRevRepcontent;
	}
	public String getCamRevRepregdate() {
		return camRevRepregdate;
	}
	public void setCamRevRepregdate(String camRevRepregdate) {
		this.camRevRepregdate = camRevRepregdate;
	}
	
	
}

