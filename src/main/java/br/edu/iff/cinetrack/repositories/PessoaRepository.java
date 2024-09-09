package br.edu.iff.cinetrack.repositories;

import br.edu.iff.cinetrack.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query("select u from Pessoa u where u.nome = ?1")
    Pessoa findByNome (String nome);
}
