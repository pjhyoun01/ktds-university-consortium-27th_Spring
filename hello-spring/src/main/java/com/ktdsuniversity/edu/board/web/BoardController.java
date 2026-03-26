package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;

@Controller
@RequestMapping("/board/")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("view")
	public String viewBoard(Model model) {
		SearchResultVO searchResultVO = this.boardService.getAllBoard();
		
		List<BoardVO> getAllBoard = searchResultVO.getResult();
		int boardCount = searchResultVO.getCount();
		
		model.addAttribute("boardList", getAllBoard);
		model.addAttribute("boardCount", boardCount);
		return "board/list";
	}
	
	@GetMapping("write")
	public String createBoard() {
		return "board/write";
	}
	
	@PostMapping("write")
	public String createBoard(BoardVO boardVO) {
		int success = this.boardService.createBoard(boardVO);
		
		
		return "redirect:view";
	}
	
}
