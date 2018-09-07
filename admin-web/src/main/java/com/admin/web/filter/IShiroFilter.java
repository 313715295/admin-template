package com.admin.web.filter;

import com.admin.commons.base.Result;
import com.admin.commons.enums.ResponseEnum;
import com.admin.commons.utils.Iptools;
import com.admin.module.shiro.JwtTokenUtil;
import com.admin.web.shiro.JWTToken;
import com.admin.web.utils.OptionsCheckUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.admin.module.shiro.JwtTokenUtil.TOKEN;


@Slf4j
public class IShiroFilter extends BasicHttpAuthenticationFilter {

    private final String ERROR = "error";
    private static final String USER_AGANT = "user-agent";

    /**
     * shiro权限拦截核心方法 返回true允许访问resource，
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        HttpServletRequest req = (HttpServletRequest) request;

        //前端预请求直接通过
        if (OptionsCheckUtil.isContain(req)) {
            return true;
        }

        String token = req.getHeader(TOKEN);

        //判断请求的请求头是否带上 "token"
        if (!StringUtils.isEmpty(token)) {
            //如果存在，则进入 executeLogin 方法执行登入，校验token 是否正确
            try {
                executeLogin(request, response);
                return true;
            } catch (SignatureException var1){
                log.warn("Ip={} 方法={} , token={}, 解密失败：异常信息{}", Iptools.gainRealIp(req), req.getRequestURI(),
                        token, var1.getMessage());
                request.setAttribute(ERROR, Result.fail());
            } catch (ExpiredJwtException var2){
                log.warn("Ip={} 方法={}, token={}, token过期：异常信息{}", Iptools.gainRealIp(req), req.getRequestURI(),
                        token, var2.getMessage());
                request.setAttribute(ERROR,Result.fail(ResponseEnum.TOKEN_EXPIRED,false) );
            } catch (Exception e) {
                log.warn("Ip={} 方法={}, token={}, 解密异常：异常信息{}", Iptools.gainRealIp(req), req.getRequestURI(),
                        token, e.getMessage());
                request.setAttribute(ERROR, Result.fail());
            }
        } else {
            log.warn("Ip={} 方法={}, 没有token进行访问", Iptools.gainRealIp(req), req.getRequestURI());
            request.setAttribute(ERROR, Result.fail());
        }
        return false;

    }



    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response){
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(TOKEN);
        //异常交给调用者处理返回对应消息
        Claims claims = JwtTokenUtil.verifySign(authorization);

        JWTToken token = new JWTToken(claims);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 拦截器返回false进入这个方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        writeResponse(res, req, req == null ? Result.fail() : req.getAttribute(ERROR));
        return false;
    }




    private void writeResponse(HttpServletResponse response, HttpServletRequest request, Object tips) {

        if (response == null || tips == null) {
            return;
        }
        setHeader(request, response);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.write(JSONObject.toJSONString(tips));
            printWriter.flush();
        } catch (IOException e) {
            log.error("获取写入数据流异常", e);
        }

    }

    /**
     * 为拦截器的response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request, HttpServletResponse response) {
        log.info("对返回请求设置header解决前后端跨域参数匹配问题");
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }



}
