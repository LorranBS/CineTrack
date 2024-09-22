package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Administrador;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.repositories.AdministradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdministradorServiceTest {

    @Mock
    private AdministradorRepository administradorRepository;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AdministradorService administradorService;

    private Administrador administrador;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        administrador = new Administrador();
        administrador.setId(UUID.randomUUID());

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
    }

    @Test
    public void testCriarAdministrador() {
        // Arrange
        when(administradorRepository.save(administrador)).thenReturn(administrador);

        // Act
        Administrador result = administradorService.criarAdministrador(administrador);

        // Assert
        assertNotNull(result);
        assertEquals(administrador.getId(), result.getId());
        verify(administradorRepository, times(1)).save(administrador);
    }

    @Test
    public void testDeletarAdministrador_Success() {
        // Arrange
        UUID id = administrador.getId();
        when(administradorRepository.existsById(id)).thenReturn(true);

        // Act
        administradorService.deletarAdministrador(id);

        // Assert
        verify(administradorRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletarAdministrador_NotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(administradorRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                administradorService.deletarAdministrador(id));
        assertEquals("Administrador não localizado para o ID: " + id, thrown.getMessage());
        verify(administradorRepository, times(1)).existsById(id);
    }

    @Test
    public void testCriarUsuario() {
        // Arrange
        when(usuarioService.criarUsuario(usuario)).thenReturn(usuario);

        // Act
        Usuario result = administradorService.criarUsuario(usuario);

        // Assert
        assertNotNull(result);
        assertEquals(usuario.getId(), result.getId());
        verify(usuarioService, times(1)).criarUsuario(usuario);
    }

    @Test
    public void testDeletarUsuario() {
        // Arrange
        UUID id = usuario.getId();
        doNothing().when(usuarioService).deletarUsuario(id);

        // Act
        administradorService.deletarUsuario(id);

        // Assert
        verify(usuarioService, times(1)).deletarUsuario(id);
    }
}
