package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.Usuario;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
