package security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaVazada {

    private SenhaVazada() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean senhaFoiVazada(String senha) {
        try {
            String hashSha1 = gerarSha1(senha).toUpperCase();
            String prefixo = hashSha1.substring(0, 5);
            String sufixo = hashSha1.substring(5);

            URI endereco = new URI("https://api.pwnedpasswords.com/range/" + prefixo);
            URL url = endereco.toURL();

            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes[0].equalsIgnoreCase(sufixo)) {
                    return true; // Senha foi vazada
                }
            }
            leitor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Senha não vazada
    }

    private static String gerarSha1(String entrada) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] resultado = sha1.digest(entrada.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : resultado) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-1 não encontrado", e);
        }
    }
}