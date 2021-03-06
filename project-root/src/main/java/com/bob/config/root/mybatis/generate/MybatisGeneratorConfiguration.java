package com.bob.config.root.mybatis.generate;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;

import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVA_TYPE_RESOLVER;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.CLASSPATH_ENTRY;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVACLIENT_TARGETPACKAGE;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVACLIENT_TARGETPROJECT;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVA_FILEEN_CODING;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVA_MODEL_TARGETPACKAGE;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JAVA_MODEL_TARGETPROJECT;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JDBC_CONNECTIONURL;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JDBC_DRIVERCLASS;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JDBC_PASSWORD;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.JDBC_USER_NAME;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.SQLMAP_TARGETPACKAGE;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.SQLMAP_TARGETPROJECT;
import static com.bob.config.root.mybatis.generate.MybatisGenerateConfigs.TABLES;

/**
 * Mybatis逆向工程基于Java形式的配置类
 *
 * @author wb-jjb318191
 * @create 2017-09-30 9:17
 */
public class MybatisGeneratorConfiguration {

    public Configuration configMybatisGenerator() {
        Configuration configuration = new Configuration();

        configuration.addClasspathEntry(CLASSPATH_ENTRY);

        Context context = new Context(null);
        context.setTargetRuntime("MyBatis3");
        context.setId("wb-jjb318191");

        context.addProperty("javaFileEncoding", JAVA_FILEEN_CODING);

        //设置注解生成器
        context.setCommentGeneratorConfiguration(generateCommentConfiguration());
        //设置JDBC连接配置
        context.setJdbcConnectionConfiguration(generateJDBCConnectionConfiguration());
        //设置JDBC Type 与Java Type之间的映射解析器
        context.setJavaTypeResolverConfiguration(generateJavaTypeResolverConfiguration());
        //设置Java Model生成配置
        context.setJavaModelGeneratorConfiguration(generateJavaModelGeneratorConfiguration());
        //设置DAO层的生成配置
        context.setSqlMapGeneratorConfiguration(generateSqlMapGeneratorConfiguration());
        //设置Mapper.xml生成
        context.setJavaClientGeneratorConfiguration(generateJavaClientGeneratorConfiguration());
        //设置需要生成的Table及生成形式
        for (TableConfiguration tableConfiguration : generateTableConfigurations(context)) {
            context.addTableConfiguration(tableConfiguration);
        }
        configuration.addContext(context);
        return configuration;
    }

    /**
     * 配置注解生成器
     *
     * @return
     */
    private CommentGeneratorConfiguration generateCommentConfiguration() {
        CommentGeneratorConfiguration configuration = new CommentGeneratorConfiguration();
        configuration.setConfigurationType(GeneralCommentGenerator.class.getName());
        //是否去除自动生成的注释 true：是 ： false:否
        configuration.addProperty("suppressAllComments", "false");
        configuration.addProperty("addRemarkComments", "true");
        return configuration;
    }

    /**
     * 设置数据库连接的信息：驱动类、连接地址、用户名、密码
     *
     * @return
     */
    private JDBCConnectionConfiguration generateJDBCConnectionConfiguration() {
        JDBCConnectionConfiguration configuration = new JDBCConnectionConfiguration();
        configuration.setDriverClass(JDBC_DRIVERCLASS);
        String jdbcSuffix = "?useUnicode=true&characterEncoding=UTF8&useSSL=false";
        configuration.setConnectionURL(JDBC_CONNECTIONURL + jdbcSuffix);
        configuration.setUserId(JDBC_USER_NAME);
        configuration.setPassword(JDBC_PASSWORD);
        return configuration;
    }

    /**
     * 设置JDBC Type 与Java Type之间的映射解析器
     *
     * @return
     */
    private JavaTypeResolverConfiguration generateJavaTypeResolverConfiguration() {
        JavaTypeResolverConfiguration configuration = new JavaTypeResolverConfiguration();
        //可自定义类型映射解析器
        configuration.setConfigurationType(JAVA_TYPE_RESOLVER);
        //默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal
        configuration.addProperty("forceBigDecimals", "true");
        return configuration;
    }

    /**
     * 配置Java Model生成
     *
     * @return
     */
    private JavaModelGeneratorConfiguration generateJavaModelGeneratorConfiguration() {
        JavaModelGeneratorConfiguration configuration = new JavaModelGeneratorConfiguration();
        configuration.setTargetProject(JAVA_MODEL_TARGETPROJECT);
        configuration.setTargetPackage(JAVA_MODEL_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        //从数据库返回的值被清理前后的空格
        configuration.addProperty("trimStrings", "true");
        return configuration;
    }

    /**
     * 配置Mapper.xml生成
     *
     * @return
     */
    private SqlMapGeneratorConfiguration generateSqlMapGeneratorConfiguration() {
        SqlMapGeneratorConfiguration configuration = new SqlMapGeneratorConfiguration();
        configuration.setTargetProject(SQLMAP_TARGETPROJECT);
        configuration.setTargetPackage(SQLMAP_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        return configuration;
    }

    /**
     * 设置DAO生成
     *
     * @return
     */
    private JavaClientGeneratorConfiguration generateJavaClientGeneratorConfiguration() {
        JavaClientGeneratorConfiguration configuration = new JavaClientGeneratorConfiguration();
        configuration.setConfigurationType("XMLMAPPER");
        configuration.setTargetProject(JAVACLIENT_TARGETPROJECT);
        configuration.setTargetPackage(JAVACLIENT_TARGETPACKAGE);
        //是否让schema作为包的后缀
        configuration.addProperty("enableSubPackages", "false");
        return configuration;
    }

    private List<TableConfiguration> generateTableConfigurations(Context context) {
        List<TableConfiguration> configurations = new ArrayList<TableConfiguration>();
        for (String table : TABLES) {
            TableConfiguration configuration = new TableConfiguration(context);
            configuration.setTableName(table);
            configuration.setSelectByExampleStatementEnabled(false);
            configuration.setDeleteByExampleStatementEnabled(false);
            configuration.setCountByExampleStatementEnabled(false);
            configuration.setUpdateByExampleStatementEnabled(false);
            configurations.add(configuration);
        }
        return configurations;
    }

}
