package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {
    public static void enviarEmail(String destinatario, String assunto, String corpo) {

        // ESTES DADOS DEVEM SER CRIPTOGRAFADOS E ADICIONADOS AO FIREBASE
        final String REMETENTE = ""; 
        final String REMETENTE_AUTENTICACAO = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(REMETENTE, REMETENTE_AUTENTICACAO);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMETENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(corpo);
            Transport.send(message);
            System.out.println("E-mail enviado com sucesso.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}