package mx.edu.utez.A5.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class IniciarPartidaDto {
    @NotNull(message="el ID es obligatorio")
    private long JugadorId;

    @NotNull(message="la apuesta es obligatoria")
    @Positive(message="la apuesta debe ser mayor a 0")
    private double apuesta;
}
