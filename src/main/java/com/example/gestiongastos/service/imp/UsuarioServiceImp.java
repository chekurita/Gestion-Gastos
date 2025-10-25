package com.example.gestiongastos.service.imp;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gestiongastos.Mapper.AppMapper;
import com.example.gestiongastos.dto.Request.UsuarioLoginRequest;
import com.example.gestiongastos.dto.Request.UsuarioRegisterRequestDto;
import com.example.gestiongastos.dto.Response.UsuarioResponse;
import com.example.gestiongastos.model.Usuario;
import com.example.gestiongastos.repository.UsuarioRepository;
import com.example.gestiongastos.security.JwtUtil;
import com.example.gestiongastos.services.UsuarioService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServiceImp implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil; // 👈 inyectado por Spring

    // 👇 constructor con inyección automática
    public UsuarioServiceImp(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UsuarioResponse register(UsuarioRegisterRequestDto req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        Usuario saved = usuarioRepository.save(u);
        return AppMapper.toUsuarioResponse(saved);
    }

    @Override
    public UsuarioResponse login(UsuarioLoginRequest req) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(req.getEmail());
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        Usuario u = opt.get();
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // ✅ usamos el bean inyectado
        String token = jwtUtil.generateToken(u.getEmail());

        UsuarioResponse res = AppMapper.toUsuarioResponse(u);
        res.setToken(token);
        return res;
    }

    @Override
    public UsuarioResponse findById(Long id) {
        return usuarioRepository.findById(id)
                .map(AppMapper::toUsuarioResponse)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}
