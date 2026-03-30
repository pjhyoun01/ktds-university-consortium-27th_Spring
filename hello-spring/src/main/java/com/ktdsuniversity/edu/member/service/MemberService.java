package com.ktdsuniversity.edu.member.service;

import java.util.List;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public interface MemberService {

	boolean createMember(MemberVO memberVO);

	List<MemberVO> readAllMember();

	MemberVO readMemberByEmail(String email);

	boolean updateMemberByEmail(MemberVO memberVO);

	boolean deleteMemberByEmail(String email);

}
