package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Diario;
import br.edu.iff.cinetrack.repositories.DiarioRepository;


public class DiarioService {
    private DiarioRepository repository;

    public Diario findByAvaliacao(String avaliacao){
        try {
            repository.findByAvaliacao(avaliacao);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não localizado");
        }
        return repository.findByAvaliacao(avaliacao);

    }
}
