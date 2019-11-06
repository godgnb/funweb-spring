package com.exam.domain;

import java.sql.Timestamp;

public class BoardVO {
	private int num; // 게시글 번호
	private String username; // 로그인아이디 또는 작성자이름
	private String passwd; // 게시글 패스워드, 로그인안한사용자가 입력함
	private String subject;
	private String content;
	private int readcount;
	private String ip;
	private Timestamp regDate;
	private int reRef;
	private int reLev;
	private int reSeq;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp reg_date) {
		this.regDate = reg_date;
	}
	public int getReRef() {
		return reRef;
	}
	public void setReRef(int reRef) {
		this.reRef = reRef;
	}
	public int getReLev() {
		return reLev;
	}
	public void setReLev(int reLev) {
		this.reLev = reLev;
	}
	public int getReSeq() {
		return reSeq;
	}
	public void setReSeq(int reSeq) {
		this.reSeq = reSeq;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardVO [num=").append(num).append(", username=").append(username).append(", passwd=")
				.append(passwd).append(", subject=").append(subject).append(", content=").append(content)
				.append(", readcount=").append(readcount).append(", ip=").append(ip).append(", reg_date=")
				.append(regDate).append(", reRef=").append(reRef).append(", reLev=").append(reLev).append(", reSeq=")
				.append(reSeq).append("]");
		return builder.toString();
	}
}
