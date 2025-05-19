package main;

import controller.UsuarioController;
import ui.TelaCadastroUsuario;
import ui.TelaCofreDigital;
import ui.TelaLogin;

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
        TelaCadastroUsuario telaCadastro = new TelaCadastroUsuario(layout, container, usuarioController);
        TelaCofreDigital telaCofre = new TelaCofreDigital(layout, container, usuarioController);

        container.add(telaLogin, "telaLogin");
        container.add(telaCadastro, "telaCadastro");
        container.add(telaCofre, "telaCofreDigital");

        frame.add(container);
        frame.setVisible(true);

        layout.show(container, "telaLogin");
    }
}