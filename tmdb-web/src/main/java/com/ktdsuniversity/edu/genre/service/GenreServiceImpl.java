package com.ktdsuniversity.edu.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.category.vo.CategoryVO;
import com.ktdsuniversity.edu.genre.dao.GenreDao;
import com.ktdsuniversity.edu.genre.vo.GenreVO;
import com.ktdsuniversity.edu.genre.vo.request.InsertVO;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreDao genreDao;

	@Override
	public boolean insertGenre(InsertVO insertVO) {
		if (insertVO == null || insertVO.getMovieId() == null || insertVO.getCategories() == null) {
			return false;
		}

		GenreVO genreVO = new GenreVO();
		genreVO.setMovieId(insertVO.getMovieId());

		int insertSuccessCount = 0;
		for (CategoryVO categoryVO : insertVO.getCategories()) {
			genreVO.setCategoryId(categoryVO.getCategoryId());
			insertSuccessCount += this.genreDao.insertGenre(genreVO);
		}

		return insertSuccessCount == insertVO.getCategories().size();
	}
}
