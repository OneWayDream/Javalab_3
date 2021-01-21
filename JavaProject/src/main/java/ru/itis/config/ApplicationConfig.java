package ru.itis.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.itis.repositories.SessionCookiesRepository;
import ru.itis.repositories.SessionCookiesRepositoryImpl;
import ru.itis.repositories.UsersRepository;
import ru.itis.repositories.UsersRepositoryJDBCImpl;
import ru.itis.repositories.minions_repositories.implementations.*;
import ru.itis.services.*;

import javax.sql.DataSource;
import java.util.Objects;


@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "ru.itis")
public class ApplicationConfig {

    @Autowired
    private Environment environment;

    @Bean
    public HikariConfig hikariConfig(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("db.hikari.max-pool-size"))));
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver.classname"));
        return hikariConfig;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public UsersRepository usersRepository(){
        return new UsersRepositoryJDBCImpl(dataSource());
    }

    @Bean
    public UsersService usersService(){
        return new UsersServiceImpl(usersRepository());
    }

    @Bean
    public SessionCookiesRepository sessionCookiesRepository(){
        return new SessionCookiesRepositoryImpl(dataSource());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityService securityService(){
        return new SecurityServiceImpl(sessionCookiesRepository(), usersRepository(), passwordEncoder());
    }

    @Bean
    public MinionsDataService minionsDataService(){
        return new MinionsDataServiceImpl(new MinionsBazaarPriceRepositoryImpl(dataSource()),
                new MinionsFuelsRepositoryImpl(dataSource()),
                new MinionsItemCompactRepositoryImpl(dataSource()),
                new MinionsItemSmeltRepositoryImpl(dataSource()),
                new MinionsItemSuperCompactRepositoryImpl(dataSource()),
                new MinionsNPSPriceRepositoryImpl(dataSource()),
                new MinionsProductionRepositoryImpl(dataSource()),
                new MinionsRepositoryImpl(dataSource()),
                new MinionsUpgradeGroupsRepositoryImpl(dataSource()),
                new MinionsUpgradesImpl(dataSource()));
    }

    @Bean
    public String hypixelConnectionKey(){
        return environment.getProperty("hypixel.connection.key");
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftlh");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfig(){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/ftl/");
        return configurer;
    }
}
