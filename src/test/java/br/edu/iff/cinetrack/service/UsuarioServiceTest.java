package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.DiarioItem;
import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private DiarioItem diarioItem;
    private DiarioItem diarioItem1;
    private DiarioItem diarioItem2;
    private ListaPersonalizada lista;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setDiario(new ArrayList<>());
        usuario.setListasPersonalizadas(new ArrayList<>());

        diarioItem1 = new DiarioItem();
        diarioItem1.setId(UUID.randomUUID());

        diarioItem2 = new DiarioItem();
        diarioItem2.setId(UUID.randomUUID());

        usuario.setDiario(new ArrayList<>(List.of(diarioItem1, diarioItem2)));

        diarioItem = new DiarioItem();
        diarioItem.setId(UUID.randomUUID());

        lista = new ListaPersonalizada();
        lista.setId(UUID.randomUUID());
    }

    @Test
    public void testBuscarPorId_Success() {
        // Arrange
        UUID id = usuario.getId();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario result = usuarioService.buscarPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testBuscarPorId_NotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(id));
        assertEquals("Usuário não localizado para o ID: " + id, thrown.getMessage());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testAdicionarDiario() {
        // Arrange
        UUID usuarioId = usuario.getId();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        usuarioService.adicionarObraAoDiario(usuarioId, diarioItem);

        // Assert
        assertTrue(usuario.getDiario().contains(diarioItem));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testRemoverDiario() {
        // Arrange
        UUID usuarioId = usuario.getId();
        usuario.getDiario().add(diarioItem); // Adiciona o diário à lista do usuário
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        usuarioService.removerObraDoDiario(usuarioId, diarioItem.getId());

        // Assert
        assertFalse(usuario.getDiario().contains(diarioItem));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testAdicionarListaPersonalizada() {
        // Arrange
        UUID usuarioId = usuario.getId();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        usuarioService.adicionarListaPersonalizada(usuarioId, lista);

        // Assert
        assertTrue(usuario.getListasPersonalizadas().contains(lista));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testRemoverListaPersonalizada() {
        // Arrange
        UUID usuarioId = usuario.getId();
        usuario.getListasPersonalizadas().add(lista); // Adiciona a lista à lista do usuário
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        usuarioService.removerListaPersonalizada(usuarioId, lista.getId());

        // Assert
        assertFalse(usuario.getListasPersonalizadas().contains(lista));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testBuscarDiarioDoUsuario_Success() {
        // Arrange
        UUID usuarioId = usuario.getId();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        // Act
        List<DiarioItem> diarioItems = usuarioService.buscarDiarioDoUsuario(usuarioId);

        // Assert
        assertNotNull(diarioItems);
        assertEquals(2, diarioItems.size());
        assertTrue(diarioItems.contains(diarioItem1));
        assertTrue(diarioItems.contains(diarioItem2));
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    public void testBuscarDiarioDoUsuario_NotFound() {
        // Arrange
        UUID usuarioId = UUID.randomUUID();
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usuarioService.buscarDiarioDoUsuario(usuarioId));
        assertEquals("Usuário não localizado para o ID: " + usuarioId, thrown.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }
}