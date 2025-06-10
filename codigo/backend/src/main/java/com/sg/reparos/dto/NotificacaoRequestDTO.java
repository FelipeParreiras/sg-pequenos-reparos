package com.sg.reparos.dto;

import com.sg.reparos.model.Notificacao.TipoNotificacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacaoRequestDTO {
    private String titulo;
    private String mensagem;
    private Long destinatarioId;
    private TipoNotificacao tipo;
}
