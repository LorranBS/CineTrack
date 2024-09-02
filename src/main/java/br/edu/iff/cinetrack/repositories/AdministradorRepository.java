package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.Administrador;
import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {
}
