브라우저에서 Username과 Password를 담아 POST 요청 (default Endpoint: /login)

FilterChainProxy가 요청 URL에 매칭되는 SecurityFilterChain을 찾고 필터들을 통과

UsernamePasswordAuthenticationFilter의 부모 클래스인 AbstractAuthenticationProcessingFilter의 doFilter 메서드를 실행

requiresAuthentication로 로그인 요청이 맞는지 확인

UsernamePasswordAuthenticationFilter의 attemptAuthentication 메서드를 실행

파라미터에서 추출한 Username과 Password를 인증되지 않은 임시 토큰 UsernamePasswordAuthenticationToken을 생성

임시 토큰을 AuthenticationManager에게 전달

AuthenticationManager는 Bean으로 등록된 AuthenticationProvider의 authenticate 메서드를 호출 (여러 인증방식을 이 과정에서 처리)

UserDetailsService의 loadUserByUsername을 호출하여 DB에서 회원 정보를 찾아 UserDetails 객체로 가져옴

입력받은 PW와 UserDetails에 담긴 SALT를 PasswordEncoder 사용하여 암호화한 값과 UserDetails에 담긴 암호화된 PW와 대조

대조 결과가 틀리다면 AuthenticationException을 발생시키고 필터로 돌아가 AuthenticationFailureHandler 수행

대조 결과가 일치한다면 UserDetails의 정보, credentials, Authorities를 담은 인증 완료 상태의 새로운 UsernamePasswordAuthenticationToken을 생성하여 필터로 반환
(이미 인증이 완료되었고 credentails은 @Nullable인데 비밀번호를 담아서 반환하는 이유는?)

필터는 이 완성된 토큰을 SecurityContextHolder.getContext().setAuthentication(토큰 값)에 저장하고 SecurityContextHolder.getContext().getAuthentication()으로 사용

이후 AuthenticationSuccessHandler를 실행하여 로그인 성공 처리와 페이지 redirect처리
