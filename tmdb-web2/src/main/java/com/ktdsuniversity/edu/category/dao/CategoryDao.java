package com.ktdsuniversity.edu.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.category.vo.CategoryVO;
import com.ktdsuniversity.edu.category.vo.request.InsertVO;

@Mapper
public interface CategoryDao {

	List<CategoryVO> seleteAllCategory();

	List<String> readAllCategoryNameByMovieId(String movieId);

	int insertCategory(InsertVO insertVO);

}
