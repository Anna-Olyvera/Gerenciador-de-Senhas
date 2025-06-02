package core;

import java.util.regex.Pattern;

public class ValidadorUsuario {

    // VALIDAÇÃO DE LOGIN
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,15}$");

    // VALIDAÇÃO DE EMAIL
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$"
    );

    // VALIDAÇÃO DE TELEFONE
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\d+$");

    // VALIDAÇÃO DA SENHA
    private static final Pattern SENHA_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d[^A-Za-z\\d]]{6,15}$"
    );

    public boolean validarLogin(String login) {
        if (login == null) return false;
        return LOGIN_PATTERN.matcher(login).matches();
    }

    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validarTelefone(String telefone) {
        if (telefone == null || telefone.isEmpty()) return false;
        return TELEFONE_PATTERN.matcher(telefone).matches();
    }
    

    // Valida se email ou telefone estão preenchidos e corretos
    public boolean validarEmailOuTelefone(String email, String telefone) {
        boolean emailValido = (email != null && !email.isEmpty()) && validarEmail(email);
        boolean telefoneValido = (telefone != null && !telefone.isEmpty()) && validarTelefone(telefone);
        return emailValido || telefoneValido;
    }

    public boolean validarChaveMestra(String chaveMestra) {
        if (chaveMestra == null) return false;
        return SENHA_PATTERN.matcher(chaveMestra).matches();
    }
}