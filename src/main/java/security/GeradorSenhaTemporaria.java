package security;

import java.security.SecureRandom;

public class GeradorSenhaTemporaria {

    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String ESPECIAIS = "!@#$%&";

    private static final SecureRandom random = new SecureRandom();

    public static String gerarSenha(int tamanhoMinimo) {
        if (tamanhoMinimo < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }

        StringBuilder senha = new StringBuilder();

        // Garantir pelo menos 1 número e 1 caractere especial
        senha.append(NUMEROS.charAt(random.nextInt(NUMEROS.length())));
        senha.append(ESPECIAIS.charAt(random.nextInt(ESPECIAIS.length())));

        // Preencher o restante com letras, números e especiais aleatórios
        String todosCaracteres = LETRAS + NUMEROS + ESPECIAIS;
        while (senha.length() < tamanhoMinimo) {
            senha.append(todosCaracteres.charAt(random.nextInt(todosCaracteres.length())));
        }

        // Embaralhar os caracteres para não ficarem sempre número + especial no início
        return embaralhar(senha.toString());
    }

    private static String embaralhar(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Troca de posições
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
}
