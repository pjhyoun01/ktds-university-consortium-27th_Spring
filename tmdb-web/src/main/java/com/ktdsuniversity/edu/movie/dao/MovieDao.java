package com.ktdsuniversity.edu.movie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.movie.vo.request.MovieCreateVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

@Mapper
public interface MovieDao {

	List<MovieListVO> selectAllMovie();

	int insertMovie(MovieCreateVO movieCreateVO);

	OneMovieVO selectMovieById(String movieId);

}
