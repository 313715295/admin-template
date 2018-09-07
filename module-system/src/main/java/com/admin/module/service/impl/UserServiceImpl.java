package com.admin.module.service.impl;

import com.admin.commons.base.Result;
import com.admin.module.entity.User;
import com.admin.module.dao.UserMapper;
import com.admin.module.service.UserService;
import com.admin.module.shiro.JwtTokenUtil;
import com.admin.module.shiro.TokenInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-09-06
 */
@CacheConfig(cacheNames = "user")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
    @Override
    public TokenInfo getTokenInfo() {
        return (TokenInfo) SecurityUtils.getSubject().getPrincipal();
    }

    @Cacheable(key = "#user.name",unless = "#result == null")
    @Override
    public User getUserByName(User user) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("name", user.getName()));
    }
}
