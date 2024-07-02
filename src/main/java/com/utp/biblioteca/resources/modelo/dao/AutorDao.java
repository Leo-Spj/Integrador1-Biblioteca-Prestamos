package com.utp.biblioteca.resources.modelo.dao;

import java.sql.PreparedStatement;
import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDao implements CrudDao<Autor, Integer> {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public void crear(Autor entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Autor (nombre) VALUES (?)")) {
            ps.setString(1, entidad.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Autor> buscarTodos() {
        List<Autor> autores = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Autor");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setAutor_id(rs.getInt("autor_id"));
                autor.setNombre(rs.getString("nombre"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autores;
    }

    @Override
    public Autor buscarUno(Integer id) {
        Autor autor = new Autor();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Autor WHERE autor_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    autor.setAutor_id(rs.getInt("autor_id"));
                    autor.setNombre(rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autor;
    }

    @Override
    public void actualizar(Autor entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Autor SET nombre = ? WHERE autor_id = ?")) {
            ps.setString(1, entidad.getNombre());
            ps.setInt(2, entidad.getAutor_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Autor WHERE autor_id = ?")) {
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Autor WHERE autor_id = ?")) {
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