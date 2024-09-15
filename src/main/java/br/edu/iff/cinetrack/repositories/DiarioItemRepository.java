package br.edu.iff.cinetrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.edu.iff.cinetrack.entities.DiarioItem;


import java.util.List;
import java.util.UUID;

public interface DiarioItemRepository extends JpaRepository<DiarioItem, UUID> { }
