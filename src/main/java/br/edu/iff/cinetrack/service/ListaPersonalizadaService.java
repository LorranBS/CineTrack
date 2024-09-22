package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario; // Certifique-se de importar a entidade Usuario
import br.edu.iff.cinetrack.repositories.ListaPersonalizadaRepository;
import br.edu.iff.cinetrack.repositories.UsuarioRepository; // Adicione a importação do repositório de Usuario
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListaPersonalizadaService {

    private final ListaPersonalizadaRepository listaPersonalizadaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ListaPersonalizadaService(ListaPersonalizadaRepository listaPersonalizadaRepository, UsuarioRepository usuarioRepository) {
        this.listaPersonalizadaRepository = listaPersonalizadaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ListaPersonalizada> buscarPeloCriador(UUID criadorId) {
        return listaPersonalizadaRepository.buscarPorCriadorId(criadorId);
    }

    public ListaPersonalizada criarListaPersonalizada(UUID criadorId, ListaPersonalizada listaPersonalizada) {
        Usuario criador = usuarioRepository.findById(criadorId)
                .orElseThrow(() -> new RuntimeException("Usuário não localizado para o ID: " + criadorId));

        listaPersonalizada.setCriador(criador);
        return listaPersonalizadaRepository.save(listaPersonalizada);
    }

    public void deletarListaPersonalizada(UUID id) {
        if (!listaPersonalizadaRepository.existsById(id)) {
            throw new RuntimeException("Lista personalizada não localizada para o ID: " + id);
        }
        listaPersonalizadaRepository.deleteById(id);
    }

    public void adicionarObraNaLista(UUID idLista, Obra obra) {
        ListaPersonalizada lista = listaPersonalizadaRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista personalizada não localizada para o ID: " + idLista));

        // Verifica se a obra já existe na lista
        boolean obraJaExiste = lista.getObras().stream()
                .anyMatch(obraExistente -> obraExistente.getId().equals(obra.getId()));

        if (obraJaExiste) {
            throw new RuntimeException("A obra já está presente na lista personalizada.");
        }

        // Adiciona a obra na lista de obras, caso não exista
        lista.getObras().add(obra);

        // Salva a lista atualizada
        listaPersonalizadaRepository.save(lista);
    }

    public void removerObraDaLista(UUID idLista, UUID obraId) {
        ListaPersonalizada lista = listaPersonalizadaRepository.findById(idLista)
                .orElseThrow(() -> new RuntimeException("Lista personalizada não localizada para o ID: " + idLista));

        Obra obra = lista.getObras().stream()
                .filter(o -> o.getId().equals(obraId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Obra não localizada para o ID: " + obraId));

        lista.getObras().remove(obra);
        listaPersonalizadaRepository.save(lista);
    }

    public ListaPersonalizada buscarPorId(UUID id) {
        return listaPersonalizadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista personalizada não localizada para o ID: " + id));
    }
}
