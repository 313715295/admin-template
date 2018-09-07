package com.admin.commons.utils;

import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 程序启动控制类
 */
public class ApplicationContextUtil {

    private static Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);
    private final static String LOG_PATH_KEY = "log.path";


    /**
     * 启动
     *
     * @param source 启动的类
     * @param args   启动指定参数
     */
    @Synchronized
    public static void run(Class source, String... args) {

        logger.info("开始启动程序......");
        try {
            //设定日志的主体地址
            System.setProperty(LOG_PATH_KEY, Paths.get("..").toRealPath().normalize() + File.separator + "logs" + File.separator+"admin"+ File.separator);
        } catch (IOException e) {
            logger.error("设定日志配置出错", e);
        }

        ConfigurableApplicationContext context = SpringApplication.run(source, args);
        Environment environment = context.getEnvironment();
        String logName = environment.getProperty("spring.application.name");
        String port = environment.getProperty("server.port");
        logger.info("======================================================================================");
        logger.info("                                                                                      ");
        logger.info(String.format("................................程序%s启动成功,端口为%s................................", logName, port));
        logger.info(String.format("................................日志保存路径为: %s................................", System.getProperty(LOG_PATH_KEY)));
        logger.info("                                                                                      ");
        logger.info("======================================================================================");
    }
}
