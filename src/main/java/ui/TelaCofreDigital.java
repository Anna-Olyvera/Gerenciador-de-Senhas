package ui;

import com.google.firebase.database.*;
import controller.UsuarioController;
import core.Sessao;
import core.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCofreDigital extends JPanel {

    private UsuarioController controller;
    private CardLayout layout;
    private JPanel container;
    private Usuario usuario;

    private JList<String> listaCredenciais;
    private DefaultListModel<String> modeloLista;

    public TelaCofreDigital(CardLayout layout, JPanel container, UsuarioController controller, Usuario usuarioLogado) {
        this.controller = controller;
        this.layout = layout;
        this.container = container;
        this.usuario = usuarioLogado;

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cofre Digital de " + usuario.getLogin(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Lista de credenciais
        modeloLista = new DefaultListModel<>();
        listaCredenciais = new JList<>(modeloLista);
        listaCredenciais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaCredenciais), BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botaoAdicionarServico = new JButton("Adicionar Serviço");
        botaoAdicionarServico.addActionListener(e -> layout.show(container, "telaAdicionarServico"));

        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.addActionListener(e -> {
            String selecionado = listaCredenciais.getSelectedValue();
            if (selecionado == null || !selecionado.contains("Serviço:")) {
                JOptionPane.showMessageDialog(this, "Selecione uma credencial para excluir.");
                return;
            }

            String nomeServico = selecionado.split("\\|")[0].replace("Serviço:", "").trim();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir o serviço: " + nomeServico + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = controller.removerCredencial(usuario.getLogin(), nomeServico);
                if (sucesso) {
                    modeloLista.removeElement(selecionado);
                    JOptionPane.showMessageDialog(this, "Credencial excluída com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir credencial.");
                }
            }
        });

        JButton botaoVerificarEmail = new JButton("Verificar Vazamento");
        botaoVerificarEmail.addActionListener(e -> {
            String email = usuario.getEmail(); // Usa o e-mail do usuário logado
            String resultado = security.EmailVazado.verificarEmail(email); // Chama a verificação

            JOptionPane.showMessageDialog(this, resultado, "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE);
        });

painelBotoes.add(botaoVerificarEmail);


        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> layout.show(container, "telaLogin"));

        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> {
            Sessao.limparSessao();
            layout.show(container, "telaLogin");
        });

        painelBotoes.add(botaoAdicionarServico);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(btnVoltar);
        painelBotoes.add(botaoSair);
        painelBotoes.add(botaoVerificarEmail);

        add(painelBotoes, BorderLayout.SOUTH);

        carregarCredenciais();
    }

    public void carregarCredenciais() {
        modeloLista.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Usuarios")
                .child(usuario.getLogin())
                .child("Credenciais");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot credencialSnapshot : snapshot.getChildren()) {
                        String nomeServico = credencialSnapshot.getKey();
                        String senha = credencialSnapshot.getValue(String.class);
                        modeloLista.addElement("Serviço: " + nomeServico + " | Senha: " + senha);
                    }
                } else {
                    modeloLista.addElement("Nenhuma credencial encontrada.");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                modeloLista.addElement("Erro ao carregar credenciais: " + error.getMessage());
            }
        });
    }
}
