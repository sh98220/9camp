package com.reviews;

public class ReviewsDTO {
	private int camRevnum;
	private int camInfonum;
	private String userId;
	private String userName;
	private String camRevsubject;
	private String camRevcontent;
	private int camRevhitcount;
	private String camRevregdate;
	
	private long camInfoPhotonum;
	private String camInfoPhotoname;
	
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
	public long getCamInfoPhotonum() {
		return camInfoPhotonum;
	}
	public void setCamInfoPhotonum(long camInfoPhotonum) {
		this.camInfoPhotonum = camInfoPhotonum;
	}
	public String getCamInfoPhotoname() {
		return camInfoPhotoname;
	}
	public void setCamInfoPhotoname(String camInfoPhotoname) {
		this.camInfoPhotoname = camInfoPhotoname;
	}
	
}
