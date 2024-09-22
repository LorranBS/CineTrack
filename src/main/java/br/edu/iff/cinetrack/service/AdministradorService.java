package br.edu.iff.cinetrack.service;

import br.edu.iff.cinetrack.entities.Administrador;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.repositories.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final UsuarioService usuarioService;
    private final ObraService obraService;

    @Autowired
    public AdministradorService(AdministradorRepository administradorRepository, UsuarioService usuarioService, ObraService obraService) {
        this.administradorRepository = administradorRepository;
        this.usuarioService = usuarioService;
        this.obraService = obraService;
    }

    public Administrador criarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void deletarAdministrador(UUID id) {
        if (!administradorRepository.existsById(id)) {
            throw new RuntimeException("Administrador não localizado para o ID: " + id);
        }
        administradorRepository.deleteById(id);
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    public void deletarUsuario(UUID id) {
        usuarioService.deletarUsuario(id);
    }

    public Obra criarObra(Obra obra) {
        return obraService.criarObra(obra);
    }

    public void removerObra(UUID obraId) {
        obraService.removerObra(obraId);
    }
}
