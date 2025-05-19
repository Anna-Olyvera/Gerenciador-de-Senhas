package core;

public class ValidadorCredenciais {
    // VALIDAÇÃO DO SERVIÇO
    public boolean validarNomeCredencial(String nomeCredencial) {
        if (nomeCredencial == null || nomeCredencial.trim().isEmpty()) return false;
        if (nomeCredencial.length() > 50) return false;
        // AVALIA OS CARACTERES ESPECIAIS
        return nomeCredencial.matches("^[\\w\\s\\-\\.]{1,50}$");
    }

    // VALIDAÇÃO SENHA
    public boolean validarSenhaCredencial(String senhaCredencial) {
        if (senhaCredencial == null || senhaCredencial.isEmpty()) return false;
        if (senhaCredencial.length() < 6 || senhaCredencial.length() > 50) return false;
        return true; // opcional: outras validações aqui
    }
}