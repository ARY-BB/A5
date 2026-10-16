package mx.edu.utez.A5.controller;

import mx.edu.utez.A5.dto.JugadorDto;

import mx.edu.utez.A5.dto.RecargarDto;
import mx.edu.utez.A5.entity.Jugador;
import mx.edu.utez.A5.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@Valid @RequestBody JugadorDto dto) {
        Jugador nuevoJugador = jugadorService.crearJugador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoJugador);
    }

    @PutMapping("/{id}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long id, @Valid @RequestBody RecargarDto dto) {
        Jugador jugadorActualizado = jugadorService.recargarSaldo(id, dto.getMonto());
        return ResponseEntity.ok(jugadorActualizado);
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> listarJugadores() {
        return ResponseEntity.ok(jugadorService.listarJugadores());
    }
}