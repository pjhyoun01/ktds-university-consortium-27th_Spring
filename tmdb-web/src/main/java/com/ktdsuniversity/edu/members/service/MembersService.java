package com.ktdsuniversity.edu.members.service;

import java.util.List;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;

public interface MembersService {

	List<MembersVO> readAllMember();

	MembersVO readMemberByEmail(String email);

	boolean createMember(RegistVO registVO);

	boolean deleteMemberByEmail(String email);

}
