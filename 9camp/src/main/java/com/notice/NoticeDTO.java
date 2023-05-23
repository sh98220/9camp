package com.notice;

public class NoticeDTO {
	private int noticeNum;
	private String userId;
	private String noticeSubject;
	private String noticeContent;
	private int noticeHitCount;
	private String noticeRegDate;
	
	private int noticePhotoNum;
	private String noticePhotoName;
	private String[] imageFiles;
	
	public int getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(int noticeNum) {
		this.noticeNum = noticeNum;
	}
	public String getuserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public String getNoticeSubject() {
		return noticeSubject;
	}
	public void setNoticeSubject(String noticeSubject) {
		this.noticeSubject = noticeSubject;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public int getNoticeHitCount() {
		return noticeHitCount;
	}
	public void setNoticeHitCount(int noticeHitCount) {
		this.noticeHitCount = noticeHitCount;
	}
	public String getNoticeRegDate() {
		return noticeRegDate;
	}
	public void setNoticeRegDate(String noticeRegDate) {
		this.noticeRegDate = noticeRegDate;
	}
	
	public int getNoticePhotoNum() {
		return noticePhotoNum;
	}
	public void setNoticePhotoNum(int noticePhotoNum) {
		this.noticePhotoNum = noticePhotoNum;
	}
	public String getNoticePhotoName() {
		return noticePhotoName;
	}
	public void setNoticePhotoName(String noticePhotoName) {
		this.noticePhotoName = noticePhotoName;
	}
	public String[] getImageFiles() {
		return imageFiles;
	}
	public void setImageFiles(String[] imageFiles) {
		this.imageFiles = imageFiles;
	}
	
	
}
