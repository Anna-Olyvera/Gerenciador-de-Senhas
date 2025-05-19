package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroUsuario extends JPanel {
    private JTextField campoLogin;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JPasswordField campoChaveMestra;
    private JButton botaoCadastrarUsuario;

    public TelaCadastroUsuario(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(5, 2, 10, 10));

        // ADICIONANDO OS CAMPOS
        add(new JLabel("Login: "));
        campoLogin = new JTextField();
        add(campoLogin);

        add(new JLabel("E-mail: "));
        campoEmail = new JTextField();
        add(campoEmail);

        add(new JLabel("Telefone: "));
        campoTelefone = new JTextField();
        add(campoTelefone);

        add(new JLabel("Chave Mestra: "));
        campoChaveMestra = new JPasswordField();
        add(campoChaveMestra);

        // BOTÃO DE CADASTRO
        botaoCadastrarUsuario = new JButton("Cadastrar");
        add(new JLabel()); // Espaço vazio
        add(botaoCadastrarUsuario);

        // EVENTO ACIONADO PELO BOTÃO "CADASTRAR"
        botaoCadastrarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = campoLogin.getText();
                String email = campoEmail.getText();
                String telefone = campoTelefone.getText();
                String chaveMestra = new String(campoChaveMestra.getPassword());

                boolean sucesso = controller.cadastroUsuario(login, email, telefone, chaveMestra);

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    layout.show(container, "telaCofreDigital");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário. Verifique os dados e tente novamente.");
                }
            }
        });
    }
}