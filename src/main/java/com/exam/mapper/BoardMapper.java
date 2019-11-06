package com.exam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.exam.domain.BoardVO;

public interface BoardMapper {

	// insert할 레코드의 번호 생성 메소드
	public int nextBoardNum();
	
	// 게시글 한개 등록하는 메소드
	public int insertBoard(BoardVO boardVO);
	
	// 검색어로 검색된 행의 시작행번호부터 갯수만큼 가져오기(페이징)
	// 매개변수가 2개 이상일때는 @Param("설정값이름") 이름 설정하기
	public List<BoardVO> getBoards(@Param("startRow") int startRow, @Param("pageSize") int pageSize, @Param("search") String search);
	
	// 게시판(jspdb.board) 테이블 레코드 개수 가져오기 메소드
	public int getBoardCount(String search);
	
	// 특정 레코드의 조회수를 1 증가시키는 메소드
	public void updateReadcount(int num);
	
	// 글 한개를 가져오는 메소드
	public BoardVO getBoard(int num);
	
	// 게시글 패스워드비교(로그인 안한 사용자가 수행함)
	public int countByNumAndPasswd(@Param("num") int num, @Param("passwd") String passwd);
	
	// 게시글 수정하기 메소드
	public void updateBoard(BoardVO boardVO);
	
	// 글번호에 해당하는 글 한개 삭제하기 메소드
	public void deleteBoard(int num);
	
	// 답글쓰기 메소드(update 이후 insert)
	// 트랜잭션 처리가 요구됨(안전하게 처리하려는 목적)
	public int updateReplyGroupSequence(@Param("reRef") int reRef, @Param("reSeq") int reSeq);
	
} // BoardMapper interface
