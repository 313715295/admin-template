package com.admin.web.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 公共字段自动填充
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("name","zwq", metaObject);
        setFieldValByName("cityName","杭州", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("cityName","杭州", metaObject);
    }
}
