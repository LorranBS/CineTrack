package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Obra {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;
    private String genero;
    private int ano;
    private String diretor;
    private String tipo;

    @OneToMany(mappedBy = "obra")
    private List<DiarioItem> diarioItems;

    @ManyToMany(mappedBy = "obras")
    private List<ListaPersonalizada> listasPersonalizadas;
}
