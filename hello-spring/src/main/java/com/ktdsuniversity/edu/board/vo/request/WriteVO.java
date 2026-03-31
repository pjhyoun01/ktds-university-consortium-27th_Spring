package com.ktdsuniversity.edu.board.vo.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WriteVO {
	private String id;
	
	private String subject;
	private String email;
	private String content;
	
	private List<MultipartFile> attachFile;
	
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<MultipartFile> getAttachFile() {
		return this.attachFile;
	}
	public void setAttachFile(List<MultipartFile> attechFile) {
		this.attachFile = attechFile;
	}
}
