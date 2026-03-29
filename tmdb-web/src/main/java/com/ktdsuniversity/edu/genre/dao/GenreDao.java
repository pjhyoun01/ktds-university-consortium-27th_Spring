package com.ktdsuniversity.edu.genre.dao;

import java.util.List;

import com.ktdsuniversity.edu.genre.vo.GenreVO;

public interface GenreDao {

	List<GenreVO> selectAllGenreByMovieId(String movieId);

}
