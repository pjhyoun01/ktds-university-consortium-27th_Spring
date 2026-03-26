package com.ktdsuniversity.edu;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestEndPointController {

//	@GetMapping("/hello")
//	public ResponseEntity<String> veiwHelloHtml() {
//		
//		return new ResponseEntity<>("Hello", HttpStatus.OK);
//	}
//	
//	@GetMapping("/")
//	public ResponseEntity<String> viewMain() {
//		
//		return new ResponseEntity<>("첫 페이지 입니다 환영합니다", HttpStatus.OK);
//	}
//	
//	@GetMapping("/jsp")
//	public String viewJspPage(Model model) {
//		System.out.println(model);
//		model.addAttribute("name", "박재현");
//		System.out.println(model);
//		return "hello";
//	}
}
