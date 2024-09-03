package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.Diario;
import java.util.UUID;

public interface DiarioRepository extends JpaRepository<Diario, UUID> {
}
