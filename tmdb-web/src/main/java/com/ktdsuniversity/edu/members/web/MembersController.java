package com.ktdsuniversity.edu.members.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.response.DuplicateVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/members/")
public class MembersController {

	@Autowired
	private MembersService membersService;
	
	@GetMapping("login")
	public String viewLoginPage() {
		return "members/login";
	}
	
	@PostMapping("login")
	public String doLogin(@Valid @ModelAttribute LoginVO loginVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("loginData", loginVO);
			return "members/login";
		}
		
		
		
		return "";
	}
	

	@GetMapping("view")
	public String viewMembers(Model model) {
		List<MembersVO> memberList = this.membersService.readAllMember();

		model.addAttribute("memberList", memberList);

		return "member/list";
	}

	@GetMapping("view/{email}")
	public String viewOneMembers(@PathVariable String email, Model model) {
		MembersVO membersVO = this.membersService.readMemberByEmail(email);

		model.addAttribute("memberList", membersVO);

		return "member/view";
	}

	@GetMapping("regist")
	public String viewRegistPage() {
		return "members/regist";
	}

	@ResponseBody
	@GetMapping("regist/{email}")
	public DuplicateVO doCheckDuplicateEmail(@PathVariable String email) {
		MembersVO membersVO = this.membersService.readMemberByEmail(email);

		DuplicateVO duplicateVO = new DuplicateVO();
		duplicateVO.setEmail(email);
		duplicateVO.setDuplicate(membersVO != null);

		return duplicateVO;
	}

	@PostMapping("regist")
	public String doRegist(@Valid @ModelAttribute RegistVO registVO, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("validError", registVO);
			return "members/regist";
		}

		boolean registSuccess = this.membersService.createMember(registVO);
		if (registSuccess) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}

	@PostMapping("delete/{email}")
	public String deleteMembersByEmail(@PathVariable String email) {
		boolean deleteSuccecc = this.membersService.deleteMemberByEmail(email);
		if (deleteSuccecc) {
			return "";
		} else {
			return "";
		}
	}
}
