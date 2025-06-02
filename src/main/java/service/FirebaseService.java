package service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.auth.oauth2.GoogleCredentials;
import core.Credenciais;
import core.EmailConfig;
import core.Usuario;
import config.ConfigFirebase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    private void inicializarFirebase() {
        try {
            InputStream serviceAccount = new ByteArrayInputStream(ConfigFirebase.JSON_CHAVE.getBytes(StandardCharsets.UTF_8));

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

    public void salvarUsuario(Usuario usuario) {
        database.child("Usuarios").child(usuario.getLogin()).setValueAsync(usuario);
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        final Usuario[] resultado = {null};
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Usuarios").child(login).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void salvarCredencial(String loginUsuario, Credenciais credencial) {
    database.child("Usuarios")
            .child(loginUsuario)
            .child("Credenciais")
            .child(credencial.getNomeCredencial())
            .setValueAsync(credencial.getSenhaCredencial());  // Salva só a senha como valor direto
}


public boolean removerCredencial(String loginUsuario, String nomeCredencial) {
    try {
        DatabaseReference ref = database.child("Usuarios")
            .child(loginUsuario)
            .child("Credenciais")
            .child(nomeCredencial);

        ref.removeValueAsync();
        return true;
    } catch (Exception e) {
        System.err.println("Erro ao remover credencial: " + e.getMessage());
        return false;
    }
}


public Credenciais buscarCredencial(String loginUsuario, String nomeCredencial) {
    final Credenciais[] resultado = {null};
    CountDownLatch latch = new CountDownLatch(1);

    database.child("Usuarios").child(loginUsuario).child("Credenciais").child(nomeCredencial)
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
        e.printStackTrace();
    }

    return lista;
}


    public void listarDadosDeUsuario(String nomeUsuario) {
        DatabaseReference ref = database.child("Usuarios").child(nomeUsuario);

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

    public String buscarAlgoritmoHash() {
        final String[] resultado = {null};
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Manager").child("Algoritmo Cripto")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado[0] = snapshot.getValue(String.class);
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

    // NOVO: método para buscar EmailConfig
    public EmailConfig buscarEmailConfig() {
        final EmailConfig[] resultado = {null};
        CountDownLatch latch = new CountDownLatch(1);

        database.child("Manager").child("EmailConfig")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            resultado[0] = snapshot.getValue(EmailConfig.class);
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
}
