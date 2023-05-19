package com.freeboard;

public class FreeBoardReplyDTO {
	private long camChatRepNum;
	private long camChatNum;
	private String userId;
	private String userName;
	private String camChatRepContent;
	private String camChatRepRegDate;
	
	
	public long getCamChatRepNum() {
		return camChatRepNum;
	}
	public void setCamChatRepNum(long camChatRepNum) {
		this.camChatRepNum = camChatRepNum;
	}
	public long getCamChatNum() {
		return camChatNum;
	}
	public void setCamChatNum(long camChatNum) {
		this.camChatNum = camChatNum;
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
	public String getCamChatRepContent() {
		return camChatRepContent;
	}
	public void setCamChatRepContent(String camChatRepContent) {
		this.camChatRepContent = camChatRepContent;
	}
	public String getCamChatRepRegDate() {
		return camChatRepRegDate;
	}
	public void setCamChatRepRegDate(String camChatRepRegDate) {
		this.camChatRepRegDate = camChatRepRegDate;
	}
	
	
	
}
