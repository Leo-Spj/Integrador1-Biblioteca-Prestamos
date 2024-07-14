package com.utp.biblioteca.resources.modelo.dao.sp;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Autor;
import com.utp.biblioteca.resources.modelo.FrecuenciaPrestamo;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.dao.AutorDao;
import com.utp.biblioteca.resources.modelo.dao.RolDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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

    // Procedimiento para obtener libros sin stock
    public List<Libro> spLibrosSinStock() {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_libros_sin_stock()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(new AutorDao().buscarUno(rs.getInt("autor_id")));
                libro.setLink_imagen(rs.getString("link_imagen"));
                libro.setDescripcion(rs.getString("descripcion"));
                libro.setStock(rs.getInt("stock"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    // Procedimiento para obtener usuarios atrasados
    public List<Usuario> spUsuariosAtrasados() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_usuarios_atrasados()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(new RolDao().buscarUno(rs.getInt("rol_id")));
                usuario.setEstado(rs.getBoolean("estado"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Procedimiento para obtener frecuencia de préstamos
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

    public List<Usuario> spQuienesTienenLibro(int libroId) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall("{CALL sp_quienes_tienen_libro(?)}")) {
            cs.setInt(1, libroId);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setUsuario_id(rs.getInt("usuario_id"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setDni(rs.getInt("dni"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setRol(new RolDao().buscarUno(rs.getInt("rol_id")));
                    usuario.setEstado(rs.getBoolean("estado"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
