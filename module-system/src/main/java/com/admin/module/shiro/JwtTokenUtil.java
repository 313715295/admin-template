package com.admin.module.shiro;

import com.admin.commons.base.BaseEntity;
import com.admin.module.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成token和校验token
 */
public class JwtTokenUtil {

    public static final String CLAIM_KEY_USERID = "userId";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String secret = "cb7a3aa1a736aac734e1e2b19b698b7e";
    private static final Long expiration = 28800L;
    public static final String TOKEN = "token";




    //校验token签名并获取主体信息，会抛出相关校验异常
    public static Claims verifySign(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }



    /**
     * 生产token
     * @param user 负载信息
     */
    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERID,user.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

}
