package ui;

import controller.CredenciaisController;
import core.ValidadorCredenciais;

import javax.swing.*;
import java.awt.*;

public class TelaAdicionarServico extends JPanel {
    private final TelaCofreDigital telaCofre;  // final para uso no listener
    private final JTextField campoNomeCredencial;
    private final JTextField campoSenhaCredencial;
    private final JButton botaoConfirmar;

    public TelaAdicionarServico(CardLayout layout, JPanel container, CredenciaisController controller, TelaCofreDigital telaCofre) {
        this.telaCofre = telaCofre;
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Nome do Serviço: "));
        campoNomeCredencial = new JTextField();
        add(campoNomeCredencial);

        add(new JLabel("Senha do Serviço: "));
        campoSenhaCredencial = new JTextField();
        add(campoSenhaCredencial);

        add(new JLabel()); // espaço vazio para alinhar o botão
        botaoConfirmar = new JButton("Confirmar");
        add(botaoConfirmar);

        ValidadorCredenciais validador = new ValidadorCredenciais();

        botaoConfirmar.addActionListener(e -> {
            String nomeCredencial = campoNomeCredencial.getText().trim();
            String senhaCredencial = campoSenhaCredencial.getText().trim();

            if (!validador.validarNomeCredencial(nomeCredencial)) {
                JOptionPane.showMessageDialog(this, "Nome do serviço inválido.\nUse até 50 caracteres, letras, números, traços ou pontos.");
                return;
            }

            if (!validador.validarSenhaCredencial(senhaCredencial)) {
                JOptionPane.showMessageDialog(this, "Senha inválida.\nDeve conter entre 6 e 50 caracteres.");
                return;
            }

            boolean sucesso = controller.adicionarCredencial(nomeCredencial, senhaCredencial);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Credencial adicionada com sucesso!");
                limparCampos();

                // Usa this.telaCofre para garantir acesso ao campo
                this.telaCofre.carregarCredenciais();

                layout.show(container, "telaCofreDigital");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar credencial. Tente novamente.");
            }
        });
    }

    private void limparCampos() {
        campoNomeCredencial.setText("");
        campoSenhaCredencial.setText("");
    }
}
