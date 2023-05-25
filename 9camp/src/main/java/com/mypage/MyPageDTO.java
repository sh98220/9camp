package com.mypage;

/**
 * @author hanjeongsu
 *
 */
public class MyPageDTO {
	//member
	private String userId;
	private String userName;
	private String userPwd;
	private String userTel, userTel1, userTel2, userTel3;
	private String userBirth;
	private String userNickName;
	private String userEmail, userEmail1, userEmail2, userEmail3;
	private String userRegDate;
	private long userPoint;
	private String userUpdateDate;
	
	
	//campInfo
	private long camInfoNum;
	private String adminId;
	private String camInfoSubject;
	private String camInfoContent;
	private String camInfoAddr;
	private int camInfoHitCount;
	private String camInfoRegDate;
	private String camThemaName;
	
	//campMate
	private String camMateSubject;
	private String camMateContent;
	private int camMateHitCount;
	private String camMateStartDate;
	private String camMateEndDate;
	private String camMateRegDate;
	private int camMateDues;
	private String campStyle;
	
	
	

	//campMateApply
	private long camMateNum;
	private String camMateAppContent;
	private String camMateAppDate;
	private String camMateAppGender;
	private int camMateAppAge;
	private String camMateAppConfirm; 
	private String camMateAppSubject;
	
	//restrictedMember
	private String restContent;
	private String restStartDate;
	private String restEndDate;
	
	
	
	
	public String getCamMateAppSubject() {
		return camMateAppSubject;
	}
	public void setCamMateAppSubject(String camMateAppSubject) {
		this.camMateAppSubject = camMateAppSubject;
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
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getUserTel1() {
		return userTel1;
	}
	public void setUserTel1(String userTel1) {
		this.userTel1 = userTel1;
	}
	public String getUserTel2() {
		return userTel2;
	}
	public void setUserTel2(String userTel2) {
		this.userTel2 = userTel2;
	}
	public String getUserTel3() {
		return userTel3;
	}
	public void setUserTel3(String userTel3) {
		this.userTel3 = userTel3;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserEmail1() {
		return userEmail1;
	}
	public void setUserEmail1(String userEmail1) {
		this.userEmail1 = userEmail1;
	}
	public String getUserEmail2() {
		return userEmail2;
	}
	public void setUserEmail2(String userEmail2) {
		this.userEmail2 = userEmail2;
	}
	public String getUserEmail3() {
		return userEmail3;
	}
	public void setUserEmail3(String userEmail3) {
		this.userEmail3 = userEmail3;
	}
	public String getUserRegDate() {
		return userRegDate;
	}
	public void setUserRegDate(String userRegDate) {
		this.userRegDate = userRegDate;
	}
	public long getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(long userPoint) {
		this.userPoint = userPoint;
	}
	public String getUserUpdateDate() {
		return userUpdateDate;
	}
	public void setUserUpdateDate(String userUpdateDate) {
		this.userUpdateDate = userUpdateDate;
	}
	public long getCamInfoNum() {
		return camInfoNum;
	}
	public void setCamInfoNum(long camInfoNum) {
		this.camInfoNum = camInfoNum;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getCamInfoSubject() {
		return camInfoSubject;
	}
	public void setCamInfoSubject(String camInfoSubject) {
		this.camInfoSubject = camInfoSubject;
	}
	public String getCamInfoContent() {
		return camInfoContent;
	}
	public void setCamInfoContent(String camInfoContent) {
		this.camInfoContent = camInfoContent;
	}
	public String getCamInfoAddr() {
		return camInfoAddr;
	}
	public void setCamInfoAddr(String camInfoAddr) {
		this.camInfoAddr = camInfoAddr;
	}
	public int getCamInfoHitCount() {
		return camInfoHitCount;
	}
	public void setCamInfoHitCount(int camInfoHitCount) {
		this.camInfoHitCount = camInfoHitCount;
	}
	public String getCamInfoRegDate() {
		return camInfoRegDate;
	}
	public void setCamInfoRegDate(String camInfoRegDate) {
		this.camInfoRegDate = camInfoRegDate;
	}
	public String getCamThemaName() {
		return camThemaName;
	}
	public void setCamThemaName(String camThemaName) {
		this.camThemaName = camThemaName;
	}
	public String getCamMateSubject() {
		return camMateSubject;
	}
	public void setCamMateSubject(String camMateSubject) {
		this.camMateSubject = camMateSubject;
	}
	public String getCamMateContent() {
		return camMateContent;
	}
	public void setCamMateContent(String camMateContent) {
		this.camMateContent = camMateContent;
	}
	public int getCamMateHitCount() {
		return camMateHitCount;
	}
	public void setCamMateHitCount(int camMateHitCount) {
		this.camMateHitCount = camMateHitCount;
	}
	public String getCamMateStartDate() {
		return camMateStartDate;
	}
	public void setCamMateStartDate(String camMateStartDate) {
		this.camMateStartDate = camMateStartDate;
	}
	public String getCamMateEndDate() {
		return camMateEndDate;
	}
	public void setCamMateEndDate(String camMateEndDate) {
		this.camMateEndDate = camMateEndDate;
	}
	public String getCamMateRegDate() {
		return camMateRegDate;
	}
	public void setCamMateRegDate(String camMateRegDate) {
		this.camMateRegDate = camMateRegDate;
	}
	public int getCamMateDues() {
		return camMateDues;
	}
	public void setCamMateDues(int camMateDues) {
		this.camMateDues = camMateDues;
	}
	public long getCamMateNum() {
		return camMateNum;
	}
	public void setCamMateNum(long camMateNum) {
		this.camMateNum = camMateNum;
	}
	public String getCamMateAppContent() {
		return camMateAppContent;
	}
	public void setCamMateAppContent(String camMateAppContent) {
		this.camMateAppContent = camMateAppContent;
	}
	public String getCamMateAppDate() {
		return camMateAppDate;
	}
	public void setCamMateAppDate(String camMateAppDate) {
		this.camMateAppDate = camMateAppDate;
	}
	public String getCamMateAppGender() {
		return camMateAppGender;
	}
	public void setCamMateAppGender(String camMateAppGender) {
		this.camMateAppGender = camMateAppGender;
	}
	public int getCamMateAppAge() {
		return camMateAppAge;
	}
	public void setCamMateAppAge(int camMateAppAge) {
		this.camMateAppAge = camMateAppAge;
	}
	public String getCamMateAppConfirm() {
		return camMateAppConfirm;
	}
	public void setCamMateAppConfirm(String camMateAppConfirm) {
		this.camMateAppConfirm = camMateAppConfirm;
	}
	public String getCampStyle() {
		return campStyle;
	}
	public void setCampStyle(String campStyle) {
		this.campStyle = campStyle;
	}
	public String getRestContent() {
		return restContent;
	}
	public void setRestContent(String restContent) {
		this.restContent = restContent;
	}
	public String getRestStartDate() {
		return restStartDate;
	}
	public void setRestStartDate(String restStartDate) {
		this.restStartDate = restStartDate;
	}
	public String getRestEndDate() {
		return restEndDate;
	}
	public void setRestEndDate(String restEndDate) {
		this.restEndDate = restEndDate;
	}
}
