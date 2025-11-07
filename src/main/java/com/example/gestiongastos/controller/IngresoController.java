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

import com.example.gestiongastos.dto.Request.IngresoRequest;
import com.example.gestiongastos.dto.Response.IngresoResponse;
import com.example.gestiongastos.services.IngresoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    private final IngresoService ingresoService;

    public IngresoController(IngresoService ingresoService) {
        this.ingresoService = ingresoService;
    }

    @PostMapping
    public ResponseEntity<IngresoResponse> create(@Valid @RequestBody IngresoRequest req) {
        return ResponseEntity.ok(ingresoService.create(req));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<IngresoResponse>> listByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ingresoService.listByUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngresoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ingresoService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingresoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
