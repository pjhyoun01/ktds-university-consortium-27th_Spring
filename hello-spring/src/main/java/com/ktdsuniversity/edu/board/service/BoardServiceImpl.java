package com.ktdsuniversity.edu.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.SearchResultVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	
	@Override
	public SearchResultVO getAllBoard() {
		List<BoardVO> list= this.boardDao.getAllBoard();
		int count = this.boardDao.getBoardCount();
		
		SearchResultVO result = new SearchResultVO();
		result.setResult(list);
		result.setCount(count);
		
		return result;
	}


	@Override
	public int createBoard(BoardVO boardVO) {
		int success = this.boardDao.createBoard(boardVO);
		return success;
	}

}
