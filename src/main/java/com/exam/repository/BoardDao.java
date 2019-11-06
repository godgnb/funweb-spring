package com.exam.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.exam.domain.BoardVO;
import com.exam.mapper.BoardMapper;

public class BoardDao {

	private static BoardDao instance = new BoardDao();
	
	public static BoardDao getInstance() {
		return instance;
	}

	private BoardDao() {
	}
	
	// insert할 레코드의 번호 생성 메소드
	public int nextBoardNum() {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			int bnum = mapper.nextBoardNum();
			
			return bnum;
		}
	} // nextBoardNum
	
	
	// 게시글 한개 등록하는 메소드
	public void insertBoard(BoardVO boardVO) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			// 인터페이스를 구현한 Mapper 프록시 객체를 만들어서 리턴함
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			mapper.insertBoard(boardVO);
			sqlSession.commit();
		}
		// try블록이 끝나명 SqlSession 변수의 close() 메소드 자동호출함.
	} // insertBoard

	
	// 검색어로 검색된 행의 시작행번호부터 갯수만큼 가져오기(페이징)
	public List<BoardVO> getBoards(int startRow, int pageSize, String search) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			List<BoardVO> boardList = mapper.getBoards(startRow, pageSize, search);
			
			return boardList;
		}
	} // getBoards method
	
	
		
	// 게시판(jspdb.board) 테이블 레코드 개수 가져오기 메소드
	public int getBoardCount(String search) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			return mapper.getBoardCount(search);
		}
	} // getBoardCount method
		
	
	// 특정 레코드의 조회수를 1 증가시키는 메소드
	public void updateReadcount(int num) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			mapper.updateReadcount(num);
			sqlSession.commit();
		}
	} // updateReadcount method
	

	// 글 한개를 가져오는 메소드 
	public BoardVO getBoard(int num) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			
			return mapper.getBoard(num);
		}
	} // getBoard method
	
	
	// 게시글 패스워드비교(로그인 안한 사용자가 수행함)
	public boolean isPasswdEqual(int num, String passwd) {
		boolean result = false;
		
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			int count = sqlSession.getMapper(BoardMapper.class).countByNumAndPasswd(num, passwd);
			if (count == 1) {
				result = true; // 게시글 패스워드 같음
			} else { // count == 0
				result = false; // 게시글 패스워드 다름
			}
			
		}
		return result;
	} // isPasswdEqual method
	
	
	// 게시글 수정하기 메소드
	public void updateBoard(BoardVO boardVO) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			sqlSession.getMapper(BoardMapper.class).updateBoard(boardVO);
			
			sqlSession.commit();
		}
	} // updateBoard method
	
	
	// 글번호에 해당하는 글 한개 삭제하기 메소드
	public void deleteBoard(int num) {
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			sqlSession.getMapper(BoardMapper.class).deleteBoard(num);
			
			sqlSession.commit();
		}
	} // deleteBoard method
	
	
/*
num		subject				reRef		reLev	   [reSeq]
==========================================================
 6		 주글3				  6			  0			  0
 4		 주글2				  4			  0			  0
 5		  ㄴ답글2			  4			  1			  1
 1		 주글1				  1			  0			  0
[7]		  ㄴ답글2			  1			  1			  1
 2		  ㄴ답글1			  1			  1			  1+1=2
 3			 ㄴ답글1_1		  1			  2			  2+1=3
 
  
*/
	// 답글쓰기 메소드(update 이후 insert)
	// 트랜잭션 처리가 요구됨(안전하게 처리하려는 목적)
	public boolean reInsertBoard(BoardVO boardVO) {
		boolean isInserted = false; // 답글쓰기 성공여부
		
		// Connection 가져오기
		try (SqlSession sqlSession = DBManager.getSqlSessionFactory().openSession()) {
			BoardMapper mapper = sqlSession.getMapper(BoardMapper.class);
			// 같은 글그룹에서 답글순서(re_seq) 재배치
			// 	조건 re_ref 같은그룹	re_seq 큰값 re_seq+1
			mapper.updateReplyGroupSequence(boardVO.getReRef(), boardVO.getReSeq());
			
			// 답글 insert	re_ref그대로	re_lev+1	re_seq+1
			// re_lev는 [답글을 다는 대상글]의 들여쓰기값 + 1
			boardVO.setReLev(boardVO.getReLev() + 1);
			// re_seq는 [답글을 다는 대상글]의 글그룹 내 순번값 + 1
			boardVO.setReSeq(boardVO.getReSeq() + 1);
			// 답글 insert 수행
			mapper.insertBoard(boardVO);
			
			// 트랜잭션 작업 모두 성공적으로 수행되면 commit
			sqlSession.commit();
		}
		// insert, update, delete문 수행후에는
		// sqlSession.commit(); 메소드를 호출해야 DB에 반영됨.
		
		// sqlSession.commit();이 호출되지 않은 상태에서
		// sqlSession.close() 이 호출되면 rollback 처리되어
		// DB에 적용되지 않음.
		return isInserted;
	} // reInsertBoard method
	
} // BoardDao class
