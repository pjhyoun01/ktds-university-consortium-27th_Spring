package com.ktdsuniversity.edu.members.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.common.utils.SHA256Util;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;

	@Override
	public MembersVO readMemberByEmailAndPassword(LoginVO loginVO) {
		MembersVO loginUser = this.membersDao.selectMembersByEmail(loginVO.getEmail());
		if (loginUser == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다.");
		}

		if (loginUser.getBlockYn().equals("Y")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss");
			LocalDateTime latestLoginFailDate = LocalDateTime.parse(loginUser.getLatestLoginFailDate(), formatter);
			if (latestLoginFailDate.isAfter(LocalDateTime.now().minusMinutes(120))) {
				this.membersDao.updateBlockN(loginVO.getEmail());
			}

			Duration duration = Duration.between(latestLoginFailDate.plusMinutes(120), LocalDateTime.now());
			long minutes = duration.toMinutes();
			if (minutes % 60 == 0) {
				throw new IllegalArgumentException(minutes + "분 후에 시도해 주세요.");
			} else {
				long hour = minutes % 60;
				minutes /= 60;
				throw new IllegalArgumentException(hour + "시간 " + minutes + "분 후에 시도해 주세요.");
			}
		}

		String encryptInputPassword = SHA256Util.getEncrypt(loginVO.getPassword(), loginUser.getSalt());
		if (!encryptInputPassword.equals(loginUser.getPassword())) {
			this.membersDao.updateLoginFailCountPlusOne(loginVO.getEmail());

			this.membersDao.updateBlockY(loginVO.getEmail());
			throw new IllegalArgumentException("이메일 또는 비밀번호가 틀렸습니다.");
		}

		this.membersDao.updateLoginSuccess(loginVO);

		return loginUser;
	}

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
