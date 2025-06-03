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

    // Conteúdo criptografado fixo (salt:iv:encrypted)
    private static final String CONTEUDO_CRIPTOGRAFADO = "m1dx5kw8YMYeiWuYyM9foA==:yYPiMiUV5HSkGNz5p8xNrQ==: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";

    private static SecretKey gerarChaveSecreta(char[] senha, byte[] salt) throws Exception {
        SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec especificacao = new PBEKeySpec(senha, salt, 65536, 256);
        SecretKey chaveTemporaria = fabrica.generateSecret(especificacao);
        return new SecretKeySpec(chaveTemporaria.getEncoded(), "AES");
    }

    public static String descriptografarArquivo(String senha) throws Exception {
        // Usa a string fixa em vez de ler arquivo
        String[] partes = CONTEUDO_CRIPTOGRAFADO.split(":");
        if (partes.length != 3) throw new IllegalArgumentException("Conteúdo criptografado inválido.");

        byte[] salt = Base64.getDecoder().decode(partes[0]);
        byte[] iv = Base64.getDecoder().decode(partes[1]);
        byte[] textoCriptografado = Base64.getDecoder().decode(partes[2]);

        SecretKey chave = gerarChaveSecreta(senha.toCharArray(), salt);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cifra.init(Cipher.DECRYPT_MODE, chave, ivSpec);

        byte[] textoDescriptografado = cifra.doFinal(textoCriptografado);
        return new String(textoDescriptografado, "UTF-8");
    }
}
