package ui;

import controller.CredenciaisController;
import core.ValidadorCredenciais;

import javax.swing.*;
import java.awt.*;

public class TelaAdicionarServico extends JPanel {
    private TelaCofreDigital telaCofre;
    private JTextField campoNomeCredencial;
    private JTextField campoSenhaCredencial;
    private JButton botaoConfirmar;

    public TelaAdicionarServico(CardLayout layout, JPanel container, CredenciaisController controller, TelaCofreDigital telaCofre) {
        this.telaCofre = telaCofre;
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Nome do Serviço: "));
        campoNomeCredencial = new JTextField();
        add(campoNomeCredencial);

        add(new JLabel("Senha do Serviço: "));
        campoSenhaCredencial = new JTextField();
        add(campoSenhaCredencial);

        add(new JLabel());
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
                telaCofre.carregarCredenciais();
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
