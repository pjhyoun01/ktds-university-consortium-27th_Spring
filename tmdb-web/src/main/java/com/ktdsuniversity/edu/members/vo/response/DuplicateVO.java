package com.ktdsuniversity.edu.members.vo.response;

public class DuplicateVO {
	private String email;
	private Boolean duplicate;

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getDuplicate() {
		return this.duplicate;
	}
	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
	}
}
