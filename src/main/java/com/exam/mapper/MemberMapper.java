package com.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.exam.domain.MemberVO;

public interface MemberMapper {

	@Select("SELECT COUNT(*) AS cnt FROM jspdb.member WHERE id = #{id}")
	public int countMemberById(String id);
	
	
	public void insertMember(MemberVO vo);
	
	@Select("SELECT * FROM jspdb.member WHERE id = #{id}")
	public MemberVO getMemberById(String id);
	
	// 전체회원정보 가져오기
	@Select("SELECT * FROM jspdb.member ORDER BY id ASC")
	public List<MemberVO> getMembers();
	
	@Update("UPDATE jspdb.member SET name=#{name}, age=#{age}, gender=#{gender}, email=#{email} WHERE id=#{id}")
	public void updateMember(MemberVO memberVO);
	
	@Delete("DELETE FROM jspdb.member WHERE id= #{id}")
	public void deleteMember(String id);
	
	
}
