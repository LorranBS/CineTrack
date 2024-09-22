package br.edu.iff.cinetrack.repositories;

import br.edu.iff.cinetrack.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("SELECT u FROM Pessoa u WHERE u.nome = ?1")
    Optional<Pessoa> buscarPorNome(String nome);
}