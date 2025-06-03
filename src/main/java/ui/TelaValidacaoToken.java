package ui;

import controller.TentativaLoginController;
import controller.UsuarioController;
import core.Sessao;
import core.Usuario;
import service.EmailService;
import service.TokenService;

import javax.swing.*;
import java.awt.*;

public class TelaValidacaoToken extends JPanel {
    private JTextField campoToken;
    private JButton botaoValidarToken;
    private JButton botaoReenvioToken;
    private JButton botaoVoltar;
    private TentativaLoginController tentativaTokenController = new TentativaLoginController();

    private CardLayout layout;
    private JPanel container;

    public TelaValidacaoToken(CardLayout layout, JPanel container, UsuarioController controller) {
        this.layout = layout;
        this.container = container;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Fundo branco

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Título
        JLabel titulo = new JLabel("Validação de Token");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(titulo, gbc);

        // Campo token
        gbc.gridy++;
        JLabel labelToken = new JLabel("Digite o token enviado:");
        labelToken.setFont(new Font("Arial", Font.PLAIN, 14));
        add(labelToken, gbc);

        gbc.gridy++;
        campoToken = new JTextField(20);
        campoToken.setPreferredSize(new Dimension(200, 30));
        add(campoToken, gbc);

        // Botão Validar Token
        gbc.gridy++;
        botaoValidarToken = new JButton("Confirmar");
        botaoValidarToken.setPreferredSize(new Dimension(150, 35));
        add(botaoValidarToken, gbc);

        // Botão Reenviar Token
        gbc.gridy++;
        botaoReenvioToken = new JButton("Reenviar Token");
        botaoReenvioToken.setPreferredSize(new Dimension(150, 35));
        add(botaoReenvioToken, gbc);

        // Botão Voltar
        gbc.gridy++;
        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setPreferredSize(new Dimension(150, 35));
        add(botaoVoltar, gbc);

        // Ações dos botões
        botaoVoltar.addActionListener(e -> this.layout.show(this.container, "telaLogin"));

        botaoValidarToken.addActionListener(e -> {
            String tokenDigitado = campoToken.getText();
            String login = Sessao.getUsuarioLogado().getLogin();

            if (tentativaTokenController.estaBloqueado(login + "_token")) {
                JOptionPane.showMessageDialog(this, "Você excedeu o número de tentativas. Tente novamente em alguns minutos.");
                return;
            }
            if (TokenService.precisaReenviarToken()) {
                JOptionPane.showMessageDialog(this, "O token expirou. Por favor, solicite um novo.");
                return;
            }
            if (TokenService.validarToken(tokenDigitado)) {
                JOptionPane.showMessageDialog(this, "Token válido!");
                tentativaTokenController.limparTentativas(login + "_token");
                this.layout.show(this.container, "telaCofreDigital");
            } else {
                tentativaTokenController.registrarFalha(login + "_token");
                JOptionPane.showMessageDialog(this, "Token inválido. Tente novamente.");
            }
        });

        botaoReenvioToken.addActionListener(e -> {
            Usuario usuario = Sessao.getUsuarioLogado();

            if (usuario != null) {
                String token = TokenService.gerarToken();
                EmailService.enviarEmail(usuario.getEmail(), "Aqui está seu código para validação em duas etapas: " + token);
                JOptionPane.showMessageDialog(this, "Token enviado para o e-mail: " + usuario.getEmail());
            } else {
                JOptionPane.showMessageDialog(this, "Erro: nenhum usuário logado.");
            }
        });
    }
}
