package com.ktdsuniversity.edu.members.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegistVO {

	@NotBlank(message = "이메일을 입력해 주세요")
	@Email(message = "이메일 형식으로 입력해 주세요")
	private String email;
	
	@NotBlank(message = "이름을 입력해 주세요")
	private String name;

	@NotBlank(message = "비밀번호를 입력해 주세요")
	private String password;

	@NotBlank(message = "확인 비밀번호를 입력해 주세요")
	private String confirmPassword;
	private String salt;

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return this.confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
