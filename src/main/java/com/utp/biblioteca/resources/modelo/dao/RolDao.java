package com.utp.biblioteca.resources.modelo.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDao implements CrudDao<Rol, Integer> {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public void crear(Rol entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Rol (nombre) VALUES (?)")) {
            ps.setString(1, entidad.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rol> buscarTodos() {
        List<Rol> roles = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Rol");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setRol_id(rs.getInt("rol_id"));
                rol.setNombre(rs.getString("nombre"));
                roles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Rol buscarUno(Integer id) {
        Rol rol = new Rol();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Rol WHERE rol_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol.setRol_id(rs.getInt("rol_id"));
                    rol.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    @Override
    public void actualizar(Rol entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Rol SET nombre = ? WHERE rol_id = ?")) {
            ps.setString(1, entidad.getNombre());
            ps.setInt(2, entidad.getRol_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Rol WHERE rol_id = ?")) {
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Rol WHERE rol_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                existe = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}
