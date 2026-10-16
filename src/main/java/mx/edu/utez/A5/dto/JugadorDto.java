package mx.edu.utez.A5.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class JugadorDto {
    @NotBlank(message="el nombre no puede ir vacio")
    private String nombre;

    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private Double saldo;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
