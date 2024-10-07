package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.DiarioItem;
import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.exceptions.UserNotFoundException;
import br.edu.iff.cinetrack.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ListaPersonalizadaService listaPersonalizadaService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ListaPersonalizadaService listaPersonalizadaService) {
        this.usuarioRepository = usuarioRepository;
        this.listaPersonalizadaService = listaPersonalizadaService;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(UUID id) {
        
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não localizado para o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Usuario buscarPorNome(String nome) {
        return usuarioRepository.buscarPorNome(nome)
                .orElseThrow(() -> new RuntimeException("Usuário não localizado para o nome: " + nome));
    }

    public List<DiarioItem> buscarDiarioDoUsuario(UUID usuarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        return usuario.getDiario();
    }

    public void adicionarObraAoDiario(UUID usuarioId, DiarioItem diarioItem) {
        Usuario usuario = buscarPorId(usuarioId);
        usuario.getDiario().add(diarioItem);
        diarioItem.setUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    public void removerObraDoDiario(UUID usuarioId, UUID diarioId) {
        Usuario usuario = buscarPorId(usuarioId);
        DiarioItem diarioItem = usuario.getDiario().stream()
                .filter(d -> d.getId().equals(diarioId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Diário não localizado para o ID: " + diarioId));
        usuario.getDiario().remove(diarioItem);
        usuarioRepository.save(usuario);
    }

    public void adicionarListaPersonalizada(UUID usuarioId, ListaPersonalizada lista) {
        Usuario usuario = buscarPorId(usuarioId);
        usuario.getListasPersonalizadas().add(lista);
        lista.setCriador(usuario);

        usuarioRepository.save(usuario);
    }

    public void removerListaPersonalizada(UUID usuarioId, UUID listaId) {
        Usuario usuario = buscarPorId(usuarioId);
        ListaPersonalizada lista = usuario.getListasPersonalizadas().stream()
                .filter(l -> l.getId().equals(listaId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lista personalizada não localizada para o ID: " + listaId));
        usuario.getListasPersonalizadas().remove(lista);
        usuarioRepository.save(usuario);
    }

    public void adicionarObraNaListaPersonalizada(UUID usuarioId, UUID listaId, Obra obra) {
        // Verifica se o usuário e a lista personalizada existem
        Usuario usuario = buscarPorId(usuarioId);
        ListaPersonalizada lista = listaPersonalizadaService.buscarPorId(listaId);

        // Verifica se a lista personalizada pertence ao usuário
        if (!lista.getCriador().getId().equals(usuario.getId())) {
            throw new RuntimeException("A lista personalizada não pertence ao usuário.");
        }

        // Adiciona a obra na lista personalizada
        listaPersonalizadaService.adicionarObraNaLista(listaId, obra);
    }

    public void removerObraDaListaPersonalizada(UUID usuarioId, UUID listaId, UUID obraId) {
        Usuario usuario = buscarPorId(usuarioId);
        ListaPersonalizada lista = listaPersonalizadaService.buscarPorId(listaId);

        if (!lista.getCriador().getId().equals(usuario.getId())) {
            throw new RuntimeException("A lista personalizada não pertence ao usuário.");
        }

        listaPersonalizadaService.removerObraDaLista(listaId, obraId);
    }
}
