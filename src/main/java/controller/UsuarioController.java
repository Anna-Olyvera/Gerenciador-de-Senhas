package controller;

import core.Usuario;
import core.ValidadorUsuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private List<Usuario> usuarios = new ArrayList<>();
    private ValidadorUsuario validador = new ValidadorUsuario();

    // FUNÇÃO DE CADASTRO DE USUÁRIO
    public boolean cadastroUsuario(String login, String email, String telefone, String chaveMestra) {
        if (!validador.validarLogin(login)) {
            System.out.println("Erro: login é obrigatório.");
            return false;
        }
        
        if (!validador.validarEmailOuTelefone(email, telefone)) {
            System.out.println("Erro: informe ao menos e-mail ou telefone.");
            return false;
        }
        
        if (!validador.validarChaveMestra(chaveMestra)) {
            System.out.println("Erro: chave mestra é obrigatória.");
            return false;
        }

        Usuario novoUsuario = new Usuario(login, email, telefone, chaveMestra);
        usuarios.add(novoUsuario);
        return true;
    }

    // FUNÇÃO AUTENTICAÇÃO DE USUÁRIO
    public Usuario autenticar(String login, String chaveMestra) {
        for (Usuario u : usuarios) {
            if (u.getLogin().equals(login) && u.getChaveMestra().equals(chaveMestra)) {
                return u;
            }
        }
        return null;
    }
}