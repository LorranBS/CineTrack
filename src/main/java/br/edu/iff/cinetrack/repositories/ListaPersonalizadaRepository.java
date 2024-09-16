package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ListaPersonalizadaRepository extends JpaRepository<ListaPersonalizada, UUID> {

    @Query("SELECT l FROM ListaPersonalizada l WHERE l.criador.id = ?1")
    List<ListaPersonalizada> buscarPorCriadorId(UUID criadorId);
}
