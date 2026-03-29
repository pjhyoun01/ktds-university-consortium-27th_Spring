package com.ktdsuniversity.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean createMember(MemberVO memberVO) {
		
		int isDuplicatedEmail = this.memberDao.selectEmailCount(memberVO.getEmail());
		int isSuccess = 0;
		if(isDuplicatedEmail == 0) {
			isSuccess = this.memberDao.insertMember(memberVO);
			System.out.println("성공");
		} else {
			System.out.println("실패");
		}
		
		return isSuccess == 1;
	}

}
