package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorUsuarioTest {

    private ValidadorUsuario validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorUsuario();
    }

    // --- LOGIN ---
    @Test
    void testLoginValido() {
        assertTrue(validador.validarLogin("user123"));
    }

    @Test
    void testLoginInvalido_CaracteresInvalidos() {
        assertFalse(validador.validarLogin("user_123")); // _ não permitido
    }

    @Test
    void testLoginInvalido_TamanhoIncorreto() {
        assertFalse(validador.validarLogin("us")); // muito curto
        assertFalse(validador.validarLogin("usuarioMuitoGrande123")); // muito longo
    }

    // --- EMAIL ---
    @Test
    void testEmailValido() {
        assertTrue(validador.validarEmail("teste@email.com"));
    }

    @Test
    void testEmailInvalido() {
        assertFalse(validador.validarEmail("email_invalido"));
        assertFalse(validador.validarEmail(null));
        assertFalse(validador.validarEmail(""));
    }

    // --- TELEFONE ---
    @Test
    void testTelefoneValido() {
        assertTrue(validador.validarTelefone("11999999999"));
    }

    @Test
    void testTelefoneInvalido() {
        assertFalse(validador.validarTelefone("11-99999")); // contém hífen
        assertFalse(validador.validarTelefone("abc")); // letras
        assertFalse(validador.validarTelefone(null));
    }

    // --- EMAIL OU TELEFONE ---
    @Test
    void testEmailOuTelefone_Valido() {
        assertTrue(validador.validarEmailOuTelefone("teste@email.com", null));
        assertTrue(validador.validarEmailOuTelefone(null, "11999999999"));
    }

    @Test
    void testEmailOuTelefone_Invalido() {
        assertFalse(validador.validarEmailOuTelefone(null, null));
        assertFalse(validador.validarEmailOuTelefone("", ""));
        assertFalse(validador.validarEmailOuTelefone("invalido", "123abc"));
    }

    // --- CHAVE MESTRA ---
    @Test
    void testChaveMestraValida() {
        assertTrue(validador.validarChaveMestra("Senha@123"));
    }

    @Test
    void testChaveMestraInvalida() {
        assertFalse(validador.validarChaveMestra("senha")); // muito fraca
        assertFalse(validador.validarChaveMestra("123456")); // só números
        assertFalse(validador.validarChaveMestra("Abcdefg")); // só letras
        assertFalse(validador.validarChaveMestra(null));
    }
}