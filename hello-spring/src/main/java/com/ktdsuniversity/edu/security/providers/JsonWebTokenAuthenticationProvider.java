package com.ktdsuniversity.edu.security.providers;

import io.jsonwebtoken.Claims;
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


	private String secretKey;
	private String issuer;

	public JsonWebTokenAuthenticationProvider(String secretKey, String issuer) {
		this.secretKey = secretKey;
		this.issuer = issuer;
	}

	/**
	 * 사용자가 요청할 때마다 Request Header[Authorization]에 전달한
	 * JsonWebRToken을 가져와 복호화 시킴
	 * 복호화 된 결과에서 사용자의 이메일(identify)을 추출하여 반환
	 *
	 * @param jsonWebToken 사용자가 전달한 토큰
	 * @return jsonWebToken에서 추출한 사용자의 이메일
	 */
	public String decryptJsonWebToken(String jsonWebToken) {

		SecretKey signKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

		Claims claims = Jwts.parser() // jsonWebToken을 분석하기 위한 선언
							.verifyWith(signKey) // jsonWebToken을 복호화 하기 위한 비밀키 지정
							.requireIssuer(this.issuer) // 사용자가 전달한 JsonWebToken이 hello-spring의 시스템에서 만든것인지 확인
							.build() // jsonWebToken을 복호화를 시작
							.parseSignedClaims(jsonWebToken) // 사용자가 전달한 JsonWebToken 을 복호화 한다
							.getPayload(); // 복호화된 경과에서 claims만 모아 반환(Map의 형태)


		// 사용자가 전달한 JsonWebToken을 복호화 한 뒤  identity값을 추출
		String email = claims.get("identify", String.class);
		return email;
	}

	/**
	 * 사용자의 이메일을 이용해 인증용 JWT를 생성하고
	 * 결과를 사용자에게 보내주어야 함
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
		SecretKey signKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

		String jsonWebToken = Jwts.builder()
				// JsonWebToken을 발생한 시스템의 이름
				.issuer(this.issuer)
				// JsonWebToken의 이름
				.subject(email + "_token")
				// JsonWebToken에 포함되어야할 회원의 정보들
				// claim이 늘어날 수록 안정성 떨어짐
				.claim("identify", email)
				// JsonWebToken을 발행한 시간
				.issuedAt(issuedAt)
				// JsonWebToken의 만료되는 시간
				.expiration(expiryDate)
				// 평문으로 구성된 JsonWebToken을 암호화
				.signWith(signKey)
				// Jwts에제공된 데이터를 이용해 String Type의 Token을 생성
				.compact();
		return jsonWebToken;
	}

//	public static void main(String[] args) {
//		JsonWebTokenAuthenticationProvider jwtProvider = new JsonWebTokenAuthenticationProvider("jvjoauet1hn0v948gndsvotya2q49038t", "hello-spring");
//		String jwt = jwtProvider.makeJsonWebToken("test@gmail.com", Duration.ofMinutes(20));
//		System.out.println(jwt);
//
//		String email = jwtProvider.decryptJsonWebToken(jwt);
//		System.out.println(email);
//	}
}
