package com.example.gestiongastos.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.gestiongastos.Mapper.AppMapper;
import com.example.gestiongastos.dto.Request.CategoriaRequest;
import com.example.gestiongastos.dto.Response.CategoriaResponse;
import com.example.gestiongastos.model.Categoria;
import com.example.gestiongastos.repository.CategoriaRepository;
import com.example.gestiongastos.services.CategoriaService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoriaServiceImp implements CategoriaService {
	private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImp(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponse create(CategoriaRequest request) {
        Categoria c = new Categoria();
        c.setNombre(request.getNombre());
        Categoria saved = categoriaRepository.save(c);
        return AppMapper.toCategoriaResponse(saved);
    }

    public List<CategoriaResponse> listAll() {
        return categoriaRepository.findAll().stream()
                .map(AppMapper::toCategoriaResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponse getById(Long id) {
        return categoriaRepository.findById(id)
                .map(AppMapper::toCategoriaResponse)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

}
