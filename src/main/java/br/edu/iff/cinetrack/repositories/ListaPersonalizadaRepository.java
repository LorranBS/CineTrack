package br.edu.iff.cinetrack.repositories;

import br.edu.iff.cinetrack.entities.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.ListaPersonalizada;

import java.util.List;
import java.util.UUID;

public interface ListaPersonalizadaRepository extends JpaRepository<ListaPersonalizada, UUID> {
    List<ListaPersonalizada> findByCriadorId(UUID criadorId);
}
