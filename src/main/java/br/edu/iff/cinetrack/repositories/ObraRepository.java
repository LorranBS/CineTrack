package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.iff.cinetrack.entities.Obra;
import java.util.UUID;


public interface ObraRepository extends JpaRepository<Obra, UUID> {
    
    @Query("select u from Obra u where u.genero = ?1")
    Obra findByGenero(String genero);

    @Query("select u from Obra u where u.titulo = ?1")
    Obra findByTitulo(String titulo);
}