package com.example.gestiongastos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestiongastos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}