package com.ktdsuniversity.edu.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.category.dao.CategoryDao;
import com.ktdsuniversity.edu.category.vo.CategoryVO;
import com.ktdsuniversity.edu.category.vo.request.InsertVO;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<CategoryVO> readAllCategory() {
		List<CategoryVO> categoryList = this.categoryDao.seleteAllCategory();
		return categoryList;
	}

	@Override
	public List<String> readAllCategoryNameByMovieId(String movieId) {
		List<String> categoryNameListById = this.categoryDao.readAllCategoryNameByMovieId(movieId);
		return categoryNameListById;
	}

	@Override
	public boolean insertCategory(InsertVO insertVO) {
		int insertSuccessCount = this.categoryDao.insertCategory(insertVO);
		return insertSuccessCount == 1;
	}

}
