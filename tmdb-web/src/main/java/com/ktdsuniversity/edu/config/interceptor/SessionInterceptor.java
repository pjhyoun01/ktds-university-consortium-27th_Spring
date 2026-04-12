package com.ktdsuniversity.edu.config.interceptor;

import java.io.PrintWriter;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("__USER__") == null) {
			String pathname = request.getRequestURI();
			if (pathname.startsWith("/api/")) {

				String jsonResult = "{" 
						+ "    \"status\": 403," 
						+ "    \"error\" : \"권한이 부족합니다.\"" 
						+ "}";

				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");

				PrintWriter writer = response.getWriter();
				writer.write(jsonResult);
				writer.flush();

				return false;
			}

			String loginPagePath = "/WEB-INF/views/members/login.jsp";

			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPagePath);
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}
}
