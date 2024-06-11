package configuracion;

import com.utp.biblioteca.resources.configuracion.Conexion;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseConnectionTest {

    @Test
    public void testGetConectar() {
        Conexion conexion = new Conexion();
        Connection conn = null;
        try {
            conn = conexion.getConectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(conn);
    }
}