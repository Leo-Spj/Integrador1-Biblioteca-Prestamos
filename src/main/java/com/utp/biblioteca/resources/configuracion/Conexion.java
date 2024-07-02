package com.utp.biblioteca.resources.configuracion;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private static final Properties properties = new Properties();
    private static String URL;
    private static String USER;
    private static String PASS;
    private static BasicDataSource pool;

    private Conexion() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("db/db.properties"));
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.username");
            PASS = properties.getProperty("db.password");
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo db.properties", e);
        }
    }

    public static BasicDataSource getInstancia() throws SQLException {
        if (pool == null) {
            new Conexion();
            pool = new BasicDataSource();
            pool.setUrl(URL);
            pool.setUsername(USER);
            pool.setPassword(PASS);
            
            pool.setInitialSize(5);
            pool.setMaxTotal(10);
            pool.setMaxIdle(7);
            pool.setMinIdle(5);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstancia().getConnection();
    }

}