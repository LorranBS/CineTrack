package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.iff.cinetrack.entities.DiarioItem;

import java.util.UUID;

public interface DiarioItemRepository extends JpaRepository<DiarioItem, UUID> { }
