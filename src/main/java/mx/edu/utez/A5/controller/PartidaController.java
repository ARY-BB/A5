package mx.edu.utez.A5.controller;

import mx.edu.utez.A5.dto.IniciarPartidaDto;
import mx.edu.utez.A5.entity.Partida;
import mx.edu.utez.A5.entity.Tiro;
import mx.edu.utez.A5.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@Valid @RequestBody IniciarPartidaDto dto) {
        Partida partida = partidaService.iniciarPartida(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(partida);
    }

    @PostMapping("/{id}/tirar")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long id) {
        Tiro tiro = partidaService.realizarTiro(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(tiro);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long id) {
        Partida partida = partidaService.finalizarPartidaManual(id);
        return ResponseEntity.ok(partida);
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        List<Partida> historial = partidaService.obtenerHistorialPartida(jugadorId);
        return ResponseEntity.ok(historial);
    }
}