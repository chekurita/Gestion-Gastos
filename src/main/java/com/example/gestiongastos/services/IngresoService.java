package com.example.gestiongastos.services;

import java.util.List;

import com.example.gestiongastos.dto.Request.IngresoRequest;
import com.example.gestiongastos.dto.Response.IngresoResponse;

public interface IngresoService {
    
    IngresoResponse create(IngresoRequest req);

    List<IngresoResponse> listByUsuario(Long usuarioId);

    IngresoResponse getById(Long id);

    void delete(Long id);
}