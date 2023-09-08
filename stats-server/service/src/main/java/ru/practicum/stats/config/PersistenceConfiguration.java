package ru.practicum.stats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    public static final String H2_URL="jdbc:h2:mem:stats";
    public static final String H2_USERNAME="qwerty";
    public static final String H2_PASSWORD="qwerty";
    public static final String H2_DRIVER="org.h2.Driver";

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(H2_DRIVER);
        dataSource.setUrl(H2_URL);
        dataSource.setUsername(H2_USERNAME);
        dataSource.setPassword(H2_PASSWORD);
        return dataSource;
    }
}
