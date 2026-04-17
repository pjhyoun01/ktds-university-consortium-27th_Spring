package com.ktdsuniversity.edu.config;

import jakarta.servlet.ServletRequest;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.security.authenticate.handler.LoginFailureHandler;
import com.ktdsuniversity.edu.security.authenticate.handler.LoginSuccessHandler;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityUserDetailsSevice;
import com.ktdsuniversity.edu.security.providers.UsernameAndPasswordAuthenticationProvider;

import java.io.IOException;
import java.net.URI;

// application.yml에서 작성할 수 없는 설정들을 적용하기 위한 Annotation
// @Component 의 자식 Annotation
@Configuration
// spring-boot-starter-validation 동작 활성화 시키기
// @EnableWebMvc가 추가되면 application.yml의 mvc 관련 설정들이 모두 무시된다.
//   1. spring.mvc.view.prefix, spring.mvc.view.suffix
//   2. src/main/resources/static 경로 사용 불가능.
@EnableWebMvc
// Spring Security 라이브러리를 활성화 시킴
// Spring Security 의 필터 목록을 확인하기 위해 사용
@EnableWebSecurity(debug = true) // 생략 가능
// 컨트롤러 혹은 서비스 코드에서 권한 검사를 수행하기 위한 Annotation add
@EnableMethodSecurity
public class HelloSpringConfiguration implements
		// WebMvc 설정을 위한 Configuration
		// @EnableWebMvc Annotation 에서 적용하는 기본 설정들을 변경하기 위함.
		WebMvcConfigurer {

	@Autowired
	private MembersDao membersDao;

	// SecurityPasswordEncoder의 Bean을 생성한다
	@Bean
	// <- 메소드가 실행되어서 반환되는 객체를 Bean Container 에 적재
	PasswordEncoder createsPasswordEncoder() {
		return new SecurityPasswordEncoder();
	}

	// SecurityUserDetailsService의 Bean을 생성한다
	// @Bean 으로 생성하는 객체들은 필요한 의존 생성 객체를 전달해줘야함
	@Bean
	UserDetailsService createUserDetailsService() {
		return new SecurityUserDetailsSevice(this.membersDao);
	}

	// UsernameAndPasswordAuthenticationProvider의 Bean을 생성한다
	@Bean
	// <- 유일한 객체로 관리함 / 익명클래스를 Bean으로 관리하고 싶을 때 사용할 수 있는 이점 존재
	AuthenticationProvider vreateAuthenticationProvider() {
		// 메소드가 여러번 실행되더라도 Bean 에 이미 적재된 객체가 있다면 생성하지 않음
		UserDetailsService userDetailsService = this.createUserDetailsService();
		PasswordEncoder passwordEncoder = this.createsPasswordEncoder();

		return new UsernameAndPasswordAuthenticationProvider(userDetailsService, passwordEncoder);
	}

	@Bean
	AuthenticationSuccessHandler createLoginSuccessHandler() {
		return new LoginSuccessHandler(this.membersDao);
	}

	@Bean
	AuthenticationFailureHandler createLoginFailureHandler() {
		return new LoginFailureHandler(this.membersDao);
	}

	// Spring Security 의 기본 로그인 절차를 수행하는 작업
	@Bean
	SecurityFilterChain configureFilterChain(HttpSecurity httpSecurity) {

		// 상대방이 내 서버로 접속할 수 있도록 허용하기
		// ==> 내 서버로 접속 가능한 안전한 URL 등록하기
		httpSecurity.cors(cors -> {
			CorsConfigurationSource source = (httpServletRequest) -> {
				//허용할 타 사이트의 도메인을 작성
				CorsConfiguration config = new CorsConfiguration();

				//허용할 타 사이트의 URL
				//192.168.211.11:3737에서 오는 모든 접근(API) 허용
				config.addAllowedOrigin("http://192.168.211.11:3737");
				config.addAllowedOrigin("http://192.168.211.11:8081");

				//허용할 타 사이트의 Method
				//192.168.211.11:3737에서 오는 POST와 GET으로 요청하는 접근만 허용
				// PUT, DELETE: 허용 X
				config.addAllowedMethod("POST");
				config.addAllowedMethod("GET");

				// 허용할 타 사이트의 요청 HttpHeader
				// 모든 요청 HttpHeader 허용
				config.addAllowedHeader("*");

				return config;
			};

			cors.configurationSource(source);
		});

		// CSRF 수정 (댓글 추가나 기타 기능들을 사용할 수 없음)
		// Spring Security CSRF 무효화
		httpSecurity.csrf(csrf -> csrf.disable());

		// usernamePasswordAuthenticationFilter 수정
		httpSecurity.formLogin(formLogin -> {
			// 로그인 URL지정
			formLogin.loginPage("/login")
					// Login 인증처리 URL
					// UsernameAndPasswordAuthenticationProvider가 실행 될 EndPoint
					.loginProcessingUrl("/login-provider")
					// 로그인에 필요한 아이디 파라미터 이름을 default"username"에서 "email"로 변경
					.usernameParameter("email")
					// 로그인에 성공하면
					// this.membersDao.updateSuccess(loginVO);
					.successHandler(this.createLoginSuccessHandler())
					// 로그인에 실패하면
					// this.membersDao.updateIncreaseLoginFailCount(email)
					// this.membersDao.updateBlock(email)
					.failureHandler(this.createLoginFailureHandler())
			;
		});
		return httpSecurity.build();
	}

	// Interceptor 등록 및 대상 URL 지정.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

	}

	// configureViewResolvers 설정
	// spring.mvc.view.prefix, spring.mvc.view.suffix 재설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// addResourceHandlers
	// src/main/resources/static 경로의 endpoint 재설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// /static/css/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/css/**") // /static/css/ 의 엔드포인트
				.addResourceLocations("classpath:/static/css/"); // /static/css/ 의 물리적인 위치

		// /static/image/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/image/**") // /static/image/ 의 엔드포인트
				.addResourceLocations("classpath:/static/image/"); // /static/image/ 의 물리적인 위치

		// /static/js/ 폴더에 있는 파일들에 대한 Endpoint 설정.
		registry.addResourceHandler("/js/**") // /static/js/ 의 엔드포인트
				.addResourceLocations("classpath:/static/js/"); // /static/js/ 의 물리적인 위치
	}
}
