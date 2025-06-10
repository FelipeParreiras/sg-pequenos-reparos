package com.sg.reparos.service;

import com.sg.reparos.dto.ServicoRequestDTO;
import com.sg.reparos.dto.ServicoResponseDTO;
import com.sg.reparos.model.Servico;
import com.sg.reparos.model.Servico.DiaSemana;
import com.sg.reparos.model.Servico.Periodo;
import com.sg.reparos.model.Servico.StatusServico;
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
        servico.setStatus(StatusServico.SOLICITADO);

        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        TipoServico tipoServico = tipoServicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new RuntimeException("Tipo de serviço não encontrado"));

        servico.setCliente(cliente);
        servico.setTipoServico(tipoServico);

        List<DiaSemana> diasDisponiveis = dto.getDiasDisponiveisCliente().stream()
                .map(dia -> DiaSemana.valueOf(dia.toUpperCase()))
                .collect(Collectors.toList());
        servico.setDiasDisponiveisCliente(diasDisponiveis);

        servico.setPeriodoDisponivelCliente(Periodo.valueOf(dto.getPeriodoDisponivelCliente().toUpperCase()));

        Servico salvo = servicoRepository.save(servico);
        return toResponseDTO(salvo);
    }

    public List<ServicoResponseDTO> listarTodos() {
        return servicoRepository.findAll().stream()
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

        DiaSemana diaSemanaAgendado = converterDiaSemana(dataAgendada.getDayOfWeek().name());
        if (!servico.getDiasDisponiveisCliente().contains(diaSemanaAgendado)) {
            throw new RuntimeException("O dia selecionado não está disponível para o cliente.");
        }

        if (!validarHorarioDentroPeriodo(servico.getPeriodoDisponivelCliente(), horarioAgendado)) {
            throw new RuntimeException("O horário selecionado não está dentro do período disponível do cliente.");
        }

        servico.setStatus(StatusServico.ACEITO);
        servico.setAdministrador(administrador);
        servico.setData(dataAgendada);
        servico.setHorario(horarioAgendado);

        Servico atualizado = servicoRepository.save(servico);
        return toResponseDTO(atualizado);
    }

    public ServicoResponseDTO recusarServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(StatusServico.RECUSADO);
        return toResponseDTO(servicoRepository.save(servico));
    }

    public ServicoResponseDTO cancelarServico(Long id, String motivo) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(StatusServico.CANCELADO);
        servico.setMotivoCancelamento(motivo);
        return toResponseDTO(servicoRepository.save(servico));
    }

    public ServicoResponseDTO concluirServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        servico.setStatus(StatusServico.CONCLUIDO);
        return toResponseDTO(servicoRepository.save(servico));
    }

    private ServicoResponseDTO toResponseDTO(Servico servico) {
        ServicoResponseDTO dto = new ServicoResponseDTO();
        dto.setId(servico.getId());
        dto.setNome(servico.getNome());
        dto.setDescricao(servico.getDescricao());
        dto.setTipoServico(servico.getTipoServico().getNome());
        dto.setClienteId(servico.getCliente().getId());
        dto.setClienteNome(servico.getCliente().getNome());
        dto.setEmailContato(servico.getEmailContato());
        dto.setTelefoneContato(servico.getTelefoneContato());

        List<String> dias = servico.getDiasDisponiveisCliente().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        dto.setDiasDisponiveisCliente(dias);

        dto.setPeriodoDisponivelCliente(servico.getPeriodoDisponivelCliente().name());

        dto.setStatus(servico.getStatus().name());
        if (servico.getAdministrador() != null) {
            dto.setAdministradorNome(servico.getAdministrador().getNome());
        }
        dto.setData(servico.getData());
        dto.setHorario(servico.getHorario());
        return dto;
    }

    private DiaSemana converterDiaSemana(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "MONDAY" -> DiaSemana.SEGUNDA;
            case "TUESDAY" -> DiaSemana.TERCA;
            case "WEDNESDAY" -> DiaSemana.QUARTA;
            case "THURSDAY" -> DiaSemana.QUINTA;
            case "FRIDAY" -> DiaSemana.SEXTA;
            case "SATURDAY" -> DiaSemana.SABADO;
            case "SUNDAY" -> DiaSemana.DOMINGO;
            default -> throw new IllegalArgumentException("Dia da semana inválido.");
        };
    }

    private boolean validarHorarioDentroPeriodo(Periodo periodoCliente, LocalTime horario) {
        return switch (periodoCliente) {
            case MANHA -> horario.isAfter(LocalTime.of(5, 59)) && horario.isBefore(LocalTime.of(12, 0));
            case TARDE -> horario.isAfter(LocalTime.of(11, 59)) && horario.isBefore(LocalTime.of(18, 0));
            case NOITE -> horario.isAfter(LocalTime.of(17, 59)) && horario.isBefore(LocalTime.of(23, 59));
        };
    }

    public ServicoResponseDTO editarServico(Long id, ServicoRequestDTO dto) {
        System.out.println("Editando serviço ID: " + id);
        System.out.println("DTO recebido: " + dto);

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());

        TipoServico tipo = tipoServicoRepository.findById(dto.getTipoServicoId())
                .orElseThrow(() -> new RuntimeException("Tipo de serviço não encontrado"));
        servico.setTipoServico(tipo);

        if (dto.getClienteId() == null) {
            throw new IllegalArgumentException("O ID do cliente (clienteId) não pode ser nulo na edição do serviço.");
        }
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + dto.getClienteId()));
        servico.setCliente(cliente);
        System.out.println("Serviço atualizado com cliente ID: " + cliente.getId());

        servico.setEmailContato(dto.getEmailContato());
        servico.setTelefoneContato(dto.getTelefoneContato());

        List<DiaSemana> dias = dto.getDiasDisponiveisCliente().stream()
                .map(String::toUpperCase)
                .map(DiaSemana::valueOf)
                .collect(Collectors.toList());

        try {
            servico.setPeriodoDisponivelCliente(Periodo.valueOf(dto.getPeriodoDisponivelCliente().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Período inválido: " + dto.getPeriodoDisponivelCliente());
        }

        if (dto.getData() != null) {
            servico.setData(dto.getData());
        }
        if (dto.getHorario() != null) {
            servico.setHorario(dto.getHorario());
        }
        if (dto.getStatus() != null) {
            try {
                servico.setStatus(StatusServico.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Status inválido: " + dto.getStatus());
            }
        }

        servicoRepository.save(servico);
        return new ServicoResponseDTO(servico);
    }
}
