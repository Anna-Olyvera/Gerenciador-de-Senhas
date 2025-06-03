package ui;

import com.google.firebase.database.*;
import controller.UsuarioController;
import core.Sessao;
import core.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCofreDigital extends JPanel {

    private final UsuarioController controller;
    private final CardLayout layout;
    private final JPanel container;
    private final Usuario usuario;

    private final JList<String> listaCredenciais;
    private final DefaultListModel<String> modeloLista;

    public TelaCofreDigital(CardLayout layout, JPanel container, UsuarioController controller, Usuario usuarioLogado) {
        this.controller = controller;
        this.layout = layout;
        this.container = container;
        this.usuario = usuarioLogado;

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cofre Digital de " + usuario.getLogin(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaCredenciais = new JList<>(modeloLista);
        listaCredenciais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaCredenciais), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botaoAdicionarServico = new JButton("Adicionar Serviço");
        botaoAdicionarServico.addActionListener(e -> this.layout.show(this.container, "telaAdicionarServico"));

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
                boolean sucesso = this.controller.removerCredencial(this.usuario.getLogin(), nomeServico);
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
            String email = this.usuario.getEmail();
            String resultado = security.EmailVazado.verificarEmail(email);
            JOptionPane.showMessageDialog(this, resultado, "Resultado da Verificação", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> this.layout.show(this.container, "telaLogin"));

        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> {
            Sessao.limparSessao();
            this.layout.show(this.container, "telaLogin");
        });

        painelBotoes.add(botaoAdicionarServico);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoVerificarEmail);
        painelBotoes.add(botaoVoltar);
        painelBotoes.add(botaoSair);

        add(painelBotoes, BorderLayout.SOUTH);

        carregarCredenciais();
    }

    public void carregarCredenciais() {
        modeloLista.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Usuarios")
                .child(this.usuario.getLogin())
                .child("Credenciais");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                modeloLista.clear();
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
                modeloLista.clear();
                modeloLista.addElement("Erro ao carregar credenciais: " + error.getMessage());
            }
        });
    }
}
