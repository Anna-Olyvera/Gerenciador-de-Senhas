package controller;

import core.Usuario;
import core.ValidadorUsuario;
import security.Senha;
import service.EmailService;
import service.FirebaseService;
import service.TokenService;

public class UsuarioController {
    private ValidadorUsuario validador = new ValidadorUsuario();
    private FirebaseService firebaseService = new FirebaseService();
    private TentativaLoginController tentativaController = new TentativaLoginController();

    public boolean removerCredencial(String loginUsuario, String nomeCredencial) {
        if (loginUsuario == null || nomeCredencial == null) {
            return false;
        }
        return firebaseService.removerCredencial(loginUsuario, nomeCredencial);
    }

    public String cadastroUsuario(String login, String email, String telefone, String chaveMestraPura) {
        if (!validador.validarLogin(login)) {
            return "Login é obrigatório.";
        }

        if (!validador.validarEmailOuTelefone(email, telefone)) {
            return "Informe ao menos e-mail ou telefone.";
        }

        if (!validador.validarChaveMestra(chaveMestraPura)) {
            return "Chave mestra é obrigatória.";
        }

        if (firebaseService.usuarioExiste(login)) {
            return "Usuário já existe.";
        }

        try {
            String salt = Senha.gerarSalt();
            String hash = Senha.gerarHash(chaveMestraPura, salt);

            Usuario novoUsuario = new Usuario(login, email, telefone, salt, hash);
            firebaseService.salvarUsuario(novoUsuario);
            return null; // sucesso
        } catch (Exception e) {
            // Ideal: usar logger para gravar erro
            System.err.println("Erro interno ao cadastrar usuário: " + e.getMessage());
            return "Erro interno ao cadastrar usuário.";
        }
    }

    public Usuario autenticar(String login, String chaveMestraPura) {
        if (login == null || chaveMestraPura == null) {
            return null;
        }

        if (tentativaController.estaBloqueado(login)) {
            System.out.println("Usuário bloqueado temporariamente.");
            return null;
        }

        Usuario usuarioFirebase = firebaseService.buscarUsuarioPorLogin(login);
        if (usuarioFirebase != null) {
            try {
                String hashCalculado = Senha.gerarHash(chaveMestraPura, usuarioFirebase.getSalt());
                if (hashCalculado.equals(usuarioFirebase.getHash())) {
                    tentativaController.limparTentativas(login);
                    return usuarioFirebase;
                }
            } catch (Exception e) {
                System.err.println("Erro ao calcular hash para autenticação: " + e.getMessage());
            }
        }

        tentativaController.registrarFalha(login);
        return null;
    }

    public boolean estaBloqueado(String login) {
        if (login == null) return false;
        return tentativaController.estaBloqueado(login);
    }

    public boolean enviarTokenEsqueceuSenha(String email, String telefone) {
        Usuario usuario = buscarUsuarioPorEmailOuTelefone(email, telefone);
        if (usuario == null) {
            return false;
        }

        String token = TokenService.gerarToken();
        long timestamp = System.currentTimeMillis();

        firebaseService.salvarTokenEsqueceuSenha(usuario.getLogin(), token, timestamp);

        String corpoEmail = "Seu código para resetar a senha é: " + token + ". Este código é válido por 10 minutos.";

        try {
            EmailService.enviarEmail(usuario.getEmail(), corpoEmail);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            return false;
        }
    }

    private Usuario buscarUsuarioPorEmailOuTelefone(String email, String telefone) {
        Usuario usuarioEmail = firebaseService.buscarUsuarioPorEmail(email);
        if (usuarioEmail != null) {
            return usuarioEmail;
        }
        return firebaseService.buscarUsuarioPorTelefone(telefone);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return firebaseService.buscarUsuarioPorEmail(email);
    }
}
