package com.ktdsuniversity.edu.file.service;

import com.ktdsuniversity.edu.file.vo.response.LoadVO;

public interface FileService {

	LoadVO readFileByFileGroupId(String fileGroupId);

}
