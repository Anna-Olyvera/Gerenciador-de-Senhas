package controller;

import core.Credenciais;
import core.Sessao;
import core.ValidadorCredenciais;
import service.FirebaseService;

import java.util.List;

public class CredenciaisController {
    private ValidadorCredenciais validador;
    private FirebaseService firebaseService;

    public CredenciaisController() {
        this.validador = new ValidadorCredenciais();
        this.firebaseService = new FirebaseService();
    }

    // Método privado para pegar login com checagem de null
    private String getLoginUsuario() {
        if (Sessao.getUsuarioLogado() != null && Sessao.getUsuarioLogado().getLogin() != null) {
            return Sessao.getUsuarioLogado().getLogin();
        }
        return null;
    }

    // ADICIONA UMA CREDENCIAL AO FIREBASE
    public boolean adicionarCredencial(String nomeCredencial, String senhaCredencial) {
        String loginUsuario = getLoginUsuario();
        if (loginUsuario == null) {
            return false;
        }

        if (!validador.validarNomeCredencial(nomeCredencial)) {
            return false;
        }

        if (!validador.validarSenhaCredencial(senhaCredencial)) {
            return false;
        }

        try {
            Credenciais existente = firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
            if (existente != null) {
                return false;
            }

            Credenciais nova = new Credenciais(nomeCredencial, senhaCredencial);
            firebaseService.salvarCredencial(loginUsuario, nova);
            return true;
        } catch (Exception e) {
            // Pode logar erro aqui
            return false;
        }
    }

    // ATUALIZA A SENHA DE UMA CREDENCIAL NO FIREBASE
    public boolean atualizarSenhaCredencial(String nomeCredencial, String novaSenhaCredencial) {
        String loginUsuario = getLoginUsuario();
        if (loginUsuario == null) {
            return false;
        }

        if (!validador.validarSenhaCredencial(novaSenhaCredencial)) {
            return false;
        }

        try {
            Credenciais existente = firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
            if (existente == null) {
                return false;
            }

            existente.setSenhaCredencial(novaSenhaCredencial);
            firebaseService.salvarCredencial(loginUsuario, existente);
            return true;
        } catch (Exception e) {
            // Pode logar erro aqui
            return false;
        }
    }

    // REMOVE UMA CREDENCIAL DO FIREBASE
    public boolean removerCredencial(String nomeCredencial) {
        String loginUsuario = getLoginUsuario();
        if (loginUsuario == null) {
            return false;
        }

        try {
            return firebaseService.removerCredencial(loginUsuario, nomeCredencial);
        } catch (Exception e) {
            // Pode logar erro aqui
            return false;
        }
    }

    // LISTA TODAS AS CREDENCIAIS DO USUÁRIO
    public List<Credenciais> listarCredenciais() {
        String loginUsuario = getLoginUsuario();
        if (loginUsuario == null) {
            return java.util.Collections.emptyList();
        }

        try {
            return firebaseService.buscarCredenciais(loginUsuario);
        } catch (Exception e) {
            // Pode logar erro aqui
            return java.util.Collections.emptyList();
        }
    }

    // BUSCA UMA CREDENCIAL PELO NOME
    public Credenciais buscarCredencial(String nomeCredencial) {
        String loginUsuario = getLoginUsuario();
        if (loginUsuario == null) {
            return null;
        }

        try {
            return firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
        } catch (Exception e) {
            // Pode logar erro aqui
            return null;
        }
    }
}
