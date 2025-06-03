package controller;

import core.Credenciais;
import core.Sessao;
import core.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import service.FirebaseService;
import core.ValidadorCredenciais;

class CredenciaisControllerTest {

    @InjectMocks
    private CredenciaisController credenciaisController;

    @Mock
    private FirebaseService firebaseService;

    @Mock
    private ValidadorCredenciais validadorCredenciais;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Simula usuário logado na Sessao
        Sessao.setUsuarioLogado(new Usuario("user1", "email@example.com", null, "salt", "hash"));
    }

    @Test
    void testAdicionarCredencialSucesso() {
        when(validadorCredenciais.validarNomeCredencial("cred1")).thenReturn(true);
        when(validadorCredenciais.validarSenhaCredencial("senha123")).thenReturn(true);
        when(firebaseService.buscarCredencial("user1", "cred1")).thenReturn(null);

        boolean resultado = credenciaisController.adicionarCredencial("cred1", "senha123");

        assertTrue(resultado);
        verify(firebaseService).salvarCredencial(eq("user1"), any(Credenciais.class));
    }

    @Test
    void testRemoverCredencialSemUsuarioLogado() {
        Sessao.setUsuarioLogado(null); // sem usuário

        boolean resultado = credenciaisController.removerCredencial("cred1");

        assertFalse(resultado);
    }

    @Test
    void testListarCredenciaisComUsuarioLogado() {
        List<Credenciais> listaMock = List.of(new Credenciais("cred1", "senha1"));
        when(firebaseService.buscarCredenciais("user1")).thenReturn(listaMock);

        List<Credenciais> lista = credenciaisController.listarCredenciais();

        assertEquals(1, lista.size());
    }
}