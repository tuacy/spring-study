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
@MapperScan(basePackages = "com.tuacy.jta.mapper.business", sqlSessionFactoryRef = "sqlSessionFactoryBusiness")
public class BusinessDatasourceConfig {

    private DataSource businessDataSource;

    @Autowired
    @Qualifier("businessDataSource")
    public void setBusinessDataSource(DataSource businessDataSource) {
        this.businessDataSource = businessDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBusiness() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(businessDataSource);
        //指定mapper xml目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/business/*.xml"));
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBusiness()); // 使用上面配置的Factory
    }


}
