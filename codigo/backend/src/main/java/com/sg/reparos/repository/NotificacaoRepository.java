package com.sg.reparos.repository;

import com.sg.reparos.model.Notificacao;
import com.sg.reparos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findTop3ByDestinatarioOrderByDataCriacaoDesc(Usuario destinatario);
    List<Notificacao> findByDestinatarioOrderByDataCriacaoDesc(Usuario destinatario);
}
