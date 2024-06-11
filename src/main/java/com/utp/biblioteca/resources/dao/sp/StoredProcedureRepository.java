package com.utp.biblioteca.resources.dao.sp;

import com.utp.biblioteca.resources.configuracion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoredProcedureRepository {

    Conexion con;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public StoredProcedureRepository() {
        con = new Conexion();
    }

    // Procedimiento para realizar un préstamo comprobando que el "estado" del usuario sea TRUE
    public void spRealizarPrestamo(int usuarioDni, int libroId, int dias) throws SQLException {
        String sql = "CALL sp_realizar_prestamo(?, ?, ?)";
        try (Connection connection = con.getConectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuarioDni);
            statement.setInt(2, libroId);
            statement.setInt(3, dias);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Procedimiento para devolver un libro
    public void spDevolverLibro(int prestamoId) throws SQLException {
        String sql = "CALL sp_devolver_libro(?)";
        try (Connection connection = con.getConectar();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, prestamoId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
