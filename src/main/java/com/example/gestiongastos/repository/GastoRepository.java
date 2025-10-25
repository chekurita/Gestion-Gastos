package com.example.gestiongastos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestiongastos.model.Gasto;
import com.example.gestiongastos.model.Usuario;

import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    List<Gasto> findByUsuarioOrderByFechaDesc(Usuario usuario);
}