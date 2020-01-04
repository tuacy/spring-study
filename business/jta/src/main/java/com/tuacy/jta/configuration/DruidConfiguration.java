package com.tuacy.jta.configuration;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.Map;

/**
 * @name: DruidConfig
 * @author: tuacy.
 * @date: 2019/12/31.
 * @version: 1.0
 * @Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties
public class DruidConfiguration {

    /**
     * 系统库配置前缀.
     */
    private final static String SYSTEM_DB_PREFIX = "spring.datasource.druid.system-db";
    /**
     * 业务库配置前缀.
     */
    private final static String BUSINESS_DB_PREFIX = "spring.datasource.druid.business-db";

    /**
     * 系统库对应的数据源(DataSource)
     */
    @Bean(name = "systemDataSource")
    @ConfigurationProperties(prefix = SYSTEM_DB_PREFIX)
    public DataSource systemDataSource() {
        return new AtomikosDataSourceBean();

    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.system-db")
    public DataSourceProperties memberDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        return dataSourceProperties;
    }

    /**
     * 业务库对应的数据源(DataSource)
     */
    @Bean(name = "businessDataSource")
    @ConfigurationProperties(prefix = BUSINESS_DB_PREFIX)
    public DataSource businessDataSource() {
        return new AtomikosDataSourceBean();
    }


    /**
     * 注入事物管理器
     */
    @Bean(name = "xatx")
    public JtaTransactionManager regTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }

    /**
     * 配置web监控的filter
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true); //slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
        statFilter.setMergeSql(true); //SQL合并配置
        statFilter.setSlowSqlMillis(1000);//slowSqlMillis的缺省值为3000，也就是3秒。
        return statFilter;
    }


    /**
     * 配置Druid监控
     * 后台管理Servlet
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();//这是配置的druid监控的登录密码
        initParams.put("loginUsername", "root");
        initParams.put("loginPassword", "123456");
        //默认就是允许所有访问
        initParams.put("allow", "");
        bean.setInitParameters(initParams);
        return bean;
    }

}
