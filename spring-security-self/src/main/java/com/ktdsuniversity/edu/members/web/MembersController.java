package com.ktdsuniversity.edu.members.web;

import com.ktdsuniversity.edu.common.utils.AuthUtils;
import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.response.DuplicateResultVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MembersController {

	private static final Logger logger = LoggerFactory.getLogger(MembersController.class);

	@Autowired
	private MembersService membersService;

	@ResponseBody
	@GetMapping("/regist/check/duplicate/{email}")
	public DuplicateResultVO doCheckDuplicateEmailAction(@PathVariable String email) {

		MembersVO membersVO = this.membersService.findMemberByEmail(email);

		// 확인된 결과를 브라우저에게 JSON으로 전송한다.
		// 이미 사용중 ==> {email: "test@gmail", duplicate: true}
		// 사용중이지 않음 ==> {email: "test@gmail", duplicate: false}
		DuplicateResultVO result = new DuplicateResultVO();
		result.setEmail(email);
		result.setDuplicate(membersVO != null);
		return result;
	}

	@GetMapping("/regist")
	public String viewRegistPage() {
		if (!AuthUtils.isAuthenticated()) {
			return "redirect:/";
		}
		return "members/regist";
	}

	@PreAuthorize("isAnonymous()")
	@PostMapping("/regist")
	public String doRegistAction(@Valid @ModelAttribute RegistVO registVO, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("inputData", registVO);
			return "members/regist";
		}
		boolean createResult = this.membersService.createNewMember(registVO);
		logger.debug("회원 가입 결과? {}", createResult);
		return "redirect:/login";
	}

	@PreAuthorize("isAuthenticated() and #email == authentication.principal.email")
	@GetMapping("/member/view/{email}")
	public String viewMemberPage(@PathVariable String email, Model model) {
		MembersVO searchReuslt = this.membersService.findMemberByEmail(email);
		model.addAttribute("member", searchReuslt);
		return "members/view";
	}

	@PreAuthorize("isAuthenticated() and #email == authentication.principal.email")
	@GetMapping("/member/update/{email}")
	public String viewUpdatePage(@PathVariable String email, Model model) {
		MembersVO searchReuslt = this.membersService.findMemberByEmail(email);
		model.addAttribute("member", searchReuslt);
		return "members/update";
	}

	@PreAuthorize("isAuthenticated() and #email == authentication.principal.email")
	@PostMapping("/member/update/{email}")
	public String doUpdateAction(@PathVariable String email, UpdateVO updateVO) {
		updateVO.setEmail(email);
		boolean updateResult = this.membersService.updateMemberByEmail(updateVO);
		logger.debug("수정 결과? {}", updateResult);
		return "redirect:/member/view/" + email;
	}

	@PreAuthorize("isAuthenticated() and #email == authentication.principal.email")
	@GetMapping("/member/delete")
	public String doDeleteAction(@RequestParam String id) {
		boolean updateResult = this.membersService.deleteMemberByEmail(id);
		logger.debug("삭제 결과? {}", updateResult);
		return "redirect:/member";
	}

	@PreAuthorize("hasRole('RL-20260414-000002')")
	@GetMapping("/member")
	public String viewMembersPage(Model model) {
		SearchResultVO searchResult = this.membersService.findMembersList();
		model.addAttribute("searchList", searchResult.getResult());
		model.addAttribute("searchCount", searchResult.getCount());
		return "members/newlist";
	}

	@GetMapping("/login")
	public String viewLoginPage() {

		return "members/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/logout")
	public String doLogoutAction() {
		AuthUtils.doLogout();

		return "redirect:/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete-me")
	public String doDeleteAction() {

		boolean deleteSuccess = this.membersService.deleteMemberByEmail(AuthUtils.getUserEmail());
		logger.debug("탈퇴 성공? {}", deleteSuccess);

		AuthUtils.doLogout();

		return "members/deletesuccess";
	}
}