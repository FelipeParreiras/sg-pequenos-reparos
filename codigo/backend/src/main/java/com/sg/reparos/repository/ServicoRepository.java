package com.sg.reparos.repository;

import com.sg.reparos.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByClienteId(Long clienteId);

    List<Servico> findByAdministradorId(Long administradorId);

    List<Servico> findByStatus(Servico.StatusServico status);

}
