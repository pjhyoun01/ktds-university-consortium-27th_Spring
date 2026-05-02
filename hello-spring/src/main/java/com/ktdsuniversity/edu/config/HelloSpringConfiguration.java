package com.ktdsuniversity.edu.config;

import com.ktdsuniversity.edu.exceptions.handlers.AuthorizationDeniedExceptionHandler;
import com.ktdsuniversity.edu.security.authenticate.filters.JsonWebTokenAuthenticationFilter;
import com.ktdsuniversity.edu.security.providers.JsonWebTokenAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.security.authenticate.handler.LoginFailureHandler;
import com.ktdsuniversity.edu.security.authenticate.handler.LoginSuccessHandler;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityUserDetailsService;
import com.ktdsuniversity.edu.security.providers.UsernameAndPasswordAuthenticationProvider;

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

	@Autowired(required = false)
	@Lazy
	private MembersDao membersDao;

	// application.yml 에서 관련된 정보를 가져옴
	@Value("${app.jwt.secret-key}") // 환경설정 정보를 Bean으로 가져오는 방법
	private String jwtSecretKey;
	@Value("${app.jwt.issuer}") // Value 가 동작하는 조건 : @Component가 적용된 클래스에서만 가능
	private String jwtIssuer;

	@Bean
	JsonWebTokenAuthenticationProvider createJwtAuthenticationProvider() {
		return new JsonWebTokenAuthenticationProvider(this.jwtSecretKey, this.jwtIssuer);
	}

	// SecurityPasswordEncoder의 Bean을 생성한다.
	@Bean // 메소드가 실행되어서 반환되는 객체를 Bean Container에 적재한다.
	PasswordEncoder createPasswordEncoder() {
		return new SecurityPasswordEncoder();
	}

	// SecurityUserDetailsService의 Bean을 생성한다.
	// @Bean으로 생성하는 객체(Bean)들은 필요한 의존 객체를 생성자로 주입해 주어야 한다.
	@Bean
	UserDetailsService createUserDetailsService() {
		return new SecurityUserDetailsService(this.membersDao);
	}

	// UsernameAndPasswordAuthenticationProvider의 Bean을 생성한다.
	@Bean
	AuthenticationProvider createAuthenticationProvider() {
		UserDetailsService userDetailsService = this.createUserDetailsService();
		PasswordEncoder passwordEncoder = this.createPasswordEncoder();

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

	@Bean
	OncePerRequestFilter createJwtAuthFilter() {
		return new JsonWebTokenAuthenticationFilter(this.createJwtAuthenticationProvider(),
				this.createUserDetailsService());
	}

	/**
	 * 특정URL에 대해서 Spring Security가 개입하지 않도록 설정.
	 * /WEB-INF/views/ 아래의 모든 jsp 파일들은 Spring Security의 간섭을 받지 않는다.
	 * ---------
	 * Controller에서 해당 페이지를 노출하려 할 때 "/WEB-INF/views/.../*.jsp" 경로 사용시
	 * 인증이 된 사용자에게만 노출시키려 하는 경우가 존재 ==> Spring Security가 개입하지 않도록 설정.
	 * @return
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers("/WEB-INF/views/**");
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
				config.addAllowedOrigin("*");
//				config.addAllowedOrigin("http://192.168.211.11:8081");

				//허용할 타 사이트의 Method
				//192.168.211.11:3737에서 오는 POST와 GET으로 요청하는 접근만 허용
				// PUT, DELETE: 허용 X
				config.addAllowedMethod("POST");
				config.addAllowedMethod("GET");
				config.addAllowedMethod("PUT");
				config.addAllowedMethod("DELETE");

				// 허용할 타 사이트의 요청 HttpHeader
				// 모든 요청 HttpHeader 허용
				config.addAllowedHeader("*");

				return config;
			};

			cors.configurationSource(source);
		});

		// CSRF 수정 (댓글 추가나 기타 기능들을 사용할 수 없음)
		// Spring Security CSRF 무효화
//		httpSecurity.csrf(csrf -> csrf.disable());

		// API 통신에서는 CSRF 를 체크하지 않도록 설정
		// "/api/**" : api로 시작하는 모든 엔드포인트는 csrf를 체크하지 않음
		httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

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

		// Custom Filter(JsonWebTokenAuthenticationFilter) 추가
		httpSecurity.addFilterAfter(this.createJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		// auth가 비어있지 않다면 인증 정보가 있는거 true  비어있다면 인증정보 없음 false
		// AuthorizationDeniedExceptionHandler 추가
		// Controller 코드 이하에서 @preAuthgorized() 검증에 실패하면 아래 설정에 등록한 Handler가 동작
		httpSecurity.exceptionHandling(exception -> {
			exception.accessDeniedHandler(new AuthorizationDeniedExceptionHandler());
		});
		//

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
