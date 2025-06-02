package ui;

import controller.UsuarioController;
import controller.CredenciaisController;
import core.Sessao;
import core.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JPanel {
    private JTextField campoLogin;
    private JPasswordField campoChaveMestra;
    private JButton botaoAcessar;

    public TelaLogin(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Login: "));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("Chave Mestra: "));
        campoChaveMestra = new JPasswordField();
        add(campoChaveMestra);

        add(new JLabel());
        botaoAcessar = new JButton("Acessar");
        add(botaoAcessar);

        JLabel labelEsqueceu = new JLabel("<html><a href='#'>Esqueceu a senha?</a></html>");
        labelEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelEsqueceu);

        JLabel labelPrimeiroAcesso = new JLabel("Primeiro acesso?");
        JLabel labelCadastro = new JLabel("<html><a href='#'>Cadastre-se</a></html>");
        labelCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelPrimeiroAcesso);
        add(labelCadastro);

        labelEsqueceu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layout.show(container, "telaEsqueceuSenha");
            }
        });

        labelCadastro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layout.show(container, "telaCadastroUsuario");
            }
        });

        botaoAcessar.addActionListener(e -> {
            String login = campoLogin.getText().trim();
            char[] chaveArray = campoChaveMestra.getPassword();
            String chaveMestra = new String(chaveArray);

            if (login.isEmpty() || chaveMestra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            // ✅ Verifica bloqueio antes de tentar autenticar
            if (controller.estaBloqueado(login)) {
                JOptionPane.showMessageDialog(this, "Usuário bloqueado por 30 minutos após 3 tentativas. Tente novamente mais tarde.");
                campoChaveMestra.setText("");
                return;
            }

            Usuario usuario = controller.autenticar(login, chaveMestra);
            if (usuario != null) {
                Sessao.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                limparCampos();

                // Cria a tela do cofre e adiciona
                TelaCofreDigital telaCofre = new TelaCofreDigital(layout, container, controller, usuario);
                container.add(telaCofre, "telaCofreDigital");

                // Cria e adiciona a tela de adicionar serviço com acesso ao cofre
                CredenciaisController credenciaisController = new CredenciaisController();
                TelaAdicionarServico telaAdicionar = new TelaAdicionarServico(layout, container, credenciaisController, telaCofre);
                container.add(telaAdicionar, "telaAdicionarServico");

                layout.show(container, "telaValidacaoDuasEtapas");
            } else {
                JOptionPane.showMessageDialog(this, "Login ou chave mestra incorretos.");
                campoChaveMestra.setText("");
            }
        });
    }

    private void limparCampos() {
        campoLogin.setText("");
        campoChaveMestra.setText("");
    }
}