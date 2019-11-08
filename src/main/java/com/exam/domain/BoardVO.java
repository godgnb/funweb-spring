package com.exam.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private int num; // 게시글 번호
	private String username; // 로그인아이디 또는 작성자이름
	private String passwd; // 게시글 패스워드, 로그인안한사용자가 입력함
	private String subject;
	private String content;
	private int readcount;
	private String ip;
	private Date regDate;
	private int reRef;
	private int reLev;
	private int reSeq;
	
}
