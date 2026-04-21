package com.ktdsuniversity.edu.board.web;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;
import com.ktdsuniversity.edu.common.utils.AuthUtils;
import com.ktdsuniversity.edu.common.vo.SearchListVO;
import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class BoardApiController {

	private static final Logger logger = LoggerFactory.getLogger(BoardApiController.class);

	/**
	 * 빈 컨테이너에 들어있는 객체 중 타입이 일치하는 객체를 할당 받는다.
	 */
	@Autowired
	private BoardService boardService;

	// http://192.168.211.11:8080/?pageNo=0&listSize=10&searchType=&searchKeyword
	@ResponseBody
	@GetMapping("/articles")
	public Map<String, Object> viewListPage(Model model, SearchListVO searchListVO) {

		SearchResultVO searchResult = this.boardService.findAllBoard(searchListVO);

		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("result", searchResult);
		jsonResult.put("pagination", searchListVO);

		return jsonResult;
	}

	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/articles")
	// @RequestBody는 파일이 없을 때만 사용가능 (file은 json으로 통신할 수 없기 때문)
	public Map<String, Boolean> doWriteAction(@Valid WriteVO writeVO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new HelloSpringApiException("글쓰기 실패", HttpStatus.BAD_REQUEST.value(), bindingResult.getFieldError());
		}

		writeVO.setEmail(AuthUtils.getUserEmail());

		boolean createResult = this.boardService.createNewBoard(writeVO);

		logger.debug("게시글 생성 성공? {}", createResult);

		return Map.of("result", createResult);
	}

	@ResponseBody
	@GetMapping("/articles/{articleId}")
	public BoardVO viewDetailPage(@PathVariable String articleId) {
		BoardVO findResult = this.boardService.findBoardByArticleId(articleId, ReadType.VIEW);

		return findResult;
	}

	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/articles/{id}")
	public Map<String, Boolean> doDeleteAction(@PathVariable String id) {
		boolean deleteResult = this.boardService.deleteBoardByArticleId(id);

		return Map.of("result", deleteResult);
	}

	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/article/{articleId}")
	public Map<String, Boolean> doUpdateAction(@PathVariable String articleId, UpdateVO updateVO) {

		updateVO.setId(articleId);

		updateVO.setEmail(AuthUtils.getUserEmail());

		boolean updateResult = this.boardService.updateBoardByArticleId(updateVO);
		logger.debug("수정 성공? {}", updateResult);

		return Map.of("result", updateResult);
	}

	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/articles")
	public Map<String, Boolean> doDeleteAllAction() {
		boolean isSuccess = this.boardService.deleteAllBoard();
		return Map.of("result",  isSuccess);
	}
}
