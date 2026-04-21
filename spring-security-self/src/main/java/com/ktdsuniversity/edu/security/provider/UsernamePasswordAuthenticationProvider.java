package com.ktdsuniversity.edu.security.provider;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.security.authenticate.service.PasswordEncoderImpl;
import com.ktdsuniversity.edu.security.authenticate.user.SecurityUserDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoderImpl passwordEncoderImpl;

// AuthenticationManager에서 spring이 Bean으로 등록된 AuthenticationProvider의 authenticate 메서드를 동작
@Override
public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {

    // UsernamePasswordAuthenticationFilter의 attemptAuthentication 에서 Username과 Password를 인증되지 않은 임시 토큰 UsernamePasswordAuthenticationToken을 생성
    // 생성된 임시 토큰애서 이름을 가져옴
    String email = authentication.getName();

    // UserDetailsService의 loadUserByUsername을 호출하여 DB에서 회원 정보를 찾아 UserDetails 객체로 가져옴
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
    // userDetails의 계정이 잠겨있다면 LockedException을 던짐
    if (!userDetails.isAccountNonLocked()) {
        throw new LockedException("아아디 또는 비밀번호가 일치하지 않습니다.");
    }

    // 생성된 임시 토큰애서 비밀번호를 가져옴
    String rawPassword = authentication.getCredentials().toString();

    // SecurityUserDetails로 변환한 userDetails의 MembersVO를 가져옴
    MembersVO membersVO = ((SecurityUserDetails) userDetails).getMembersVO();

    // passwordEncoderImpl에서 salt를 이용해서 구현한 파라미터가 3개인 matches수행
    boolean isMatch = this.passwordEncoderImpl.matches(rawPassword, membersVO.getSalt(), userDetails.getPassword());
    // 입력받은 PW와 UserDetails에 담긴 SALT를 PasswordEncoder 사용하여 암호화한 값과 UserDetails에 담긴 암호화된 PW와 대조
    // 만약 일치하지 않다면 BadCredentialsException을 던짐
    if (!isMatch) {
        throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    // 대조 결과가 일치한다면 UserDetails의 정보, credentials, Authorities를 담은 인증 완료 상태의 새로운 UsernamePasswordAuthenticationToken을 생성하여 필터로 반환
    return new UsernamePasswordAuthenticationToken(membersVO, null, userDetails.getAuthorities());
}

    // 이 provider가 발급하는 토큰의 종류 설정
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
