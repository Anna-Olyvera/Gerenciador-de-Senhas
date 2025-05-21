package controller;

import core.Usuario;
import core.ValidadorUsuario;
import service.FirebaseService;

public class UsuarioController {
    private ValidadorUsuario validador = new ValidadorUsuario();
    private FirebaseService firebaseService = new FirebaseService();

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

        // Criar usuário e salvar no Firebase
        Usuario novoUsuario = new Usuario(login, email, telefone, chaveMestra);
        firebaseService.salvarUsuario(novoUsuario);
        return true;
    }

    // FUNÇÃO AUTENTICAÇÃO DE USUÁRIO
    public Usuario autenticar(String login, String chaveMestra) {
        Usuario usuarioFirebase = firebaseService.buscarUsuarioPorLogin(login);

        if (usuarioFirebase != null && usuarioFirebase.getChaveMestra().equals(chaveMestra)) {
            return usuarioFirebase;
        }

        return null;
    }
}