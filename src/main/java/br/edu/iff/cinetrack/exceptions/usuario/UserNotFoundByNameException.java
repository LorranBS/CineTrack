package br.edu.iff.cinetrack.exceptions.usuario;

public class UserNotFoundByNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundByNameException(String name){
        super("Usuário não localizado para o nome: " + name);
    }

}