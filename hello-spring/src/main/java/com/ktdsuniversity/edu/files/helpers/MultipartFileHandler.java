package com.ktdsuniversity.edu.files.helpers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.files.dao.FilesDao;
import com.ktdsuniversity.edu.files.vo.request.UploadVO;

@Component
public class MultipartFileHandler {

	private static final Logger logger = LoggerFactory.getLogger(MultipartFileHandler.class);
	
	@Autowired
	private FilesDao filesDao;

	/**
	 * @return 첨부파일의 그룹 아이디
	 */
	public String upload(List<MultipartFile> attachFiles) {
		if (attachFiles != null && attachFiles.size() > 0) {
			String fileGroupId = this.filesDao.selectFileGroupId();
			this.filesDao.insertFileGroupId(fileGroupId);

			this.upload(attachFiles, fileGroupId);

			return fileGroupId;
		}
		return null;
	}

	public String upload(List<MultipartFile> attachFiles, String fileGroupId) {
		if (attachFiles != null && attachFiles.size() > 0) {
			for (int i = 0; i < attachFiles.size(); i++) {

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

					String filename = attachFiles.get(i).getOriginalFilename();
					String ext = filename.substring(filename.lastIndexOf(".") + 1);

					uploadVO.setFileGroupId(fileGroupId);
					uploadVO.setObfuscateName(obfuscateName);
					uploadVO.setDisplayName(filename);
					uploadVO.setExtendName(ext);
					uploadVO.setFileLength(storeFile.length());
					uploadVO.setFilePath(storeFile.getAbsolutePath());

					this.filesDao.insertAttachFile(uploadVO);
				} catch (IllegalStateException | IOException e) {
					logger.debug("파일 업로드 에러 발생", e);
				}
			}
			return fileGroupId;
		}
		return null;
	}

}
