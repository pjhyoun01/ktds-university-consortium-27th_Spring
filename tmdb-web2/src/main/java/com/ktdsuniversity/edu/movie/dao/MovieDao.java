package com.ktdsuniversity.edu.movie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.request.UpdateVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

@Mapper
public interface MovieDao {

	List<MovieListVO> selectAllMovie();

	OneMovieVO selectMovieById(String movieId);

	int insertMovie(InsertVO insertVO);

	int updateMovieByMovieId(UpdateVO updateVO);

	int deleteMovieById(String movieId);

}
