package com.utp.biblioteca.resources.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDao implements CrudDao<Rol, Integer> {

    Conexion con;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public RolDao() {
        con = new Conexion();
    }

    @Override
    public void crear(Rol entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("INSERT INTO Rol (nombre) VALUES (?)");
            ps.setString(1, entidad.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rol> buscarTodos() {
        List<Rol> roles = new ArrayList<>();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Rol");
            rs = ps.executeQuery();
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
    public Rol buscarUno(Integer integer) {
        Rol rol = new Rol();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Rol WHERE rol_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            while (rs.next()) {
                rol.setRol_id(rs.getInt("rol_id"));
                rol.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    @Override
    public void actualizar(Rol entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("UPDATE Rol SET nombre = ? WHERE rol_id = ?");
            ps.setString(1, entidad.getNombre());
            ps.setInt(2, entidad.getRol_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer integer) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("DELETE FROM Rol WHERE rol_id = ?");
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
            ps = conn.prepareStatement("SELECT * FROM Rol WHERE rol_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}