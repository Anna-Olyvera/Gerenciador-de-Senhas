package core;

import java.util.regex.Pattern;

public class ValidadorUsuario {

    // VALIDAÇÃO DE LOGIN: letras e números, 4 a 15 chars
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,15}$");

    // VALIDAÇÃO DE EMAIL: básico, pode ser melhorado conforme necessidade
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$"
    );

    // VALIDAÇÃO DE TELEFONE: só números, pelo menos 1 dígito
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\d+$");

    // VALIDAÇÃO DA SENHA (chave mestra):
    // Pelo menos uma letra, um número, um símbolo, tamanho 6 a 15
    private static final Pattern SENHA_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{6,15}$"
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

    // Valida se pelo menos email ou telefone são válidos
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
