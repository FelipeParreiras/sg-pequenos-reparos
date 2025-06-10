package com.sg.reparos.repository;

import com.sg.reparos.model.Avaliacao;
import com.sg.reparos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<Avaliacao> findByServico(Servico servico);
}
