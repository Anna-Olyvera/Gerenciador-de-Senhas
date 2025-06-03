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

    private UsuarioController controller;
    private CardLayout layout;
    private JPanel container;

    public TelaLogin(CardLayout layout, JPanel container, UsuarioController controller) {
        this.controller = controller;
        this.layout = layout;
        this.container = container;

        setLayout(new BorderLayout());

        JPanel painelCentral = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: Login
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentral.add(new JLabel("Login:"), gbc);

        gbc.gridx = 1;
        campoLogin = new JTextField(15);
        painelCentral.add(campoLogin, gbc);

        // Linha 1: Chave Mestra
        gbc.gridx = 0;
        gbc.gridy++;
        painelCentral.add(new JLabel("Chave Mestra:"), gbc);

        gbc.gridx = 1;
        campoChaveMestra = new JPasswordField(15);
        painelCentral.add(campoChaveMestra, gbc);

        // Linha 2: Botão Acessar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        botaoAcessar = new JButton("Acessar");
        painelCentral.add(botaoAcessar, gbc);

        // Linha 3: "Esqueceu a senha?" centralizado
        gbc.gridy++;
        JLabel labelEsqueceu = new JLabel("<html><a href='#'>Esqueceu a senha?</a></html>");
        labelEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelEsqueceu.setHorizontalAlignment(SwingConstants.CENTER);
        painelCentral.add(labelEsqueceu, gbc);

        // Linha 4: Primeiro acesso + cadastre-se
        gbc.gridy++;
        JPanel painelCadastro = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JLabel labelPrimeiroAcesso = new JLabel("Primeiro acesso?");
        JLabel labelCadastro = new JLabel("<html><a href='#'>Cadastre-se</a></html>");
        labelCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        painelCadastro.add(labelPrimeiroAcesso);
        painelCadastro.add(labelCadastro);
        painelCentral.add(painelCadastro, gbc);

        add(painelCentral, BorderLayout.CENTER);

        // Ações dos links
        labelEsqueceu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaLogin.this.layout.show(TelaLogin.this.container, "telaEsqueceuSenha");
            }
        });

        labelCadastro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TelaLogin.this.layout.show(TelaLogin.this.container, "telaCadastroUsuario");
            }
        });

        // Ação do botão Acessar
        botaoAcessar.addActionListener(e -> {
            String login = campoLogin.getText().trim();
            char[] chaveArray = campoChaveMestra.getPassword();
            String chaveMestra = new String(chaveArray);

            if (login.isEmpty() || chaveMestra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            if (TelaLogin.this.controller.estaBloqueado(login)) {
                JOptionPane.showMessageDialog(this, "Usuário bloqueado por 30 minutos após 3 tentativas. Tente novamente mais tarde.");
                campoChaveMestra.setText("");
                return;
            }

            Usuario usuario = TelaLogin.this.controller.autenticar(login, chaveMestra);
            if (usuario != null) {
                Sessao.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                limparCampos();

                boolean telaCofreExiste = false;
                for (Component comp : TelaLogin.this.container.getComponents()) {
                    if (comp instanceof TelaCofreDigital) {
                        telaCofreExiste = true;
                        break;
                    }
                }
                if (!telaCofreExiste) {
                    TelaCofreDigital telaCofre = new TelaCofreDigital(TelaLogin.this.layout, TelaLogin.this.container, TelaLogin.this.controller, usuario);
                    TelaLogin.this.container.add(telaCofre, "telaCofreDigital");

                    CredenciaisController credenciaisController = new CredenciaisController();
                    TelaAdicionarServico telaAdicionar = new TelaAdicionarServico(TelaLogin.this.layout, TelaLogin.this.container, credenciaisController, telaCofre);
                    TelaLogin.this.container.add(telaAdicionar, "telaAdicionarServico");
                }

                TelaLogin.this.layout.show(TelaLogin.this.container, "telaValidacaoDuasEtapas");
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
