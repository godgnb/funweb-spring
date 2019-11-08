package com.exam.domain;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

// Value Object(VO) = 자바빈(Java Bean)클래스 = DTO(Data Transfer Object)
@Data
public class MemberVO {
	private String id;
	private String passwd;
	private String name;
	private String email;
	private String address;
	private String tel;
	private String mtel;
	private Timestamp regDate;
	
	// 컨트롤러에서 파라미터값 birthday를 "1997-02-27" 받으면
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
}
