package mx.edu.utez.A5.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecargarDto {
    @NotNull(message="el monto es obligatorio")
    @Positive(message="monto mayor a 0")
    private double monto;
}
