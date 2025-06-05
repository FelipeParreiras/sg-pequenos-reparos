package com.sg.reparos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")

public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    private String senha;

    public enum TipoUsuario {
        CLIENTE,
        ADMIN
    }
}
