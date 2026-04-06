package com.ktdsuniversity.edu.members.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.members.service.MembersService;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.response.DuplicateVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Endpoint 생성 관리 + Validation Check
 */
@Controller
public class MembersController {

	@Autowired
	private MembersService membersService;
	
	@GetMapping("/login")
	public String viewLoginpage(HttpServletRequest request) {
		if (request.getSession().getAttribute("__LOGIN_DATA__") != null) {
			return "redirect:/";
		}
		return "members/login";
	}
	
	@PostMapping("/login")
	public String doLogin(@Valid @ModelAttribute LoginVO loginVO, BindingResult bindingResult, Model model, HttpServletRequest request) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("loginData", loginVO);
			return "members/login";
		}
		
		String userIp = request.getRemoteAddr();
		loginVO.setIp(userIp);
		
		MembersVO member = this.membersService.findMemberByEmailAndPassword(loginVO);
		
		// 서버의 세션 삭제 -> 로그아웃
//		request.getSession().invalidate();
		
		// request.getSession(); -> HttpRequestHeader로 전달된 JSESSIONID의 객체를 반환
		// request.getSession(true); -> 기존 JSESSIONID로 발급된 세션객체는 버리고 새로운 ID의 세션 객체를 생성 후 반환
		HttpSession session = request.getSession(true);
		session.setAttribute("__LOGIN_DATA__", member);
		session.setMaxInactiveInterval(1800);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String doLogout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/login";
	}

	@GetMapping("/regist")
	public String viewRegistPage() {
		return "members/regist";
	}

	@ResponseBody
	@GetMapping("regist/check/duplicate/{email}")
	public DuplicateVO doCheckDuplicate(@PathVariable String email) {
		// email이 이미 사용중인지 확인
		MembersVO membersVO = this.membersService.findMemberByEmail(email);
		// 확인된 결과를 브라우저에게 JSON으로 전송
		// 사용중 ==> {email: "test@test.com", duplicte: true}
		// 사용중이 아님 ==> {email: "test@test.com", duplicte: false}
		DuplicateVO result = new DuplicateVO();
		result.setEmail(email);
		result.setDuplicate(membersVO != null);
		return result;
	}

	@PostMapping("/regist")
	public String doRegistAction(@Valid @ModelAttribute RegistVO registVO, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("inputData", registVO);
			return "members/regist";
		}
		boolean createResult = this.membersService.createNewMember(registVO);
		if (createResult) {
			return "redirect:/login";
		} else {
			return "error/404";
		}
	}

	/*
	 * /member/view/사용자아이디 ==> 회원 정보 조회 하기. /member/update/사용자아이디 ==> 회원 정보 수정 페이지
	 * 보기. /member/update/사용자아이디 ==> 회원 정보 수정 하기. /member/delete?id=사용자아이디 ==> 회원 정보
	 * 삭제 하기.
	 */
	@GetMapping("/member/view/{email}")
	public String viewMemberPage(@PathVariable String email, Model model) {
		MembersVO searchReuslt = this.membersService.findMemberByEmail(email);
		model.addAttribute("member", searchReuslt);
		return "members/view";
	}

	@GetMapping("/member/update/{email}")
	public String viewUpdatePage(@PathVariable String email, Model model) {
		MembersVO searchReuslt = this.membersService.findMemberByEmail(email);
		model.addAttribute("member", searchReuslt);
		return "members/update";
	}

	@PostMapping("/member/update/{email}")
	public String doUpdateAction(@PathVariable String email, UpdateVO updateVO) {
		updateVO.setEmail(email);
		boolean updateResult = this.membersService.updateMemberByEmail(updateVO);
		if (updateResult) {
			return "redirect:/member/view/" + email;
		} else {
			return "error/404";
		}
	}

	@GetMapping("/member/delete")
	public String doDeleteAction(@RequestParam String id) {
		boolean updateResult = this.membersService.deleteMemberByEmail(id);
		if (updateResult) {
			return "redirect:/member";
		} else {
			return "error/404";
		}
	}

	// /member ==> 회원들의 목록이 조회되도록 코드를 작성.
	// ==> 회원 목록 조회.
	// ==> members/list.jsp : 회원 목록 반복.
	// : 회원의 수 출력
	// : 회원의 수가 없을 때, "등록된 회원이 없습니다" 출력
	// : 목록 아래에는 "새로운 회원 등록" 링크 추가.
	@GetMapping("/member")
	public String viewMembersPage(Model model, HttpServletRequest request) {
		SearchResultVO searchResult = this.membersService.findMembersList();
		model.addAttribute("searchList", searchResult.getResult());
		model.addAttribute("searchCount", searchResult.getCount());
		
		if (request.getSession().getAttribute("__LOGIN_DATA__") != null) {
			model.addAttribute("loginData", true);
		}
		
		return "members/newlist";
	}
}