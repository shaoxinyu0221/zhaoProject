package com.zhao.zhaoproject.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;

public class MybatisPlusCodeUtil {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/zhao_project?serverTimezone=UTC", "root", "root")
                .globalConfig(builder -> {
                    builder.author("邵鑫雨") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("D:\\学习\\zhaoProject\\src\\main\\java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.zhao.zhaoproject.entity") // 设置父包名
                                .moduleName("zhaoProject") // 设置父包模块名
//                                .pathInfo(Collections.singletonMap(OutputFile.xml, "D://")) // 设置mapperXml生成路径
//                                .entity()
                )
                .strategyConfig(builder ->
                        builder.addInclude("point_card") // 设置需要生成的表名
//                                .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                .entityBuilder().enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
