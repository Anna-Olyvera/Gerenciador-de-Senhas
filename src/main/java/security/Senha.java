package security;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

public class Senha {

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String gerarHash(String senha, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(senha.toCharArray(), Base64.getDecoder().decode(salt), 100_000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Gera salt + hash concatenado, tipo "salt:hash"
    public static String gerarSenhaCriptografada(String senha) {
        try {
            String salt = gerarSalt();
            String hash = gerarHash(senha, salt);
            return salt + ":" + hash;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar senha criptografada", e);
        }
    }

    // Verifica se a senha pura bate com o hash armazenado
    public static boolean verificarSenha(String senhaPura, String senhaArmazenada) {
        try {
            String[] parts = senhaArmazenada.split(":");
            if (parts.length != 2) return false;
            String salt = parts[0];
            String hash = parts[1];
            String hashTentativa = gerarHash(senhaPura, salt);
            return hashTentativa.equals(hash);
        } catch (Exception e) {
            return false;
        }
    }
}