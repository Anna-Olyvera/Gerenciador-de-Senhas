package config;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Descriptografador {
    private static final int TAG_LENGTH_BIT = 128;

    public static String descriptografarArquivo(String senha) throws Exception {
        
        String conteudoCriptografado = "H4RMl9Sghnco2HeJWT5hNQ==:vRfko4LWPzT3qFsw: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";

        // Divide o conteúdo em partes: salt, iv e dados
        String[] partes = conteudoCriptografado.split(":");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato inválido de conteúdo criptografado");
        }

        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] iv = Base64.getDecoder().decode(partes[1]);
        byte[] dadosCriptografados = Base64.getDecoder().decode(partes[2]);

        // Deriva a chave com a senha
        SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec espec = new PBEKeySpec(senha.toCharArray(), salt, 65536, 256);
        SecretKey chave = new SecretKeySpec(fabrica.generateSecret(espec).getEncoded(), "AES");

        // Define o parâmetro GCM
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);

        // Inicializa o Cipher em modo DECRIPTOGRAFIA
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, chave, gcmSpec);

        // Descriptografa os dados
        byte[] jsonBytes = cipher.doFinal(dadosCriptografados);
        return new String(jsonBytes, "UTF-8");
    }
}