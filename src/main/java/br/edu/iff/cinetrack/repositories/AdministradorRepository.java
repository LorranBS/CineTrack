package br.edu.iff.cinetrack.repositories;

import br.edu.iff.cinetrack.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdministradorRepository extends JpaRepository<Administrador, UUID> {
}
