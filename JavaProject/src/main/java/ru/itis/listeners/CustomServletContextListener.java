package ru.itis.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.itis.config.ApplicationConfig;
import ru.itis.services.*;
import ru.itis.utils.MinionsBazaarPricesUpdater;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;


@WebListener
public class CustomServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        //Top 3 properties
        Properties top3Properties = new Properties();
        try {
            top3Properties.load(servletContext.getResourceAsStream("/WEB-INF/classes/top3.properties"));
        } catch (IOException|NullPointerException e) {
            throw new IllegalArgumentException("Exception while loading top 3 properties", e);
        }
        servletContext.setAttribute("top3Properties", top3Properties);

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        servletContext.setAttribute("applicationContext", context);

        String hypixelConnectionKey = (String) context.getBean("hypixelConnectionKey");
        MinionsDataService minionsDataService = context.getBean(MinionsDataService.class);
        Thread thread = new Thread(new MinionsBazaarPricesUpdater(minionsDataService,
                new HypixelBazaarPriceService(hypixelConnectionKey),
                true,
                1200000L,
                servletContext));
        thread.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
