package com.ktdsuniversity.edu.board.service;

import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.BoardVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;

public interface BoardService {

	SearchResultVO getAllBoard();

	boolean createBoard(WriteVO writeVO);

	BoardVO readBoardById(String boardId);
	
}
