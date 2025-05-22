package service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;
import core.Credenciais;
import core.Usuario;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class FirebaseService {
    private static boolean inicializado = false;
    private final DatabaseReference database;

    public FirebaseService() {
        if (!inicializado) {
            inicializarFirebase();
        }
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    // INICIALIZAÇÃO
    private void inicializarFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/java/resources/senhaFirebase.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://momo-senha-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
            inicializado = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // MÉTODOS DE USUÁRIO
    public void salvarUsuario(Usuario usuario) {
        database.child("Usuarios").child(usuario.getLogin()).setValueAsync(usuario);
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        final Usuario[] resultado = {null};
        CountDownLatch latch = new CountDownLatch(1);

        database.child("usuarios").child(login).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    resultado[0] = snapshot.getValue(Usuario.class);
                }
                latch.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultado[0];
    }

    // MÉTODOS DE CREDENCIAL
    public void salvarCredencial(String loginUsuario, Credenciais credencial) {
        database.child("credenciais").child(loginUsuario).child(credencial.getNomeCredencial()).setValueAsync(credencial);
    }

    public boolean removerCredencial(String loginUsuario, String nomeCredencial) {
        try {
            database.child("credenciais").child(loginUsuario).child(nomeCredencial).removeValueAsync();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Credenciais buscarCredencial(String loginUsuario, String nomeCredencial) {
        final Credenciais[] resultado = {null};
        CountDownLatch latch = new CountDownLatch(1);

        database.child("credenciais").child(loginUsuario).child(nomeCredencial)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado[0] = snapshot.getValue(Credenciais.class);
                        }
                        latch.countDown();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        latch.countDown();
                    }
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultado[0];
    }

    public List<Credenciais> buscarCredenciais(String loginUsuario) {
        List<Credenciais> lista = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        database.child("credenciais").child(loginUsuario)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot dado : snapshot.getChildren()) {
                            Credenciais c = dado.getValue(Credenciais.class);
                            if (c != null) lista.add(c);
                        }
                        latch.countDown();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        latch.countDown();
                    }
                });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // MÉTODO EXTRA: Exibir dados de um nó (Exemplo)
    public void listarDadosDeUsuario(String nomeUsuario) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Usuarios/" + nomeUsuario);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String chave = child.getKey();
                        String valor = child.getValue(String.class);
                        System.out.println("Chave: " + chave + " - Valor: " + valor);
                    }
                } else {
                    System.out.println("Nenhum dado encontrado para '" + nomeUsuario + "'");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Erro ao ler dados: " + error.getMessage());
            }
        });
    }
}