package dao;

import com.utp.biblioteca.resources.modelo.dao.UsuarioDao;
import com.utp.biblioteca.resources.modelo.Usuario;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UsuarioDaoTest {

    @Test
    public void testBuscarTodos() {
        UsuarioDao usuarioDao = new UsuarioDao();
        List<Usuario> usuarios = usuarioDao.buscarTodos();

        if (!usuarios.isEmpty()) {
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } else {
            System.out.println("No se encontraron usuarios");
        }

    }
}