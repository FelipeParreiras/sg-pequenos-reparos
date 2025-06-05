package com.sg.reparos.controller;

import com.sg.reparos.model.AuthRequest;
import com.sg.reparos.model.AuthResponse;
import com.sg.reparos.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permitir CORS para o frontend
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            // Tenta autenticar
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getSenha()
                    );

            authenticationManager.authenticate(authInputToken);

            // Se não lançou exceção, autenticação foi ok
            String token = jwtUtil.generateToken(authRequest.getUsername());
            return new AuthResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
