package com.admin.web.shiro;

import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationToken;

import static com.admin.module.shiro.JwtTokenUtil.CLAIM_KEY_USERID;


/**
 * shiro登录用户信息
 */
public class JWTToken  implements AuthenticationToken {

    // 用户id
    private Integer userId;

    public JWTToken(Claims claims) {
        this.userId = (Integer) claims.get(CLAIM_KEY_USERID);
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return userId;
    }
}
