package com.ktdsuniversity.edu.security.authenticate.handler;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	private MembersDao membersDao;

	public LoginFailureHandler(MembersDao membersDao) {
		this.membersDao = membersDao;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String email = request.getParameter("email");

		if (exception instanceof BadCredentialsException) {
			this.membersDao.updateIncreaseLoginFailCount(email);
			this.membersDao.updateBlock(email);
		}

		// 로그인 페이지 보여주기
		String loginPagePath = "/WEB-INF/views/members/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginPagePath);

		// 로그인 페이지에 이메일 전달해주기
		LoginVO loginVO = new LoginVO();
		loginVO.setEmail(email);

		// Spring > model.addAttribute(k, v)와 같은 코드
		request.setAttribute("inputData", loginVO);

		// 에러 메세지 보내주기
		request.setAttribute("errorMessage", exception.getMessage());

		dispatcher.forward(request, response);
	}

}
