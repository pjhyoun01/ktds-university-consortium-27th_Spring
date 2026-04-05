package com.ktdsuniversity.edu.members.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;

@Mapper
public interface MembersDao {

	List<MembersVO> selectAllMember();

	MembersVO selectMembersByEmail(String email);

	int insertMembers(RegistVO registVO);

	int deleteMemerByEmail(String email);

}
