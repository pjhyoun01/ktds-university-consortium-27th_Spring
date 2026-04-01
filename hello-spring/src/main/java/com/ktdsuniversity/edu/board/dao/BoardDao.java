package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.request.UpdateVO;
import com.ktdsuniversity.edu.board.vo.request.WriteVO;

@Mapper
public interface BoardDao {

	List<BoardVO> getAllBoard();

	int getBoardCount();

	int updateBoardViewCount(String boardId);

	BoardVO selectBoardById(String boardId);

	int createBoard(WriteVO writeVO);

	int updateOneBoardById(UpdateVO updateVO);

	int deleteBoardById(String boardId);

}
