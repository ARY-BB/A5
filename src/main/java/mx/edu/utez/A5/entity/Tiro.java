package mx.edu.utez.A5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiros")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @Column(nullable = false)
    private Integer valorDado1;

    @Column(nullable = false)
    private Integer valorDado2;

    @Column(nullable = false)
    private Integer suma;

    @Column(nullable = false)
    private Boolean esGanador;
}