package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.repositories.ObraRepository;

public class ObraService {

    private ObraRepository repository;
    
    public Obra findByGenero(String genero){
        try {
            repository.findByGenero(genero);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não localizado");
        }
        return repository.findByGenero(genero);

    }

    public Obra findByTitulo(String titulo){
        try {
            repository.findByTitulo(titulo);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não localizado");
        }
        return repository.findByTitulo(titulo);

    }

}
