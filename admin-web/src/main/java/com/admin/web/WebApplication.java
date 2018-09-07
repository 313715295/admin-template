package com.admin.web;

import com.admin.commons.utils.ApplicationContextUtil;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@ComponentScan({"com.admin"})
public class WebApplication {


	public static void main(String[] args) {
        ApplicationContextUtil.run(WebApplication.class,args);

	}
}
