package com.reviews;

public class ReviewsDTO {
	private int camRevnum;
	private int camInfonum;
	private String userId;
	private String userName;
	private String userNickName;
	private String camRevsubject;
	private String camRevcontent;
	private int camRevhitcount;
	private String camRevregdate;
	private String camname;
	
	private int camRevphotonum;
	private String camRevphotoname;
	private String[] imageFiles;
	
	private int reviewsLikeCount;
	
	
	public long getCamRevnum() {
		return camRevnum;
	}
	public void setCamRevnum(int camRevnum) {
		this.camRevnum = camRevnum;
	}
	public long getCamInfonum() {
		return camInfonum;
	}
	public void setCamInfonum(int camInfonum) {
		this.camInfonum = camInfonum;
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
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getCamRevsubject() {
		return camRevsubject;
	}
	public void setCamRevsubject(String camRevsubject) {
		this.camRevsubject = camRevsubject;
	}
	public String getCamRevcontent() {
		return camRevcontent;
	}
	public void setCamRevcontent(String camRevcontent) {
		this.camRevcontent = camRevcontent;
	}
	public int getCamRevhitcount() {
		return camRevhitcount;
	}
	public void setCamRevhitcount(int camRevhitcount) {
		this.camRevhitcount = camRevhitcount;
	}
	public String getCamRevregdate() {
		return camRevregdate;
	}
	public void setCamRevregdate(String camRevregdate) {
		this.camRevregdate = camRevregdate;
	}

	public String[] getImageFiles() {
		return imageFiles;
	}
	public void setImageFiles(String[] imageFiles) {
		this.imageFiles = imageFiles;
	}
	public long getCamRevphotonum() {
		return camRevphotonum;
	}
	public void setCamRevphotonum(int camRevphotonum) {
		this.camRevphotonum = camRevphotonum;
	}
	public String getCamRevphotoname() {
		return camRevphotoname;
	}
	public void setCamRevphotoname(String camRevphotoname) {
		this.camRevphotoname = camRevphotoname;
	}
	public int getReviewsLikeCount() {
		return reviewsLikeCount;
	}
	public void setReviewsLikeCount(int boardLikeCount) {
		this.reviewsLikeCount = boardLikeCount;
	}
	public String getCamname() {
		return camname;
	}
	public void setCamname(String camname) {
		this.camname = camname;
	}
}
