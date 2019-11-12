package com.exam.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exam.domain.AttachVO;
import com.exam.domain.BoardVO;
import com.exam.service.AttachService;
import com.exam.service.BoardService;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/board/*")
@Log4j
public class FileBoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private AttachService attachService;
	
	@GetMapping("/fileList")
	public String fileList(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "", required = false) String search,
			Model model) {
		
		log.info("pageNum : " + pageNum);
		
		// ===========================================
		// 한 페이지에 해당하는 글목록 구하기 작업
		// ===========================================	

		// 한페이지(화면)에 보여줄 글 개수
		int pageSize = 5;

		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize; 

		
		// 글목록 가져오기 메소드 호출
		List<BoardVO> boardList = boardService.getBoards(startRow, pageSize,search);
		
		
		// ===========================================
		// 페이지 블록 관련정보 구하기 작업
		// ===========================================
		
		// board테이블 전체글개수 가져오기 메소드
		int count = boardService.getBoardCount(search);
		
		// 총 페이지 개수 구하기
		//	전체 글개수 / 한페이지당 글개수 (+ 1 : 나머지 있을때)
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
		
		// 페이지블록 수 설정
		int pageBlock = 5;
		
		// 시작페이지번호 구하기
		// pageNum값이 1~5 사이면 -> 시작페이지는 항상 1이 나와야 함
		
		// ((1 - 1) / 5) * 5 + 1 -> 1
		// ((2 - 1) / 5) * 5 + 1 -> 1
		// ((3 - 1) / 5) * 5 + 1 -> 1
		// ((4 - 1) / 5) * 5 + 1 -> 1
		// ((5 - 1) / 5) * 5 + 1 -> 1
		
		// ((6 - 1) / 5) * 5 + 1 -> 6
		// ((7 - 1) / 5) * 5 + 1 -> 6
		// ((8 - 1) / 5) * 5 + 1 -> 6
		// ((9 - 1) / 5) * 5 + 1 -> 6
		// ((10 - 1) / 5) * 5 + 1 -> 6
		int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
		
		// 끝페이지번호 endPage 구하기
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		// 페이지블록 관련정보를 Map 또는 VO 객체로 준비
		Map<String, Integer> pageInfoMap = new HashMap<String, Integer>();
		pageInfoMap.put("count", count);
		pageInfoMap.put("pageCount", pageCount);
		pageInfoMap.put("pageBlock", pageBlock);
		pageInfoMap.put("startPage", startPage);
		pageInfoMap.put("endPage", endPage);
		
		// 뷰(jsp)에 사용할 데이터를 request 영역개체에 저장
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageInfoMap", pageInfoMap);
		model.addAttribute("search", search);
		model.addAttribute("pageNum", pageNum);
		
		return "center/fnotice";
	} // fileList get
	
	
	@GetMapping("/fileWrite")
	public String fileWrite(HttpSession session) {
		String id =(String) session.getAttribute("id");
		
		if (id == null) {
			 return "redirect:/board/fileList";
		}
		return "center/fwrite";
		
	} // fileWrite get
	
	
	@PostMapping("/fileWrite")
	public String fileWrite(MultipartFile[] files, BoardVO boardVO, HttpServletRequest request) throws Exception {// 넘어오는 파일이 한개일경우 MultipartFile files, 여러개일 경우 MultipartFile[] files 배열 사용
		if (files != null) {
			log.info("file.length : " + files.length);
		}
		
		// IP주소 값 저장
		boardVO.setIp(request.getRemoteAddr());

		// 게시글 번호 생성하는 메소드 호출
		int num = boardService.nextBoardNum();
		// 생성된 번호를 자바빈 글번호 필드에 설정
		boardVO.setNum(num);
		boardVO.setReadcount(0); // 조회수 0

		// 주글일 경우
		boardVO.setReRef(num); // [글그룹번호]는 글번호와 동일함.
		boardVO.setReLev(0); // [들여쓰기 레벨] 0
		boardVO.setReSeq(0); // [글그룹 안에서의 순서] 0
		
		//=================== boardVO 준비 완료 ===================
		
		//=================== Upload 시작 ===================
		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath("/resources/upload");
		log.info("realPath : " + realPath);
		
		// 폴더 동적 생성하기 /resources/upload/2019/11/11
		File uploadpath = new File(realPath, getFolder());
		log.info("uploadpath : " + uploadpath);
		if (!uploadpath.exists()) {
			uploadpath.mkdirs(); // 업로드할 폴더 생성
		}
		
		List<AttachVO> attachList = new ArrayList<AttachVO>();
		
		for (MultipartFile multipartFile : files) {
			log.info("파일명: " + multipartFile.getOriginalFilename());
			log.info("파일크기: " + multipartFile.getSize());
			
			if (multipartFile.isEmpty()) {
				continue;
			}
			String uploadFileName = multipartFile.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			log.info("최종 업로드 파일명: " + uploadFileName);
			
			File saveFile = new File(uploadpath, uploadFileName);
			
			multipartFile.transferTo(saveFile); // 파일 업로드 수행
			
			//=====================================================
			
			// attach 테이블에 insert할 AttachVO를 리스츠로 준비하기
			AttachVO attachVO = new AttachVO();
			attachVO.setBno(boardVO.getNum());
			attachVO.setUuid(uuid.toString());
			attachVO.setUploadpath(getFolder());
			attachVO.setFilename(multipartFile.getOriginalFilename());
			
			if (isImageType(saveFile)) { // Image file
				// 섬네일 이미지 생성하기
				File thumbnailFile = new File(uploadpath, "s_" + uploadFileName);
				
				try(FileOutputStream fos = new FileOutputStream(thumbnailFile)) {
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), fos, 100, 100);
				}
				
				attachVO.setFiletype("I");
			} else { // Other file
				attachVO.setFiletype("O");
			}
			
			attachList.add(attachVO);
		}
		
		// 테이블 insert : board테이블과 attach테이블 트랜잭션으로 insert
		boardService.insertBoardAndAttaches(boardVO, attachList);
		
		return "redirect:/board/fileList";
	} // fileWrite post
	
	private boolean isImageType(File file) throws IOException {
		boolean isImageType = false;
		
		String contentType = Files.probeContentType(file.toPath());
		log.info("contentType : " + contentType);
		if (contentType != null) {
			isImageType = contentType.startsWith("image");
		} else {
			isImageType = false;
		}
		
		return isImageType;
	} // isImageType method
	
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str; // ex) "2019/11/11"
	} // getFolder method
	
	
	@GetMapping("/fileContent")
	public String fileContent(int num, @ModelAttribute("pageNum") String pageNum, Model model) {
		// 조회수 1증가시키는 메소드 호출
		boardService.updateReadcount(num);

		//글번호에 해당하는 레코드 한개 가져오기
		BoardVO boardVO = boardService.getBoard(num);
		List<AttachVO> attachList = attachService.getAttaches(num);
		
		// request 영역개체에 저장
		model.addAttribute("board", boardVO);
		model.addAttribute("attachList", attachList);
		
		return "center/fcontent";
	} // fileContent get
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) throws Exception {
		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath("/resources/upload");
		
		Resource resource = new FileSystemResource(realPath + "/" + fileName);
		
		if (!resource.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
		}
		
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		String downloadName = "";
		downloadName = new String(resourceOriginalName.getBytes("utf-8"));
		
		headers.add("Content-Disposition", "attachment: filename=" + downloadName);
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	} // download method
	
	
	
} // FileBoardController class
