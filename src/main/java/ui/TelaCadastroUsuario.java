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
    private JButton botaoVoltar;

    public TelaCadastroUsuario(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(5, 2, 10, 10)); // 5 linhas e 2 colunas para os campos

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

        // Painel para os botões, para ficar numa linha só e com espaçamento natural
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener((ActionEvent e) -> {
            limparCampos();
            layout.show(container, "telaLogin");
        });
        painelBotoes.add(botaoVoltar);

        botaoGerarSenha = new JButton("Gerar Senha Temporária");
        botaoGerarSenha.addActionListener((ActionEvent e) -> {
            String senhaTemporaria = GeradorSenhaTemporaria.gerarSenha(8);
            campoChaveMestra.setText(senhaTemporaria);
            JOptionPane.showMessageDialog(this, "Senha temporária gerada:\n" + senhaTemporaria);
        });
        painelBotoes.add(botaoGerarSenha);

        botaoCadastrarUsuario = new JButton("Cadastrar");
        botaoCadastrarUsuario.addActionListener((ActionEvent e) -> {
            String login = campoLogin.getText().trim();
            String email = campoEmail.getText().trim();
            String telefone = campoTelefone.getText().trim();
            String chaveMestraPura = new String(campoChaveMestra.getPassword());

            String resultado = controller.cadastroUsuario(login, email, telefone, chaveMestraPura);

            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                layout.show(container, "telaLogin");
            } else {
                JOptionPane.showMessageDialog(this, resultado, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        painelBotoes.add(botaoCadastrarUsuario);

        // Agora adiciona o painel de botões no GridLayout ocupando as duas colunas da última linha
        // Para isso, adicionamos um label vazio para alinhar antes, e depois o painel, ocupando 2 colunas
        add(new JLabel()); // célula vazia para alinhamento
        add(painelBotoes);
    }

    private void limparCampos() {
        campoLogin.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        campoChaveMestra.setText("");
    }
}
