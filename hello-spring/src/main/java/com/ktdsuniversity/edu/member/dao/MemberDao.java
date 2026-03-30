package com.ktdsuniversity.edu.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.member.vo.MemberVO;

@Mapper
public interface MemberDao {

	int selectEmailCount(String email);

	int insertMember(MemberVO memberVO);

	List<MemberVO> selectAllMember();

	MemberVO selectOneMemberByEmail(String email);

	int updateMemberByEmail(MemberVO memberVO);

	int deleteMemberByEmail(String email);

}
