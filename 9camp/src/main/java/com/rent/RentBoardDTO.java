package com.rent;

public class RentBoardDTO {
	private long rentNum;
	private String hostId;
	private String rentObject;
	private String rentContent;
	private String rentSubject;
	private long rentFee;
	private String rentStartDate;
	private String rentEndDate;
	private String rentRegDate;
	private int rentHitCount;
	
	private long rentPhotoNum;
	private String rentPhotoName;
	private String[] rentPhotos;
	
	
	

	public int getRentHitCount() {
		return rentHitCount;
	}
	public void setRentHitCount(int rentHitCount) {
		this.rentHitCount = rentHitCount;
	}
	public String getRentRegDate() {
		return rentRegDate;
	}
	public void setRentRegDate(String rentRegDate) {
		this.rentRegDate = rentRegDate;
	}
	public String getRentSubject() {
		return rentSubject;
	}
	public void setRentSubject(String rentSubject) {
		this.rentSubject = rentSubject;
	}
	public String[] getRentPhotos() {
		return rentPhotos;
	}
	public void setRentPhotos(String[] rentPhotos) {
		this.rentPhotos = rentPhotos;
	}
	
	public long getRentNum() {
		return rentNum;
	}
	public void setRentNum(long rentNum) {
		this.rentNum = rentNum;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getRentObject() {
		return rentObject;
	}
	public void setRentObject(String rentObject) {
		this.rentObject = rentObject;
	}
	public String getRentContent() {
		return rentContent;
	}
	public void setRentContent(String rentContent) {
		this.rentContent = rentContent;
	}
	public long getRentFee() {
		return rentFee;
	}
	public void setRentFee(long rentFee) {
		this.rentFee = rentFee;
	}
	public String getRentStartDate() {
		return rentStartDate;
	}
	public void setRentStartDate(String rentStartDate) {
		this.rentStartDate = rentStartDate;
	}
	public String getRentEndDate() {
		return rentEndDate;
	}
	public void setRentEndDate(String rentEndDate) {
		this.rentEndDate = rentEndDate;
	}
	public long getRentPhotoNum() {
		return rentPhotoNum;
	}
	public void setRentPhotoNum(long rentPhotoNum) {
		this.rentPhotoNum = rentPhotoNum;
	}
	public String getRentPhotoName() {
		return rentPhotoName;
	}
	public void setRentPhotoName(String rentPhotoName) {
		this.rentPhotoName = rentPhotoName;
	}
	
}
