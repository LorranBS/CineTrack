package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;
    private String genero;
    private int ano;
    private String diretor;
    private String tipo;

    @ManyToMany(mappedBy = "listaParaAssistir")
    private List<Usuario> operadores;
}
