package com.ktdsuniversity.edu.exceptions;

public class TMDBException extends RuntimeException {

	private static final long serialVersionUID = 6393786263198807743L;

	private String errorPage;
	private Object object;

	public TMDBException(String message, String errorPage) {
		super(message);
		this.errorPage = errorPage;
	}
	public TMDBException(String message, String errorPage, Object object) {
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
