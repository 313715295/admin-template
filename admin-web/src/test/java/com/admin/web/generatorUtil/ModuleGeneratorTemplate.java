package com.admin.web.generatorUtil;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * created by zwq on 2018/9/2 数据源信息类，用于自动生成代码工具使用
 */
@Data
@Accessors(chain = true)
class ModuleGeneratorTemplate {
    /**
     * 模块名
     */
    private String moduleName;
    //datasource 属性
    private String userName;
    private String password;
    private String url;
    private String driverName;
    private DbType dbType;
    /**
     * 需要生成的表名
     */
    private String[] tables;
    /**
     * 设置公共填充字段和策略
     */
    private List<TableFill> tableFills;
}
