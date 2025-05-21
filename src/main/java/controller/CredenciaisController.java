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

    // ADICIONA UMA CREDENCIAL AO FIREBASE
    public boolean adicionarCredencial(String nomeCredencial, String senhaCredencial) {
        String loginUsuario = Sessao.getUsuarioLogado().getLogin();

        if (!validador.validarNomeCredencial(nomeCredencial)) {
            System.out.println("Erro: Nome da credencial inválido.");
            return false;
        }

        if (!validador.validarSenhaCredencial(senhaCredencial)) {
            System.out.println("Erro: Senha da credencial inválida.");
            return false;
        }

        // VERIFICA SE JÁ EXISTE COM ESSE NOME NO FIREBASE
        Credenciais existente = firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
        if (existente != null) {
            System.out.println("Erro: Já existe uma credencial com esse nome.");
            return false;
        }

        Credenciais nova = new Credenciais(nomeCredencial, senhaCredencial);
        firebaseService.salvarCredencial(loginUsuario, nova);
        return true;
    }

    // ATUALIZA A SENHA DE UMA CREDENCIAL NO FIREBASE
    public boolean atualizarSenhaCredencial(String nomeCredencial, String novaSenhaCredencial) {
        String loginUsuario = Sessao.getUsuarioLogado().getLogin();

        if (!validador.validarSenhaCredencial(novaSenhaCredencial)) {
            System.out.println("Erro: Nova senha inválida.");
            return false;
        }

        Credenciais existente = firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
        if (existente == null) {
            System.out.println("Erro: Credencial não encontrada.");
            return false;
        }

        existente.setSenhaCredencial(novaSenhaCredencial);
        firebaseService.salvarCredencial(loginUsuario, existente);
        return true;
    }

    // REMOVE UMA CREDENCIAL DO FIREBASE
    public boolean removerCredencial(String nomeCredencial) {
        String loginUsuario = Sessao.getUsuarioLogado().getLogin();
        return firebaseService.removerCredencial(loginUsuario, nomeCredencial);
    }

    // LISTA TODAS AS CREDENCIAIS DO USUÁRIO
    public List<Credenciais> listarCredenciais() {
        String loginUsuario = Sessao.getUsuarioLogado().getLogin();
        return firebaseService.buscarCredenciais(loginUsuario);
    }

    // BUSCA UMA CREDENCIAL PELO NOME
    public Credenciais buscarCredencial(String nomeCredencial) {
        String loginUsuario = Sessao.getUsuarioLogado().getLogin();
        return firebaseService.buscarCredencial(loginUsuario, nomeCredencial);
    }
}