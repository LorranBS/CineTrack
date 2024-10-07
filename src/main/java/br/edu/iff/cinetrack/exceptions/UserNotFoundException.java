package br.edu.iff.cinetrack.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(UUID id){
        super("Usuário não localizado para o ID: " + id);
    }

}
