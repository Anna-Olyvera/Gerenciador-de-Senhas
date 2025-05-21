package ui;

import controller.UsuarioController;
import core.Sessao;
import core.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JPanel {
    private JTextField campoLogin;
    private JPasswordField campoChaveMestra;
    private JButton botaoAcessar;

    // CONSTRUTOR DA TELA DE LOGIN
    public TelaLogin(CardLayout layout, JPanel container, UsuarioController controller) {
        // DEFINE LAYOUT EM GRADE COM ESPAÇAMENTO
        setLayout(new GridLayout(4, 2, 10, 10));

        // CAMPO DE ACESSO
        add(new JLabel("Login: "));
        campoLogin = new JTextField();
        add(campoLogin);

        // CAMPO DA CHAVE MESTRA
        add(new JLabel("Chave Mestra: "));
        campoChaveMestra = new JPasswordField();
        add(campoChaveMestra);

        // BOTÃO DE ACESSO
        add(new JLabel());
        botaoAcessar = new JButton("Acessar");
        add(botaoAcessar);

        // LINK: ESQUECEU A SENHA
        JLabel labelEsqueceu = new JLabel("<html><a href='#'>Esqueceu a senha?</a></html>");
        labelEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelEsqueceu);

        // LINK: PRIMEIRO ACESSO (CADASTRO)
        JLabel labelPrimeiroAcesso = new JLabel("Primeiro acesso?");
        JLabel labelCadastro = new JLabel("<html><a href='#'>Cadastre-se</a></html>");
        labelCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelPrimeiroAcesso);
        add(labelCadastro);

        // EVENTO: CLIQUE NO LINK "ESQUECEU A SENHA?"
        labelEsqueceu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layout.show(container, "telaEsqueceuSenha");
            }
        });

        // EVENTO: CLIQUE NO LINK "CADASTRE-SE"
        labelCadastro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layout.show(container, "telaCadastroUsuario");
            }
        });

        // EVENTO: CLIQUE NO BOTÃO "ACESSAR"
        botaoAcessar.addActionListener(e -> {
            // OBTÉM O LOGIN E A CHAVE MESTRA DIGITADA
            String login = campoLogin.getText().trim();
            char[] chaveArray = campoChaveMestra.getPassword();
            String chaveMestra = new String(chaveArray);

            // VERIFICA SE OS CAMPOS ESTÃO PREENCHIDOS
            if (login.isEmpty() || chaveMestra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            // AUTENTICA O USUÁRIO
            Usuario usuario = controller.autenticar(login, chaveMestra);
            if (usuario != null) {
                // SALVA USUÁRIO NA SESSÃO E VAI PARA VALIDAÇÃO DE DUAS ETAPAS
                Sessao.setUsuarioLogado(usuario);
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                limparCampos();
                layout.show(container, "telaValidacaoDuasEtapas");
            } else {
                // EXIBE ERRO E LIMPA O CAMPO DA SENHA
                JOptionPane.showMessageDialog(this, "Login ou chave mestra incorretos.");
                campoChaveMestra.setText("");
            }
        });
    }

    // MÉTODO PARA LIMPAR OS CAMPOS DE ENTRADA
    private void limparCampos() {
        campoLogin.setText("");
        campoChaveMestra.setText("");
    }
}