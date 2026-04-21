package com.ktdsuniversity.edu.security.authenticate.filters;

import com.ktdsuniversity.edu.common.utils.StringUtils;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.security.providers.JsonWebTokenAuthenticationProvider;
import com.ktdsuniversity.edu.security.user.SecurityUser;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 이 클래스의 객체가 SecurityFilterChain에 등록되어
 * 인증이 필요한 모든 end point가 실행되기 전에
 * Authentication(UsernamePasswordAuthenticationToken)을 생성하도록 하는 필터
 *
 * HttpServletRequest의 jeader로 전달된 Authorization에 들어있는 JWT를 가져와 분석을 진행하고
 * 분석된 경과를 AuthenticationToken으로 생성
 */
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {

	private JsonWebTokenAuthenticationProvider jsonWebTokenAuthenticationProvider;
	private UserDetailsService userDetailsService;

	public JsonWebTokenAuthenticationFilter(JsonWebTokenAuthenticationProvider jsonWebTokenAuthenticationProvider, UserDetailsService userDetailsService) {
		this.jsonWebTokenAuthenticationProvider = jsonWebTokenAuthenticationProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// 다음 필터가 동작되기 이전의 이 필터가 해야할 일 작성
		// preHandle

		// Authorization
		// 요청 URL이 "/api/"로 시작하는 경우만 실행
		// 요청 RUL 가져오기
		String requestURI = request.getServletPath();
		// requestURI : 경로
		if (requestURI.startsWith("/api/")) {
			// Request에서 header에 있는 Authorization 꺼내온다
			String jsonWebToken = request.getHeader("Authorization");

			if (!StringUtils.isEmpty(jsonWebToken)) {
//				String errorMessage = "{ \"error\": \"인증이 필요합니다\" }";
//
//				//json 반환 시작
//				response.setCharacterEncoding("UTF-8");
//				response.setContentType("application/json");
//
//				PrintWriter writer = response.getWriter();
//				writer.write(errorMessage);
//				writer.flush();
//				return;
				// JWT를 복호화 시켜 email을 가져온다
//				String email = this.jsonWebTokenAuthenticationProvider.decryptJsonWebToken(jsonWebToken);
				String email = null;
				try {
					email = this.jsonWebTokenAuthenticationProvider.decryptJsonWebToken(jsonWebToken);
				} catch (JwtException je) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json");

					PrintWriter writer = response.getWriter();
					writer.append("{ \"error\": \"인증이 필요하거나 잘못된 권한입니다.\" }");
					writer.flush();
				}

				// email을 이용해서 사용자의 정보와 권한을 조회한다
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

				// 사용자의 정보를 이용해 AuthenticationToken(UsernamePasswordAuthenticationToken)을 발행
				Authentication authToken = new UsernamePasswordAuthenticationToken(((SecurityUser) userDetails).getMembersVO(), userDetails.getPassword(), userDetails.getAuthorities());

				// 발행한 AUthenticationToken을 SecurityContext에 적재 (일회성 토큰)
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		}


		filterChain.doFilter(request, response); //이 다음 필터가 있다면 그 필터를 동작

		// 모든 필터가 동작이 완료되고 Filter Chain의 역순으로 응답이 돌아 올 때
		// 이 필터가 해야할 일 작성
		// postHandle
	}
}
