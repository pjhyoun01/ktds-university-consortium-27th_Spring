package com.ktdsuniversity.edu.genre.vo.request;

import java.util.List;

import com.ktdsuniversity.edu.category.vo.CategoryVO;

public class InsertVO {
	private String genreId;
	private String movieId;
	private List<CategoryVO> categories;

	public String getGenreId() {
		return this.genreId;
	}
	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public List<CategoryVO> getCategories() {
		return this.categories;
	}
	public void setCategories(List<CategoryVO> categories) {
		this.categories = categories;
	}
}
