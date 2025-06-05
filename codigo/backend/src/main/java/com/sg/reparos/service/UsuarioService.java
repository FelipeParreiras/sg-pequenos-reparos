package com.sg.reparos.service;

import com.sg.reparos.dto.UsuarioUpdateDTO;
import com.sg.reparos.model.Usuario;
import com.sg.reparos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Verificar se o username está mudando
    if (!usuario.getUsername().equals(dto.getUsername())) {
        boolean usernameExists = usuarioRepository.existsByUsername(dto.getUsername());
        if (usernameExists) {
            throw new RuntimeException("Username já está em uso por outro usuário");
        }
        usuario.setUsername(dto.getUsername());
    }

    usuario.setNome(dto.getNome());
    usuario.setEmail(dto.getEmail());
    usuario.setTelefone(dto.getTelefone());

    if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
    }

    return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
