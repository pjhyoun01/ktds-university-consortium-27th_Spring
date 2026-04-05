package com.ktdsuniversity.edu.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.utils.SHA256Util;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;

	@Override
	public List<MembersVO> readAllMember() {
		List<MembersVO> memberList = this.membersDao.selectAllMember();

		return memberList;
	}

	@Override
	public MembersVO readMemberByEmail(String email) {
		MembersVO membersVO = this.membersDao.selectMembersByEmail(email);

		return membersVO;
	}

	@Override
	public boolean createMember(RegistVO registVO) {
		if (this.membersDao.selectMembersByEmail(registVO.getEmail()) != null) {
			throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
		}

		String salt = SHA256Util.generateSalt();
		String encryptPassword = SHA256Util.getEncrypt(registVO.getPassword(), salt);
		String encryptConfirmPassword = SHA256Util.getEncrypt(registVO.getConfirmPassword(), salt);

		int createSuccessCount = 0;
		if (encryptPassword.equals(encryptConfirmPassword)) {
			registVO.setPassword(encryptPassword);
			registVO.setSalt(salt);
			createSuccessCount = this.membersDao.insertMembers(registVO);
		}

		return createSuccessCount > 0;
	}

	@Override
	public boolean deleteMemberByEmail(String email) {
		int deleteSuccessCount = this.membersDao.deleteMemerByEmail(email);

		return deleteSuccessCount > 0;
	}

}
