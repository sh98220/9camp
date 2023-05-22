CREATE TABLE member ( --회원 테이블 1
    userId VARCHAR2(50),
    userName VARCHAR2(50) NOT NULL,
    userPwd VARCHAR2(50) NOT NULL,
    userTel VARCHAR2(50) NOT NULL,
    userBirth VARCHAR2(50) NOT NULL,
    userNickName VARCHAR2(50) NOT NULL,
    userEmail VARCHAR2(50) NOT NULL,
    userRegDate DATE DEFAULT SYSDATE,
    userPoint NUMBER DEFAULT 0,
    userUpdateDate DATE,
    CONSTRAINT pk_member_userId PRIMARY KEY(userId),
    CONSTRAINT uk_member_userNickName UNIQUE(userNickName)
);


CREATE TABLE message( --쪽지 테이블
    msgNum NUMBER,
    msgWriterId VARCHAR2(50),
    msgSenderId VARCHAR2(50),
    msgContent CLOB NOT NULL,
    msgRegDate DATE DEFAULT SYSDATE,
    msgWriEnabled NUMBER DEFAULT 1,
    msgSenEnabled NUMBER DEFAULT 1,
    msgRead NUMBER(1) DEFAULT 0 NOT NULL,
    msgReadDate DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT pk_message_msgNum PRIMARY KEY(msgNum),
    CONSTRAINT fk_message_msgWriterId FOREIGN KEY(msgWriterId) REFERENCES member(userId), --ON DELETE CASCADE,
    CONSTRAINT fk_message_msgSenderId FOREIGN KEY(msgSenderId) REFERENCES member(userId) --ON DELETE CASCADE
);

create table qna(
   qnaNum NUMBER NOT NULL,
   userId VARCHAR2(50) NOT NULL,
   qnaSubject VARCHAR2(50) NOT NULL,
   qnaContent CLOB NOT NULL,
   qnaRegDate VARCHAR2(5) NOT NULL,
   qnaHitCount NUMBER NOT NULL,
   qnaGroupNum NUMBER NOT NULL,
   qnaOrderNum NUMBER NOT NULL,
   CONSTRAINT pk_qna_qnaNum PRIMARY KEY(qnaNum),
   CONSTRAINT fk_qna_userId FOREIGN KEY(userId) REFERENCES member(userId)
   
);

create table qnaPhoto(
   qnaNum NUMBER NOT NULL,
   qnaPhotoNum NUMBER NOT NULL,
   qnaPhotoName VARCHAR2(50) NOT NULL,
   CONSTRAINT pk_qnaPhoto_qnaPhotoNum PRIMARY KEY(qnaPhotoNum),
   CONSTRAINT fk_qnaPhoto_qnaNum FOREIGN KEY(qnaNum) REFERENCES qna(qnaNum)
);

create table qnaFile(
   qnaNum NUMBER NOT NULL,
   qnaFileNum NUMBER NOT NULL,
   qnaorigianlFileName VARCHAR2(50) NOT NULL,
   qnasaveFileName VARCHAR2(50) NOT NULL,
   CONSTRAINT pk_qnaFile_qnaFileNum PRIMARY KEY(qnaFileNum),
   CONSTRAINT fk_qnaFile_qnaNum FOREIGN KEY(qnaNum) REFERENCES qna(qnaNum)
);

CREATE SEQUENCE qnaNum_seq --Q & N 게시판 시퀀스
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

CREATE SEQUENCE qnaPhotoNum_seq --Q & N 사진 시퀀스
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

CREATE SEQUENCE qnaFileNum_seq --Q & N 파일 시퀀스
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;


CREATE TABLE campInfo( --캠핌 정보 테이블 8
    camInfoNum NUMBER,
    adminId VARCHAR2(50) NOT NULL,
    camInfoSubject VARCHAR2(50) NOT NULL,
    camInfoContent CLOB NOT NULL,
    camInfoAddr VARCHAR2(50) NOT NULL,
    camInfoHitCount NUMBER DEFAULT 0,
    camInfoRegDate DATE DEFAULT SYSDATE,
    camThemaName VARCHAR2(50) NOT NULL,
    camKeyWord VARCHAR2(50) NOT NULL,
    camPhoneNum VARCHAR2(50) NOT NULL,
    camNomalWeekDayPrice VARCHAR2(50) NOT NULL,
    camNomalWeekEndPrice VARCHAR2(50) NOT NULL,
    camPeakWeekDayPrice VARCHAR2(50) NOT NULL,
    camPeakWeekEndPrice VARCHAR2(50) NOT NULL,
    camFaciltiy VARCHAR2(10) NOT NULL,
    CONSTRAINT pk_campInfo_camInfoNum PRIMARY KEY(camInfoNum),
    CONSTRAINT fk_campInfo_adminId FOREIGN KEY(adminId) REFERENCES admin(adminId) ON DELETE CASCADE
);




CREATE TABLE campPhoto ( --캠핑장 정보 사진 9
    camInfoPhotoNum NUMBER,
    camInfoNum NUMBER NOT NULL,
    camInfoPhotoName VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_campPhoto_camInfoPhotoNum PRIMARY KEY(camInfoPhotoNum),
    CONSTRAINT fk_campPhoto_camInfoNum FOREIGN KEY(camInfoNum) REFERENCES campInfo(camInfoNum) ON DELETE CASCADE
);


CREATE TABLE keywordName( --키워드명 테이블 10
    keywordName VARCHAR2(50),
    CONSTRAINT pk_keywordName_keywordName PRIMARY KEY(keywordName)
);

CREATE TABLE campWish( --찜 테이블 12
    camInfoNum NUMBER NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    PRIMARY KEY (camInfoNum,userId),
    CONSTRAINT fk_campWish_camInfoNum FOREIGN KEY(camInfoNum) REFERENCES campInfo(camInfoNum) ON DELETE CASCADE,
    CONSTRAINT fk_campWish_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);

CREATE TABLE pointRecord( --포인트 입출금 13
    pointNum NUMBER,
    userId VARCHAR2(50) NOT NULL,
    pointMode VARCHAR2(10) NOT NULL,
    pointDate DATE DEFAULT SYSDATE,
    pointAmount NUMBER DEFAULT 0,
    CONSTRAINT pk_pointRecord_pointNum PRIMARY KEY (pointNum),
    CONSTRAINT fk_pointRecord_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE,
    CHECK (pointMode IN('입금', '출금'))
);

CREATE TABLE rental( --렌탈 테이블
    rentNum NUMBER,
    hostId VARCHAR2(50) NOT NULL,
    rentObject VARCHAR2(50) NOT NULL,
    rentFee NUMBER NOT NULL,
    rentStartDate DATE NOT NULL,
    rentEndDate DATE NOT NULL,
    rentHitCount NUMBER NOT NULL,
    rentRegDate VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_rental_rentNum PRIMARY KEY (rentNum),
    CONSTRAINT fk_rental_hostId FOREIGN KEY(hostId) REFERENCES member(userId) ON DELETE CASCADE
);

CREATE TABLE rentPhoto( --렌탈 테이블
    rentNum NUMBER NOT NULL,
    rentPhotoNum NUMBER NOT NULL,
    rentPhotoName VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_rentPhoto_rentPhotoNum PRIMARY KEY (rentPhotoNum),
    CONSTRAINT fk_rentPhoto_rentNum FOREIGN KEY(rentNum) REFERENCES rental (rentNum) ON DELETE CASCADE
);

CREATE SEQUENCE rentPhoto_seq -- 렌탈 사진 시퀀스
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;




CREATE TABLE auction ( -- 경매 테이블 15
     auctionNum NUMBER,
     auctionSaleId VARCHAR2(50),
     auctionObject VARCHAR2(50) NOT NULL,
     auctionStartAmount NUMBER NOT NULL,
     auctionPhotoOriginalname VARCHAR2(50)  NOT NULL,
     auctionRegDate DATE DEFAULT SYSDATE,
     auctionEndDate DATE NOT NULL,
     auctionContent CLOB NOT NULL, 
     auctionFinalAmount NUMBER NOT NULL,
     auctionEnabled NUMBER DEFAULT 1,
     CONSTRAINT pk_auction_auctionNum PRIMARY KEY(auctionNum),
     CONSTRAINT fk_auction_auctionSaleId FOREIGN KEY(auctionSaleId) REFERENCES member(userId),
     CHECK (auctionEnabled IN (0,1))
);


CREATE TABLE auctionRecord ( -- 경매 기록 16
     auctionNum NUMBER NOT NULL,
     auctionUserId VARCHAR2(50) NOT NULL,
     auctionRecAmount NUMBER NOT NULL,
     auctionRecDate DATE DEFAULT SYSDATE,
     auctionConfirm NUMBER NOT NULL,
     CONSTRAINT fk_auctionRecord_auctionNum FOREIGN KEY(auctionNum) REFERENCES auction(auctionNum) ON DELETE CASCADE,
     CONSTRAINT fk_auctionRecord_auctionUserId FOREIGN KEY(auctionUserId) REFERENCES member(userId) ON DELETE CASCADE,
     CHECK (auctionConfirm IN (0,1))
);

CREATE TABLE auctionPhoto ( -- 경매 사진 17
    auctionPhotoNum NUMBER NOT NULL,
    auctionNum NUMBER NOT NULL,
    auctionPhotoName VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_auctionPhoto_auctionPhotoNum PRIMARY KEY(auctionPhotoNum),
    CONSTRAINT fk_auctionPhoto_auctionNum FOREIGN KEY(auctionNum) REFERENCES auction(auctionNum)
);


CREATE TABLE reportAuction ( --경매장 신고 내역 (처리 전) 18
   auctionNum NUMBER NOT NULL,
   reportAuctionId VARCHAR2(50) NOT NULL,
   reportAuctionContent CLOB,
   reportAuctionDate DATE DEFAULT SYSDATE,
   CONSTRAINT fk_reportAuction_auctionNum FOREIGN KEY(auctionNum) REFERENCES auction(auctionNum) ON DELETE CASCADE,
   CONSTRAINT fk_reportAuction_reportAuctionId FOREIGN KEY(reportAuctionId) REFERENCES member(userId) ON DELETE CASCADE

);

CREATE TABLE restrictedMember( --제한된 회원 (처리 후) 19
    userId VARCHAR2(50) NOT NULL,
    restContent CLOB,
    restStartDate DATE DEFAULT SYSDATE,
    restEndDate DATE NOT NULL,
    CONSTRAINT fk_restrictedMember_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);

CREATE TABLE campReviews( -- 전국 캠핑 자랑 20
   camRevNum NUMBER,
   camInfoNum NUMBER,
   userId VARCHAR2(50) NOT NULL,
   camRevSubject VARCHAR2(50) NOT NULL,
   camRevContent CLOB NOT NULL,
   camRevHitCount NUMBER DEFAULT 0,
   camRevRegDate DATE DEFAULT SYSDATE,
   CONSTRAINT pk_campReviews_camRevNum PRIMARY KEY(camRevNum),
   CONSTRAINT fk_campReviews_camInfoNum FOREIGN KEY(camInfoNum) REFERENCES campInfo(camInfoNum) ON DELETE CASCADE,
   CONSTRAINT fk_campReviews_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);


CREATE TABLE campReviewsPhoto ( -- 전국 캠핑 자랑 사진 21
   camRevPhotoNum VARCHAR2(50),
   camRevNum NUMBER NOT NULL,
   camRevPhotoName VARCHAR2(50) NOT NULL,
   CONSTRAINT pk_campReviewsPhoto_camRevPhotoNum PRIMARY KEY (camRevPhotoNum),
   CONSTRAINT fk_campReviewsPhoto_camRevNum FOREIGN KEY(camRevNum) REFERENCES campReviews(camRevNum) ON DELETE CASCADE
);


CREATE TABLE campReviewsReply( --캠핑 자랑 리플 22
    camRevRepNum NUMBER,
    camRevNum NUMBER NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    camRevRepContent CLOB NOT NULL,
    camRevRepRegDate DATE DEFAULT SYSDATE,
    camRevRepPreNum NUMBER,
    CONSTRAINT pk_campReviewsReply_camRevRepNum PRIMARY KEY (camRevRepNum),
    CONSTRAINT fk_campReviewsReply_camRevNum FOREIGN KEY (camRevNum) REFERENCES campReviews(camRevNum) ON DELETE CASCADE,
    CONSTRAINT fk_campReviewsReply_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);


CREATE TABLE campMate( --캠핑 메이트 23
    camMateNum NUMBER,
    camInfoNum NUMBER,
    userId VARCHAR2(50) NOT NULL,
    camMateSubject VARCHAR2(50) NOT NULL,
    camMateContent  CLOB NOT NULL,
    camMateHitCount NUMBER DEFAULT 0,
    camMateStartDate DATE NOT NULL,
    camMateEndDate DATE NOT NULL,
    camMateRegDate DATE DEFAULT SYSDATE,
    camMateDues NUMBER,
    CONSTRAINT pk_campMate_camMateNum PRIMARY KEY (camMateNum),
    CONSTRAINT fk_campMate_campInfoNum FOREIGN KEY(camInfoNum) REFERENCES campInfo(camInfoNum) ON DELETE CASCADE,
    CONSTRAINT fk_campMate_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);

CREATE TABLE campStyle( --캠핑 스타일 24
    camStyle VARCHAR2(50),
    CONSTRAINT pk_campStyle_camStyle PRIMARY KEY(camStyle)
);

CREATE TABLE campMateStyle( --캠핑메이트의 스타일 25
    camMateNum NUMBER NOT NULL, 
    camStyle VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_campMateStyle_camMateNum FOREIGN KEY(camMateNum) REFERENCES campMate(camMateNum) ON DELETE CASCADE,
    CONSTRAINT fk_campMateStyle_camStyle FOREIGN KEY(camStyle) REFERENCES campStyle(camStyle) ON DELETE CASCADE
);


CREATE TABLE campMateApply( --메이트 지원 26
    camMateNum NUMBER,
    userid VARCHAR2(50) NOT NULL,
    camMateAppContent CLOB NOT NULL,
    camMateAppDate DATE DEFAULT SYSDATE,
    camMateAppGender VARCHAR2(50) NOT NULL,
    camMateAppAge NUMBER NOT NULL,
    camMateAppConfirm VARCHAR2(50) NOT NULL,
    CONSTRAINT fk_campMateApply_camMateNum FOREIGN KEY(camMateNum) REFERENCES campMate(camMateNum) ON DELETE CASCADE,
    CONSTRAINT fk_campMateApply_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE,
    CHECK (camMateAppGender IN ('M','F')),
    CHECK (camMateAppConfirm IN (0, 1))
);

CREATE TABLE campChat( --캠핑 수다 27
    camChatNum NUMBER,
    userId VARCHAR2(50) NOT NULL,
    camChatSubject VARCHAR2(50) NOT NULL,
    camChatContent CLOB NOT NULL,
    camChatHitCount NUMBER DEFAULT 0,
    camChatRegDate DATE DEFAULT SYSDATE,
    CONSTRAINT pk_campChat_camChatNum PRIMARY KEY(camChatNum),
    CONSTRAINT fk_campChat_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);


CREATE TABLE campChatReply( --캠핑수다 리플 테이블 28
    camChatRepNum NUMBER,
    camChatNum NUMBER NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    camChatRepContent CLOB NOT NULL,
    camChatRepDate DATE DEFAULT SYSDATE,
    camChatRepPreNum NUMBER,
    CONSTRAINT pk_campChatReply_camChatRepNum PRIMARY KEY(camChatRepNum),
    CONSTRAINT fk_campChatReply_camChatNum FOREIGN KEY(camChatNum) REFERENCES campChat(camChatNum) ON DELETE CASCADE,
    CONSTRAINT fk_campChatReply_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE    
);

CREATE TABLE campChatLike( --캠핑 수다 좋아요 테이블 29
    camChatNum NUMBER NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    PRIMARY KEY (camChatNum,userId),
    CONSTRAINT fk_campChatLike_camChatNum FOREIGN KEY(camChatNum) REFERENCES campChat(camChatNum) ON DELETE CASCADE,
    CONSTRAINT fk_campChatLike_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);

CREATE TABLE notice ( --공지사항 30
    noticeNum NUMBER,
    adminId VARCHAR2(50) NOT NULL,
    noticeSubject VARCHAR2(50) NOT NULL,
    noticeContent CLOB NOT NULL,
    noticeHitCount NUMBER DEFAULT 0,
    noticeRegDate DATE DEFAULT SYSDATE,
    CONSTRAINT pk_notice_noticeNum PRIMARY KEY(noticeNum),
    CONSTRAINT fk_notice_adminId FOREIGN KEY(adminId) REFERENCES admin(adminId) ON DELETE CASCADE
);

CREATE TABLE noticePhoto ( --공지사항 사진 31
    noticePhotoNum NUMBER,
    noticePhotoName VARCHAR2(50) NOT NULL,
    noticeNum NUMBER NOT NULL,
    CONSTRAINT pk_noticePhoto_noticePhotoNum PRIMARY KEY(noticePhotoNum),
    CONSTRAINT fk_noticePhoto_noticeNum FOREIGN KEY(noticeNum) REFERENCES notice(noticeNum) ON DELETE CASCADE
);

CREATE TABLE campReviewsLike( -- 캠핑 수다 좋아요 32
    camRevNum NUMBER NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    PRIMARY KEY (camRevNum,userId),
    CONSTRAINT fk_campLike_camReviewsNum FOREIGN KEY(camRevNum) REFERENCES campReviews(camRevNum) ON DELETE CASCADE,
    CONSTRAINT fk_campLike_userId FOREIGN KEY(userId) REFERENCES member(userId) ON DELETE CASCADE
);


drop table campChatLike purge;
drop table campChatReply purge;
drop table campChat purge;
drop table campMateApply purge;
drop table campMateStyle purge;
drop table campStyle purge;
DROP TABLE campMate purge;
drop table campReviewsReply purge;
drop table campReviewsPhoto purge;
drop table campReviews purge;
drop table restrictedMember purge;
drop table reportAuction purge;
drop table auctionPhoto purge;
drop table auctionRecord purge;
drop table auction purge;
drop table rental purge;
drop table pointRecord purge;
drop table campWish purge;
drop table keywordName purge;
drop table campPhoto purge;
drop table campInfo purge;
drop table qna purge;
drop table qnaPhoto purge;
drop table qnaFile purge;
drop table message purge;
drop table member purge;

