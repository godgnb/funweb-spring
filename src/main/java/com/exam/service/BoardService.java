package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.domain.BoardVO;
import com.exam.mapper.BoardMapper;

import lombok.extern.log4j.Log4j;

@Service
@Transactional
@Log4j
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	// insert할 레코드의 번호 생성 메소드
	public int nextBoardNum() {
		int bnum = boardMapper.nextBoardNum();
		return bnum;
	} // nextBoardNum
	
	
	// 게시글 한개 등록하는 메소드
	public void insertBoard(BoardVO boardVO) {
		boardMapper.insertBoard(boardVO);
	} // insertBoard

	
	// 검색어로 검색된 행의 시작행번호부터 갯수만큼 가져오기(페이징)
	public List<BoardVO> getBoards(int startRow, int pageSize, String search) {
		List<BoardVO> boardList = boardMapper.getBoards(startRow, pageSize, search);
		return boardList;
	} // getBoards method
	
	
	// 게시판(jspdb.board) 테이블 레코드 개수 가져오기 메소드
	public int getBoardCount(String search) {
		return boardMapper.getBoardCount(search);
	} // getBoardCount method
		
	
	// 특정 레코드의 조회수를 1 증가시키는 메소드
	public void updateReadcount(int num) {
		boardMapper.updateReadcount(num);
	} // updateReadcount method
	

	// 글 한개를 가져오는 메소드 
	public BoardVO getBoard(int num) {
		return boardMapper.getBoard(num);
	} // getBoard method
	
	
	// 게시글 패스워드비교(로그인 안한 사용자가 수행함)
	public boolean isPasswdEqual(int num, String passwd) {
		log.info("num: " + num + ", passwd: " + passwd);
		
		boolean result = false;
		
		int count = boardMapper.countByNumAndPasswd(num, passwd);
		if (count == 1) {
			result = true; // 게시글 패스워드 같음
		} else { // count == 0
			result = false; // 게시글 패스워드 다름
		}
			
		return result;
	} // isPasswdEqual method
	
	
	// 게시글 수정하기 메소드
	public void updateBoard(BoardVO boardVO) {
		boardMapper.updateBoard(boardVO);
	} // updateBoard method
	
	
	// 글번호에 해당하는 글 한개 삭제하기 메소드
	public void deleteBoard(int num) {
		boardMapper.deleteBoard(num);
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
	public void reInsertBoard(BoardVO boardVO) {
		
		// 같은 글그룹에서 답글순서(re_seq) 재배치
		// 	조건 re_ref 같은그룹	re_seq 큰값 re_seq+1
		boardMapper.updateReplyGroupSequence(boardVO.getReRef(), boardVO.getReSeq());
		
		// 답글 insert	re_ref그대로	re_lev+1	re_seq+1
		// re_lev는 [답글을 다는 대상글]의 들여쓰기값 + 1
		boardVO.setReLev(boardVO.getReLev() + 1);
		// re_seq는 [답글을 다는 대상글]의 글그룹 내 순번값 + 1
		boardVO.setReSeq(boardVO.getReSeq() + 1);
		
		// 답글 insert 수행
		boardMapper.insertBoard(boardVO);
			
	} // reInsertBoard method
	
} // BoardService class
