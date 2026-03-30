package com.ktdsuniversity.edu.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.enums.ActionType;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean createMember(MemberVO memberVO) {
		int duplicatedEmailCount = this.memberDao.selectEmailCount(memberVO.getEmail());
		int createSuccess = 0;
		if (duplicatedEmailCount == 0) {
			createSuccess = this.memberDao.insertMember(memberVO);
		} 
		return createSuccess == 1;
	}

	@Override
	public List<MemberVO> readAllMember() {
		List<MemberVO> memberList = this.memberDao.selectAllMember();
		return memberList;
	}

	@Override
	public MemberVO readMemberByEmail(String email) {
		MemberVO oneMemberByEmail = this.memberDao.selectOneMemberByEmail(email);
		return oneMemberByEmail;
	}

	@Override
	public boolean updateMemberByEmail(MemberVO memberVO) {
		int updateSuccessCount = this.memberDao.updateMemberByEmail(memberVO);
		return updateSuccessCount == 1;
	}

	@Override
	public boolean deleteMemberByEmail(String email) {
		int deleteSuccessCount = this.memberDao.deleteMemberByEmail(email);
		return deleteSuccessCount == 1;
	}

}
