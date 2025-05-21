package ui;

import controller.UsuarioController;
import core.Sessao; // <- IMPORTAÇÃO NECESSÁRIA
import core.Usuario;
import service.TokenService;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaValidacaoDuasEtapas extends JPanel {
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

        // EVENTO: envio de token por email e direcionamento para tela de inserção de token
        botaoEmail.addActionListener(e -> {
            String token = TokenService.gerarToken();

            Usuario usuario = Sessao.getUsuarioLogado(); // <- PEGA o usuário logado da sessão

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Token enviado para o e-mail: " + usuario.getEmail() + "\nToken: " + token);
                layout.show(container, "telaValidacaoToken");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: nenhum usuário logado.");
            }
        });

        // EVENTO: envio de token por SMS e direcionamento para tela de inserção de token
        botaoSms.addActionListener(e -> {
            String token = TokenService.gerarToken();

            Usuario usuario = Sessao.getUsuarioLogado(); // <- PEGA o usuário logado da sessão

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Token enviado por SMS: " + usuario.getTelefone() + "\nToken: " + token);
                layout.show(container, "telaValidacaoToken");
            } else {
                JOptionPane.showMessageDialog(this, "Erro: nenhum usuário logado.");
            }
        });
    }
}