package com.utp.biblioteca.resources.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao implements CrudDao<Prestamo, Integer> {

    Conexion con;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public PrestamoDao() {
        con = new Conexion();
    }

    @Override
    public void crear(Prestamo entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("INSERT INTO Prestamo (usuario_id, libro_id, fecha_prestamo, fecha_limite, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, entidad.getUsuario_id());
            ps.setInt(2, entidad.getLibro_id());
            ps.setDate(3, entidad.getFecha_prestamo());
            ps.setDate(4, entidad.getFecha_limite());
            ps.setDate(5, entidad.getFecha_devolucion());
            ps.setBoolean(6, entidad.isDevuelto());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Prestamo> buscarTodos() {
        List<Prestamo> prestamos = new ArrayList<>();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Prestamo");
            rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setPrestamo_id(rs.getInt("prestamo_id"));
                prestamo.setUsuario_id(rs.getInt("usuario_id"));
                prestamo.setLibro_id(rs.getInt("libro_id"));
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
    public Prestamo buscarUno(Integer integer) {
        Prestamo prestamo = new Prestamo();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Prestamo WHERE prestamo_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            while (rs.next()) {
                prestamo.setPrestamo_id(rs.getInt("prestamo_id"));
                prestamo.setUsuario_id(rs.getInt("usuario_id"));
                prestamo.setLibro_id(rs.getInt("libro_id"));
                prestamo.setFecha_prestamo(rs.getDate("fecha_prestamo"));
                prestamo.setFecha_limite(rs.getDate("fecha_limite"));
                prestamo.setFecha_devolucion(rs.getDate("fecha_devolucion"));
                prestamo.setDevuelto(rs.getBoolean("devuelto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamo;
    }

    @Override
    public void actualizar(Prestamo entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("UPDATE Prestamo SET usuario_id = ?, libro_id = ?, fecha_prestamo = ?, fecha_limite = ?, fecha_devolucion = ?, devuelto = ? WHERE prestamo_id = ?");
            ps.setInt(1, entidad.getUsuario_id());
            ps.setInt(2, entidad.getLibro_id());
            ps.setDate(3, entidad.getFecha_prestamo());
            ps.setDate(4, entidad.getFecha_limite());
            ps.setDate(5, entidad.getFecha_devolucion());
            ps.setBoolean(6, entidad.isDevuelto());
            ps.setInt(7, entidad.getPrestamo_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer integer) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("DELETE FROM Prestamo WHERE prestamo_id = ?");
            ps.setInt(1, integer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean existe(Integer integer) {
        boolean existe = false;
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Prestamo WHERE prestamo_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    // los 3 libros mas prestados "TOP"
    public List<Libro> buscarTop(int top) {
        List<Libro> libros = new ArrayList<>();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT libro_id, COUNT(*) as count FROM Prestamo GROUP BY libro_id ORDER BY count DESC LIMIT ?");
            ps.setInt(1, top);
            rs = ps.executeQuery();
            while (rs.next()) {
                int libro_id = rs.getInt("libro_id");
                Libro libro = new LibroDao().buscarUno(libro_id);
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}