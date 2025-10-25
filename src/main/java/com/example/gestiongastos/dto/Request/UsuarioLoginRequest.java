package com.example.gestiongastos.dto.Request;

import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    // getters y setters
    
}