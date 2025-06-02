package ui;

import controller.UsuarioController;
import security.GeradorSenhaTemporaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaCadastroUsuario extends JPanel {
    private JTextField campoLogin;
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JPasswordField campoChaveMestra;
    private JButton botaoCadastrarUsuario;
    private JButton botaoGerarSenha;

    public TelaCadastroUsuario(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(6, 2, 10, 10));

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

        botaoGerarSenha = new JButton("Gerar Senha Temporária");
        botaoGerarSenha.addActionListener((ActionEvent e) -> {
            String senhaTemporaria = GeradorSenhaTemporaria.gerarSenha(8); // Tamanho mínimo desejado
            campoChaveMestra.setText(senhaTemporaria);
            JOptionPane.showMessageDialog(this, "Senha temporária gerada:\n" + senhaTemporaria);
        });

        botaoCadastrarUsuario = new JButton("Cadastrar");
        botaoCadastrarUsuario.addActionListener((ActionEvent e) -> {
            String login = campoLogin.getText();
            String email = campoEmail.getText();
            String telefone = campoTelefone.getText();
            String chaveMestraPura = new String(campoChaveMestra.getPassword());

            boolean sucesso = controller.cadastroUsuario(login, email, telefone, chaveMestraPura);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                layout.show(container, "telaCofreDigital");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário. Verifique os dados e tente novamente.");
            }
        });

        add(botaoGerarSenha);
        add(botaoCadastrarUsuario);
    }
}
