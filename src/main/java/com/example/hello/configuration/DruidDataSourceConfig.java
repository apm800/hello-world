package com.example.hello.configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author zk
 * @date 2020/12/15 14:08
 */
@Configuration
public class DruidDataSourceConfig {

//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//
//    @Value("${spring.druid.datasource.type}")
//    private String dbType;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${multiple.datasource.master.driver-class-name}")
//    private String driverClassName;
//
//    @Value("${spring.druid.datasource.initialSize}")
//    private int initialSize;
//
//    @Value("${spring.druid.datasource.minIdle}")
//    private int minIdle;
//
//    @Value("${spring.druid.datasource.maxActive}")
//    private int maxActive;
//
//    @Value("${spring.druid.datasource.maxWait}")
//    private int maxWait;
//
//    @Value("${spring.druid.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//
//    @Value("${spring.druid.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//
//    @Value("${spring.druid.datasource.validationQuery}")
//    private String validationQuery;
//
//    @Value("${spring.druid.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//
//    @Value("${spring.druid.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//
//    @Value("${spring.druid.datasource.testOnReturn}")
//    private boolean testOnReturn;
//
//    @Value("${spring.druid.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//
//    @Value("${spring.druid.datasource.filters}")
//    private String filters;
//
//    @Value("${spring.druid.datasource.connectionProperties}")
//    private String connectionProperties;

//    @Value("${spring.druid.datasource.useGlobalDataSourceStat}")
//    private boolean useGlobalDataSourceStat;

//    @Value("${spring.druid.datasource.druidLoginName}")
//    private String druidLoginName;
//
//    @Value("${spring.druid.datasource.druidLoginPwd}")
//    private String druidPassword;

//    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
//    @Primary
//    public DataSource dataSource() {
//        DruidDataSource datasource = new DruidDataSource();
//        try {
//            datasource.setUrl(this.dbUrl);
//            datasource.setDbType(dbType);
//            datasource.setUsername(username);
//            datasource.setPassword(password);
//            datasource.setDriverClassName(driverClassName);
//            datasource.setInitialSize(initialSize);
//            datasource.setMinIdle(minIdle);
//            datasource.setMaxActive(maxActive);
//            datasource.setMaxWait(maxWait);
//            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//            datasource.setValidationQuery(validationQuery);
//            datasource.setTestWhileIdle(testWhileIdle);
//            datasource.setTestOnBorrow(testOnBorrow);
//            datasource.setTestOnReturn(testOnReturn);
//            datasource.setPoolPreparedStatements(poolPreparedStatements);
//            datasource.setFilters(filters);
//            datasource.setConnectionProperties(connectionProperties);
////            datasource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return datasource;
//    }



    /**
     * druid 监控访问的设置
     *
     * @author zk
     * @date 2020/12/16 13:23
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        //url 匹配
        reg.addUrlMappings("/druid/*");
//        reg.addInitParameter("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
//        reg.addInitParameter("deny", ""); //IP黑名单 (存在共同时，deny优先于allow)
//        reg.addInitParameter("loginUsername", this.druidLoginName);//登录名
//        reg.addInitParameter("loginPassword", this.druidPassword);//登录密码
//        reg.addInitParameter("resetEnable", "false"); // 禁用HTML页面上的“Reset All”功能
        return reg;
    }

    @Bean(name = "druidWebStatFilter")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略资源
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        return filterRegistrationBean;
    }
}
