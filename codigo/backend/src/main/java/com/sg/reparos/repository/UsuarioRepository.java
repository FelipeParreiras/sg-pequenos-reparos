package com.sg.reparos.repository;

import com.sg.reparos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aqui você pode adicionar métodos personalizados, se necessário
    // Por exemplo, para buscar usuários por email:
    // Optional<Usuario> findByEmail(String email);
}
