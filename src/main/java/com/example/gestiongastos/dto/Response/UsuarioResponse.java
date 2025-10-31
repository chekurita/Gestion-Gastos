package com.example.gestiongastos.dto.Response;

public class UsuarioResponse {
	 	
		private Long id;
	    private String nombre;
	    private String email;
	    private String token;
	    
	    
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
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

	    

}
