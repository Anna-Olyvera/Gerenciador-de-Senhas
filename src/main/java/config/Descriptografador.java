package config;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.KeySpec;
import java.util.Base64;

public class Descriptografador {
    private Descriptografador() {
        throw new IllegalStateException("Classe utilitária");
    }

    private static final String CONTEUDO_CRIPTOGRAFADO = "q2Uk35Ql5FBRBlTPmT1dZQ==:uAbsmm2+BV0bap5aj1pvbA==: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";

    private static SecretKey gerarChaveSecreta(char[] senha, byte[] salt) throws Exception {
        SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec espec = new PBEKeySpec(senha, salt, 65536, 256);
        SecretKey chaveTemp = fabrica.generateSecret(espec);
        return new SecretKeySpec(chaveTemp.getEncoded(), "AES");
    }

    public static String descriptografarArquivo(String senha) throws Exception {
        String[] partes = CONTEUDO_CRIPTOGRAFADO.split(":");
        if (partes.length != 3) {
            throw new IllegalArgumentException("Formato inválido");
        }

        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] iv = Base64.getDecoder().decode(partes[1]);
        byte[] conteudoCripto = Base64.getDecoder().decode(partes[2]);

        SecretKey chave = gerarChaveSecreta(senha.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, chave, new IvParameterSpec(iv));

        byte[] bytesDescriptografados = cipher.doFinal(conteudoCripto);
        return new String(bytesDescriptografados, "UTF-8");
    }
}
