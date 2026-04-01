package com.ktdsuniversity.edu.files.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.files.service.FilesService;
import com.ktdsuniversity.edu.files.vo.request.SearchFileVO;
import com.ktdsuniversity.edu.files.vo.response.DownloadVO;

@Controller
public class FilesController {

	@Autowired
	private FilesService filesService;
	
	private Map<String, String> mimeTypeMap;

	public FilesController() {
		this.mimeTypeMap = new HashMap<>();
		
		this.mimeTypeMap.put("txt", "text/plain");
		// Images
		this.mimeTypeMap.put("jpg", "image/jpg");
		this.mimeTypeMap.put("gif", "image/gif");
		this.mimeTypeMap.put("jpeg", "image/jpeg");
		this.mimeTypeMap.put("png", "image/png");
		this.mimeTypeMap.put("webp", "image/webp");
		// text
		this.mimeTypeMap.put("css", "text/css");
		this.mimeTypeMap.put("html", "text/html");
		this.mimeTypeMap.put("js", "text/javascript");
		//
		this.mimeTypeMap.put("csv", "text/csv");
		this.mimeTypeMap.put("xls", "application/vnd.ms-excel");
		this.mimeTypeMap.put("doc", "application/msword");
		this.mimeTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		this.mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		
	}
	
	@GetMapping("/file/{fileGroupId}/{fileNum}")
	public ResponseEntity<Resource> doDownLoad(@PathVariable String fileGroupId, @PathVariable int fileNum) {
		SearchFileVO searchFileVO = new SearchFileVO();
		searchFileVO.setFileGroupId(fileGroupId);
		searchFileVO.setFileNum(fileNum);

		// 다운로드를 위한 정보와 파일 찾아오기
		DownloadVO downloadVO = this.filesService.findAttachFile(searchFileVO);

		// 다운로드 시작
		// http response 세팅
		// http response header 세팅
		HttpHeaders headers = new HttpHeaders();
		// content-disposition: 다운로드 할 파일 이름
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadVO.getDisplayName());
		// content-length: 파일 크기
		headers.set(HttpHeaders.CONTENT_LENGTH, downloadVO.getFileLength() + "");
		// content-type: 파일의 마임 타입
		headers.set(HttpHeaders.CONTENT_TYPE, 
					this.mimeTypeMap.getOrDefault(downloadVO.getExtendName().toLowerCase(),
												  "application/octet-stream"));
		
		return ResponseEntity.ok()
							 .headers(headers)
							 .body(downloadVO.getResource());
	}

}
