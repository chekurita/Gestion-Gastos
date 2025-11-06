package com.example.gestiongastos.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IngresoResponse {
    private Long id;
    private BigDecimal monto;
    private String descripcion;
    private LocalDate fecha;
    private String categoriaNombre; 
    private String tipo;

    public IngresoResponse(Long id, BigDecimal monto, String descripcion, LocalDate fecha, String categoriaNombre, String tipo) {
        this.id = id;
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.categoriaNombre = categoriaNombre;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}