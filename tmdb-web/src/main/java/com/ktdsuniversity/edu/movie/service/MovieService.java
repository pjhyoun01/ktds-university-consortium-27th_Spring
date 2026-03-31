package com.ktdsuniversity.edu.movie.service;

import java.util.List;

import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

public interface MovieService {

	List<MovieListVO> readAllMovie();

	boolean insertMovie(InsertVO movieInsertVO);

	OneMovieVO readMovieById(String movieId);

}
