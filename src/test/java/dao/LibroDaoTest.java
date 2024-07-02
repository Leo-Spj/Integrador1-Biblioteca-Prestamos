package dao;

import com.utp.biblioteca.resources.modelo.dao.LibroDao;
import com.utp.biblioteca.resources.modelo.Libro;
import com.utp.biblioteca.resources.modelo.dao.PrestamoDao;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LibroDaoTest {


    @Test
    public void testBuscarTodosLibros() {
        LibroDao libroDao = new LibroDao();
        List<Libro> libros = libroDao.buscarPaginado(1, 3);

        if (!libros.isEmpty()) {
            for (Libro libro : libros) {
                System.out.println(libro);
            }
        } else {
            System.out.println("No se encontraron libros");
        }
    }
}
