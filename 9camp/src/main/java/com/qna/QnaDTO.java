package com.qna;

public class QnaDTO {
	private String userId;
	private String userNickName;
	private long qnaNum;
	private String qnaSubject;
	private String qnaContent;
	private String qnaPwd;
	private int qnaHitCount;
	private String qnaRegDate;
	private long groupNum;
	private int orderNum;
	private int depth;
	private long parent;
	private String qnaOrChange;
	
    public String getQnaOrChange() {
		return qnaOrChange;
	}
	public void setQnaOrChange(String qnaOrChange) {
		this.qnaOrChange = qnaOrChange;
	}
	private long qnaFileNum;
	private String qnasaveFilename;
	private String qnaoriginalFilename;
	
	private String[] qnasaveFiles;
	private String[] qnaoriginalFiles;
	
	
	
	
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public long getParent() {
		return parent;
	}
	public void setParent(long parent) {
		this.parent = parent;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
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
	public String getQnaPwd() {
		return qnaPwd;
	}
	public void setQnaPwd(String qnaPwd) {
		this.qnaPwd = qnaPwd;
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
