package com.utp.biblioteca.resources.modelo.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Rol;
import com.utp.biblioteca.resources.modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements CrudDao<Usuario, Integer> {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public void crear(Usuario entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Usuario (nombres, apellidos, dni, correo, contraseña, rol_id, estado) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setInt(3, entidad.getDni());
            ps.setString(4, entidad.getCorreo());
            ps.setString(5, entidad.getContraseña());
            ps.setInt(6, entidad.getRol().getRol_id());
            ps.setBoolean(7, entidad.isEstado());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Usuario");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();

                RolDao rolDao = new RolDao();
                Rol rol = rolDao.buscarUno(rs.getInt("rol_id"));

                usuario.setUsuario_id(rs.getInt("usuario_id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getInt("dni"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setRol(rol);
                usuario.setEstado(rs.getBoolean("estado"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario buscarUno(Integer id) {
        Usuario usuario = new Usuario();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Usuario WHERE usuario_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RolDao rolDao = new RolDao();
                    Rol rol = rolDao.buscarUno(rs.getInt("rol_id"));

                    usuario.setUsuario_id(rs.getInt("usuario_id"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setDni(rs.getInt("dni"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setRol(rol);
                    usuario.setEstado(rs.getBoolean("estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void actualizar(Usuario entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Usuario SET nombres = ?, apellidos = ?, dni = ?, correo = ?, contraseña = ?, rol_id = ?, estado = ? WHERE usuario_id = ?")) {
            ps.setString(1, entidad.getNombres());
            ps.setString(2, entidad.getApellidos());
            ps.setInt(3, entidad.getDni());
            ps.setString(4, entidad.getCorreo());
            ps.setString(5, entidad.getContraseña());
            ps.setInt(6, entidad.getRol().getRol_id());
            ps.setBoolean(7, entidad.isEstado());
            ps.setInt(8, entidad.getUsuario_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Usuario WHERE usuario_id = ?")) {
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Usuario WHERE usuario_id = ?")) {
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
