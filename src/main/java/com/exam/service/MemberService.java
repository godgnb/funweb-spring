package com.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.domain.MemberVO;
import com.exam.mapper.MemberMapper;

//@Component 가 확장된 애노테이션
//@Controller, @Service, @Repository
@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	public int insertMember(MemberVO memberVO) {
		return memberMapper.insertMember(memberVO);
	} // insertMember
	
	
	public int userCheck(String id, String passwd) {
		// -1 아이디 불일치. 0 패스워드 불일치. 1 일치함
		int check = -1;
		
		// 회원 정보 가져오기
		MemberVO memberVO = memberMapper.getMemberById(id);
		if (memberVO != null) {
			if (passwd.equals(memberVO.getPasswd())) {
				check = 1; // 아이디, 패스워드 일치
			} else { 
				check = 0; // 패스워드 불일치
			}
		} else { // memberVO == null
			check = -1; // 아이디 없음
		}
		return check;
	} // userCheck
	
	
	// 아이디 중복여부 확인 
	public boolean isIdDuplicated(String id) {
		// 중복이면 true, 중복아니면 false
		boolean isIdDuplicated = false;
		
		// 아이디중복 확인
		int count = memberMapper.countMemberById(id);
		if (count > 0) {
			isIdDuplicated = true;
		}
		
		return isIdDuplicated;
		
	} // isIdDuplicated
	
	
	
} // MemberService class
