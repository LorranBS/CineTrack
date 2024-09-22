package br.edu.iff.cinetrack.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.repositories.ObraRepository;

@ExtendWith(MockitoExtension.class)
public class ObraServiceTest {

    @InjectMocks
    private ObraService obraService;

    @Mock
    private ObraRepository obraRepository;

    private Obra obraMock;

    @BeforeEach
    void setUp() {
        obraMock = new Obra();
        obraMock.setGenero("Ação");
        obraMock.setTitulo("Missão Impossível");
        obraMock.setDiretor("John Woo");
    }

    @Test
    void testCriarObra_Success() {
        // Arrange
        when(obraRepository.save(any(Obra.class))).thenReturn(obraMock);

        // Act
        Obra result = obraService.criarObra(obraMock);

        // Assert
        assertNotNull(result);
        assertEquals("Ação", result.getGenero());
        assertEquals("Missão Impossível", result.getTitulo());
        assertEquals("John Woo", result.getDiretor());
        verify(obraRepository, times(1)).save(obraMock);
    }

    @Test
    void testBuscarPorGenero_Success() {
        // Arrange
        when(obraRepository.buscarPorGenero(any(String.class))).thenReturn(List.of(obraMock));

        // Act
        List<Obra> result = obraService.buscarPorGenero("Ação");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ação", result.get(0).getGenero());
        verify(obraRepository, times(1)).buscarPorGenero("Ação");
    }

    @Test
    void testBuscarPorGenero_NotFound() {
        // Arrange
        when(obraRepository.buscarPorGenero(any(String.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> obraService.buscarPorGenero("Comédia"));
        verify(obraRepository, times(1)).buscarPorGenero("Comédia");
    }

    @Test
    void testBuscarPorTitulo_Success() {
        // Arrange
        when(obraRepository.buscarPorTitulo(any(String.class))).thenReturn(Optional.of(obraMock));

        // Act
        Obra result = obraService.buscarPorTitulo("Missão Impossível");

        // Assert
        assertNotNull(result);
        assertEquals("Missão Impossível", result.getTitulo());
        verify(obraRepository, times(1)).buscarPorTitulo("Missão Impossível");
    }

    @Test
    void testBuscarPorTitulo_NotFound() {
        // Arrange
        when(obraRepository.buscarPorTitulo(any(String.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> obraService.buscarPorTitulo("Filme Inexistente"));
        verify(obraRepository, times(1)).buscarPorTitulo("Filme Inexistente");
    }

    @Test
    void testBuscarPorDiretor_Success() {
        // Arrange
        when(obraRepository.buscarPorDiretor(any(String.class))).thenReturn(List.of(obraMock));

        // Act
        List<Obra> result = obraService.buscarPorDiretor("John Woo");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Woo", result.get(0).getDiretor());
        verify(obraRepository, times(1)).buscarPorDiretor("John Woo");
    }

    @Test
    void testBuscarPorDiretor_NotFound() {
        // Arrange
        when(obraRepository.buscarPorDiretor(any(String.class))).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> obraService.buscarPorDiretor("Diretor Inexistente"));
        verify(obraRepository, times(1)).buscarPorDiretor("Diretor Inexistente");
    }
}
