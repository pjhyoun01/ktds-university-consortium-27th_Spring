package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.helper.SHA256Util;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.members.vo.request.RegistVO;
import com.ktdsuniversity.edu.members.vo.request.UpdateVO;
import com.ktdsuniversity.edu.members.vo.response.SearchResultVO;

@Service
public class MembersServiceImpl implements MembersService {

	@Autowired
	private MembersDao membersDao;
	
	@Override
	public boolean createNewMember(RegistVO registVO) {
		MembersVO membersVO = this.membersDao.selectMemberByEmail(registVO.getEmail());
		if (membersVO != null) {
			throw new IllegalArgumentException(registVO.getEmail() + "은 이미 사용중입니다");
		}

		String newSalt = SHA256Util.generateSalt();
		String userPassword = registVO.getPassword();
		
		userPassword = SHA256Util.getEncrypt(userPassword, newSalt);
		
		registVO.setSalt(newSalt);
		
		registVO.setPassword(userPassword);
		
		int insertCount = this.membersDao.insertNewMember(registVO);
		return insertCount == 1;
	}

	@Override
	public MembersVO findMemberByEmail(String email) {
		MembersVO searchResult = this.membersDao.selectMemberByEmail(email);
		return searchResult;
	}

	@Override
	public boolean updateMemberByEmail(UpdateVO updateVO) {
		int updateCount = this.membersDao.updateMemberByEmail(updateVO);
		return updateCount == 1;
	}

	@Override
	public boolean deleteMemberByEmail(String email) {
		int deleteCount = this.membersDao.deleteMemberByEmail(email);
		return deleteCount == 1;
	}

	@Override
	public SearchResultVO findMembersList() {
		SearchResultVO result = new SearchResultVO();
		int searchCount = this.membersDao.selectMembersCount();
		result.setCount(searchCount);
		
		if (searchCount == 0) {
			return result;
		}
		
		List<MembersVO> searchResult = this.membersDao.selectMembersList();
		result.setResult(searchResult);
		
		return result;
	}

	@Override
	public MembersVO findMemberByEmailAndPassword(LoginVO loginVO) {

		MembersVO member = this.membersDao.selectMemberByEmail(loginVO.getEmail());
		if (member == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}

		if (member.getBlockYn().equals("Y")) {
			String latestLoginFailDate = member.getLatestLoginFailDate();
			DateTimeFormatter dateTimepattern = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");
			LocalDateTime latestBlockDateTime = LocalDateTime.parse(latestLoginFailDate, dateTimepattern);

			if (latestBlockDateTime.isAfter(LocalDateTime.now().minusMinutes(120))) {
				throw new IllegalArgumentException("차단된 계정입니다.");
			}
		}

		String getPassword = member.getPassword();
		String encryptInputPassword = SHA256Util.getEncrypt(loginVO.getPassword(), member.getSalt());

		if (!getPassword.equals(encryptInputPassword)) {
			this.membersDao.updateLoginFailCount(loginVO.getEmail());

			if (member.getLoginFailCount() >= 5) {
				this.membersDao.updateBlock(loginVO.getEmail());
			}

			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}

		this.membersDao.updateIpAndLoginDateByEmail(loginVO);

		return member;
	}

}
