package com.example.gestiongastos.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.gestiongastos.Mapper.AppMapper;
import com.example.gestiongastos.dto.Request.GastoRequest;
import com.example.gestiongastos.dto.Response.GastoResponse;
import com.example.gestiongastos.model.Categoria;
import com.example.gestiongastos.model.Gasto;
import com.example.gestiongastos.model.Usuario;
import com.example.gestiongastos.repository.CategoriaRepository;
import com.example.gestiongastos.repository.GastoRepository;
import com.example.gestiongastos.repository.UsuarioRepository;
import com.example.gestiongastos.services.GastoService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GastoServiceImp implements GastoService {
	private final GastoRepository gastoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public GastoServiceImp(GastoRepository gastoRepository,
                            UsuarioRepository usuarioRepository,
                            CategoriaRepository categoriaRepository) {
        this.gastoRepository = gastoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public GastoResponse create(GastoRequest req) {
        Usuario u = usuarioRepository.findById(req.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Categoria c = null;
        if (req.getCategoriaId() != null) {
            c = categoriaRepository.findById(req.getCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        }
        Gasto g = new Gasto();
        g.setDescripcion(req.getDescripcion());
        g.setMonto(req.getMonto());
        g.setFecha(req.getFecha());
        g.setUsuario(u);
        g.setCategoria(c);
        Gasto saved = gastoRepository.save(g);
        return AppMapper.toGastoResponse(saved);
    }

    public List<GastoResponse> listByUsuario(Long usuarioId) {
        Usuario u = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return gastoRepository.findByUsuarioOrderByFechaDesc(u).stream()
                .map(AppMapper::toGastoResponse)
                .collect(Collectors.toList());
    }

    public GastoResponse getById(Long id) {
        return gastoRepository.findById(id)
                .map(AppMapper::toGastoResponse)
                .orElseThrow(() -> new IllegalArgumentException("Gasto no encontrado"));
    }

    public void delete(Long id) {
        if (!gastoRepository.existsById(id)) {
            throw new IllegalArgumentException("Gasto no encontrado");
        }
        gastoRepository.deleteById(id);
    }

}
