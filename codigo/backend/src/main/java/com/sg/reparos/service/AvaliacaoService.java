package com.sg.reparos.service;

import com.sg.reparos.dto.AvaliacaoRequestDTO;
import com.sg.reparos.dto.AvaliacaoResponseDTO;
import com.sg.reparos.model.Avaliacao;
import com.sg.reparos.model.Servico;
import com.sg.reparos.model.Usuario;
import com.sg.reparos.repository.AvaliacaoRepository;
import com.sg.reparos.repository.ServicoRepository;
import com.sg.reparos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ServicoRepository servicoRepository;
    private final UsuarioRepository usuarioRepository;

    public AvaliacaoResponseDTO avaliarServico(AvaliacaoRequestDTO dto) {
        Servico servico = servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));

        if (servico.getStatus() != Servico.StatusServico.CONCLUIDO) {
            throw new IllegalStateException("Só é possível avaliar serviços concluídos.");
        }

        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        Avaliacao avaliacao = avaliacaoRepository.findByServico(servico).orElse(null);

        if (avaliacao == null) {
            avaliacao = new Avaliacao();
            avaliacao.setServico(servico);
            avaliacao.setCliente(cliente);
        }

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setDataAvaliacao(LocalDateTime.now());

        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        AvaliacaoResponseDTO response = new AvaliacaoResponseDTO();
        response.setId(salva.getId());
        response.setNota(salva.getNota());
        response.setComentario(salva.getComentario());
        response.setDataAvaliacao(salva.getDataAvaliacao());
        response.setServicoId(salva.getServico().getId());
        response.setClienteId(salva.getCliente().getId());
        response.setClienteNome(salva.getCliente().getNome());

        return response;
    }
}
