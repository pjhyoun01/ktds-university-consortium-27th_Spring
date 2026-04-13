package com.ktdsuniversity.edu.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.vo.response.LoadVO;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	private FileDao fileDao;

	@Override
	public LoadVO readFileByFileGroupId(String fileGroupId) {
		LoadVO fileVO = this.fileDao.selectFileByFileGroupId(fileGroupId);

		return fileVO;
	}

}
