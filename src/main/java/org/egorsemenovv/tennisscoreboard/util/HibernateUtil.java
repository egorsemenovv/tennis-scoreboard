package org.egorsemenovv.tennisscoreboard.util;

import lombok.experimental.UtilityClass;
import org.egorsemenovv.tennisscoreboard.exception.NoSuchResourceException;
import org.egorsemenovv.tennisscoreboard.exception.RepositoryException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@UtilityClass
public class HibernateUtil {

    private static volatile SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        initializeSessionFactory();
        return sessionFactory;
    }

    private static void initializeSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    Configuration configuration = buildConfiguration();
                    configuration.configure();
                    sessionFactory = configuration.buildSessionFactory();
                }
            }
        }
    }

    public static void initializeDatabase(){
        if (sessionFactory == null) initializeSessionFactory();
        String initSql;
        try{
            initSql = new String(Files.readAllBytes(Paths.get(Objects.requireNonNull(
                    HibernateUtil.class.getClassLoader().getResource("data.sql")).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new NoSuchResourceException(e.getMessage());
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createNativeQuery(initSql, void.class).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    private static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration;
    }

}
