package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import core.EmailConfig;

public class EmailService {
    public static void enviarEmail(String destinatario, String corpo) {

        FirebaseService firebase = new FirebaseService();
        EmailConfig config = firebase.buscarEmailConfig();

        if (config == null) {
            System.err.println("Não foi possível carregar a configuração de e-mail do Firebase.");
            return;
        }

        final String remetente = config.getRemetente();
        final String autenticacao = config.getAutenticacaoRemetente().trim();

        System.out.println("Remetente: " + remetente);
        System.out.println("Senha (autenticação): " + autenticacao);
        System.out.println("Senha recebida: [" + autenticacao + "]");
        System.out.println("Tamanho: " + autenticacao.length());

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remetente, autenticacao);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Código de Validação Duas Etapas");
            message.setText(corpo);
            Transport.send(message);
            System.out.println("E-mail enviado com sucesso.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
