package controller;

import core.Usuario;
import core.ValidadorUsuario;
import security.Senha;
import service.FirebaseService;

public class UsuarioController {
    private ValidadorUsuario validador = new ValidadorUsuario();
    private FirebaseService firebaseService = new FirebaseService();

    public boolean removerCredencial(String loginUsuario, String nomeCredencial) {
    return firebaseService.removerCredencial(loginUsuario, nomeCredencial);
    }

    public boolean cadastroUsuario(String login, String email, String telefone, String chaveMestraPura) {
        if (!validador.validarLogin(login)) {
            System.out.println("Erro: login é obrigatório.");
            return false;
        }

        if (!validador.validarEmailOuTelefone(email, telefone)) {
            System.out.println("Erro: informe ao menos e-mail ou telefone.");
            return false;
        }

        if (!validador.validarChaveMestra(chaveMestraPura)) {
            System.out.println("Erro: chave mestra é obrigatória.");
            return false;
        }

        try {
            String salt = Senha.gerarSalt();
            String hash = Senha.gerarHash(chaveMestraPura, salt);

            Usuario novoUsuario = new Usuario(login, email, telefone, salt, hash);
            firebaseService.salvarUsuario(novoUsuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // VERIFICAÇÃO DE TENTATIVAS EXCESSIVAS
    private TentativaLoginController tentativaController = new TentativaLoginController();

    public Usuario autenticar(String login, String chaveMestraPura) {
        if (tentativaController.estaBloqueado(login)) {
            System.out.println("Usuário bloqueado temporariamente.");
            return null;
        }

        Usuario usuarioFirebase = firebaseService.buscarUsuarioPorLogin(login);
        if (usuarioFirebase != null) {
            try {
                String hashCalculado = Senha.gerarHash(chaveMestraPura, usuarioFirebase.getSalt());
                if (hashCalculado.equals(usuarioFirebase.getHash())) {
                    tentativaController.limparTentativas(login); // sucesso: zera tentativas
                    return usuarioFirebase;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tentativaController.registrarFalha(login); // falha: conta tentativa
        return null;
    }

    public boolean estaBloqueado(String login) {
        return tentativaController.estaBloqueado(login);
    }
}