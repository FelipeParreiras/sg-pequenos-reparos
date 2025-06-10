package com.sg.reparos.service;

import com.sg.reparos.dto.NotificacaoRequestDTO;
import com.sg.reparos.dto.NotificacaoResponseDTO;
import com.sg.reparos.model.Notificacao;
import com.sg.reparos.model.Usuario;
import com.sg.reparos.repository.NotificacaoRepository;
import com.sg.reparos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public void enviar(NotificacaoRequestDTO dto) {
        Usuario destinatario = usuarioRepository.findById(dto.getDestinatarioId())
                .orElseThrow(() -> new IllegalArgumentException("Destinatário não encontrado"));

        Notificacao notificacao = Notificacao.builder()
                .titulo(dto.getTitulo())
                .mensagem(dto.getMensagem())
                .dataCriacao(LocalDateTime.now())
                .lida(false)
                .destinatario(destinatario)
                .tipo(dto.getTipo())
                .build();

        notificacaoRepository.save(notificacao);
    }

    public List<NotificacaoResponseDTO> listarPorDestinatario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return notificacaoRepository.findByDestinatarioOrderByDataCriacaoDesc(usuario).stream().map(n -> {
            NotificacaoResponseDTO dto = new NotificacaoResponseDTO();
            dto.setId(n.getId());
            dto.setTitulo(n.getTitulo());
            dto.setMensagem(n.getMensagem());
            dto.setDataCriacao(n.getDataCriacao());
            dto.setLida(n.isLida());
            dto.setDestinatarioId(n.getDestinatario().getId());
            dto.setDestinatarioNome(n.getDestinatario().getNome());
            dto.setTipo(n.getTipo());
            return dto;
        }).toList();
    }

    public List<NotificacaoResponseDTO> listarRecentes(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return notificacaoRepository.findTop3ByDestinatarioOrderByDataCriacaoDesc(usuario).stream().map(n -> {
            NotificacaoResponseDTO dto = new NotificacaoResponseDTO();
            dto.setId(n.getId());
            dto.setTitulo(n.getTitulo());
            dto.setMensagem(n.getMensagem());
            dto.setDataCriacao(n.getDataCriacao());
            dto.setLida(n.isLida());
            dto.setDestinatarioId(n.getDestinatario().getId());
            dto.setDestinatarioNome(n.getDestinatario().getNome());
            dto.setTipo(n.getTipo());
            return dto;
        }).toList();
    }
}
