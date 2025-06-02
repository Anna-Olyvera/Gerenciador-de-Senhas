package security;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EmailVazado {

        private EmailVazado() {
        throw new IllegalStateException("Utility class");
    }

    public static String verificarEmail(String email) {
        if (email == null || !email.contains("@") || email.length() < 5) {
            return "E-mail inválido.";
        }
        String url = "https://hackcheck.woventeams.com/api/v4/breachedaccount/" + email;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", "Java Apache HttpClient");

            try (CloseableHttpResponse response = client.execute(request)) {
                int status = response.getStatusLine().getStatusCode();
                String body = EntityUtils.toString(response.getEntity());

                if (status == 200) {
                    return "⚠️ E-mail encontrado em vazamentos:\n" + body;
                } else if (status == 404) {
                    return "✅ E-mail não encontrado em vazamentos.";
                } else {
                    return "Erro ao consultar a API. Código: " + status + "\nResposta: " + body;
                }
            }
        } catch (Exception e) {
            return "Erro ao enviar requisição: " + e.getMessage();
        }
    }
}
