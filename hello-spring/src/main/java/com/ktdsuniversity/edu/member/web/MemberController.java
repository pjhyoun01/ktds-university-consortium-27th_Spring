package com.ktdsuniversity.edu.member.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;


@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@GetMapping("view")
	public String viewMemberById(Model model) {
		List<MemberVO> memberList = this.memberService.readAllMember();
		model.addAttribute("memberList", memberList);
		return "member/list";
	}
	
	@GetMapping("view/{email}")
	public String viewMemberById(@PathVariable String email, Model model) {
		MemberVO oneMemberById = this.memberService.readMemberByEmail(email);
		if (oneMemberById == null) {
			return "404";
		}
		model.addAttribute("member", oneMemberById);
		
		return "member/view";
	}

	@GetMapping("create")
	public String doCreateMember() {
		return "member/create";
	}

	@PostMapping("create")
	public String doCreateMember(MemberVO memberVO) {
		boolean isCreate = this.memberService.createMember(memberVO);
		if (isCreate) {
			return "redirect:/member/view";
		} else {
			return "error/404";
		}
	}

	@GetMapping("update/{email}")
	public String viewUpdateMemberPage(@PathVariable String email, Model model) {
		MemberVO oneMemberById = this.memberService.readMemberByEmail(email);
		model.addAttribute("member", oneMemberById);
		
		return "member/update";
	}

	@PostMapping("update/{email}")
	public String doUpdateMemberById(MemberVO memberVO) {
		
		System.out.println(memberVO.getEmail());
		boolean updateSuccess = this.memberService.updateMemberByEmail(memberVO);
		System.out.println("updateSuccess" + updateSuccess);
		if (updateSuccess) {
			
			System.out.println("성공");
			return "redirect:/member/view/" + memberVO.getEmail();
		} else {
			return "404";
		}
	}

	@PostMapping("delete/{email}")
	public String doDeletememberById(@PathVariable String email) {
		boolean deleteSuccess = this.memberService.deleteMemberByEmail(email);
		if (deleteSuccess) {
			return "redirect:/member/view";
		} else {
			return "404";
		}
	}
}