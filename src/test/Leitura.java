package test;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;

public class Leitura {

    public static void main(String[] args) throws Exception {
        // Caminho para o arquivo JSON com sua chave privada
        FileInputStream serviceAccount = new FileInputStream("src/main/java/resources/senhaFirebase.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://momo-senha-default-rtdb.firebaseio.com/")
                .build();
        FirebaseApp.initializeApp(options);

        // Referência para o nó 'usuarios'
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios/Ana");

        // Busca o dado uma vez (single event)
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Itera sobre todos os filhos do nó 'usuarios'
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String nome = child.getKey(); // Nome do usuário (chave)
                        String senha = child.getValue(String.class); // Senha do usuário (valor)

                        System.out.println("Nome: " + nome + " - Senha: " + senha);
                    }
                } else {
                    System.out.println("Nenhum dado encontrado em 'usuarios'");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Erro ao ler dados: " + error.getMessage());
            }
        });

        // Aguarda para o listener receber resposta antes de encerrar o programa
        Thread.sleep(10000);
    }
}