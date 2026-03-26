package com.ktdsuniversity.edu.board.vo;

import java.util.List;

public class SearchResultVO {

	private List<BoardVO> result;
	private int count;
	
	public List<BoardVO> getResult() {
		return this.result;
	}
	public void setResult(List<BoardVO> result) {
		this.result = result;
	}
	public int getCount() {
		return this.count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
