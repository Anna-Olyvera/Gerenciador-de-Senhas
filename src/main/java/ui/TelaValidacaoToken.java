package ui;

import controller.UsuarioController;
import service.TokenService;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;

public class TelaValidacaoToken extends JPanel{
    private JTextField campoToken;
    private JButton botaoValidarToken;

    public TelaValidacaoToken(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        // ADICIONANDO CAMPOS
        add(new JLabel("Token: "));
        campoToken = new JTextField();
        add(campoToken);

        // BOTÃO: acesso a página principal
        add(new JButton());
        botaoValidarToken = new JButton("Confirmar");
        add(botaoValidarToken);


        // EVENTO: validação de token e direcionamento para tela principal
        botaoValidarToken.addActionListener(e -> {
            String tokenDigitado = campoToken.getText();

            if (TokenService.validarToken(tokenDigitado)) {
                JOptionPane.showMessageDialog(this, "Token válido!");
                layout.show(container, "telaCofreDigital");
            }
            else {
                JOptionPane.showMessageDialog(this, "Token inválido. Tente novamente.");
            }
        });
    }
}