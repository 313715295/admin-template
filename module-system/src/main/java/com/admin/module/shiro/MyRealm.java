package com.admin.module.shiro;

import com.admin.module.entity.User;
import com.admin.module.service.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class MyRealm extends AuthorizingRealm {

   private final UserService userService;
   /*
   大坑！！！bean加载顺序问题，shiro体系会先加载bean，导致bean不能被springcache增强代理，缓存无效
    可以用 @Lazy懒加载，取消预初始化，使用的时候再加载。或者使用 springContext.getBean("")
    */
    @Lazy
    public MyRealm(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        TokenInfo token = (TokenInfo) principalCollection.getPrimaryPrincipal();
        Integer userId = token.getUserId();
        User user = userService.getUserByName(new User().setName("zwq"));
        //根据用户id获取权限列表，存入shiro中，不开启shiro的缓存，使用service的缓存
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<>();
        permissions.add("get");
        permissions.add("get1");
        info.setStringPermissions(permissions);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        /*因在拦截器已做过token校验，这里直接把用户信息放入shiro供后续使用,暂时只存放userID，后续可拓展
          第一个参数即后续的用户登录信息，可通过SecurityUtils.getSubject().getPrincipal()获得*/
        JWTToken jwtToken = (JWTToken) authenticationToken;
        TokenInfo token = new TokenInfo().setUserId((Integer) jwtToken.getPrincipal());
        return new SimpleAuthenticationInfo(token,authenticationToken.getCredentials(),this.getName());
    }

}
