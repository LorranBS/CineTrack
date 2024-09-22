package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Pessoa;
import br.edu.iff.cinetrack.repositories.PessoaRepository;

public class PessoaService {
    private PessoaRepository repository;

    public Pessoa buscarPorNome (String Nome){
        return repository.buscarPorNome(Nome).orElseThrow(() -> new RuntimeException("Usuario não localizado"));
    }
}
