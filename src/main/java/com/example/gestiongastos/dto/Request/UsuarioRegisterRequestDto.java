package com.example.gestiongastos.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRegisterRequestDto {
	 	@NotBlank
	    private String nombre;

	    @Email @NotBlank
	    private String email;

	    @NotBlank @Size(min = 6)
	    private String password;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

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
