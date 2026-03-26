package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.board.vo.BoardVO;

@Mapper
public interface BoardDao {

	
	List<BoardVO> getAllBoard();

	int getBoardCount();

	int createBoard(BoardVO boardVO);
	
}
