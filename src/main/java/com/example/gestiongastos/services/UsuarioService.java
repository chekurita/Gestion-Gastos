package com.example.gestiongastos.services;

import com.example.gestiongastos.dto.Request.UsuarioLoginRequest;
import com.example.gestiongastos.dto.Request.UsuarioRegisterRequestDto;
import com.example.gestiongastos.dto.Response.UsuarioResponse;
import com.example.gestiongastos.model.Usuario;

public interface UsuarioService {

	UsuarioResponse register(UsuarioRegisterRequestDto request);
    UsuarioResponse login(UsuarioLoginRequest request); // para MVP básico
    UsuarioResponse findById(Long id);
	Usuario findByEmail(String email);
}
