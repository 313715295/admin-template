package com.admin.web.generatorUtil;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * created by zwq on 2018/9/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MpGeneratorUtil {

    private DbType dbType = DbType.MYSQL;

    @Value("${spring.datasource.dynamic.datasource.system.driver-class-name}")
    private String sysDriverName;
    @Value("${spring.datasource.dynamic.datasource.system.url}")
    private String sysUrl;
    @Value("${spring.datasource.dynamic.datasource.system.username}")
    private String sysUserName;
    @Value("${spring.datasource.dynamic.datasource.system.password}")
    private String sysPassword;

    @Value("${spring.datasource.dynamic.datasource.service.driver-class-name}")
    private String serviceDriverName;
    @Value("${spring.datasource.dynamic.datasource.service.url}")
    private String serviceUrl;
    @Value("${spring.datasource.dynamic.datasource.service.username}")
    private String serviceUserName;
    @Value("${spring.datasource.dynamic.datasource.service.password}")
    private String servicePassword;
    @Value("${spring.application.name}")
    private String applicationName;

    @Test
    public  void generator() {

        /*
        如 每张表都有一个创建时间、修改时间
        而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        修改时，修改时间会修改，
        虽然像Mysql数据库有自动更新机制，但像ORACLE的数据库就没有了，
        使用公共字段填充功能，就可以实现，自动按场景更新了。
        如下是配置
        或者采用继承实体父类，父类里设置公共字段，然后mybatisPlus公共字段填充
         */
        // 自定义需要填充的字段
        List<TableFill> sysFillList = new ArrayList<>();
        TableFill sysField = new TableFill("name", FieldFill.INSERT);
        sysFillList.add(sysField);

        List<TableFill> serviceFillList = new ArrayList<>();
        TableFill serviceField = new TableFill("city_name", FieldFill.INSERT_UPDATE);
        serviceFillList.add(serviceField);

        //需要生成的表名
//        String[] systemTables = {"user","order_"};
        String[] systemTables = {"user"};
        String[] serviceTables = {"city"};

        ModuleGeneratorTemplate systemTemplate = new ModuleGeneratorTemplate();
        ModuleGeneratorTemplate serviceTemplate = new ModuleGeneratorTemplate();

        systemTemplate.setModuleName("module-system")
                .setDriverName(sysDriverName)
                .setUrl(sysUrl)
                .setDbType(dbType)
                .setUserName(sysUserName)
                .setPassword(sysPassword)
                .setTableFills(sysFillList)
                .setTables(systemTables);

        serviceTemplate.setModuleName("module-service")
                .setDriverName(serviceDriverName)
                .setUrl(serviceUrl)
                .setDbType(dbType)
                .setUserName(serviceUserName)
                .setPassword(servicePassword)
                .setTableFills(serviceFillList)
                .setTables(serviceTables);

        ModuleGeneratorTemplate[] templates = {systemTemplate,serviceTemplate};
//        ModuleGeneratorTemplate[] templates = {systemTemplate};

//        generatorModules(templates);

    }


    private static void generatorModules(ModuleGeneratorTemplate[] templates) {
        for (ModuleGeneratorTemplate template:templates) {
            generatorTamplate(template,"zwq",false);

        }
    }

    private static void generatorTamplate(ModuleGeneratorTemplate moduleGeneratorTemplate, String author, boolean serviceStartWithI) {
//      全局配置
        File file = new File("../"+moduleGeneratorTemplate.getModuleName());
        GlobalConfig gc = new GlobalConfig();
        //测试下运行获取的路径是模块下的位置，所以需要加../
        gc.setOutputDir(file.getAbsolutePath() + "/src/main/java")
                .setFileOverride(true)
                .setActiveRecord(true)// 不需要ActiveRecord特性的请改为false
//                .setEnableCache(false)// XML 二级缓存
//                .setBaseResultMap(true)// XML ResultMap
//                .setBaseColumnList(false)// XML columList
                // .setKotlin(true) 是否生成 kotlin 代码
                .setAuthor(author);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        if (!serviceStartWithI) {
            gc.setServiceName("%sService"); //默认是I**Service
            // gc.setServiceImplName("%sServiceDiy");
            // gc.setControllerName("%sAction");
        }

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(moduleGeneratorTemplate.getDbType())
//                .setTypeConvert(new MySqlTypeConvert() {
//                    // 自定义数据库表字段类型转换【可选】
//                    @Override
//                    public DbColumnType processTypeConvert(String fieldType) {
//                        System.out.println("转换类型：" + fieldType);
//                        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
//                        return super.processTypeConvert(fieldType);
//                    }
//                })
                .setDriverName(moduleGeneratorTemplate.getDriverName())
                .setUrl(moduleGeneratorTemplate.getUrl())
                .setUsername(moduleGeneratorTemplate.getUserName())
                .setPassword(moduleGeneratorTemplate.getPassword());


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true)// 全局大写命名 ORACLE 注意
//                .setTablePrefix(new String[]{"tlog_", "tsys_"})// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel) // 数据库字段下划线转驼峰命令策略
                .setDbColumnUnderline(true)  // 指定表名和字段名是否使用了下划线
                .setInclude(moduleGeneratorTemplate.getTables()) // 需要生成的表 多个表名传入数组
//                .setExclude(new String[]{"test"}) // 排除生成的表
//              自定义实体父类
                .setSuperEntityClass("com.admin.commons.base.BaseEntity")  //只是继承这个类，并不会自动创建实体父类，
//              自定义实体，公共字段  原定方案是公共字段放父类实体，然后父类实体设定字段填充方案，因使用AR需要继承model，
//              然后自动生成的时候pkVal方法需要重新导入（继承冲突导包失败），所以不自定义父类，采用设定填充表字段
//                .setTableFillList(moduleGeneratorTemplate.getTableFills())
                .setSuperEntityColumns("test_id", "age")  //默认实体父类有这些字段，工具生成的实体忽略这些公共字段
//              自定义 dao 父类
                .setSuperMapperClass("com.admin.commons.base.BaseDao")
//              自定义 service 父类
                .setSuperServiceClass("com.admin.commons.base.BaseService")
//              自定义 service 实现类父类
//                .setSuperServiceImplClass("com.commons.commons.service.impl.BaseServiceImpl")
//              自定义 controller 父类
                .setSuperControllerClass("com.admin.commons.base.BaseController")
//              【实体】是否生成字段常量（默认 false）
//              public static final String ID = "test_id";
//                .setEntityColumnConstant(true)
//              【实体】是否为构建者模型（默认 false）
//              public User setName(String name) {this.name = name; return this;}
                .setEntityBuilderModel(true)
//              Boolean类型字段是否移除is前缀处理
//              .setEntityBooleanColumnRemoveIsPrefix(true)
//              .setRestControllerStyle(true)
//              .setControllerMappingHyphenStyle(true)
//              【实体】是否为lombok模型（默认 false）
                .setEntityLombokModel(true);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.admin.module")
                .setMapper("dao")
                .setEntity("entity")
                .setService("service")
                .setController("controller")
                .setServiceImpl("service.impl")
                .setXml("mapper");
//                .setModuleName("user"); module包下的总包
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController(null); //不生成controller或者可以设置模板

        AutoGenerator mpg = new AutoGenerator();
        mpg.setDataSource(dsc)
                .setPackageInfo(pc)
//              选择 freemarker 引擎，默认 Veloctiy
//              .setTemplateEngine(new FreemarkerTemplateEngine());
                .setStrategy(strategy)
                .setGlobalConfig(gc)
                .setTemplate(templateConfig)
                .execute();
    }
}
