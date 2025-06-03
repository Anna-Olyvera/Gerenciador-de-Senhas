package core;

public class ValidadorCredenciais {

    // Valida o nome da credencial (serviço)
    public boolean validarNomeCredencial(String nomeCredencial) {
        if (nomeCredencial == null || nomeCredencial.trim().isEmpty()) {
            return false;
        }
        if (nomeCredencial.length() > 50) {
            return false;
        }
        // Permite letras, números, espaços, hífens, underline e ponto (máx 50 chars)
        return nomeCredencial.matches("^[\\w\\s\\-\\.]{1,50}$");
    }

    // Valida a senha da credencial
    public boolean validarSenhaCredencial(String senhaCredencial) {
        if (senhaCredencial == null || senhaCredencial.isEmpty()) {
            return false;
        }
        if (senhaCredencial.length() < 6 || senhaCredencial.length() > 50) {
            return false;
        }
        // Pode incluir regras extras (ex: força, caracteres especiais, etc)
        return true;
    }
}
