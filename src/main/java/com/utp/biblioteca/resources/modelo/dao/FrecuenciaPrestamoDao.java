package com.utp.biblioteca.resources.modelo.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrecuenciaPrestamoDao {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    public List<FrecuenciaPrestamo> spFrecuenciaPrestamos() {
        List<FrecuenciaPrestamo> frecuenciaPrestamos = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_frecuencia_prestamos()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                FrecuenciaPrestamo frecuenciaPrestamo = new FrecuenciaPrestamo();
                frecuenciaPrestamo.setTitulo(rs.getString("titulo"));
                frecuenciaPrestamo.setFrecuencia(rs.getInt("frecuencia"));
                frecuenciaPrestamos.add(frecuenciaPrestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return frecuenciaPrestamos;
    }
}

