package com.ktdsuniversity.edu.security.authenticate.user;

import com.ktdsuniversity.edu.members.vo.MembersVO;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// spring Security의 UserDetails를 구현하는 클래스
public class SecurityUserDetails implements UserDetails {

    //MembersVO를 속성으로 가짐
    private MembersVO membersVO;

    //membersVO의 getter
    public MembersVO getMembersVO() {
        return this.membersVO;
    }

    // SecurityUserDetails의 생성자
    public SecurityUserDetails(MembersVO membersVO) {
        this.membersVO = membersVO;
    }

    // 회원의 role을 Security가 인식할 수 있는 형태로 변환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // membersVO의 roles를 가져와 각각의 role앞에 ROLE_을 붙여서 반환
        return this.membersVO.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    // 로그인한 회원의 PW
    @Override
    public @Nullable String getPassword() {
        return this.membersVO.getPassword();
    }

    // 사용자의 아이디 (email)
    @Override
    public String getUsername() {
        return this.membersVO.getEmail();
    }

    // 계정의 잠김여부 (잠기지 않았는지를 묻고 있음)
    @Override
    public boolean isAccountNonLocked() {
        return this.membersVO.getBlockYn().equals("N");
    }
}
