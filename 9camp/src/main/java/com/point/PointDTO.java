package com.point;

public class PointDTO {
	private String userId;
	private long userPoint;
	private long amount;
	private String pointDate;
	private long pointAmount;
	private long pointNum;
	private long balance2;
	public long getBalance2() {
		return balance2;
	}
	public void setBalance2(long balance2) {
		this.balance2 = balance2;
	}
	private String pointmode;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(long userPoint) {
		this.userPoint = userPoint;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getPointDate() {
		return pointDate;
	}
	public void setPointDate(String pointDate) {
		this.pointDate = pointDate;
	}
	public long getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(long pointAmount) {
		this.pointAmount = pointAmount;
	}
	public long getPointNum() {
		return pointNum;
	}
	public void setPointNum(long pointNum) {
		this.pointNum = pointNum;
	}
	public String getPointmode() {
		return pointmode;
	}
	public void setPointmode(String pointmode) {
		this.pointmode = pointmode;
	}
	
}
