package com.example.hello.configuration.yml;

import com.example.hello.datasource.yml.MyAbstractRoutingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 如果使用properties格式配置,需要将此注掉
 *
 * @author zhoukai
 * @date 2020/7/23 10:26
 */
@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({DataSourceConfiguration.class})
@MapperScan(basePackages = {"com.example.hello.dao"})
public class MybatisConfiguration {
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
    @Value("${spring.datasource.readSize}")
    private String dataSourceSize;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext ac) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSourceProxy(ac));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.hello.entity");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 有多少个数据源就要配置多少个bean
     *
     * @return
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy(ApplicationContext ac) {

        int size = Integer.parseInt(dataSourceSize);
        System.out.println("size:" + size);

        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        DataSource writeDataSource = (DataSource) ac.getBean("writeDataSource");
        //多个读数据库时
        List<DataSource> readDataSources = (List<DataSource>) ac.getBean("readDataSources");
        for (int i = 0; i < size; i++) {
            targetDataSources.put(i, readDataSources.get(i));
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
