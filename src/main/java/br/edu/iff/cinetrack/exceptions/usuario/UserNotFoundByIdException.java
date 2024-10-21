package br.edu.iff.cinetrack.exceptions.usuario;

import java.util.UUID;

public class UserNotFoundByIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundByIdException(UUID id){
        super("Usuário não localizado para o ID: " + id);
    }

}
