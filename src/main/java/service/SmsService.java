package service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsService {
    
    // ESTES DADOS DEVEM SER CRIPTOGRAFADOS E ADICIONADOS AO FIREBASE
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";
    public static final String FROM_NUMBER = "";

    public static void enviarSms(String para, String mensagem) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
            new com.twilio.type.PhoneNumber(para),
            new com.twilio.type.PhoneNumber(FROM_NUMBER),
            mensagem
        ).create();

        System.out.println("SMS enviado com SID: " + message.getSid());
    }
}