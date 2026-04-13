package com.ktdsuniversity.edu.category.service;

import java.util.List;

import com.ktdsuniversity.edu.category.vo.CategoryVO;
import com.ktdsuniversity.edu.category.vo.request.InsertVO;

public interface CategoryService {

	List<String> readAllCategoryNameByMovieId(String movieId);

	boolean insertCategory(InsertVO insertVO);

	List<CategoryVO> readAllCategory();

}
