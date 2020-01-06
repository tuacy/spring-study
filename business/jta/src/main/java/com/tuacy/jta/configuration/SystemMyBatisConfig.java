package com.tuacy.jta.configuration;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
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
@MapperScan(basePackages = "com.tuacy.jta.mapper.system", sqlSessionTemplateRef = "systemSqlSessionTemplate")
public class SystemMyBatisConfig {

    @Value("classpath*:mapper/system/*.xml")
    private Resource[] mapperLocations;

    @Bean(name = "systemDataSource")
    @Primary
    public DataSource systemDataSource(SystemDataSourceProperties testConfig) throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(testConfig.getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(testConfig.getPassword());
        mysqlXaDataSource.setUser(testConfig.getUsername());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("systemDataSource");

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


    @Bean(name = "systemSqlSessionFactory")
    @Primary
    public SqlSessionFactory systemSqlSessionFactory(@Qualifier("systemDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
//        /*加载mybatis全局配置文件*/
//        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/mybatis-config.xml"));
        /*加载所有的mapper.xml映射文件*/
        bean.setMapperLocations(mapperLocations);
        return bean.getObject();
    }


    @Bean(name = "systemSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate systemSqlSessionTemplate(
            @Qualifier("systemSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /*
     * 使用这个来做总事务 后面的数据源就不用设置事务了.不管有多少个数据源只要配置一个 JtaTransactionManager
     * */
    @Bean(name = "transactionManager")
    @Primary
    public JtaTransactionManager regTransactionManager () {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

}
