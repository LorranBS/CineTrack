package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Usuario extends Pessoa {

    @OneToMany(mappedBy = "operador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diario> diarios;

    @ManyToMany
    @JoinTable(
            name = "operador_lista_para_assistir",
            joinColumns = @JoinColumn(name = "operador_id"),
            inverseJoinColumns = @JoinColumn(name = "obra_id")
    )

    private List<Obra> listaParaAssistir;
}
