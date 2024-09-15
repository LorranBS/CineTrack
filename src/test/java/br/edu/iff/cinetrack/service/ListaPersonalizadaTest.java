package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.repositories.ListaPersonalizadaRepository;
import br.edu.iff.cinetrack.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListaPersonalizadaTest {

    @Mock
    private ListaPersonalizadaRepository listaPersonalizadaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ListaPersonalizadaService listaPersonalizadaService;

    private ListaPersonalizada listaPersonalizada;
    private Obra obra;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        listaPersonalizada = new ListaPersonalizada();
        listaPersonalizada.setId(UUID.randomUUID());

        obra = new Obra();
        obra.setId(UUID.randomUUID());
        obra.setTitulo("Teste Obra");

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Teste Usuário");
    }

    @Test
    public void testAdicionarObraNaLista_Success() {
        // Arrange
        UUID listaId = listaPersonalizada.getId();
        when(listaPersonalizadaRepository.findById(listaId)).thenReturn(Optional.of(listaPersonalizada));

        // Act
        listaPersonalizadaService.adicionarObraNaLista(listaId, obra);

        // Assert
        ArgumentCaptor<ListaPersonalizada> listaCaptor = ArgumentCaptor.forClass(ListaPersonalizada.class);
        verify(listaPersonalizadaRepository, times(1)).save(listaCaptor.capture());

        ListaPersonalizada listaSalva = listaCaptor.getValue();
        assertTrue(listaSalva.getObras().contains(obra), "A obra deve estar presente na lista salva");
    }

    @Test
    public void testAdicionarObraNaLista_ObraJaExiste() {
        // Arrange
        listaPersonalizada.setObras(Collections.singletonList(obra)); // Adiciona a obra já na lista
        UUID listaId = listaPersonalizada.getId();

        when(listaPersonalizadaRepository.findById(listaId)).thenReturn(Optional.of(listaPersonalizada));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                listaPersonalizadaService.adicionarObraNaLista(listaId, obra));
        assertEquals("A obra já está presente na lista personalizada.", thrown.getMessage());

        // Verifica que o repositório não tentou salvar a lista novamente
        verify(listaPersonalizadaRepository, never()).save(any(ListaPersonalizada.class));
    }

    @Test
    public void testFindById_Success() {
        // Arrange
        UUID id = listaPersonalizada.getId();
        when(listaPersonalizadaRepository.findById(id)).thenReturn(Optional.of(listaPersonalizada));

        // Act
        ListaPersonalizada result = listaPersonalizadaService.buscarPorId(id);

        // Assert
        assertNotNull(result, "A lista personalizada deve ser encontrada");
        assertEquals(id, result.getId(), "O ID da lista personalizada deve corresponder");
        verify(listaPersonalizadaRepository, times(1)).findById(id);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(listaPersonalizadaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                listaPersonalizadaService.buscarPorId(id));
        assertEquals("Lista personalizada não localizada para o ID: " + id, thrown.getMessage());
        verify(listaPersonalizadaRepository, times(1)).findById(id);
    }

    @Test
    public void testFindByCriador_Success() {
        // Arrange
        UUID criadorId = UUID.randomUUID();
        ListaPersonalizada lista1 = new ListaPersonalizada();
        ListaPersonalizada lista2 = new ListaPersonalizada();
        List<ListaPersonalizada> listas = new ArrayList<>();
        listas.add(lista1);
        listas.add(lista2);
        when(listaPersonalizadaRepository.findByCriadorId(criadorId)).thenReturn(listas);

        // Act
        List<ListaPersonalizada> result = listaPersonalizadaService.buscarPeloCriador(criadorId);

        // Assert
        assertNotNull(result, "A lista personalizada deve ser encontrada");
        assertEquals(2, result.size(), "A lista personalizada deve conter 2 itens");
        verify(listaPersonalizadaRepository, times(1)).findByCriadorId(criadorId);
    }

    @Test
    public void testCriarListaPersonalizada_Success() {
        // Arrange
        UUID criadorId = usuario.getId();
        when(usuarioRepository.findById(criadorId)).thenReturn(Optional.of(usuario));
        when(listaPersonalizadaRepository.save(listaPersonalizada)).thenReturn(listaPersonalizada);

        // Act
        ListaPersonalizada result = listaPersonalizadaService.criarListaPersonalizada(criadorId, listaPersonalizada);

        // Assert
        assertNotNull(result, "A lista personalizada deve ser criada");
        assertEquals(criadorId, result.getCriador().getId(), "O criador da lista deve corresponder");
        assertEquals(listaPersonalizada.getId(), result.getId(), "O ID da lista personalizada deve corresponder");
        verify(listaPersonalizadaRepository, times(1)).save(listaPersonalizada);
    }

    @Test
    public void testCriarListaPersonalizada_UsuarioNaoEncontrado() {
        // Arrange
        UUID criadorId = UUID.randomUUID();
        when(usuarioRepository.findById(criadorId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                listaPersonalizadaService.criarListaPersonalizada(criadorId, listaPersonalizada));
        assertEquals("Usuário não localizado para o ID: " + criadorId, thrown.getMessage());
        verify(listaPersonalizadaRepository, never()).save(any(ListaPersonalizada.class));
    }

    @Test
    public void testDeletarListaPersonalizada_Success() {
        // Arrange
        UUID id = listaPersonalizada.getId();
        when(listaPersonalizadaRepository.existsById(id)).thenReturn(true);

        // Act
        listaPersonalizadaService.deletarListaPersonalizada(id);

        // Assert
        verify(listaPersonalizadaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeletarListaPersonalizada_NotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(listaPersonalizadaRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                listaPersonalizadaService.deletarListaPersonalizada(id));
        assertEquals("Lista personalizada não localizada para o ID: " + id, thrown.getMessage());
        verify(listaPersonalizadaRepository, times(1)).existsById(id);
    }
}
