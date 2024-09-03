package br.edu.iff.cinetrack.repositories;

import br.edu.iff.cinetrack.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
}
