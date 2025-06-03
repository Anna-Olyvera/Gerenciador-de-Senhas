package ui;

import controller.UsuarioController;
import core.Sessao;
import core.Usuario;
import service.TokenService;
import service.EmailService;

import javax.swing.*;
import java.awt.*;

public class TelaValidacaoDuasEtapas extends JPanel {
    private JButton botaoEmail;
    private JButton botaoVoltar;

    public TelaValidacaoDuasEtapas(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Estilo visual
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Validação em Duas Etapas");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        botaoEmail = new JButton("Receber Token por Email");
        botaoEmail.setPreferredSize(new Dimension(220, 35));
        gbc.gridy++;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(botaoEmail, gbc);

        botaoVoltar = new JButton("Voltar");
        botaoVoltar.setPreferredSize(new Dimension(220, 35));
        gbc.gridy++;
        add(botaoVoltar, gbc);

        // Ação: Enviar token por e-mail
        botaoEmail.addActionListener(e -> {
            Usuario usuario = Sessao.getUsuarioLogado();

            if (usuario != null) {
                final JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Aguarde...", true);
                JLabel label = new JLabel("Enviando token por e-mail, por favor aguarde...");
                label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                dialog.add(label);
                dialog.pack();
                dialog.setLocationRelativeTo(this);

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    private String token;

                    @Override
                    protected Void doInBackground() {
                        token = TokenService.gerarToken();
                        EmailService.enviarEmail(usuario.getEmail(),
                            "Aqui está seu código para validação em duas etapas: " + token);
                        return null;
                    }

                    @Override
                    protected void done() {
                        dialog.dispose();
                        JOptionPane.showMessageDialog(TelaValidacaoDuasEtapas.this,
                            "Token enviado para o e-mail: " + usuario.getEmail());
                        layout.show(container, "telaValidacaoToken");
                    }
                };

                worker.execute();
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro: nenhum usuário logado.");
            }
        });

        // Ação: Voltar
        botaoVoltar.addActionListener(e -> {
            layout.show(container, "telaLogin");
        });
    }
}
