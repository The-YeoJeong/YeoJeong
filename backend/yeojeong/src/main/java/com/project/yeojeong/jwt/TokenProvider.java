package com.project.yeojeong.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


// Token의 생성과 유효성 검증 등을 담당
@Component
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String MEMBER_ID = "memberId";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000; // Token 만료시간 (3600 * 1000 : 1시간)
    }
    // InitializingBean implements해서 afterPropertiesSet() overide한 이유는
    // 빈이 생성이되고 의존성 주입을 받은 후에 TokenProvider에서 주입받은 secret값을 Base64 Decode해서 key변수에 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Authentication 객체의 권한 정보를 이용해, Token을 생성
    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberId", authentication.getName());

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setHeader(headers)
                .setSubject("member auth")
                .setClaims(payloads)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // OAtuh용 token(oauth는 인증을 거치고 들어온 사용자이고, 비밀번호를 확인할수 없기 때문에 authentication을
    // UsernamePasswordAuthenticationToken로 만들수 없음)
    public String createToken(String memberId) {
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberId", memberId);

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setHeader(headers)
                .setSubject("member auth")
                .setClaims(payloads)
                .claim(AUTHORITIES_KEY, "ROLE_USER")
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // 역으로 Token을 parameter로 받아서 Token에 담겨있는 정보를 이용해 Authentication 객체를 return
    public Authentication getAuthentication(String token) {
        // Token으로 Claims을 만들고
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Claims에서 권한 정보를 빼냄
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 빼낸 권한정보로 User객체를 만듦
        User principal = new User((String) claims.get(MEMBER_ID), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // Token을 parameter로 받아서 Token의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            // Token을 parsing해서
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        // 발생하는 Exception들을 catch에서 문제가 있으면 false, 정상이면 true
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
