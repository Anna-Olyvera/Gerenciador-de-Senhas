package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaValidacaoToken extends JPanel{
    private JTextField campoToken;
    private JButton botaoValidarToken;

    public TelaValidacaoToken(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        // ADICIONANDO CAMPOS
        add(new JLabel("Token: "));
        campoToken = new JTextField();
        add(campoToken);

        // BOTÃO DE ACESSO
        add(new JButton());
        botaoValidarToken = new JButton("Confirmar");
        add(botaoValidarToken);
    }
}