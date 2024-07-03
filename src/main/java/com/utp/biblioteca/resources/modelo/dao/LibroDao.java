package com.utp.biblioteca.resources.modelo.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Autor;
import com.utp.biblioteca.resources.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDao implements CrudDao<Libro, Integer> {

    private Connection getConnection() throws SQLException {
        return Conexion.getConnection();
    }

    @Override
    public void crear(Libro entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Libro (isbn, titulo, autor_id, link_imagen, descripcion, stock) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, entidad.getIsbn());
            ps.setString(2, entidad.getTitulo());
            ps.setInt(3, entidad.getAutor().getAutor_id());
            ps.setString(4, entidad.getLink_imagen());
            ps.setString(5, entidad.getDescripcion());
            ps.setInt(6, entidad.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Libro> buscarTodos() {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AutorDao autorDao = new AutorDao();
                Autor autor = autorDao.buscarUno(rs.getInt("autor_id"));

                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
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

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro WHERE titulo LIKE ?")) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AutorDao autorDao = new AutorDao();
                Autor autor = autorDao.buscarUno(rs.getInt("autor_id"));

                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
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

    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT L.* FROM Libro L INNER JOIN Autor A ON L.autor_id = A.autor_id WHERE A.nombre LIKE ?")) {
            ps.setString(1, "%" + autor + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AutorDao autorDao = new AutorDao();
                Autor autorObj = autorDao.buscarUno(rs.getInt("autor_id"));

                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autorObj);
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

    public List<Libro> buscarPorIsbn(String isbn) {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro WHERE isbn LIKE ?")) {
            ps.setString(1, "%" + isbn + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AutorDao autorDao = new AutorDao();
                Autor autor = autorDao.buscarUno(rs.getInt("autor_id"));

                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
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

    public int cantidadPaginas(int cantidad) {
        int cantidadPaginas = 0;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM Libro");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int totalLibros = rs.getInt(1);
                cantidadPaginas = (int) Math.ceil((double) totalLibros / cantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidadPaginas;
    }

    public List<Libro> buscarPaginado(Integer pagina, Integer cantidad) {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro LIMIT ?, ?")) {
            ps.setInt(1, (pagina - 1) * cantidad);
            ps.setInt(2, cantidad);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AutorDao autorDao = new AutorDao();
                Autor autor = autorDao.buscarUno(rs.getInt("autor_id"));

                Libro libro = new Libro();
                libro.setLibro_id(rs.getInt("libro_id"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(autor);
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

    @Override
    public Libro buscarUno(Integer id) {
        Libro libro = new Libro();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro WHERE libro_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AutorDao autorDao = new AutorDao();
                    Autor autor = autorDao.buscarUno(rs.getInt("autor_id"));

                    libro.setLibro_id(rs.getInt("libro_id"));
                    libro.setIsbn(rs.getString("isbn"));
                    libro.setTitulo(rs.getString("titulo"));
                    libro.setAutor(autor);
                    libro.setLink_imagen(rs.getString("link_imagen"));
                    libro.setDescripcion(rs.getString("descripcion"));
                    libro.setStock(rs.getInt("stock"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libro;
    }

    @Override
    public void actualizar(Libro entidad) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Libro SET isbn = ?, titulo = ?, autor_id = ?, link_imagen = ?, descripcion = ?, stock = ? WHERE libro_id = ?")) {
            ps.setString(1, entidad.getIsbn());
            ps.setString(2, entidad.getTitulo());
            ps.setInt(3, entidad.getAutor().getAutor_id());
            ps.setString(4, entidad.getLink_imagen());
            ps.setString(5, entidad.getDescripcion());
            ps.setInt(6, entidad.getStock());
            ps.setInt(7, entidad.getLibro_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Libro WHERE libro_id = ?")) {
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
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Libro WHERE libro_id = ?")) {
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
