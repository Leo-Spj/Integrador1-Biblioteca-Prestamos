package com.utp.biblioteca.resources.modelo.dao.sp;

import com.utp.biblioteca.resources.configuracion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StoredProcedureRepository {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    // Procedimiento para realizar un préstamo comprobando que el "estado" del usuario sea TRUE
    public void spRealizarPrestamo(int usuarioDni, int libroId, int dias) {

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("CALL sp_realizar_prestamo(?, ?, ?)")) {
            ps.setInt(1, usuarioDni);
            ps.setInt(2, libroId);
            ps.setInt(3, dias);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Procedimiento para devolver un libro
    public void spDevolverLibro(int prestamoId) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("CALL sp_devolver_libro(?)")) {
            ps.setInt(1, prestamoId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
