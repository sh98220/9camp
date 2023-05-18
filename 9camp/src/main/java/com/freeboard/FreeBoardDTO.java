package com.freeboard;

public class FreeBoardDTO {
	private int camChatNum;
	private String userId;
	private String userName;
	private String camChatSubject;
	private String camChatContent;
	private int camChatHitCount;
	private String camChatRegDate;
	
	private int replyCount;
	private int freeboardLikeCount;
	
	public int getcamChatNum() {
		return camChatNum;
	}
	public void setcamChatNum(int camChatNum) {
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
	public String getcamChatSubject() {
		return camChatSubject;
	}
	public void setcamChatSubject(String camChatSubject) {
		this.camChatSubject = camChatSubject;
	}
	public String getcamChatContent() {
		return camChatContent;
	}
	public void setcamChatContent(String camChatContent) {
		this.camChatContent = camChatContent;
	}
	public int getcamChatHitCount() {
		return camChatHitCount;
	}
	public void setcamChatHitCount(int camChatHitCount) {
		this.camChatHitCount = camChatHitCount;
	}
	public String getcamChatRegDate() {
		return camChatRegDate;
	}
	public void setcamChatRegDate(String camChatRegDate) {
		this.camChatRegDate = camChatRegDate;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getFreeboardLikeCount() {
		return freeboardLikeCount;
	}
	public void setFreeboardLikeCount(int freeboardLikeCount) {
		this.freeboardLikeCount = freeboardLikeCount;
	}

	
	
}
