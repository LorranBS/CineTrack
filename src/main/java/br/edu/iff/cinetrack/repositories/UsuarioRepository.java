package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.Usuario;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("SELECT u FROM Usuario u WHERE u.nome = ?1")
    Optional<Usuario> buscarPorNome(String nome);

}