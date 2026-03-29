package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.board.vo.request.WriteVO;
import com.ktdsuniversity.edu.board.vo.response.BoardVO;

@Mapper
public interface BoardDao {

	
	List<BoardVO> getAllBoard();

	int getBoardCount();

	int createBoard(WriteVO writeVO);

	void updateBoardViewCount(String boardId);

	BoardVO selectBoardById(String boardId);

}
