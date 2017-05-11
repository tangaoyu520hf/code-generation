package com.tangaoyu.gen.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MpGenerator{

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D://create");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("tangaoyu");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        gc.setXmlName("%sDao");
        gc.setServiceName("%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/jeesite?useUnicode=true&characterEncoding=utf-8");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[]{"gen"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "gen_table_column","gen_table"}); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 字段名生成策略
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.tangaoyu.gen");
        pc.setEntity("model");
        pc.setMapper("dao");
        pc.setXml("dao");
        /*pc.setServiceImpl("service");*/
       /* pc.setModuleName("");*/
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("applicationCode", "security");
                this.setMap(map);
            }
        };
        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<>();
        final String url = gc.getOutputDir()+File.separator+(pc.getParent().replace(".","//")) + File.separator+"app"+ File.separator;
        File file =new File(url);
        if(!file.exists()){
            boolean result = file.mkdirs();
        }

/*        focList.add(new FileOutConfig("/templates/customController.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return url + tableInfo.getEntityName()+"Controller.java";
            }
        });*/

/*        focList.add(new FileOutConfig("/templates/index.vue.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return url + tableInfo.getEntityPath()+ "//"+ tableInfo.getEntityName()+ ".vue";
            }
        });
        focList.add(new FileOutConfig("/templates/index.js.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return url + tableInfo.getEntityPath()+ "//index.js";
            }
        });
        focList.add(new FileOutConfig("/templates/i18n.js.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return url + tableInfo.getEntityPath()+ "//i18n.js";
            }
        });*/
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
       /* TemplateConfig tc = new TemplateConfig();*/
        /*tc.setController("D:/Workspaces/PJ-201603-CSIEC/02_代码区/trunk/icss-cloud/mybatis-code-generator/src/resources/template/controller.java.vm");*/

       /* tc.setEntity("/templates/entity.java.vm");*/
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        /*mpg.setTemplate(tc);*/
        // 执行生成
        mpg.execute();

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }

}