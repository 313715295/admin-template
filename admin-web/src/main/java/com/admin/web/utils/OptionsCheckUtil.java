package com.admin.web.utils;

import com.admin.commons.utils.Iptools;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * options请求的检查
 */
@Slf4j
public class OptionsCheckUtil {



    private static final String OPTIONS_REQUEST_METHOD = "OPTIONS";

    /**
     * 检查options 是否包含
     *
     * @param request
     * @return
     */
    public static Boolean isContain(HttpServletRequest request) {
        if (OPTIONS_REQUEST_METHOD.equals(request.getMethod())) {
            log.info("IP={}下用户访问方法={}开始进行预检查", Iptools.gainRealIp(request), request.getRequestURI());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


}
