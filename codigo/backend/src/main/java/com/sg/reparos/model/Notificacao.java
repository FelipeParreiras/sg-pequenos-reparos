package com.sg.reparos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    private LocalDateTime dataCriacao;

    private boolean lida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipo;

    public enum TipoNotificacao {
        SOLICITACAO,
        ACEITE,
        RECUSA,
        AGENDAMENTO,
        CONCLUSAO,
        CANCELAMENTO,
        EDICAO,
        AVALIACAO
    }
}
