package com.ktdsuniversity.edu.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.enums.ReadType;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.BoardVO;
import com.ktdsuniversity.edu.board.vo.response.SearchResultVO;

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
	public boolean createBoard(WriteVO writeVO) {
		int  isSuccess = this.boardDao.createBoard(writeVO);
		return isSuccess == 1;
	}


	@Override
	public BoardVO readBoardById(String boardId, ReadType readType) {
//		int updateCount = 0;
//		if (readType == ReadType.VIEW) {
//			updateCount = this.boardDao.updateBoardViewCount(boardId);			
//		}
//		if (updateCount == 0) {
//			return null;
//		}
		BoardVO boardById = this.boardDao.selectBoardById(boardId);
		
		
		return boardById;
	}


	@Override
	public boolean updateOneArticle(UpdateVO updateVO) {
		int isSuccess = this.boardDao.updateOneBoardById(updateVO);
		return isSuccess == 1;
	}


}
