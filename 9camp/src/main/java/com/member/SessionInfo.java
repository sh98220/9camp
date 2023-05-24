package com.member;

public class SessionInfo {
	private String userId;
	private String userName;
	private int userRoll;
	private String userNickName;
	private Long userPoint;
	
	
	public Long getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(Long userPoint) {
		this.userPoint = userPoint;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
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
	public int getUserRoll() {
		return userRoll;
	}
	public void setUserRoll(int userRoll) {
		this.userRoll = userRoll;
	}
}
