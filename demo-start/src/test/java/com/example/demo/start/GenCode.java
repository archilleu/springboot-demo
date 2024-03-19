package com.example.demo.start;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.demo.start.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class GenCode {
    @Autowired
    private Config config;

    @Test
    public void gen() {

        String dir = config.getDir();
        String url = config.getUrl();
        //数据源配置
        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig.Builder(url, config.getUsername(), config.getPassword());
        dataSourceBuilder
                // 自定义数据库表字段类型转换【可选】
                .typeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //tinyint转换成Boolean
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        //int转换成Int
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.INTEGER;
                        }
                        //bigint转换成Long
                        if (fieldType.toLowerCase().contains("bigint")) {
                            return DbColumnType.LONG;
                        }
                        //decimal转换成double
                        if (fieldType.toLowerCase().contains("decimal")) {
                            return DbColumnType.DOUBLE;
                        }
                        //将数据库中datetime转换成date
                        if (fieldType.toLowerCase().contains("datetime")) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });

        FastAutoGenerator.create(dataSourceBuilder)
                .globalConfig(builder -> {
                    builder.author(config.getAuthor()) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(dir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(config.getParent()) // 设置父包名
                            .moduleName(config.getModule()) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, dir)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.addInclude(config.getInclude()); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }


}
