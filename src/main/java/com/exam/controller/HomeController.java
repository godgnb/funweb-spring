package com.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {
	
	@GetMapping("/")
	public String index() {
		return "main/main";
	}
	
	
	@GetMapping("/company/welcome")
	public void welcome() {
		// 리턴타입이 void면 url주소 경로의 뷰jsp를 실행
		log.info("welcome() 호출됨...");
	}
	
	
	@GetMapping("/company/history")
	public void history() {
		log.info("history() 호출됨...");
	}
	
} // HomeController class
