package com.sg.reparos.controller;

import com.sg.reparos.dto.AvaliacaoRequestDTO;
import com.sg.reparos.dto.AvaliacaoResponseDTO;
import com.sg.reparos.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> avaliarServico(@RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO response = avaliacaoService.avaliarServico(dto);
        return ResponseEntity.ok(response);
    }
}
