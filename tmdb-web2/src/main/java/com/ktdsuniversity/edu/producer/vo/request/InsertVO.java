package com.ktdsuniversity.edu.producer.vo.request;

import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.producer.vo.ProducerVO;

public class InsertVO extends ProducerVO{
	private MultipartFile file;

	public MultipartFile getFile() {
		return this.file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
