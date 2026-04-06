package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.members.vo.MembersVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class BoardController {
	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다.
	 */
	@Autowired
	private BoardService boardService;

	@GetMapping("/")
	public String viewListPage(Model model, HttpServletRequest request) {

		SearchResultVO searchResult = this.boardService.findAllBoard();

		// 게시글의 목록을 조회.
		List<BoardVO> list = searchResult.getResult();

		// 게시글의 개수 조회.
		int searchCount = searchResult.getCount();

		model.addAttribute("searchResult", list);
		model.addAttribute("searchCount", searchCount);
		if (request.getSession().getAttribute("__LOGIN_DATA__") != null) {
			model.addAttribute("loginData", true);
		} 
//		else {
//			model.addAttribute("loginData", false);
//		}

		return "board/newlist";
	}

	// 게시글 등록 화면 보여주는 EndPoint
	@GetMapping("/write")
	public String viewWritePage(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute("__LOGIN_DATA__") == null) {
			return "redirect:/";
		}
		return "board/write";
	}

	// 게시글을 등록하는 EndPoint
	@PostMapping("/write")
	public String doWriteAction(@Valid @ModelAttribute WriteVO writeVO,
			// @Valid의 결과를 받아오는 파라미터.
			// 반드시 @Valid 파라미터 이후에 작성!
			BindingResult bindingResult, Model model, HttpServletRequest request) {
		// 사용자의 입력값을 검증 했을 때, 에러가 있다면
		if (bindingResult.hasErrors()) {
			// 브라우저에게 "board/write" 페이지를 보여주도록 하고
			// 해당 페이지에 사용자가 입력한 값을 전달한다.
			model.addAttribute("inputData", writeVO);
			return "board/write";
		}

		HttpSession session = request.getSession();
		MembersVO loginMember = (MembersVO) session.getAttribute("__LOGIN_DATA__");
		if (session.getAttribute("__LOGIN_DATA__") == null) {
			return "redirect:/login";
		}
		
		writeVO.setEmail(loginMember.getEmail());
		
		// create, update, delete => 성공/실패 여부 반환.
		boolean createResult = this.boardService.createNewBoard(writeVO);
		if (createResult) {
			return "redirect:/";
		} else {
			return "error/404";
		}

		// redirect: 브라우저에게 다음 End Point를 요청하도록 지시.
		// redirect:/ ==> 브라우저에게 "/" endpoint 로 이동하도록 지시.
	}

	// 게시글 내용 조회.
	// endpoint ==> /view/게시글아이디 예> /view/BO-20260327-000001
	// 해야 하는 역할
	// 1. 게시글 내용을 조회해서 브라우저에게 노출.
	// 2. 조회수 1증가.
	@GetMapping("/view/{articleId}")
	public String viewDetailPage(Model model, @PathVariable String articleId) {

		// articleId로 데이터베이스에서 게시글을 조회한다.
		// 조회할 때 조회수가 하나 증가해야 한다.
		BoardVO findResult = this.boardService.findBoardByArticleId(articleId, ReadType.VIEW);

		model.addAttribute("article", findResult);

		return "board/view";
	}

	@GetMapping("/update/{articleId}")
	public String viewUpdatePage(@PathVariable String articleId, Model model) {
		BoardVO data = this.boardService.findBoardByArticleId(articleId, ReadType.UPDATE);
		model.addAttribute("article", data);
		return "board/update";
	}

	@PostMapping("/update/{articleId}")
	public String doUpdateAction(@PathVariable String articleId, UpdateVO updateVO) {
		updateVO.setId(articleId);
		System.out.println("업데이트 파일 그룹 아이디" + updateVO.getFileGroupId());
		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		if (updateResult) {
			return "redirect:/view/" + articleId;
		} else {
			return "error/404";
		}
	}

	@GetMapping("/delete")
	public String doDeleteAction(@RequestParam String id) {

		boolean deleteResult = this.boardService.deleteBoardByArticleId(id);
		if (deleteResult) {
			return "redirect:/";
		} else {
			return "error/404";
		}
	}
}
