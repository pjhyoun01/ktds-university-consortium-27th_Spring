package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;

@Mapper
public interface MembersDao {

	List<MembersVO> selectAllMember();

	MembersVO selectMembersByEmail(String email);

	int updateLoginFailCountPlusOne(String email);

	int updateBlockY(String email);

	int updateBlockN(String email);

	int updateLoginSuccess(LoginVO loginVO);

	int insertMembers(RegistVO registVO);

	int deleteMemerByEmail(String email);
}
