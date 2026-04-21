package com.ktdsuniversity.edu.security.authenticate.service;

import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.security.authenticate.user.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// UserDetailsService 인터페이스를 구현하는 클래스
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MembersDao membersDao;

    //UserDetailsService의 구현 메서드 파라미터는 로그인시 입력받은 username (email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // email로 회원을 조회하여 MembersVO로 가져옴
        MembersVO loadUser = this.membersDao.selectMemberByEmail(username);
        //MembersVo가 비어있다면 UsernameNotFoundException을 던짐
        if (loadUser == null) {
            throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        // email로 회원의 role을 String 타입의 리스트로 가져옴
        List<String> userRole = this.membersDao.selectMemberRolesByEmail(username);
        // MembersVo의 roles에 userRole을 set 해줌
        loadUser.setRoles(userRole);

        // loadUser를 파라미터로 SecurityUserDetails 생성
        return new SecurityUserDetails(loadUser);
    }
}
