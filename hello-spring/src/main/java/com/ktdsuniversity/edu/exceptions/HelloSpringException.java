package com.ktdsuniversity.edu.exceptions;

public class HelloSpringException extends RuntimeException {

	private static final long serialVersionUID = 5344517791626726005L;

	/**
	 * 예외 발생 시 사용자에게 보여주고 싶은 페이지
	 */
	private String errorPage;
	/**
	 * 사용자에게 보여주고 싶은 페이지에 보내줄 모델 데이터
	 */
	private Object object;

	public HelloSpringException(String message, String errorPage) {
		super(message);
		this.errorPage = errorPage;
	}

	public HelloSpringException(String message, String errorPage, Object object) {
		super(message);
		this.errorPage = errorPage;
		this.object = object;
	}

	public String getErrorPage() {
		return this.errorPage;
	}
	public Object getObject() {
		return this.object;
	}
}
