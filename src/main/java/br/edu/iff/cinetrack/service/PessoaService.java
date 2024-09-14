package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Pessoa;
import br.edu.iff.cinetrack.repositories.PessoaRepository;

public class PessoaService {
    private PessoaRepository repository;

    public Pessoa findByNome (String Nome){
        try {
            repository.findByNome(Nome);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não localizado");
        }
        return repository.findByNome(Nome);
    }
}
