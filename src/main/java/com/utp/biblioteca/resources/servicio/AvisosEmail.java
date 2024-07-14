package com.utp.biblioteca.resources.servicio;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AvisosEmail {

    private Session session;
    private Properties emailProperties;
    private String emailFrom;
    private String passwordFrom;

    public AvisosEmail() {
        emailProperties = loadEmailProperties();
        emailFrom = emailProperties.getProperty("mail.email");
        passwordFrom = emailProperties.getProperty("mail.password");

        Properties sessionProperties = new Properties();
        sessionProperties.put("mail.smtp.host", emailProperties.getProperty("mail.smtp.host"));
        sessionProperties.put("mail.smtp.ssl.trust", emailProperties.getProperty("mail.smtp.ssl.trust"));
        sessionProperties.setProperty("mail.smtp.starttls.enable", emailProperties.getProperty("mail.smtp.starttls.enable"));
        sessionProperties.setProperty("mail.smtp.port", emailProperties.getProperty("mail.smtp.port"));
        sessionProperties.setProperty("mail.smtp.user", emailFrom);
        sessionProperties.setProperty("mail.smtp.ssl.protocols", emailProperties.getProperty("mail.smtp.ssl.protocols"));
        sessionProperties.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(sessionProperties);
    }

    private Properties loadEmailProperties() {
        Properties properties = new Properties();
        try (InputStream input = AvisosEmail.class.getClassLoader().getResourceAsStream("mail/mail.properties")) {
            if (input == null) {
                Logger.getLogger(AvisosEmail.class.getName()).log(Level.SEVERE, "Unable to find mail.properties");
                return null;
            }
            properties.load(input);
        } catch (IOException ex) {
            Logger.getLogger(AvisosEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }

    public void enviarCorreo(String emailDestinatario, String asunto, String cuerpoMensaje) {
        try {
            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(emailFrom));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            mensaje.setSubject(asunto);
            mensaje.setContent(cuerpoMensaje, "text/html; charset=utf-8");

            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();

            System.out.println("Correo enviado exitosamente.");
        } catch (MessagingException ex) {
            Logger.getLogger(AvisosEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}