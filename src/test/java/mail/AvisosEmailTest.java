package mail;

import com.utp.biblioteca.resources.servicio.AvisosEmail;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AvisosEmailTest {

    @Test
    public void testEnviarCorreo() {
        AvisosEmail avisosEmail = new AvisosEmail();
        try {
            avisosEmail.enviarCorreo("leonardocesarespejo@gmail.com", "test mailapi", "mensaje test mailapi");
            assertTrue(true, "El correo se ha enviado correctamente");
        } catch (Exception e) {
            fail("El envío del correo falló: " + e.getMessage());
        }
    }
}