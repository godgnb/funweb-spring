package com.exam.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.exam.domain.MemberVO;
import com.exam.mapper.MemberMapper;


public class MemberDao {

	private static MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}
	
	private MemberDao() {
	}
	
	// 아이디 중복여부 확인 
	public boolean isIdDuplicated(String id) {
		// 중복이면 true, 중복아니면 false
		boolean isIdDuplicated = false;
		
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 아이디중복 확인
		int count = mapper.countMemberById(id);
		if (count > 0) {
			isIdDuplicated = true;
		}
		// JDBC 자원 닫기
		sqlSession.close();
		
		return isIdDuplicated;
	} // isIdDuplicated method
	
	
	
	public void insertMember(MemberVO vo) {
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 회원 가입(추가)
		mapper.insertMember(vo);
		// 커밋(JDBC수동커밋 사용할때)
		sqlSession.commit();
		// JDBC 자원 닫기
		sqlSession.close();
		
	} // insertMember
	
	
	public MemberVO getMember(String id) {
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 회원 정보 가져오기
		MemberVO memberVO = mapper.getMemberById(id);
		// JDBC 자원 닫기
		sqlSession.close();
		
		return memberVO;
	} // getMember
	
	
	
	public int userCheck(String id, String passwd) {
		// -1 아이디 불일치. 0 패스워드 불일치. 1 일치함
		int check = -1; 
		
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 회원 정보 가져오기
		MemberVO memberVO = mapper.getMemberById(id);
		if (memberVO != null) {
			if (passwd.equals(memberVO.getPasswd())) {
				check = 1; // 아이디, 패스워드 일치
			} else { 
				check = 0; // 패스워드 불일치
			}
		} else { // memberVO == null
			check = -1; // 아이디 없음
		}
		
		// JDBC 자원 닫기
		sqlSession.close();
		
		return check;
	} // userCheck method
	
	
	
	// 전체회원정보 가져오기
	public List<MemberVO> getMembers() {
		
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 전체 회원 정보 리스트 가져오기
		List<MemberVO> list = mapper.getMembers();
		// JDBC 자원 닫기
		sqlSession.close();
		
		return list;
	} // getMembers method
	

	// 회원정보 수정하기 메소드
	// 매개변수 memberVO에 passwd필드는 수정의 대상이 아니라
	// 본인 확인 용도로 사용
	public void updateMember(MemberVO memberVO) {
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 회원 정보 수정
		mapper.updateMember(memberVO);
		// JDBC 자원 닫기
		sqlSession.close();
	} // updateMember
	
	
	public void deleteMember(String id) {
		// Connection 가져오기
		SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession();
		// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
		MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
		// 회원 정보 수정
		mapper.deleteMember(id);
		// JDBC 자원 닫기
		sqlSession.close();
	} // deleteMember
	
} // MemberDao class
