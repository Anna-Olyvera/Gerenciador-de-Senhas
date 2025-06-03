package controller;

import core.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import service.FirebaseService;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private FirebaseService firebaseService;

    @Mock
    private TentativaLoginController tentativaLoginController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRemoverCredencialSucesso() {
        when(firebaseService.removerCredencial("user1", "cred1")).thenReturn(true);

        boolean result = usuarioController.removerCredencial("user1", "cred1");

        assertTrue(result);
        verify(firebaseService).removerCredencial("user1", "cred1");
    }

    @Test
    void testCadastroUsuarioLoginInvalido() {
        String resultado = usuarioController.cadastroUsuario("", "email@example.com", null, "senha123");
        assertEquals("Login é obrigatório.", resultado);
    }

    @Test
    void testAutenticarBloqueado() {
        when(tentativaLoginController.estaBloqueado("user1")).thenReturn(true);

        Usuario usuario = usuarioController.autenticar("user1", "senha123");
        assertNull(usuario);

        verify(tentativaLoginController).estaBloqueado("user1");
    }
}