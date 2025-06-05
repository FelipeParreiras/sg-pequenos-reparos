package com.sg.reparos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private String username;
    
    private String senha;

    public enum TipoUsuario {
        CLIENTE,
        ADMIN;

        @Override
        public String toString() {
            return name();
        }
    }
}
