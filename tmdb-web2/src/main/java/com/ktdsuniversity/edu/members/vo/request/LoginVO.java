package com.ktdsuniversity.edu.members.vo.request;

public class LoginVO {
	private String email;
	private String password;
	private String latestLoginIp;

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLatestLoginIp() {
		return this.latestLoginIp;
	}
	public void setLatestLoginIp(String latestLoginIp) {
		this.latestLoginIp = latestLoginIp;
	}
}
