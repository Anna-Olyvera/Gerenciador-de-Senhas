import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Email_vazado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite seu email: ");
        String email = sc.nextLine();

        consultarEmailVazado(email);

        sc.close();
    }

    public static void consultarEmailVazado(String email) {
        String url = "https://hackcheck.woventeams.com/api/v4/breachedaccount/" + email;

        // Criação do cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construção da requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // Envio da requisição e recebimento da resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificação do código de status HTTP
            if (response.statusCode() == 200) {
                System.out.println("E-mail encontrado em vazamentos:");
                System.out.println(response.body());
            } else if (response.statusCode() == 404) {
                System.out.println("E-mail não encontrado em vazamentos.");
            } else {
                System.out.println("Erro ao consultar a API. Código de status: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao enviar requisição: " + e.getMessage());
        }
    }
}
