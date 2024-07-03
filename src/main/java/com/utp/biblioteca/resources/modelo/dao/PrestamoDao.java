package com.utp.biblioteca.resources.modelo.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Prestamo;
import com.utp.biblioteca.resources.modelo.Usuario;
import com.utp.biblioteca.resources.modelo.dao.sp.StoredProcedureRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao implements CrudDao<Prestamo, Integer> {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public void crear(Prestamo entidad) {
        throw new UnsupportedOperationException("No usen este método. Utilicen 'spRealizarPrestamo' de 'StoredProcedureRepository'.");
        /*try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Prestamo (usuario_id, libro_id, fecha_prestamo, fecha_limite, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, entidad.getUsuario().getUsuario_id());
            ps.setInt(2, entidad.getLibro().getLibro_id());
            ps.setDate(3, entidad.getFecha_prestamo());
            ps.setDate(4, entidad.getFecha_limite());
            ps.setDate(5, entidad.getFecha_devolucion());
            ps.setBoolean(6, entidad.isDevuelto());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public List<Prestamo> buscarTodos() {
        List<Prestamo> prestamos = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prestamo");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();

                UsuarioDao usuarioDao = new UsuarioDao();
                Usuario usuario = usuarioDao.buscarUno(rs.getInt("usuario_id"));
                LibroDao libroDao = new LibroDao();
                Libro libro = libroDao.buscarUno(rs.getInt("libro_id"));

                prestamo.setPrestamo_id(rs.getInt("prestamo_id"));
                prestamo.setUsuario(usuario);
                prestamo.setLibro(libro);
                prestamo.setFecha_prestamo(rs.getDate("fecha_prestamo"));
                prestamo.setFecha_limite(rs.getDate("fecha_limite"));
                prestamo.setFecha_devolucion(rs.getDate("fecha_devolucion"));
                prestamo.setDevuelto(rs.getBoolean("devuelto"));
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    @Override
    public Prestamo buscarUno(Integer id) {
        Prestamo prestamo = new Prestamo();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prestamo WHERE prestamo_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    UsuarioDao usuarioDao = new UsuarioDao();
                    Usuario usuario = usuarioDao.buscarUno(rs.getInt("usuario_id"));
                    LibroDao libroDao = new LibroDao();
                    Libro libro = libroDao.buscarUno(rs.getInt("libro_id"));

                    prestamo.setPrestamo_id(rs.getInt("prestamo_id"));
                    prestamo.setUsuario(usuario);
                    prestamo.setLibro(libro);
                    prestamo.setFecha_prestamo(rs.getDate("fecha_prestamo"));
                    prestamo.setFecha_limite(rs.getDate("fecha_limite"));
                    prestamo.setFecha_devolucion(rs.getDate("fecha_devolucion"));
                    prestamo.setDevuelto(rs.getBoolean("devuelto"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamo;
    }

    @Override
    public void actualizar(Prestamo entidad) {
        throw new UnsupportedOperationException("No usen este método. Utilicen 'spDevolverLibro' de 'StoredProcedureRepository'.");
        /*try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Prestamo SET usuario_id = ?, libro_id = ?, fecha_prestamo = ?, fecha_limite = ?, fecha_devolucion = ?, devuelto = ? WHERE prestamo_id = ?")) {
            ps.setInt(1, entidad.getUsuario().getUsuario_id());
            ps.setInt(2, entidad.getLibro().getLibro_id());
            ps.setDate(3, entidad.getFecha_prestamo());
            ps.setDate(4, entidad.getFecha_limite());
            ps.setDate(5, entidad.getFecha_devolucion());
            ps.setBoolean(6, entidad.isDevuelto());
            ps.setInt(7, entidad.getPrestamo_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Prestamo WHERE prestamo_id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean existe(Integer id) {
        boolean existe = false;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Prestamo WHERE prestamo_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                existe = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    // Método para obtener los 3 libros más prestados "TOP"
    public List<Libro> buscarTop(int top) {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT libro_id, COUNT(*) as count FROM Prestamo GROUP BY libro_id ORDER BY count DESC LIMIT ?")) {
            ps.setInt(1, top);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LibroDao libroDao = new LibroDao();
                Libro libro = libroDao.buscarUno(rs.getInt("libro_id"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}
