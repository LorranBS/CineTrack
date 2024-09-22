package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaPersonalizada {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O título da lista é obrigatório")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    @Column(nullable = false)
    private String nome;

    private String descricao;

    private String imagemCapa;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario criador;

    @ManyToMany
    @JoinTable(
            name = "lista_personalizada_obras",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id")
    )
    private List<Obra> obras = new ArrayList<>();
}
