package com.ktdsuniversity.edu.members.dao;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MembersDao {

	int insertNewMember(RegistVO registVO);

	MembersVO selectMemberByEmail(String email);

	int updateMemberByEmail(UpdateVO updateVO);

	int deleteMemberByEmail(String email);

	int selectMembersCount();

	List<MembersVO> selectMembersList();

	int updateIncreaseLoginFailCount(String email);

	int updateBlock(String email);

	int updateSuccessLogin(LoginVO loginVO);

    List<String> selectMemberRolesByEmail(String username);
}
