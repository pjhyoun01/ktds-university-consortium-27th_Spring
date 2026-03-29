package com.ktdsuniversity.edu.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("create")
	public String doCreateMember() {
		
		return "member/create";
	}
	
	@PostMapping("create")
	public String doCreateMember(MemberVO memberVO) {
		boolean isCreate = this.memberService.createMember(memberVO);
		if (isCreate) {
			return "redirect:/board/view";
		} else {
			return "error/404";
		}
		
	}
}
