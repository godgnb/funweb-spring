package com.exam.controller;

import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.domain.MemberVO;
import com.exam.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired // 타입으로 의존객체 찾아서 주입해줌.
	private MemberService memberService;
	
	
	//@RequestMapping(value = "/join", method = RequestMethod.GET)
	@GetMapping("/join")
	public String join() {
		return "member/join";
	} // join get
	
	
	@PostMapping("/join")
	public String join(MemberVO memberVO) {
		// 프론트컨트롤러인 DispatcherServlet이
		// 매개변수타입 확인해서 MemberVO 객체생성후
		// 요청 파라미터 채워서 넣어줌.

		// 가입날짜 생성해서 자바빈에 저장
		memberVO.setRegDate(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(memberVO);
		
		// 회원가입 처리
		memberService.insertMember(memberVO);
		
		// 로그인 페이지로 이동하는 정보 생성해서 리턴
		return "redirect:/member/login";
	} // join post
	
	
	@GetMapping("/login")
	public String login() {
		return "member/login"; // 서버에서 바로 실행할 뷰jsp이름 리턴
	} // login get
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(String id, String passwd, String rememberMe, HttpSession session, HttpServletResponse response) {
		// 사용자 확인 메소드 호출
		int check = memberService.userCheck(id, passwd);
		// check == 1 로그인 인증(세션값생성 "id"). index.jsp로 이동
		// check == 0 "패스워드틀림" 뒤로이동
		// check == -1 "아이디없음" 뒤로이동
		
		if (check != 1) { // 로그인 실패일때
			String message ="";
			if (check == 0) {
				message = "패스워드가 다릅니다.";
			} else if (check == -1) {
				message = "존재하지 않는 아이디입니다.";
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("alert('" + message + "');");
			sb.append("history.back();");
			sb.append("</script>");
			
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		} // 로그인 실패일때
		
		// 로그인 성공일때
		// 로그인 인증
		session.setAttribute("id", id);
		
		// 로그인 상태유지 여부확인 후
		// 쿠키객체 생성해서 응답시 보내기
		if (rememberMe != null && rememberMe.equals("true")) {
			Cookie cookie = new Cookie("id", id);
			cookie.setMaxAge(60*10); // 초단위. 10분 = 60초 * 10 = 600초
			cookie.setPath("/");
			response.addCookie(cookie); // 응답객체에 추가
		}
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/"); // redirect 경로 위치 지정
		// 리다이렉트일 경우 HttpStatus.FOUND 지정해야함.
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
		
	} // login post
	
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		// 세션값 초기화
		session.invalidate();
		
		// 로그인 상태유지용 쿠키 삭제하기
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("id")) {
					cookie.setMaxAge(0); // 쿠키 유효기간 0초로 설정. -> 브라우저가 해당쿠키를 삭제처리함.
					cookie.setPath("/"); // 쿠키경로도 동일해야함
					response.addCookie(cookie);
				}
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('로그아웃 되었습니다.');");
		sb.append("location.href = '/';");
		sb.append("</script>");
		
		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	} // logout
	
	
	@GetMapping("joinIdDupCheck")
	public String joinIdDupCheck(String userid, Model model) {
		System.out.println("userid: " + userid);
		
		// 아이디 중복확인 메소드 호출
		boolean isIdDup = memberService.isIdDuplicated(userid);
		
		// model 객체에 View(JSP)에서 사용할 데이터 저장(싣기)
		model.addAttribute("isIdDup", isIdDup);
		model.addAttribute("userid", userid);
		
		return "member/joinIdDupCheck";
	} // joinIdDupCheck
	
	
	@GetMapping("/joinIdDupCheckJson")
	@ResponseBody // @ResponseBody 애노테이션으로 리턴값을 JSON형식으로 응답준다
	public boolean joinIdDupCheckJson(@RequestParam("id") String userid) {
		// 아이디 중복확인 메소드 호출
		boolean isIdDup = memberService.isIdDuplicated(userid);
		
		return isIdDup;
	} // joinIdDupCheckJson
	
	
	
	
} // MemberController class
