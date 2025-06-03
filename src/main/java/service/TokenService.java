package service;

import java.security.SecureRandom;

public class TokenService {
      private TokenService() {
        throw new IllegalStateException("Utility class");
    }
    private static String tokenGerado;
    private static long tempoGeracao;

    // TOKEN VÁLIDO POR 10 MINUTOS (600000 ms)
    private static final long TEMPO_VALIDADE_MS = 600000;

    // Conjunto de caracteres seguros (sem i, I, l, L, o, O, 0, 1)
    private static final char[] CHARSET = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz".toCharArray();

    private static final SecureRandom random = new SecureRandom();

    public static String gerarToken() {
        StringBuilder token = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARSET.length);
            token.append(CHARSET[index]);
        }

        tokenGerado = token.toString();
        tempoGeracao = System.currentTimeMillis();
        return tokenGerado;
    }

    public static boolean validarToken(String tokenDigitado) {
        return tokenGerado != null && tokenGerado.equals(tokenDigitado);
    }

    public static boolean expirou() {
        return System.currentTimeMillis() - tempoGeracao > TEMPO_VALIDADE_MS;
    }

    public static boolean precisaReenviarToken() {
        return tokenGerado == null || expirou();
    }
}
