package com.admin.module.controller;

import com.admin.commons.base.BaseController;
import com.admin.commons.base.Result;
import com.admin.module.entity.User;
import com.admin.module.service.UserService;
import com.admin.module.service.impl.UserServiceImpl;
import com.admin.module.shiro.JwtTokenUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-09-06
 */
@RestController
@RequestMapping("/user")
@CacheConfig(cacheNames = "user")
public class UserController extends BaseController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public Result login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        HttpServletResponse response) {
        User userData = userService.getUserByName(new User().setName(userName));
        if (userData == null) {
            return Result.errorMsg("账号不存在");
        }
        if (!userData.getPassword().equals(password)) {
            return Result.errorMsg("密码错误");
        }
        String token = JwtTokenUtil.generateToken(userData);
        response.addHeader("token",token);
        return Result.successMsg("登录成功");


    }

    @RequestMapping("/get")
    @RequiresPermissions("get")
    public Result test() {
        User user = userService.getUserByName(new User().setName("zwq"));
        return Result.success(user);
    }

    @RequestMapping("/get1")
    @RequiresPermissions("get1")
    public Result<User> get() {
        User user = userService.getUserByName(new User().setName("zwq"));
        return Result.success(user);
    }


}
