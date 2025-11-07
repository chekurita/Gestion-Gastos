package com.example.gestiongastos.controller;

import com.example.gestiongastos.dto.Request.IngresoRequest;
import com.example.gestiongastos.dto.Response.IngresoResponse;
import com.example.gestiongastos.services.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingresos")
public class IngresoController {

    @Autowired
    private IngresoService ingresoService;

    @PostMapping
    public ResponseEntity<IngresoResponse> crearIngreso(@RequestBody IngresoRequest request) {
        
        IngresoResponse response = ingresoService.create(request);
        

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}