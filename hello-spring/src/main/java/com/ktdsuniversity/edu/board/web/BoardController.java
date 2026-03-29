package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.BoardVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;

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
	public String createBoard(WriteVO writeVO) {
		boolean isSuccess = this.boardService.createBoard(writeVO);
		if(isSuccess) {
			return "redirect:view";
		} else {
			return "404";
		}
		
	}
	
	@GetMapping("view/{boardId}")
	public String viewBoardDetail(@PathVariable String boardId, Model model) {
//		System.out.println(boardId);
		BoardVO readOneBoard = this.boardService.readBoardById(boardId);
		model.addAttribute("board", readOneBoard);
		
		return "board/detail";
	}
}
