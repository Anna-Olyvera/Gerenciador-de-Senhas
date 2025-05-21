package service;

public class TokenService {
    private static String tokenGerado;

    // Conjunto de caracteres seguros (sem i, I, l, L, o, O, 0, 1)
    private static final char[] CHARSET = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz".toCharArray();

    public static String gerarToken() {
        StringBuilder token = new StringBuilder();
        long seed = System.nanoTime(); // Fonte de pseudoaleatoriedade básica
        for (int i = 0; i < 6; i++) {
            seed = (seed * 31 + i) ^ (seed >>> 3); // Geração de próximo "estado"
            int index = (int) Math.abs(seed % CHARSET.length);
            token.append(CHARSET[index]);
        }
        tokenGerado = token.toString();
        return tokenGerado;
    }

    public static boolean validarToken(String tokenDigitado) {
        return tokenGerado != null && tokenGerado.equals(tokenDigitado);
    }
}