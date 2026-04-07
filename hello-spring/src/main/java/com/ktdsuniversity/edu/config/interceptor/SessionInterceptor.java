package com.ktdsuniversity.edu.config.interceptor;

import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//로그인이 필요한 url (로그인 페이지로 이동시키기)
//	글 작성페이지
//	글 작성처리 url
//	글 수정 페이지
//	글 수정 처리 url
//	글 삭제 처리 url
//	회원 정보 수정 페이지
//	회원 정보 수정 url
//	회원 삭제 처리 url
//	회원 탈퇴 처리 url
//	회원 로그아웃 처리 url
//로그인이 필요없는 url
//	회원가입 페이지
//	회원가입 처리
//	로그인 페이지
//	로그인 처리
//	위 경우 세션이 있는 상태에서 login 페이지 접근 불가
//	static resource(JS, CSS, Image)
//	게시글 목록 페이지
//	게시글 내용 조회 페이지
//	이메일 중복 검사 URL
public class SessionInterceptor implements HandlerInterceptor {
	/**
	 * 컨트롤러 실행 이전에 수행할 공통 코드를 작성하는 영역
	 * 
	 * @param request  브라우저가 요청한 내용
	 * @param response 컨트롤러가 실행되기 이전에 브라우저로 응답을 보내는 역할
	 * @param handler  실행할 컨트롤러
	 * @return 컨트롤러의 실행 여부를 반환 true: 컨트롤러 실행, false: 컨트롤러 미실행
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// servlet 코드지만 동작은 spring이 실행

		// 세션을 가져옴
		HttpSession session = request.getSession();
		// 세션이 있는지 검사
		if (session.getAttribute("__LOGIN_DATA__") == null) {
			// 세션이 없으면 컨트롤러는 실행시키지 않음
			// 사용자에게 로그인 페이지를 보여주도록 해야함
			// 로그인 하고나면 기존 url을 보여줘야함
			String loginPagePath = "/WEB-INF/views/members/login.jsp";
			// 브라우저에게 보여질 화면설정
			RequestDispatcher dispatcher = request.getRequestDispatcher(loginPagePath);
			// 브라우저에게 화면을 보내줌 URL은 바뀌지 않음
			dispatcher.forward(request, response);

			return false;
		}

		// 세션이 있으면 컨트롤러를 실행
		return true;
	}

	/**
	 * 컨트롤러 실행 이후 수행할 공통 코드를 작성하는 영역
	 * 
	 * @param request      브라우저가 요청한 내용
	 * @param response     컨트롤러가 실행한 후에 브라우저로 응답을 보내는 역할
	 * @param handler      실행된 컨트롤러
	 * @param modelAndView 컨트롤러가 반환시킨 View(Template)와 Model
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * JSP를 HTML로 변환시켜 브라우저로 변환하기 직전에 수행되는 영역
	 * 
	 * @param request  브라우저가 요청한 내용
	 * @param response 컨트롤러가 실행한 후에 브라우저로 응답을 보내는 역할
	 * @param handler  실행된 컨트롤러
	 * @param ex       catch 되지 않는 예외 객체
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
