package br.edu.iff.cinetrack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.repositories.ObraRepository;


public class ObraServiceTest {

    @InjectMocks
    private ObraService obraService;

    @Mock
    private ObraRepository obraRepository;

    @Test
    void testFindByGenero() {
        // Arrange
        String genero = "Ação";
        Obra obraMock = new Obra();
        obraMock.setGenero("Ação");
        when(obraRepository.findByGenero(genero)).thenReturn(obraMock);

        // Act
        Obra result = obraService.findByGenero(genero);

        // Assert
        assertNotNull(result);
        assertEquals("Ação", result.getGenero());
        verify(obraRepository, times(1)).findByGenero(genero);
    }

    @Test
    void testFindByTitulo() {

    }
}
