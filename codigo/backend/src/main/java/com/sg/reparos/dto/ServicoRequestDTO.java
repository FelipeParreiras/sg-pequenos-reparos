package com.sg.reparos.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class ServicoRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private Long tipoServicoId;

    @NotNull
    private Long clienteId;

    @Email
    @NotBlank
    private String emailContato;

    @NotBlank
    private String telefoneContato;

    @NotEmpty
    private List<String> diasDisponiveisCliente; // Lista de dias da semana (ex: ["SEGUNDA", "TERCA"])

    @NotBlank
    private String periodoDisponivelCliente;     // Periodo (ex: "TARDE")

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getTipoServicoId() {
        return tipoServicoId;
    }

    public void setTipoServicoId(Long tipoServicoId) {
        this.tipoServicoId = tipoServicoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public List<String> getDiasDisponiveisCliente() {
        return diasDisponiveisCliente;
    }

    public void setDiasDisponiveisCliente(List<String> diasDisponiveisCliente) {
        this.diasDisponiveisCliente = diasDisponiveisCliente;
    }

    public String getPeriodoDisponivelCliente() {
        return periodoDisponivelCliente;
    }

    public void setPeriodoDisponivelCliente(String periodoDisponivelCliente) {
        this.periodoDisponivelCliente = periodoDisponivelCliente;
    }
}