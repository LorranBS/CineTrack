package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.edu.iff.cinetrack.entities.Obra;

import java.util.Optional;
import java.util.UUID;

import java.util.List;

public interface ObraRepository extends JpaRepository<Obra, UUID> {

    @Query("SELECT o FROM Obra o WHERE o.titulo = ?1")
    Optional<Obra> buscarPorTitulo(String titulo);

    @Query("SELECT o FROM Obra o WHERE o.genero = ?1")
    List<Obra> buscarPorGenero(String genero);

    @Query("SELECT o FROM Obra o WHERE o.diretor = ?1")
    List<Obra> buscarPorDiretor(String diretor);
}
