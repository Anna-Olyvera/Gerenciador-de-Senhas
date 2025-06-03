package ui;

import controller.TentativaLoginController;
import controller.UsuarioController;
import core.Sessao;
import core.Usuario;
import javax.swing.*;
import java.awt.*;
import service.TokenService;

public class TelaEsqueceuSenhaCompleta extends JPanel {
    private CardLayout cardLayoutInterno;
    private JPanel containerInterno;

    // Campos etapa 1
    private JTextField campoEmail;
    private JTextField campoTelefone;
    private JButton botaoConfirmarDados;
    private JButton botaoVoltarDados;

    // Campos etapa 2
    private JTextField campoToken;
    private JButton botaoValidarToken;
    private JButton botaoReenvioToken;
    private JButton botaoVoltarToken;

    private TentativaLoginController tentativaTokenController = new TentativaLoginController();
    private UsuarioController usuarioController;

    public TelaEsqueceuSenhaCompleta(CardLayout layoutPai, JPanel containerPai, UsuarioController usuarioController) {
        this.usuarioController = usuarioController;

        setLayout(new BorderLayout());

        this.cardLayoutInterno = new CardLayout();
        this.containerInterno = new JPanel(cardLayoutInterno);

        containerInterno.add(criarPainelDados(layoutPai, containerPai), "etapaDados");
        containerInterno.add(criarPainelValidacaoToken(layoutPai, containerPai), "etapaToken");

        add(containerInterno, BorderLayout.CENTER);

        cardLayoutInterno.show(containerInterno, "etapaDados");
    }

    private JPanel criarPainelDados(CardLayout layoutPai, JPanel containerPai) {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        painel.add(new JLabel("E-mail:"));
        campoEmail = new JTextField();
        painel.add(campoEmail);

        painel.add(new JLabel("Telefone:"));
        campoTelefone = new JTextField();
        painel.add(campoTelefone);

        botaoConfirmarDados = new JButton("Confirmar");
        painel.add(botaoConfirmarDados);

        botaoVoltarDados = new JButton("Voltar");
        painel.add(botaoVoltarDados);

        botaoVoltarDados.addActionListener(e -> {
            // Limpa campos ao voltar para login
            campoEmail.setText("");
            campoTelefone.setText("");
            cardLayoutInterno.show(containerInterno, "etapaDados");
            layoutPai.show(containerPai, "telaLogin");
        });

        botaoConfirmarDados.addActionListener(e -> {
            String email = campoEmail.getText().trim();
            String telefone = campoTelefone.getText().trim();

            if (email.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha o email e telefone.");
                return;
            }

            final JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Aguarde...", true);
            JLabel label = new JLabel("Enviando token, por favor aguarde...");
            label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            dialog.add(label);
            dialog.pack();
            dialog.setLocationRelativeTo(this);

            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() {
                    return usuarioController.enviarTokenEsqueceuSenha(email, telefone);
                }

                @Override
                protected void done() {
                    dialog.dispose();
                    try {
                        boolean sucesso = get();
                        if (sucesso) {
                            JOptionPane.showMessageDialog(TelaEsqueceuSenhaCompleta.this,
                                    "Token enviado! Verifique seu email ou SMS.");
                            cardLayoutInterno.show(containerInterno, "etapaToken");
                        } else {
                            JOptionPane.showMessageDialog(TelaEsqueceuSenhaCompleta.this,
                                    "Erro ao enviar token. Verifique os dados e tente novamente.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TelaEsqueceuSenhaCompleta.this,
                                "Erro inesperado: " + ex.getMessage());
                    }
                }
            };

            worker.execute();
            dialog.setVisible(true);
        });

        return painel;
    }

    private JPanel criarPainelValidacaoToken(CardLayout layoutPai, JPanel containerPai) {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));

        painel.add(new JLabel("Token:"));
        campoToken = new JTextField();
        painel.add(campoToken);

        botaoValidarToken = new JButton("Confirmar");
        painel.add(botaoValidarToken);

        botaoVoltarToken = new JButton("Voltar");
        painel.add(botaoVoltarToken);

        botaoReenvioToken = new JButton("Reenviar Token");
        painel.add(botaoReenvioToken);

        botaoVoltarToken.addActionListener(e -> {
            // Limpa token ao voltar para etapa de dados
            campoToken.setText("");
            cardLayoutInterno.show(containerInterno, "etapaDados");
        });

        botaoValidarToken.addActionListener(e -> {
            String tokenDigitado = campoToken.getText().trim();

            if (tentativaTokenController.estaBloqueado("esqueceuSenha_token")) {
                JOptionPane.showMessageDialog(this, "Você excedeu o número de tentativas. Tente novamente em alguns minutos.");
                return;
            }
            if (TokenService.precisaReenviarToken()) {
                JOptionPane.showMessageDialog(this, "O token expirou. Por favor, solicite um novo.");
                return;
            }
            if (TokenService.validarToken(tokenDigitado)) {
                JOptionPane.showMessageDialog(this, "Token válido!");
                tentativaTokenController.limparTentativas("esqueceuSenha_token");

                String email = campoEmail.getText().trim();
                Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);

                if (usuario == null) {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
                    return;
                }

                Sessao.setUsuarioLogado(usuario);

                // Limpar todos os campos após sucesso
                campoEmail.setText("");
                campoTelefone.setText("");
                campoToken.setText("");

                boolean telaCofreExiste = false;
                for (Component comp : containerPai.getComponents()) {
                    if (comp instanceof TelaCofreDigital) {
                        telaCofreExiste = true;
                        break;
                    }
                }
                if (!telaCofreExiste) {
                    TelaCofreDigital telaCofre = new TelaCofreDigital(layoutPai, containerPai, usuarioController, usuario);
                    containerPai.add(telaCofre, "telaCofreDigital");
                }

                layoutPai.show(containerPai, "telaCofreDigital");
            } else {
                tentativaTokenController.registrarFalha("esqueceuSenha_token");
                JOptionPane.showMessageDialog(this, "Token inválido. Tente novamente.");
            }
        });

        botaoReenvioToken.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Por favor, volte e confirme os dados para reenviar o token.");
            cardLayoutInterno.show(containerInterno, "etapaDados");
        });

        return painel;
    }
}
