package com.ktdsuniversity.edu.file.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.file.vo.FilesGroupVO;
import com.ktdsuniversity.edu.file.vo.request.InsertVO;
import com.ktdsuniversity.edu.file.vo.response.LoadVO;

@Mapper
public interface FileDao {

	int insertFile(InsertVO insertVO);

	LoadVO selectFileByMovieId(String movieId);

	String selectNextFilesGroupPK();

	int insertFileGroupId(FilesGroupVO filesGroupVO);

}
