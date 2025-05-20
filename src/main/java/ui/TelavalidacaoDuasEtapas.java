package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaValidacaoDuasEtapas extends JPanel{
    private JButton botaoEmail;
    private JButton botaoSms;

    public TelaValidacaoDuasEtapas(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));
        
        // BOTÕES PARA ESCOLHA DA FORMA DE ENVIO DO TOKEN
        add(new JLabel());
        botaoEmail = new JButton("Email");
        add(botaoEmail);

        add(new JLabel());
        botaoSms = new JButton("SMS");
        add(botaoSms);
    }
}