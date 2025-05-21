package ui;

import controller.UsuarioController;

// IMPORTS RELACIONADOS A CRIAÇÃO DA INTERFACE GRÁFICA
import javax.swing.*;
import java.awt.*;

public class TelaEditarServico extends JPanel{
    
    public TelaEditarServico(CardLayout layout, JPanel container, UsuarioController controller) {
        setLayout(new GridLayout(3, 2, 10, 10));
    }
}