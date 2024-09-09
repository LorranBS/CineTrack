package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.iff.cinetrack.entities.Diario;

import java.util.UUID;

public interface DiarioRepository extends JpaRepository<Diario, UUID> {
    
    @Query("select u from Diario u where u.avaliacao = ?1")
    Diario findByAvaliacao(String avaliacao);
}
