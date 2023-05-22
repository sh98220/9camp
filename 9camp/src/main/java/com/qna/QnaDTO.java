package com.qna;

public class QnaDTO {
	private String userId;
	private long qnaNum;
	private String qnaSubject;
	private String qnaContent;
	private String qnaQwd;
	private int qnaHitCount;
	private String qnaRegDate;
	private long groupNum;
	private int orderNum;
	
	private long qnaPhotoNum;
	private String qnaPhotoName;
	private String[] qnaPhotos;
	
    private long qnaFileNum;
	private String qnasaveFilename;
	private String qnaoriginalFilename;
	
	private String[] qnasaveFiles;
	private String[] qnaoriginalFiles;
	
	
	
	public long getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(long groupNum) {
		this.groupNum = groupNum;
	}
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getQnaNum() {
		return qnaNum;
	}
	public void setQnaNum(long qnaNum) {
		this.qnaNum = qnaNum;
	}
	public String getQnaSubject() {
		return qnaSubject;
	}
	public void setQnaSubject(String qnaSubject) {
		this.qnaSubject = qnaSubject;
	}
	public String getQnaContent() {
		return qnaContent;
	}
	public void setQnaContent(String qnaContent) {
		this.qnaContent = qnaContent;
	}
	public String getQnaQwd() {
		return qnaQwd;
	}
	public void setQnaQwd(String qnaQwd) {
		this.qnaQwd = qnaQwd;
	}
	public int getQnaHitCount() {
		return qnaHitCount;
	}
	public void setQnaHitCount(int qnaHitCount) {
		this.qnaHitCount = qnaHitCount;
	}
	public String getQnaRegDate() {
		return qnaRegDate;
	}
	public void setQnaRegDate(String qnaRegDate) {
		this.qnaRegDate = qnaRegDate;
	}
	public long getQnaPhotoNum() {
		return qnaPhotoNum;
	}
	public void setQnaPhotoNum(long qnaPhotoNum) {
		this.qnaPhotoNum = qnaPhotoNum;
	}
	public String getQnaPhotoName() {
		return qnaPhotoName;
	}
	public void setQnaPhotoName(String qnaPhotoName) {
		this.qnaPhotoName = qnaPhotoName;
	}
	public String[] getQnaPhotos() {
		return qnaPhotos;
	}
	public void setQnaPhotos(String[] qnaPhotos) {
		this.qnaPhotos = qnaPhotos;
	}
	public long getQnaFileNum() {
		return qnaFileNum;
	}
	public void setQnaFileNum(long qnaFileNum) {
		this.qnaFileNum = qnaFileNum;
	}
	public String getQnasaveFilename() {
		return qnasaveFilename;
	}
	public void setQnasaveFilename(String qnasaveFilename) {
		this.qnasaveFilename = qnasaveFilename;
	}
	public String getQnaoriginalFilename() {
		return qnaoriginalFilename;
	}
	public void setQnaoriginalFilename(String qnaoriginalFilename) {
		this.qnaoriginalFilename = qnaoriginalFilename;
	}
	public String[] getQnasaveFiles() {
		return qnasaveFiles;
	}
	public void setQnasaveFiles(String[] qnasaveFiles) {
		this.qnasaveFiles = qnasaveFiles;
	}
	public String[] getQnaoriginalFiles() {
		return qnaoriginalFiles;
	}
	public void setQnaoriginalFiles(String[] qnaoriginalFiles) {
		this.qnaoriginalFiles = qnaoriginalFiles;
	}
	
}
