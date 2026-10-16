package mx.edu.utez.A5.service;

import mx.edu.utez.A5.dto.IniciarPartidaDto;
import mx.edu.utez.A5.entity.*;
import mx.edu.utez.A5.exception.RecursoNoEncontradoException;
import mx.edu.utez.A5.exception.ReglaNegocioException;
import mx.edu.utez.A5.repository.JugadorRepository;
import mx.edu.utez.A5.repository.PartidaRepository;
import mx.edu.utez.A5.repository.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final JugadorRepository jugadorRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository, JugadorRepository jugadorRepository, TiroRepository tiroRepository) {
        this.partidaRepository = partidaRepository;
        this.jugadorRepository = jugadorRepository;
        this.tiroRepository = tiroRepository;
    }

    @Transactional
    public Partida iniciarPartida(IniciarPartidaDto dto) {
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("El jugador con ID " + dto.getJugadorId() + " no existe."));

        if (!jugador.getActivo()) {
            throw new ReglaNegocioException("El jugador se encuentra inactivo y no puede iniciar partidas.");
        }

        if (jugador.getSaldo() < dto.getApuesta()) {
            throw new ReglaNegocioException("Saldo insuficiente. Saldo actual: " + jugador.getSaldo() + ", Apuesta solicitada: " + dto.getApuesta());
        }

        jugador.setSaldo(jugador.getSaldo() - dto.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = Partida.builder()
                .fecha(LocalDateTime.now())
                .estado(EstadoPartida.EN_JUEGO)
                .jugador(jugador)
                .apuesta(dto.getApuesta())
                .build();

        return partidaRepository.save(partida);
    }

    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La partida con ID " + partidaId + " no existe."));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new ReglaNegocioException("La partida ya no está en curso.");
        }

        int valorDado1 = random.nextInt(6) + 1;
        int valorDado2 = random.nextInt(6) + 1;
        int suma = valorDado1 + valorDado2;

        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            esGanador = false;
            partida.setEstado(EstadoPartida.FINALIZADA);
        } else {
            esGanador = false;
        }

        partidaRepository.save(partida);

        Tiro tiro = Tiro.builder()
                .partida(partida)
                .valorDado1(valorDado1)
                .valorDado2(valorDado2)
                .suma(suma)
                .esGanador(esGanador)
                .build();

        return tiroRepository.save(tiro);
    }

    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La partida con ID " + partidaId + " no existe."));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new ReglaNegocioException("La partida ya se encuentra finalizada.");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new RecursoNoEncontradoException("El jugador con ID " + jugadorId + " no existe.");
        }
        return partidaRepository.findByJugadorId(jugadorId);
    }
}