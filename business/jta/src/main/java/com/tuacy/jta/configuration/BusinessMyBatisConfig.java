package com.tuacy.jta.configuration;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @name: BusinessDataSourceProperties
 * @author: tuacy.
 * @date: 2020/1/4.
 * @version: 1.0
 * @Description:
 */
@Configuration
// basePackages 最好分开配置 如果放在同一个文件夹可能会报错
@MapperScan(basePackages = "com.tuacy.jta.mapper.business", sqlSessionTemplateRef = "businessSqlSessionTemplate")
public class BusinessMyBatisConfig {

    @Bean(name = "businessDataSource")
    public DataSource testDataSource(BusinessDataSourceProperties testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("businessDataSource");

//        xaDataSource.setMinPoolSize(testConfig.getMinPoolSize());
//        xaDataSource.setMaxPoolSize(testConfig.getMaxPoolSize());
//        xaDataSource.setMaxLifetime(testConfig.getMaxLifetime());
//        xaDataSource.setBorrowConnectionTimeout(testConfig.getBorrowConnectionTimeout());
//        xaDataSource.setLoginTimeout(testConfig.getLoginTimeout());
//        xaDataSource.setMaintenanceInterval(testConfig.getMaintenanceInterval());
//        xaDataSource.setMaxIdleTime(testConfig.getMaxIdleTime());
//        xaDataSource.setTestQuery(testConfig.getTestQuery());
        return xaDataSource;
    }


    @Bean(name = "businessSqlSessionFactory")
    public SqlSessionFactory businessSqlSessionFactory(@Qualifier("businessDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }


    @Bean(name = "businessSqlSessionTemplate")
    public SqlSessionTemplate businessSqlSessionTemplate(
            @Qualifier("businessSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
