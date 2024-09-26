package com.cxl.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cxl.core.BaseEntity;
import sun.nio.cs.FastCharsetProvider;

import java.util.HashMap;
import java.util.Map;

public class coding {
    public static void main(String[] args) {
        String parentPath = System.getProperty("user.dir");
        String table="oms_order_item";
        String project="product";
        HashMap<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.entity,parentPath+"/entity/src/main/java/com/cxl/entity");
        pathInfo.put(OutputFile.mapper,parentPath+"/"+project+"/src/main/java/com/cxl/mapper");
        pathInfo.put(OutputFile.xml,parentPath+"/"+project+"/src/main/resources/com/cxl/mapper");
        pathInfo.put(OutputFile.service,parentPath+"/"+project+"/src/main/java/com/cxl/service");
        pathInfo.put(OutputFile.serviceImpl,parentPath+"/"+project+"/src/main/java/com/cxl/service/impl");
        pathInfo.put(OutputFile.controller,parentPath+"/"+project+"/src/main/java/com/cxl/controller");
        String url="jdbc:mysql://192.168.31.128/shop?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
        FastAutoGenerator.create(url,"root","123456")
        .globalConfig(builder -> {
            builder.author("cxl");


        }).packageConfig(builder -> {
            builder.parent("com.cxl").pathInfo(pathInfo);
        }).strategyConfig(builder -> {
            builder.addInclude(table).serviceBuilder().formatServiceFileName("%sService").entityBuilder().enableLombok().superClass(BaseEntity.class).addSuperEntityColumns("id").controllerBuilder().enableRestStyle();
        }).templateEngine(new FreemarkerTemplateEngine()).execute();
    }
}
