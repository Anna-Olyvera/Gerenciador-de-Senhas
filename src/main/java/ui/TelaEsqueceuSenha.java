package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;

public class TelaEsqueceuSenha extends JPanel{
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JButton botaoInfoEmailSms;

    public TelaEsqueceuSenha(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        // ADICIONANDO OS CAMPOS
        add(new JLabel("E-mail: "));
        campoEmail = new JTextField();
        add(campoEmail);

        add(new JLabel("Telefone: "));
        campoTelefone = new JTextField();
        add(campoTelefone);

        // BOTÃO DE ACESSO
        add(new JButton());
        botaoInfoEmailSms = new JButton("Confirmar");
        add(botaoInfoEmailSms);
    }
}
