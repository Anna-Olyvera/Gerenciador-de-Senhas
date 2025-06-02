package main;

import controller.UsuarioController ;
import ui.TelaCadastroUsuario;
import ui.TelaLogin;
import ui.TelaValidacaoToken;
import ui.TelaValidacaoDuasEtapas;
import ui.TelaEsqueceuSenha;

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
        TelaValidacaoToken telaValidaToken = new TelaValidacaoToken(layout, container, usuarioController);
        TelaValidacaoDuasEtapas telaValidacaoDuasEtapas = new TelaValidacaoDuasEtapas(layout, container, usuarioController);
        TelaEsqueceuSenha telaEsqueceuSenha = new TelaEsqueceuSenha(layout, container, usuarioController);
        
        container.add(telaLogin, "telaLogin");
        container.add(telaCadastroUsuario, "telaCadastroUsuario");
        container.add(telaValidaToken, "telaValidacaoToken");
        container.add(telaValidacaoDuasEtapas, "telaValidacaoDuasEtapas");
        container.add(telaEsqueceuSenha, "telaEsqueceuSenha");



        frame.add(container);
        frame.setVisible(true);

        layout.show(container, "telaLogin");
    }
}