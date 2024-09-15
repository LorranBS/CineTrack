package br.edu.iff.cinetrack.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiarioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private float avaliacao;

    @Temporal(TemporalType.DATE)
    private Date dataAssistida;

    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;
}
