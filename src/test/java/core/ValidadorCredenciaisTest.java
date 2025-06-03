package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorCredenciaisTest {

    private ValidadorCredenciais validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorCredenciais();
    }

    // NOME CREDENCIAL
    @Test
    void testNomeCredencialValido() {
        assertTrue(validador.validarNomeCredencial("Google"));
        assertTrue(validador.validarNomeCredencial("GitHub - pessoal"));
    }

    @Test
    void testNomeCredencialInvalido() {
        assertFalse(validador.validarNomeCredencial(null));
        assertFalse(validador.validarNomeCredencial(""));
        assertFalse(validador.validarNomeCredencial("   "));
        assertFalse(validador.validarNomeCredencial("NomeMuitoLongoComMaisDeCinquentaCaracteresQueDeveFalharNoTeste123456"));
        assertFalse(validador.validarNomeCredencial("Nome inválido com símbolos!@#"));
    }

    // SENHA CREDENCIAL
    @Test
    void testSenhaCredencialValida() {
        assertTrue(validador.validarSenhaCredencial("senha123"));
        assertTrue(validador.validarSenhaCredencial("123456"));
        assertTrue(validador.validarSenhaCredencial("CaracterEspeciais!@"));
    }

    @Test
    void testSenhaCredencialInvalida() {
        assertFalse(validador.validarSenhaCredencial(null));
        assertFalse(validador.validarSenhaCredencial(""));
        assertFalse(validador.validarSenhaCredencial("123")); // muito curta
        assertFalse(validador.validarSenhaCredencial("SenhaComMaisDeTrintaParaDarErro123456789*")); // muito longa
    }
}