package security;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

import service.FirebaseService;

public class Senha {

    private Senha() {
        throw new IllegalStateException("Utility class");
    }

    // Busca o algoritmo do Firebase ao iniciar a classe
    private static final String ALGORITMO;

    static {
        FirebaseService firebaseService = new FirebaseService();
        String algoritmoFirebase = firebaseService.buscarAlgoritmoHash();
        ALGORITMO = (algoritmoFirebase != null && !algoritmoFirebase.isEmpty())
                ? algoritmoFirebase
                : "Aloha"; // valor padrão de segurança
    }

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String gerarHash(String senha, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(senha.toCharArray(), Base64.getDecoder().decode(salt), 100_000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static String gerarSenhaCriptografada(String senha) {
        try {
            String salt = gerarSalt();
            String hash = gerarHash(senha, salt);
            return salt + ":" + hash;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar senha criptografada", e);
        }
    }

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