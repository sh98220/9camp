package com.message;

public class MessageDTO {
	private long msgNum;
	private String msgWriterId;
	private String msgSenderId;
	private String msgContent;
	private String msgRegDate;
	private int msgWriEnabled;
	private int msgSenEnabled;
	public long getMsgNum() {
		return msgNum;
	}
	public void setMsgNum(long msgNum) {
		this.msgNum = msgNum;
	}
	public String getMsgWriterId() {
		return msgWriterId;
	}
	public void setMsgWriterId(String msgWriterId) {
		this.msgWriterId = msgWriterId;
	}
	public String getMsgSenderId() {
		return msgSenderId;
	}
	public void setMsgSenderId(String msgSenderId) {
		this.msgSenderId = msgSenderId;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgRegDate() {
		return msgRegDate;
	}
	public void setMsgRegDate(String msgRegDate) {
		this.msgRegDate = msgRegDate;
	}
	public int getMsgWriEnabled() {
		return msgWriEnabled;
	}
	public void setMsgWriEnabled(int msgWriEnabled) {
		this.msgWriEnabled = msgWriEnabled;
	}
	public int getMsgSenEnabled() {
		return msgSenEnabled;
	}
	public void setMsgSenEnabled(int msgSenEnabled) {
		this.msgSenEnabled = msgSenEnabled;
	}
	
	
}
