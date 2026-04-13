package com.ktdsuniversity.edu.exceptions;

public class TMDBApiException extends RuntimeException {

	private static final long serialVersionUID = 8319658729446176437L;

	private int errorStatus;
	private Object error;

	public TMDBApiException(String message, int errorStatus, Object error) {
		super(message);
		this.errorStatus = errorStatus;
		this.error = error;
	}

	public int getErrorStatus() {
		return this.errorStatus;
	}
	public Object getError() {
		return this.error;
	}
}
