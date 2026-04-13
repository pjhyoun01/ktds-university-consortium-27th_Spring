package com.ktdsuniversity.edu.genre.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.genre.vo.GenreVO;

@Mapper
public interface GenreDao {

	int insertGenre(GenreVO genreVO);
}
