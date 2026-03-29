package com.ktdsuniversity.edu.genre.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.genre.dao.GenreDao;
import com.ktdsuniversity.edu.genre.vo.GenreVO;

@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	private GenreDao genreDao;

	@Override
	public List<GenreVO> readAllGenreByMovieId(String movieId) {
		List<GenreVO> genreList = this.genreDao.selectAllGenreByMovieId(movieId);
		return genreList;
	}
}
