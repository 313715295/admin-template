# springboot2.0.4 mybatisplus3.0基础框架
PS：mybatisplus多数据源是参考的官方demo，shiro+jwt和springcache+redis参考的网上很多博客~然后按照自己想法增删改。


采用的是maven多模块方式。

commons模块放置共用的类和依赖；

modules-system放置管理系统本身所需要的从entity到controller及相关uitl等等；

modules-service放置拓展业务所需要的一整套；

web模块整合依赖和配置，做启动模块。



