import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileInputStream;

public class Escrita {
    public static void main(String[] args) throws Exception {
        // Caminho da sua chave JSON
        FileInputStream serviceAccount = new FileInputStream("/resources/senhaFirebase.json");

        // Configuração do Firebase
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://momo-senha-default-rtdb.firebaseio.com/")  // URL correta do seu banco
                .build();
        FirebaseApp.initializeApp(options);

        // Referência ao nó "usuarios"
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios/Ana");

        // Salvar no Firebase com chave = nome, valor = senha
        ref.child("Teste").setValueAsync("Anabanana");

        System.out.println("Usuário adicionado com sucesso!");
        Thread.sleep(5000);
    }
}
