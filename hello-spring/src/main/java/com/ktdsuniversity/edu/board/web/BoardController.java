package com.ktdsuniversity.edu.board.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;

@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("")
	public String viewBoard(Model model) {
		SearchResultVO searchResultVO = this.boardService.getAllBoard();
		
		List<BoardVO> getAllBoard = searchResultVO.getResult();
		int boardCount = searchResultVO.getCount();
		
		model.addAttribute("boardList", getAllBoard);
		model.addAttribute("boardCount", boardCount);
		return "board/list";
	}
	
	@GetMapping("view/{boardId}")
	public String viewBoardDetail(@PathVariable String boardId, Model model) {
		BoardVO readOneBoard = this.boardService.readBoardById(boardId, ReadType.VIEW);
		model.addAttribute("board", readOneBoard);
		
		return "board/view";
	}
	
	@GetMapping("create")
	public String createBoard() {
		return "board/create";
	}
	
	@PostMapping("create")
	public String createBoard(WriteVO writeVO) {
		System.out.println("이름: " + writeVO.getAttachFile().get(0).getName());
		boolean isSuccess = this.boardService.createBoard(writeVO);
		if(isSuccess) {
			return "redirect:/";
		} else {
			return "404";
		}
	}
	
	@GetMapping("update/{boardId}")
	public String viewUpdatePage(@PathVariable String boardId, Model model) {
		BoardVO readOneBoard = this.boardService.readBoardById(boardId, ReadType.UPDATE);
		model.addAttribute("board", readOneBoard);
		
		return "board/update";
	}
	
	@PostMapping("update/{boardId}")
	public String doUpdatePage(@PathVariable String boardId,
							   UpdateVO updateVO) {
		updateVO.setId(boardId);
		boolean isSuccessUpdate = this.boardService.updateOneArticle(updateVO);
		if (isSuccessUpdate) {
			return "redirect:/view/" + boardId;
		} else {
			return "404";
		}
	}

	@PostMapping("delete/{boardId}")
	public String doDelete(@PathVariable String boardId) {
		boolean deleteSucces = this.boardService.deleteOneBoardById(boardId);
		
		return "redirect:/";
	}
}
