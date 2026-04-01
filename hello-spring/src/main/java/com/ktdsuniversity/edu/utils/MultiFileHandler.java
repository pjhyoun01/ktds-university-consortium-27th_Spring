package com.ktdsuniversity.edu.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Component
public class MultiFileHandler {
	
	@Autowired
	private FilesDao filesDao;

	public void upload(List<MultipartFile> attachFiles, String fileGroupId) {
		if (attachFiles != null && attachFiles.size() >= 0) {
	         for (int i = 0; i < attachFiles.size(); i++) {
	        	 System.out.println("" + attachFiles.get(i).getName());
	        	if (attachFiles.get(i).isEmpty()) {
					continue;
				}
	        	
	        	String obfuscateName = UUID.randomUUID().toString();
	        	
	            File storeFile = new File("C:\\dev_programs\\test", obfuscateName);
	            if (!storeFile.getParentFile().exists()) {
	               storeFile.getParentFile().mkdirs();
	            }
	            try {
	               attachFiles.get(i).transferTo(storeFile);
	               UploadVO uploadVO = new UploadVO();
	               String fileName = attachFiles.get(i).getOriginalFilename();
	               String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	               uploadVO.setFileNum(i + 1);
	               uploadVO.setFileGroupId(fileGroupId);
	               uploadVO.setObfuscateName(obfuscateName);
	               uploadVO.setDisplayName(fileName);
	               uploadVO.setExtendName(ext);
	               uploadVO.setFileLength(storeFile.length());
	               uploadVO.setFilePath(storeFile.getAbsolutePath());
	               this.filesDao.insertAttachFile(uploadVO);
	            } catch (IllegalStateException | IOException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	}
}