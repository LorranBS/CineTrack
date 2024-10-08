package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.repositories.ObraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    @Autowired
    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    public Obra criarObra(Obra obra) {
        if (obra == null) {
            throw new IllegalArgumentException("A obra não pode ser nula.");
        }
        return obraRepository.save(obra);
    }

    public void removerObra(UUID obraId) {
        if (obraId == null) {
            throw new IllegalArgumentException("O ID da obra não pode ser nulo.");
        }

        try {
            obraRepository.deleteById(obraId);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Obra não localizada para o ID: " + obraId);
        }
    }

    public Obra atualizarObra(UUID obraAntiga, Obra obra) {
        if (obra == null || obra.getId() == null) {
            throw new IllegalArgumentException("A obra ou o ID da obra não pode ser nulo.");
        }

        Optional<Obra> obraExistenteOptional = obraRepository.findById(obraAntiga);

        if (obraExistenteOptional.isPresent()) {
            Obra obraExistente = obraExistenteOptional.get();

            obraExistente.setTitulo(obra.getTitulo());

            obraRepository.save(obraExistente);
        } else {
            throw new EntityNotFoundException("Obra não encontrada com o ID: " + obra.getId());
        }
        return obra;
    }

    public List<Obra> listarObras() {
        return obraRepository.findAll();
    }

    public List<Obra> buscarPorGenero(String genero) {
        List<Obra> obras = obraRepository.buscarPorGenero(genero);
        if (obras.isEmpty()) {
            throw new RuntimeException("Nenhuma obra encontrada para o gênero: " + genero);
        }
        return obras;
    }

    public Obra buscarPorTitulo(String titulo) {
        return obraRepository.buscarPorTitulo(titulo)
                .orElseThrow(() -> new RuntimeException("Obra não localizada para o título: " + titulo));
    }

    public List<Obra> buscarPorDiretor(String diretor) {
        List<Obra> obras = obraRepository.buscarPorDiretor(diretor);
        if (obras.isEmpty()) {
            throw new RuntimeException("Nenhuma obra encontrada para o diretor: " + diretor);
        }
        return obras;
    }
}
