package com.ktdsuniversity.edu.board.service;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;

public interface BoardService {

	SearchResultVO getAllBoard();

	int createBoard(BoardVO boardVO);
	
}
