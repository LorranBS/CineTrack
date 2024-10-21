package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "O gênero é obrigatório")
    private String genero;

    @NotNull(message = "O ano de lançamento é obrigatório")
    private int ano;

    @NotBlank(message = "O diretor é obrigatório")
    private String diretor;

    @NotBlank(message = "O tipo (filme ou série) é obrigatório")
    private String tipo;

    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiarioItem> diarioItems;

    @ManyToMany(mappedBy = "obras")
    private List<ListaPersonalizada> listasPersonalizadas;
}
