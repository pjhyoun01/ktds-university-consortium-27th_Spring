package com.ktdsuniversity.edu.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.movie.dao.MovieDao;
import com.ktdsuniversity.edu.movie.vo.request.InsertVO;
import com.ktdsuniversity.edu.movie.vo.response.MovieListVO;
import com.ktdsuniversity.edu.movie.vo.response.OneMovieVO;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieDao movieDao;

	@Override
	public List<MovieListVO> readAllMovie() {
		List<MovieListVO> movieList = this.movieDao.selectAllMovie();

		return movieList;
	}

	@Override
	public boolean insertMovie(InsertVO movieInsertVO) {
		int isSuccess = this.movieDao.insertMovie(movieInsertVO);
		return isSuccess == 1;
	}

	@Override
	public OneMovieVO readMovieById(String movieId) {
		OneMovieVO oneMovieById = this.movieDao.selectMovieById(movieId);

		return oneMovieById;
	}
}
