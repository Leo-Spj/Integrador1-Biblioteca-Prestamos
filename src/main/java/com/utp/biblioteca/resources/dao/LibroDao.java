package com.utp.biblioteca.resources.dao;

import com.utp.biblioteca.resources.configuracion.Conexion;
import com.utp.biblioteca.resources.modelo.Autor;
import com.utp.biblioteca.resources.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDao implements CrudDao<Libro, Integer> {

    Conexion con;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public LibroDao() {
        con = new Conexion();
    }

    private void closeResources() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void crear(Libro entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("INSERT INTO Libro (isbn, titulo, autor_id, link_imagen, descripcion, stock) VALUES (?, ?, ?, ?, ?, ?)");
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
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Libro");
            rs = ps.executeQuery();
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
        } finally {
            closeResources();
        }
        return libros;
    }

    //obtener cantidad de paginas dispinibles
    public int cantidadPaginas(int cantidad) {
        int cantidadPaginas = 0;
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM Libro");
            rs = ps.executeQuery();

            if (rs.next()) {
                int totalLibros = rs.getInt(1);
                cantidadPaginas = (int) Math.ceil((double) totalLibros / cantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return cantidadPaginas;
    }


    public List<Libro> buscarPaginado(Integer pagina, Integer cantidad) {
        List<Libro> libros = new ArrayList<>();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Libro LIMIT ?, ?");
            ps.setInt(1, (pagina - 1) * cantidad);
            ps.setInt(2, cantidad);
            rs = ps.executeQuery();
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
        } finally {
            closeResources();
        }
        return libros;
    }

    @Override
    public Libro buscarUno(Integer integer) {
        Libro libro = new Libro();
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("SELECT * FROM Libro WHERE libro_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return libro;
    }

    @Override
    public void actualizar(Libro entidad) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("UPDATE Libro SET isbn = ?, titulo = ?, autor_id = ?, link_imagen = ?, descripcion = ?, stock = ? WHERE libro_id = ?");
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
    public void eliminar(Integer integer) {
        try {
            conn = con.getConectar();
            ps = conn.prepareStatement("DELETE FROM Libro WHERE libro_id = ?");
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
            ps = conn.prepareStatement("SELECT * FROM Libro WHERE libro_id = ?");
            ps.setInt(1, integer);
            rs = ps.executeQuery();
            existe = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return existe;
    }
}
