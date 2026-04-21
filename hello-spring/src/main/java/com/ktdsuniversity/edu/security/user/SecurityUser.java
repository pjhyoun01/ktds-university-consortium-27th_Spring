package com.ktdsuniversity.edu.security.user;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ktdsuniversity.edu.members.vo.MembersVO;

// Spring Security가 사용자를 식별할 때 사용
public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = 7907191462472441568L;

	private MembersVO membersVO;

	public MembersVO getMembersVO() {
		return this.membersVO;
	}

	public SecurityUser(MembersVO membersVO) {
		this.membersVO = membersVO;
	}

	/** 사용자의 권한 목록을 관리
	 * 추후 권한 별 사비스 제공 시 사용
	 * ROLES 테이블에서 조회
	 * 
	 * GeantedAuthorition
	 * Collection <- List
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Spring Security 가 체크하는 권한 2가지
		// 1.ROLE ==> 권한
		// 2.ACTION ==> 생성, 조회, 수정, 삭제, 다룽호드, 업로드...
		// Spring Security 가 ROLE과 ACTION을 구분하는 방법
		// ROLE ==> Prefix == 'ROLE_RL...'
		// ACTION ==> ACTION의 이름으로 작성(CREATE_RL..., UPDATE_RL..., ...)
		return this.membersVO.getRoles()
							 .stream()
							 .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
							 .toList();
	}

	/** 로그인한 회원의 패스워드
	 */
	@Override
	public @Nullable String getPassword() {
		return this.membersVO.getPassword();
	}

	/** 사용자의 아이디(식별 가능한)
	 *  현재 프로젝트 기준 memberVO.email
	 */
	@Override
	public String getUsername() {
		return this.membersVO.getEmail();
	}

	/** 계정의 잠김여부
	 */
	@Override
	public boolean isAccountNonLocked() {
		return this.membersVO.getBlockYn().equals("N");
	}
}
