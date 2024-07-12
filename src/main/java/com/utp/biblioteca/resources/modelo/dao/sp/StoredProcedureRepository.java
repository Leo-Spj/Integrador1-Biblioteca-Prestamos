package com.utp.biblioteca.resources.modelo.dao.sp;

import com.utp.biblioteca.resources.configuracion.Conexion;

import java.sql.*;

public class StoredProcedureRepository {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    // Procedimiento para realizar un préstamo comprobando que el "estado" del usuario sea TRUE
    public String spRealizarPrestamo(int usuarioDni, int libroId, int dias) {
        String mensaje = "";
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_realizar_prestamo(?, ?, ?, ?)}")) {
            cs.setInt(1, usuarioDni);
            cs.setInt(2, libroId);
            cs.setInt(3, dias);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            mensaje = cs.getString(4);
        } catch (SQLException e) {
            e.printStackTrace();
            mensaje = "Error al ejecutar el procedimiento: " + e.getMessage();
        }
        return mensaje;
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
