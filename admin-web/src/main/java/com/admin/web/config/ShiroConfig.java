package com.admin.web.config;

import com.admin.web.filter.IShiroFilter;
import com.admin.module.shiro.MyRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liugh
 * @since 2018-05-03
 */
@Slf4j
@Configuration
public class ShiroConfig {



    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new IShiroFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterChainDefinitionMap = new HashMap<>(4);
        //不拦截的请求
        filterChainDefinitionMap.put("/user/login", "anon");//todo 调试
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/showBlog/**","anon");
        filterChainDefinitionMap.put("/blog/**","anon");
        filterChainDefinitionMap.put("/login/main","anon");
        filterChainDefinitionMap.put("/genCaptcha","anon");
        // 拦截的请求
        filterChainDefinitionMap.put("/systemLogout","jwt");
        filterChainDefinitionMap.put("/**","jwt");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }



    @Bean
    public SecurityManager securityManager(MyRealm authRealm){
        log.info("- - - - - - -shiro开始加载- - - - - - ");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authRealm);
        return defaultWebSecurityManager;
    }


    /**
     * 开启权限注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
