package com.example.gestiongastos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestiongastos.model.Ingreso;
import com.example.gestiongastos.model.Usuario;

import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByUsuarioOrderByFechaDesc(Usuario usuario);
}
