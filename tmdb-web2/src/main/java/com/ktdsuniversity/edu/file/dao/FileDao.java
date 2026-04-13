package com.ktdsuniversity.edu.file.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.file.vo.FilesGroupVO;
import com.ktdsuniversity.edu.file.vo.request.InsertVO;
import com.ktdsuniversity.edu.file.vo.response.LoadVO;

@Mapper
public interface FileDao {

	LoadVO selectFileByFileGroupId(String fileGroupId);

	String selectPathByFileGroupId(String fileGroupId);

	int insertFileGroupId(FilesGroupVO filesGroupVO);

	int insertFile(InsertVO insertVO);

	int deleteFileByFileGroupId(String fileGroupId);

}
