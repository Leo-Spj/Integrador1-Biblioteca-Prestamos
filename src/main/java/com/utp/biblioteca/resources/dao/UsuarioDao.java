package com.utp.biblioteca.resources.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements CrudDao<Usuario, Integer> {

    Conexion con;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public UsuarioDao() {
        con = new Conexion();
    }

    @Override
    public void crear(Usuario entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("INSERT INTO Usuario (nombres, apellidos, dni, correo, contraseña, rol_id, estado) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setInt(3, entidad.getDni());
            ps.setString(4, entidad.getCorreo());
            ps.setString(5, entidad.getContraseña());
            ps.setInt(6, entidad.getRol_id());
            ps.setBoolean(7, entidad.isEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Usuario");
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol_id(rs.getInt("rol_id"));
                usuario.setEstado(rs.getBoolean("estado"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario buscarUno(Integer integer) {
        Usuario usuario = new Usuario();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Usuario WHERE usuario_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            while (rs.next()) {
                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol_id(rs.getInt("rol_id"));
                usuario.setEstado(rs.getBoolean("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void actualizar(Usuario entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("UPDATE Usuario SET nombres = ?, apellidos = ?, dni = ?, correo = ?, contraseña = ?, rol_id = ?, estado = ? WHERE usuario_id = ?");
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setInt(3, entidad.getDni());
            ps.setString(4, entidad.getCorreo());
            ps.setString(5, entidad.getContraseña());
            ps.setInt(6, entidad.getRol_id());
            ps.setBoolean(7, entidad.isEstado());
            ps.setInt(8, entidad.getUsuario_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer integer) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("DELETE FROM Usuario WHERE usuario_id = ?");
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
            ps = conn.prepareStatement("SELECT * FROM Usuario WHERE usuario_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}