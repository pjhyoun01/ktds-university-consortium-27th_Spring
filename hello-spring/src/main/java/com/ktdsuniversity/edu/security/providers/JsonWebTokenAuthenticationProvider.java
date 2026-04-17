package com.ktdsuniversity.edu.security.providers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

/**
 * 사용자의 정보를 이용해 인증 객체를 생성하고 검증하는 클래스
 * Spring Security AuthenticationProvider와 무관
 * 사용 목적: API를 호출할 때 인증수단으로 사용하기 위해
 */
public class JsonWebTokenAuthenticationProvider {
	/**
	 * 사용자의 이메일을 이용해 인증용 JWT를 생성
	 *
	 * @param email     사용자의 이메일
	 * @param expiredAt JWT의 유효기간 (생성된 시간으로부터 (시간, 일, 월, 연)까지 유효)
	 * @return email과 expiredAt
	 */
	public String makeJsonWebToken(String email, Duration expiredAt) {

		// JsonWebToken이 발행되는 날짜와 시간을 생성
		Date issuedAt = new Date();

		// JsonWebToken이 만료되는 날짜와 시간을 생성
		// 발행 날짜 시간 + expiredAt
		Date expiryDate = new Date(issuedAt.getTime() + expiredAt.toMillis());

		// 암/복호화 키 생성
		// TODO application.yml의 secret-key에서 가져오도록 변경
		SecretKey signKey = Keys.hmacShaKeyFor("jvjoauet1hn0v948gndsvotya2q49038t".getBytes());

		String jsonWebToken = Jwts.builder()
							// JsonWebToken을 발생한 시스템의 이름
							// TODO application.yml의 issuer에서 가져오도록 변경
							.issuer("hello-spring")
							// JsonWebToken의 이름
							.subject(email + "-token")
							// JsonWebToken에 포함되어야할 회원의 정보들
							// claim이 늘어날 수록 안정성 떨어짐
							.claim("identify", email)
							// JsonWebToken을 발행한 시간
							.issuedAt(null)
							// JsonWebToken의 만료되는 시간
							.expiration(null)
							// 평문으로 구성된 JsonWebToken을 암호화
							.signWith(null)
							// Jwts에제공된 데이터를 이용해 String Type의 Token을 생성
							.compact();
		return jsonWebToken;
	}

	public static void main(String[] args) {
		JsonWebTokenAuthenticationProvider jwtProvider = new JsonWebTokenAuthenticationProvider();
		String jwt = jwtProvider.makeJsonWebToken("test@gmail.com", Duration.ofMinutes(20));
	}
}
