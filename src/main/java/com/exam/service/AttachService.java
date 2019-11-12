package com.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.domain.AttachVO;
import com.exam.mapper.AttachMapper;

@Service
@Transactional // ex) 메소드 실행 중 5개중 하나라도 오류가 나면 롤백시켜버림
public class AttachService {
	
	@Autowired
	private AttachMapper attachMapper;
	
	public void insertAttach(AttachVO attachVO) {
		attachMapper.insertAttach(attachVO);
	}
	
	public void insertAttach(List<AttachVO> attachList) {
		for (AttachVO attachVO : attachList) {
			attachMapper.insertAttach(attachVO);
		}
	}
	
	public List<AttachVO> getAttaches(int bno) {
		return attachMapper.getAttaches(bno);
	}
	
	public void deleteAttachByBno(int bno) {
		attachMapper.deleteAttachByBno(bno);
	}
	
	public void deleteAttachByUuid(String uuid) {
		attachMapper.deleteAttachByUuid(uuid);
	}
	
} // AttachService class
