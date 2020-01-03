package com.tuacy.jta.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @name: SystemDatasourceConfig
 * @author: tuacy.
 * @date: 2019/12/31.
 * @version: 1.0
 * @Description:
 */
@Configuration
// 精确到 mapper 目录，以便跟其他数据源隔离
@MapperScan(basePackages = "com.tuacy.jta.mapper.system", sqlSessionFactoryRef = "sqlSessionFactorySystem")
public class SystemDatasourceConfig {

    private DataSource systemDataSource;

    @Autowired
    @Qualifier("systemDataSource")
    public void setSystemDataSource(DataSource systemDataSource) {
        this.systemDataSource = systemDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactorySystem() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(systemDataSource);
        //指定mapper xml目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/system/*.xml"));
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactorySystem()); // 使用上面配置的Factory
    }


}
