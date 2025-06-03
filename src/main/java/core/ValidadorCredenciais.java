package core;

public class ValidadorCredenciais {

    // Valida o nome da credencial (serviço)
    public boolean validarNomeCredencial(String nomeCredencial) {
        if (nomeCredencial == null || nomeCredencial.trim().isEmpty()) {
            return false;
        }
        if (nomeCredencial.length() > 30) {
            return false;
        }
        // Permite letras, números, espaços, hífens, underline e ponto (máx 30 chars)
        return nomeCredencial.matches("^[\\w\\s\\-\\.]{1,30}$");
    }

    // Valida a senha da credencial
    public boolean validarSenhaCredencial(String senhaCredencial) {
        if (senhaCredencial == null || senhaCredencial.isEmpty()) {
            return false;
        }
        if (senhaCredencial.length() < 6 || senhaCredencial.length() > 20) {
            return false;
        } else {
            return true;
        }
    }
}
