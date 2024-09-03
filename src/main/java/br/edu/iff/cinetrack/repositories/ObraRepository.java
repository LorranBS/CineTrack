package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.Obra;
import java.util.UUID;

public interface ObraRepository extends JpaRepository<Obra, UUID> {
}