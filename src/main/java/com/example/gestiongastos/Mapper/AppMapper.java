package com.example.gestiongastos.Mapper;

import com.example.gestiongastos.dto.Response.CategoriaResponse;
import com.example.gestiongastos.dto.Response.GastoResponse;
import com.example.gestiongastos.dto.Response.IngresoResponse;
import com.example.gestiongastos.dto.Response.UsuarioResponse;
import com.example.gestiongastos.model.Categoria;
import com.example.gestiongastos.model.Gasto;
import com.example.gestiongastos.model.Ingreso;
import com.example.gestiongastos.model.Usuario;

public class AppMapper {
    public static UsuarioResponse toUsuarioResponse(Usuario u) {
        if (u == null) return null;
        UsuarioResponse r = new UsuarioResponse();
        r.setId(u.getId());
        r.setNombre(u.getNombre());
        r.setEmail(u.getEmail());
        return r;
    }

    public static CategoriaResponse toCategoriaResponse(Categoria c) {
        if (c == null) return null;
        CategoriaResponse r = new CategoriaResponse();
        r.setId(c.getId());
        r.setNombre(c.getNombre());
        return r;
    }

    public static GastoResponse toGastoResponse(Gasto g) {
        if (g == null) return null;
        GastoResponse r = new GastoResponse();
        r.setId(g.getId());
        r.setDescripcion(g.getDescripcion());
        r.setMonto(g.getMonto());
        r.setFecha(g.getFecha());
        if (g.getUsuario() != null) r.setUsuarioId(g.getUsuario().getId());
        if (g.getCategoria() != null) {
            r.setCategoriaId(g.getCategoria().getId());
            r.setCategoriaNombre(g.getCategoria().getNombre());
        }
        return r;
    }
    
    public static IngresoResponse toIngresoResponse(Ingreso i) {
        if (i == null) return null;
        
        return new IngresoResponse(
            i.getId(),
            i.getMonto(),
            i.getDescripcion(),
            i.getFecha(),
            (i.getCategoria() != null) ? i.getCategoria().getNombre() : null,
            "ingreso"
        );
    }
}
