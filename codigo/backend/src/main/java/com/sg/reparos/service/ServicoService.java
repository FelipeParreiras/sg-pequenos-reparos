package com.sg.reparos.service;

import com.sg.reparos.dto.ServicoRequestDTO;
import com.sg.reparos.dto.ServicoResponseDTO;
import com.sg.reparos.model.Servico;
import com.sg.reparos.model.TipoServico;
import com.sg.reparos.model.Usuario;
import com.sg.reparos.repository.ServicoRepository;
import com.sg.reparos.repository.TipoServicoRepository;
import com.sg.reparos.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoServicoRepository tipoServicoRepository;

    public ServicoService(ServicoRepository servicoRepository,
                          UsuarioRepository usuarioRepository,
                          TipoServicoRepository tipoServicoRepository) {
        this.servicoRepository = servicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoServicoRepository = tipoServicoRepository;
    }

    public ServicoResponseDTO solicitarServico(ServicoRequestDTO dto) {
        Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setEmailContato(dto.getEmailContato());
        servico.setTelefoneContato(dto.getTelefoneContato());
        servico.setStatus(Servico.StatusServico.SOLICITADO);

        // Buscar Cliente
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar Tipo de Serviço
        TipoServico tipoServico = tipoServicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new RuntimeException("Tipo de serviço não encontrado"));

        // Setar Cliente e Tipo de Serviço
        servico.setCliente(cliente);
        servico.setTipoServico(tipoServico);

        // Converter diasDisponiveisCliente para Enum
        List<Servico.DiaSemana> diasDisponiveis = dto.getDiasDisponiveisCliente()
                .stream()
                .map(dia -> Servico.DiaSemana.valueOf(dia.toUpperCase()))
                .collect(Collectors.toList());
        servico.setDiasDisponiveisCliente(diasDisponiveis);

        // Converter periodoDisponivelCliente para Enum
        servico.setPeriodoDisponivelCliente(Servico.Periodo.valueOf(dto.getPeriodoDisponivelCliente().toUpperCase()));

        Servico salvo = servicoRepository.save(servico);
        return toResponseDTO(salvo);
    }

    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ServicoResponseDTO buscarPorId(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        return toResponseDTO(servico);
    }

public ServicoResponseDTO aceitarServico(Long id, Long administradorId, String data, String horario) {
    Servico servico = servicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

    Usuario administrador = usuarioRepository.findById(administradorId)
            .orElseThrow(() -> new RuntimeException("Administrador não encontrado"));

    LocalDate dataAgendada = LocalDate.parse(data);
    LocalTime horarioAgendado = LocalTime.parse(horario);

    // VALIDAÇÃO 1: Dia da semana
    Servico.DiaSemana diaSemanaAgendado = converterDiaSemana(dataAgendada.getDayOfWeek().name());

    if (!servico.getDiasDisponiveisCliente().contains(diaSemanaAgendado)) {
        throw new RuntimeException("O dia selecionado não está disponível para o cliente.");
    }

    // VALIDAÇÃO 2: Horário dentro do período
    if (!validarHorarioDentroPeriodo(servico.getPeriodoDisponivelCliente(), horarioAgendado)) {
        throw new RuntimeException("O horário selecionado não está dentro do período disponível do cliente.");
    }

    // Se passou nas validações
    servico.setStatus(Servico.StatusServico.ACEITO);
    servico.setAdministrador(administrador);
    servico.setData(dataAgendada);
    servico.setHorario(horarioAgendado);

    Servico atualizado = servicoRepository.save(servico);
    return toResponseDTO(atualizado);
}


    public ServicoResponseDTO recusarServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(Servico.StatusServico.RECUSADO);
        return toResponseDTO(servicoRepository.save(servico));
    }

    public ServicoResponseDTO cancelarServico(Long id, String motivo) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(Servico.StatusServico.CANCELADO);
        servico.setMotivoCancelamento(motivo);
        return toResponseDTO(servicoRepository.save(servico));
    }

    public ServicoResponseDTO concluirServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(Servico.StatusServico.CONCLUIDO);
        return toResponseDTO(servicoRepository.save(servico));
    }

    private ServicoResponseDTO toResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setTipoServico(servico.getTipoServico().getNome());
        dto.setClienteNome(servico.getCliente().getNome());
        dto.setEmailContato(servico.getEmailContato());
        dto.setTelefoneContato(servico.getTelefoneContato());

        // Mapear Dias Disponíveis
        List<String> dias = servico.getDiasDisponiveisCliente()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        dto.setDiasDisponiveisCliente(dias);

        // Mapear Período Disponível
        dto.setPeriodoDisponivelCliente(servico.getPeriodoDisponivelCliente().name());

        dto.setStatus(servico.getStatus().name());
        if (servico.getAdministrador() != null) {
            dto.setAdministradorNome(servico.getAdministrador().getNome());
        }
        dto.setData(servico.getData());
        dto.setHorario(servico.getHorario());
        return dto;
    }

    private Servico.DiaSemana converterDiaSemana(String dayOfWeek) {
    switch (dayOfWeek) {
        case "MONDAY":
            return Servico.DiaSemana.SEGUNDA;
        case "TUESDAY":
            return Servico.DiaSemana.TERCA;
        case "WEDNESDAY":
            return Servico.DiaSemana.QUARTA;
        case "THURSDAY":
            return Servico.DiaSemana.QUINTA;
        case "FRIDAY":
            return Servico.DiaSemana.SEXTA;
        case "SATURDAY":
            return Servico.DiaSemana.SABADO;
        case "SUNDAY":
            return Servico.DiaSemana.DOMINGO;
        default:
            throw new IllegalArgumentException("Dia da semana inválido.");
    }
}

private boolean validarHorarioDentroPeriodo(Servico.Periodo periodoCliente, LocalTime horario) {
    switch (periodoCliente) {
        case MANHA:
            return horario.isAfter(LocalTime.of(5, 59)) && horario.isBefore(LocalTime.of(12, 0));
        case TARDE:
            return horario.isAfter(LocalTime.of(11, 59)) && horario.isBefore(LocalTime.of(18, 0));
        case NOITE:
            return horario.isAfter(LocalTime.of(17, 59)) && horario.isBefore(LocalTime.of(23, 59));
        default:
            return false;
    }
}

}
