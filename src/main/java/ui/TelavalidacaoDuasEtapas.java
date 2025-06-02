package ui;

import controller.UsuarioController;
import core.Sessao; // <- IMPORTAÇÃO NECESSÁRIA
import core.Usuario;
import service.TokenService;
import service.EmailService;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;

public class TelaValidacaoDuasEtapas extends JPanel {
    private JButton botaoEmail;

    public TelaValidacaoDuasEtapas(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));
        
        // BOTÕES PARA ESCOLHA DA FORMA DE ENVIO DO TOKEN
        add(new JLabel());
        botaoEmail = new JButton("Email");
        add(botaoEmail);

        // EVENTO: envio de token por email e direcionamento para tela de inserção de token
        botaoEmail.addActionListener(e -> {
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
