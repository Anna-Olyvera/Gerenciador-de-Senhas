package controller;

import core.Credenciais;
import core.ValidadorCredenciais;

import java.util.ArrayList;
import java.util.List;

public class CredenciaisController {
    private List<Credenciais> listaCredenciais;
    private ValidadorCredenciais validador;

    public CredenciaisController() {
        this.listaCredenciais = new ArrayList<>();
        this.validador = new ValidadorCredenciais();
    }

    // ADICIONA UMA CREDENCIAL
    public boolean adicionarCredencial(String nome, String senha) {
        if (!validador.validarNomeCredencial(nome)) {
            System.out.println("Erro: Nome da credencial inválido.");
            return false;
        }

        if (!validador.validarSenhaCredencial(senha)) {
            System.out.println("Erro: Senha da credencial inválida.");
            return false;
        }

        // VERIFICA SE JÁ EXISTE UMA CREDENCIAL COM MESMO NOME
        for (Credenciais c : listaCredenciais) {
            if (c.getNomeCredencial().equalsIgnoreCase(nome)) {
                System.out.println("Erro: Já existe uma credencial com esse nome.");
                return false;
            }
        }

        listaCredenciais.add(new Credenciais(nome, senha));
        return true;
    }

    // ATUALIZA A SENHA DA CREDENCIAL
    public boolean atualizarSenhaCredencial(String nome, String novaSenha) {
        for (Credenciais c : listaCredenciais) {
            if (c.getNomeCredencial().equalsIgnoreCase(nome)) {
                if (!validador.validarSenhaCredencial(novaSenha)) {
                    System.out.println("Erro: Nova senha inválida.");
                    return false;
                }
                c.setSenhaCredencial(novaSenha);
                return true;
            }
        }
        System.out.println("Erro: Credencial não encontrada.");
        return false;
    }

    // DELETA UMA CREDENCIAL
    public boolean removerCredencial(String nome) {
        return listaCredenciais.removeIf(c -> c.getNomeCredencial().equalsIgnoreCase(nome));
    }

    // MOSTRA TODAS AS CREDENCIAIS
    public List<Credenciais> listarCredenciais() {
        return new ArrayList<>(listaCredenciais);
    }

    // PROCURA UMA CREDENCIAL PELO NOME
    public Credenciais buscarCredencial(String nome) {
        for (Credenciais c : listaCredenciais) {
            if (c.getNomeCredencial().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }
}