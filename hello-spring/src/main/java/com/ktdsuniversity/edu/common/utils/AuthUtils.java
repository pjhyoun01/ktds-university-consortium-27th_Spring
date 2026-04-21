package com.ktdsuniversity.edu.common.utils;

import com.ktdsuniversity.edu.exceptions.HelloSpringException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.ktdsuniversity.edu.members.vo.MembersVO;

import java.util.List;

public abstract class AuthUtils {

	public AuthUtils() {}

	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null;
	}

	public static Authentication getAuthentication() {
		if (isAuthenticated()) {
			return SecurityContextHolder.getContext().getAuthentication();
		}
		throw new HelloSpringException("잘못된 접근입니다.", "errors/404");
	}

	public static MembersVO getLoginUser() {
		return (MembersVO) getAuthentication().getPrincipal();
	}

	public static String getUserEmail() {
		return getLoginUser().getEmail();
	}

	public static boolean isSuperAdmin() {
		return getLoginUser().getRoles().contains("RL-20260414-000001");
	}

	public static boolean isAdmin() {
		List<String> roles = getLoginUser().getRoles();
		return roles.contains("RL-20260414-000001") || roles.contains("RL-20260414-000002");
	}

	public static void doLogout() {
		LogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(ServletUtils.getRequest(), ServletUtils.getResponse(), getAuthentication());
	}

	public static void isNotSuperAdmin() {
		if (!isSuperAdmin()) {
			throw new HelloSpringException("잘못된 접근입니다", "errors/403");
		}
	}

	public static void isNotAdmin() {
		if (!isAdmin()) {
			throw new HelloSpringException("잘못된 접근입니다", "errors/403");
		}
	}

	public static void isNotMatchEmail(String email) {
		if (!getUserEmail().equals(email)) {
			throw new HelloSpringException("잘못된 접근입니다", "errors/403");
		}
	}
}
