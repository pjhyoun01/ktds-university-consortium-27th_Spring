package com.ktdsuniversity.edu.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.ktdsuniversity.edu.members.vo.MembersVO;

public abstract class TokenUtils {

	public TokenUtils() {}

	public static Authentication getToken() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static MembersVO getLoginUser() {
		return (MembersVO) getToken().getPrincipal();
	}

	public static String getLoginUserEmail() {
		return getLoginUser().getEmail();
	}

	public static void doLogout () {
		LogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(ServletUtils.getRequest(), ServletUtils.getResponse(), getToken());
	}
}
