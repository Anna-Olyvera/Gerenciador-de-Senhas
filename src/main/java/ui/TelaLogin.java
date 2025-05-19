package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLogin extends JPanel{
    private JTextField campoLogin;
    private JPasswordField campoChaveMestra;
    private JButton botaoAcessar;

    public TelaLogin(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        // ADICIONANDO OS CAMPOS
        add(new JLabel("Login: "));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("Chave Mestra: "));
        campoChaveMestra = new JPasswordField();
        add(campoChaveMestra);

        // BOTÃO DE ACESSO
        add(new JLabel());
        botaoAcessar = new JButton("Acessar");
        add(botaoAcessar);

        // LINK PARA RECUPERAR SENHA
        JLabel labelEsqueceu = new JLabel("<html><a href='#'>Esqueceu a senha?</a></html>");
        labelEsqueceu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelEsqueceu);

        // LINK PARA SE CADASTRAR
        JLabel labelPrimeiroAcesso = new JLabel("Primeiro acesso?");
        JLabel labelCadastro = new JLabel("<html><a href='#'>Cadastre-se</a></html>");
        labelCadastro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(labelPrimeiroAcesso);
        add(labelCadastro);

        // EVENTO ACIONADO PELO "ESQUCEU A SENHA"
        labelEsqueceu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                layout.show(container, "telaEsqueceuSenha");
            }
        });

        // EVENTO ACIONADO PELO "CADASTRAR-SE"
        labelCadastro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layout.show(container, "telaCadastroUsuario");
            }
        });

        // EVENTO ACIONADO PELO BOTÃO "LOGIN"
        botaoAcessar.addActionListener(e -> {
            String login = campoLogin.getText();
            String chaveMestra = new String(campoChaveMestra.getPassword());

            if (login.isEmpty() || chaveMestra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            if (controller.autenticar(login, chaveMestra) != null) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                layout.show(container, "telaCofreDigital");
            } else {
                JOptionPane.showMessageDialog(this, "Login ou chave mestra incorretos.");
            }
        });
    }
}
