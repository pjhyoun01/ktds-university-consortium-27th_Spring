package com.ktdsuniversity.edu.actor.vo.request;

import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.actor.vo.ActorVO;

public class InsertVO extends ActorVO {
	public MultipartFile file;

	public MultipartFile getFile() {
		return this.file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
