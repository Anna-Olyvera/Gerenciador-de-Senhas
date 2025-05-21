package main;

import controller.UsuarioController;
import ui.TelaCadastroUsuario;
import ui.TelaLogin;
import ui.TelaCofreDigital;
import ui.TelaValidacaoToken;
import ui.TelaValidacaoDuasEtapas;
import ui.TelaEsqueceuSenha;
import ui.TelaAdicionarServico;
import ui.TelaEditarServico;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cofre Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        CardLayout layout = new CardLayout();
        JPanel container = new JPanel(layout);

        UsuarioController usuarioController = new UsuarioController();

        TelaLogin telaLogin = new TelaLogin(layout, container, usuarioController);
        TelaCadastroUsuario telaCadastroUsuario = new TelaCadastroUsuario(layout, container, usuarioController);
        TelaCofreDigital telaCofreDigital = new TelaCofreDigital(layout, container, usuarioController);
        TelaValidacaoToken telaValidaToken = new TelaValidacaoToken(layout, container, usuarioController);
        TelaValidacaoDuasEtapas telaValidacaoDuasEtapas = new TelaValidacaoDuasEtapas(layout, container, usuarioController);
        TelaEsqueceuSenha telaEsqueceuSenha = new TelaEsqueceuSenha(layout, container, usuarioController);
        TelaAdicionarServico telaAdicionarServico = new TelaAdicionarServico(layout, container, usuarioController);
        TelaEditarServico telaEditarServico = new TelaEditarServico(layout, container, usuarioController);

        container.add(telaLogin, "telaLogin");
        container.add(telaCadastroUsuario, "telaCadastroUsuario");
        container.add(telaCofreDigital, "telaCofreDigital");
        container.add(telaValidaToken, "telaValidacaoToken");
        container.add(telaValidacaoDuasEtapas, "telaValidacapDuasEtapas");
        container.add(telaEsqueceuSenha, "telaEsquceuSenha");
        container.add(telaAdicionarServico, "telaAdicionarServico");
        container.add(telaEditarServico, "telaEditarServico");


        frame.add(container);
        frame.setVisible(true);

        layout.show(container, "telaLogin");
    }
}