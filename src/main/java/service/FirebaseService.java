package service;

import com.google.api.core.ApiFuture;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;
import core.Credenciais;
import core.EmailConfig;
import core.Usuario;

import config.Descriptografador;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class FirebaseService {
    private static boolean inicializado = false;
    private final DatabaseReference database;

    public FirebaseService() {
        if (!inicializado) {
            try {
                inicializarFirebase();
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao inicializar o Firebase", e);
            }
        }
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("Erro ao inicializar o Firebase. Verifique sua chave ou criptografia.");
        }
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    private void inicializarFirebase() throws Exception {
        String jsonChaveDescriptografado = Descriptografador.descriptografarArquivo("minhaSenhaSegura");
        InputStream serviceAccount = new ByteArrayInputStream(jsonChaveDescriptografado.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://momo-senha-default-rtdb.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
        inicializado = true;
    }

    public void salvarUsuario(Usuario usuario) {
        database.child("Usuarios").child(usuario.getLogin()).setValueAsync(usuario);
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        AtomicReference<Usuario> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios").child(login).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    resultado.set(snapshot.getValue(Usuario.class));
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public void salvarCredencial(String loginUsuario, Credenciais credencial) {
        database.child("Usuarios")
                .child(loginUsuario)
                .child("Credenciais")
                .child(credencial.getNomeCredencial())
                .setValueAsync(credencial.getSenhaCredencial());
    }

    public boolean usuarioExiste(String login) {
        AtomicReference<Boolean> existe = new AtomicReference<>(false);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios").child(login)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        existe.set(snapshot.exists());
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
            Thread.currentThread().interrupt();
        }

        return existe.get();
    }

    public boolean removerCredencial(String loginUsuario, String nomeCredencial) {
        try {
            DatabaseReference ref = database.child("Usuarios")
                    .child(loginUsuario)
                    .child("Credenciais")
                    .child(nomeCredencial);

            ApiFuture<Void> future = ref.removeValueAsync();
            future.get(); // espera remover realmente
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao remover credencial: " + e.getMessage());
            return false;
        }
    }

    public Credenciais buscarCredencial(String loginUsuario, String nomeCredencial) {
        AtomicReference<Credenciais> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios").child(loginUsuario).child("Credenciais").child(nomeCredencial)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado.set(snapshot.getValue(Credenciais.class));
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public List<Credenciais> buscarCredenciais(String loginUsuario) {
        List<Credenciais> lista = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios").child(loginUsuario).child("Credenciais")
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
            Thread.currentThread().interrupt();
        }

        return lista;
    }

    public void listarDadosDeUsuario(String nomeUsuario) {
        DatabaseReference ref = database.child("Usuarios").child(nomeUsuario);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // sem print
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // sem print
            }
        });
    }

    public String buscarAlgoritmoHash() {
        AtomicReference<String> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Manager").child("Algoritmo Cripto")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado.set(snapshot.getValue(String.class));
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public EmailConfig buscarEmailConfig() {
        AtomicReference<EmailConfig> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Manager").child("EmailConfig")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado.set(snapshot.getValue(EmailConfig.class));
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public void salvarTokenEsqueceuSenha(String loginUsuario, String token, long timestamp) {
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", token);
        tokenData.put("timestamp", timestamp);

        database.child("TokensEsqueceuSenha").child(loginUsuario).setValueAsync(tokenData);
    }

    public Map<String, Object> buscarTokenEsqueceuSenha(String loginUsuario) {
        AtomicReference<Map<String, Object>> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("TokensEsqueceuSenha").child(loginUsuario)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Map<String, Object> tokenData = new HashMap<>();
                            for (DataSnapshot child : snapshot.getChildren()) {
                                tokenData.put(child.getKey(), child.getValue());
                            }
                            resultado.set(tokenData);
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        AtomicReference<Usuario> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios")
                .orderByChild("email")
                .equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                resultado.set(child.getValue(Usuario.class));
                                break;
                            }
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }

    public Usuario buscarUsuarioPorTelefone(String telefone) {
        AtomicReference<Usuario> resultado = new AtomicReference<>(null);
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios")
                .orderByChild("telefone")
                .equalTo(telefone)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                resultado.set(child.getValue(Usuario.class));
                                break;
                            }
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
            Thread.currentThread().interrupt();
        }

        return resultado.get();
    }
}
