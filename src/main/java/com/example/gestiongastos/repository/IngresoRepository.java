package com.example.gestiongastos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestiongastos.model.Ingreso;
import com.example.gestiongastos.model.Usuario;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    
    List<Ingreso> findByUsuarioOrderByFechaDesc(Usuario usuario);
    
}