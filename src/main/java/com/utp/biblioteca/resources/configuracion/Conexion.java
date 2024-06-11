package com.utp.biblioteca.resources.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {

    private final Properties properties = new Properties();

    private static String URL;
    private static String USER;
    private static String PASS;

    public Conexion() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("db/db.properties"));
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el archivo db.properties", e);
        }

        URL = properties.getProperty("db.url");
        USER = properties.getProperty("db.username");
        PASS = properties.getProperty("db.password");

    }

    public static Connection getConectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}