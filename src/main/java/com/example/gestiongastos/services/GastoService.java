package com.example.gestiongastos.services;

import java.util.List;

import com.example.gestiongastos.dto.Request.GastoRequest;
import com.example.gestiongastos.dto.Response.GastoResponse;

public interface GastoService {
    GastoResponse create(GastoRequest request);
    List<GastoResponse> listByUsuario(Long usuarioId);
    GastoResponse getById(Long id);
    void delete(Long id);
}