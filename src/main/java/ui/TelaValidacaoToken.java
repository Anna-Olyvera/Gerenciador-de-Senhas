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
    private TentativaLoginController tentativaTokenController = new TentativaLoginController();

    public TelaValidacaoToken(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Token: "));
        campoToken = new JTextField();
        add(campoToken);

        add(new JLabel());
        botaoValidarToken = new JButton("Confirmar");
        add(botaoValidarToken);

        add(new JLabel());
        botaoReenvioToken = new JButton("Reenviar Token");
        add(botaoReenvioToken);

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
                layout.show(container, "telaCofreDigital");
            } 
            else {
                tentativaTokenController.registrarFalha(login + "_token");
                JOptionPane.showMessageDialog(this, "Token inválido. Tente novamente.");
            }
        });

        botaoReenvioToken.addActionListener(e -> {
            Usuario usuario = Sessao.getUsuarioLogado(); // <- PEGA o usuário logado da sessão

            if (usuario != null) {
                String token = TokenService.gerarToken();
                EmailService.enviarEmail(usuario.getEmail(), "Aqui está seu código para validação em duas etapas: " + token);
                JOptionPane.showMessageDialog(this, "Token enviado para o e-mail: " + usuario.getEmail() + "\nToken: " + token);
                layout.show(container, "telaValidacaoToken");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: nenhum usuário logado.");
            }
        });
    }
}