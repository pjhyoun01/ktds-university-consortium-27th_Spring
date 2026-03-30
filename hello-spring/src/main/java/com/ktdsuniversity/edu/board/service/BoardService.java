package com.ktdsuniversity.edu.board.service;

import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.BoardVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;

public interface BoardService {

	SearchResultVO getAllBoard();

	boolean createBoard(WriteVO writeVO);

	BoardVO readBoardById(String boardId, ReadType readType);

	boolean updateOneArticle(UpdateVO updateVO);
	
}
