package com.ktdsuniversity.edu.movie.vo.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class InsertVO {
	private String movieId;
	private String posterUrl;

	@NotBlank(message = "제목을 입력해주세요.")
//	@Size(max = 50)
	private String title;
	
//	@Max(3) // <- String에도 쓸 수 있나? 
//	@Positive
	private String movieRating;
	private String openDate;

//	@Size(max = 3)
	private String openCountry;

//	@Max(4)
//	@Positive
	private int runningTime;
	
//	@Size(max = 60)
	private String introduce;

	@NotBlank(message = "줄거리를 입력해주세요.")
	private String synopsis;
	
//	@Size(max = 50)
	private String originalTitle;

	@NotBlank(message = "개봉 상태를 입력해주세요.")
	private String state;

	@NotBlank(message = "언어를 입력해주세요.")
	private String language;
	private long budget;
	private long profit;
	private String fileGroupId;

	private MultipartFile file;

	public String getMovieId() {
		return this.movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = replaceTag(movieId);
	}
	public String getPosterUrl() {
		return this.posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = replaceTag(posterUrl);
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = replaceTag(title);
	}
	public String getMovieRating() {
		return this.movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = replaceTag(movieRating);
	}
	public String getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = replaceTag(openDate);
	}
	public String getOpenCountry() {
		return this.openCountry;
	}
	public void setOpenCountry(String openCountry) {
		this.openCountry = replaceTag(openCountry);
	}
	public int getRunningTime() {
		return this.runningTime;
	}
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	public String getIntroduce() {
		return this.introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = replaceTag(introduce);
	}
	public String getSynopsis() {
		return this.synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = replaceTag(synopsis);
	}
	public String getOriginalTitle() {
		return this.originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = replaceTag(originalTitle);
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = replaceTag(state);
	}
	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = replaceTag(language);
	}
	public double getBudget() {
		return this.budget;
	}
	public void setBudget(long budget) {
		this.budget = budget;
	}
	public double getProfit() {
		return this.profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	public MultipartFile getFile() {
		return this.file;
	}
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public String replaceTag(String replaceStr) {
		if (replaceStr != null) {
			replaceStr = replaceStr.replace("<", "&lt")
								   .replace(">", "&gt");
			return replaceStr;
		}
		return null;
	}
}
