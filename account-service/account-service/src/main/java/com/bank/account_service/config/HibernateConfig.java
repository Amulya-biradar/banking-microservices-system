package com.bank.account_service.config;

import com.bank.account_service.entity.Account;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private final DataSource dataSource;

    public HibernateConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public CommandLineRunner testTransactionManager(
            ApplicationContext context) {

        return args -> {

            String[] beans =
                    context.getBeanNamesForType(
                            org.springframework.transaction.PlatformTransactionManager.class);

            System.out.println(
                    "===== TRANSACTION MANAGERS =====");

            for (String bean : beans) {
                System.out.println(bean);
            }
        };
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory =
                new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource);

        sessionFactory.setAnnotatedClasses(
                Account.class
        );

        Properties properties = new Properties();

        properties.put(
                "hibernate.dialect",
                "org.hibernate.dialect.MySQLDialect"
        );

        properties.put(
                "hibernate.show_sql",
                true
        );

        properties.put(
                "hibernate.format_sql",
                true
        );

        properties.put(
                "hibernate.hbm2ddl.auto",
                "update"
        );

        sessionFactory.setHibernateProperties(
                properties
        );

        return sessionFactory;
    }

    @Bean(name = "transactionManager")
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        return new HibernateTransactionManager(
                sessionFactory
        );
    }
}