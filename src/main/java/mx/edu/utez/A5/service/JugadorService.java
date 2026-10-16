package mx.edu.utez.A5.service;

import mx.edu.utez.A5.dto.JugadorDto;
import mx.edu.utez.A5.entity.Jugador;
import mx.edu.utez.A5.exception.RecursoNoEncontradoException;
import mx.edu.utez.A5.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Jugador crearJugador(JugadorDto dto) {
        Jugador jugador = Jugador.builder()
                .nombre(dto.getNombre())
                .saldo(dto.getSaldo())
                .activo(dto.getActivo())
                .build();
        return jugadorRepository.save(jugador);
    }

    public Jugador recargarSaldo(Long jugadorId, Double monto) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El jugador con ID " + jugadorId + " no existe."));

        jugador.setSaldo(jugador.getSaldo() + monto);
        return jugadorRepository.save(jugador);
    }

    public List<Jugador> listarJugadores() {
        return jugadorRepository.findAll();
    }
}