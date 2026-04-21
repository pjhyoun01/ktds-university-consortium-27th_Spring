package com.ktdsuniversity.edu.security.authenticate.service;

import com.ktdsuniversity.edu.members.helpers.SHA256Util;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
// spring Security의 PasswordEncoder를 구현하는 클래스
public class PasswordEncoderImpl implements PasswordEncoder {
    // TODO 오버라이딩으로 사용 가능한 방법 생각해보기

    // PasswordEncoder 인터페이스의 필수 구현 메서드
    // salt 값을 이용하여 암호화를 하고 비교하기 떄문에 interface의 규칙으로 Override만 해줌
    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return null;
    }
    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        return false;
    }

    // encode를 오버라이딩
    // 평문 비밀번호와 salt를 이용하여 암호화된 비밀번호를 반환
    public String encode(String rawPassword, String salt) {
        return SHA256Util.getEncrypt(rawPassword, salt);
    }

    // 평문 비밀번호, salt, Db에 저장된 암호화된 비밀번호를 받아 비교
    public boolean matches(String rawPassword, String salt, String encodedPassword) {
        return this.encode(rawPassword, salt).equals(encodedPassword);
    }
}
