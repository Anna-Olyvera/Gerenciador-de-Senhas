package ui;

import controller.CredenciaisController;
import core.ValidadorCredenciais;

import javax.swing.*;
import java.awt.*;

public class TelaAdicionarServico extends JPanel {
    private JTextField campoNomeCredencial;
    private JTextField campoSenhaCredencial;
    private JButton botaoConfirmar;

    public TelaAdicionarServico(CardLayout layout, JPanel container, CredenciaisController controller) {
        // DEFINE LAYOUT DA TELA
        setLayout(new GridLayout(3, 2, 10, 10));

        // CAMPO: NOME DO SERVIÇO
        add(new JLabel("Nome do Serviço: "));
        campoNomeCredencial = new JTextField();
        add(campoNomeCredencial);

        // CAMPO: SENHA DO SERVIÇO
        add(new JLabel("Senha do Serviço: "));
        campoSenhaCredencial = new JTextField();
        add(campoSenhaCredencial);

        // BOTÃO DE CONFIRMAÇÃO
        add(new JLabel()); 
        botaoConfirmar = new JButton("Confirmar");
        add(botaoConfirmar);

        // INSTANCIA VALIDADOR
        ValidadorCredenciais validador = new ValidadorCredenciais();

        // EVENTO DO BOTÃO CONFIRMAR
        botaoConfirmar.addActionListener(e -> {
            // OBTÉM OS DADOS DOS CAMPOS
            String nomeCredencial = campoNomeCredencial.getText().trim();
            String senhaCredencial = campoSenhaCredencial.getText().trim();

            // VALIDAÇÃO DO NOME
            if (!validador.validarNomeCredencial(nomeCredencial)) {
                JOptionPane.showMessageDialog(this, "Nome do serviço inválido.\nUse até 50 caracteres, letras, números, traços ou pontos.");
                return;
            }

            // VALIDAÇÃO DA SENHA
            if (!validador.validarSenhaCredencial(senhaCredencial)) {
                JOptionPane.showMessageDialog(this, "Senha inválida.\nDeve conter entre 6 e 50 caracteres.");
                return;
            }

            // ENVIA OS DADOS AO FIREBASE VIA O CONTROLLER
            boolean sucesso = controller.adicionarCredencial(nomeCredencial, senhaCredencial);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Credencial adicionada com sucesso!");
                limparCampos();
                layout.show(container, "telaCofreDigital"); 
            } 
            else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar credencial. Tente novamente.");
            }
        });
    }

    // MÉTODO PARA LIMPAR OS CAMPOS APÓS ADICIONAR
    private void limparCampos() {
        campoNomeCredencial.setText("");
        campoSenhaCredencial.setText("");
    }
}