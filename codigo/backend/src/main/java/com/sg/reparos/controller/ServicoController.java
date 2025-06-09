package com.sg.reparos.controller;

import com.sg.reparos.dto.ServicoRequestDTO;
import com.sg.reparos.dto.ServicoResponseDTO;
import com.sg.reparos.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> solicitarServico(@Valid @RequestBody ServicoRequestDTO dto) {
        ServicoResponseDTO response = servicoService.solicitarServico(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
        List<ServicoResponseDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
        ServicoResponseDTO servico = servicoService.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @PutMapping("/{id}/aceitar")
    public ResponseEntity<ServicoResponseDTO> aceitarServico(
            @PathVariable Long id,
            @RequestParam Long administradorId,
            @RequestParam String data,
            @RequestParam String horario) {
        ServicoResponseDTO response = servicoService.aceitarServico(id, administradorId, data, horario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/recusar")
    public ResponseEntity<ServicoResponseDTO> recusarServico(@PathVariable Long id) {
        ServicoResponseDTO response = servicoService.recusarServico(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ServicoResponseDTO> cancelarServico(
            @PathVariable Long id,
            @RequestParam String motivo) {
        ServicoResponseDTO response = servicoService.cancelarServico(id, motivo);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<ServicoResponseDTO> concluirServico(@PathVariable Long id) {
        ServicoResponseDTO response = servicoService.concluirServico(id);
        return ResponseEntity.ok(response);
    }
}