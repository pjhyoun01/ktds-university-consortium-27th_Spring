package com.ktdsuniversity.edu.genre.service;

import java.util.List;

import com.ktdsuniversity.edu.genre.vo.GenreVO;

public interface GenreService {

	List<GenreVO> readAllGenreByMovieId(String movieId);

}
