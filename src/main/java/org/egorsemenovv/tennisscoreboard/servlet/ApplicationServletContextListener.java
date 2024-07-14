package org.egorsemenovv.tennisscoreboard.servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.egorsemenovv.tennisscoreboard.exception.NoSuchResourceException;
import org.egorsemenovv.tennisscoreboard.exception.RepositoryException;
import util.HibernateUtil;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        try {
            HibernateUtil.initializeDatabase();
        } catch (NoSuchResourceException | RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        HibernateUtil.getSessionFactory().close();
    }
}
