package com.example.gestiongastos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestiongastos.dto.Request.GastoRequest;
import com.example.gestiongastos.dto.Response.GastoResponse;
import com.example.gestiongastos.services.GastoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {
	
	private final GastoService gastoService;
    public GastoController(GastoService gastoService) {
        this.gastoService = gastoService;
    }

    @PostMapping
    public ResponseEntity<GastoResponse> create(@Valid @RequestBody GastoRequest req) {
        return ResponseEntity.ok(gastoService.create(req));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<GastoResponse>> listByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(gastoService.listByUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gastoService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gastoService.delete(id);
        return ResponseEntity.noContent().build();
    }
	

}
