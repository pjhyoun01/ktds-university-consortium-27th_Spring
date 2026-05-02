package com.ktdsuniversity.edu.security.authenticate.web;

import com.ktdsuniversity.edu.common.utils.ServletUtils;
import com.ktdsuniversity.edu.exceptions.HelloSpringApiException;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.MembersVO;
import com.ktdsuniversity.edu.members.vo.request.LoginVO;
import com.ktdsuniversity.edu.security.authenticate.service.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.security.providers.JsonWebTokenAuthenticationProvider;
import com.ktdsuniversity.edu.security.user.SecurityUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Map;

@Controller
public class JwtLoginController {

	@Autowired
	private MembersDao membersDao;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JsonWebTokenAuthenticationProvider jwtAuthenticationProvider;

	@ResponseBody
	@PostMapping("/api/authorization")
	public Map<String, String> doJwtLogin(@Valid @RequestBody LoginVO loginVO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> System.out.println("valid error: " + error.getDefaultMessage()));
			throw new HelloSpringApiException("로그인 실패", HttpStatus.BAD_REQUEST.value(), bindingResult.getFieldErrors());
		}

		UserDetails userDetails = null;
		try {
			// 이메일을 통해 회원의 정보를 수행
			userDetails = this.userDetailsService.loadUserByUsername(loginVO.getEmail());
		} catch (UsernameNotFoundException unfe) {
			throw new HelloSpringApiException("로그인 실패", HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		if (!userDetails.isAccountNonLocked()) {
			throw new HelloSpringApiException("로그인 실패", HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		// 비밀번호 일치 검사 수행
		SecurityPasswordEncoder securityPasswordEncoder = (SecurityPasswordEncoder) this.passwordEncoder;
		SecurityUser securityUser = (SecurityUser)  userDetails;
		MembersVO membersVO = securityUser.getMembersVO();

		if (!securityPasswordEncoder.matches(loginVO.getPassword(), membersVO.getSalt(), membersVO.getPassword())) {
			this.membersDao.updateIncreaseLoginFailCount(loginVO.getEmail());
			this.membersDao.updateBlock(loginVO.getEmail());

			throw new HelloSpringApiException("로그인 실패", HttpStatus.BAD_REQUEST.value(), "아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		loginVO.setIp(ServletUtils.getIp());
		this.membersDao.updateSuccessLogin(loginVO);


		String jwt = this.jwtAuthenticationProvider.makeJsonWebToken(loginVO.getEmail(), Duration.ofHours(9));

		return Map.of("token", jwt);
	}
}
