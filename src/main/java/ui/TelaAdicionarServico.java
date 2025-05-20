package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaAdicionarServico extends JPanel{
    private JTextField campoNomeCredencial;
    private JTextField campoSenhaCredencial;
    private JButton botaoConfirmar;

    public TelaAdicionarServico(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));

        // ADICIONANDO OS CAMPOS
        add(new JLabel("Nome do Serviço: "));
        campoNomeCredencial = new JTextField();
        add(campoNomeCredencial);

        add(new JLabel("Senha do Serviço: "));
        campoSenhaCredencial = new JTextField();
        add(campoSenhaCredencial);

        // BOTÃO DE ACESSO
        add(new JLabel());
        botaoConfirmar = new JButton("Confirmar");
        add(botaoConfirmar);
    }
}
