package com.admin.module.service;

import com.admin.commons.base.Result;
import com.admin.module.entity.User;
import com.admin.commons.base.BaseService;
import com.admin.module.shiro.TokenInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwq
 * @since 2018-09-06
 */
public interface UserService extends BaseService<User> {

    TokenInfo getTokenInfo();

    User getUserByName(User user);
}
