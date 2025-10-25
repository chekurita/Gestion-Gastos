package com.example.gestiongastos.dto.Request;

import jakarta.validation.constraints.NotBlank;

public class CategoriaRequest {
		@NotBlank
	    private String nombre;

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		

}
