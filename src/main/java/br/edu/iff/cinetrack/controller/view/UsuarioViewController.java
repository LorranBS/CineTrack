package br.edu.iff.cinetrack.controller.view;

import br.edu.iff.cinetrack.entities.DiarioItem;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Listar todos os usuários
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listar"; // Nome do template Thymeleaf
    }

    // Criar um novo usuário
    @PostMapping("/criar")
    public String criarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Exibir um usuário pelo ID
    @GetMapping("/{id}")
    public String exibirUsuario(@PathVariable UUID id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/exibir";
    }

    // Exibir o diário de um usuário pelo ID do usuário
    @GetMapping("/{id}/diario")
    public String exibirDiario(@PathVariable UUID id, Model model) {
        List<DiarioItem> diario = usuarioService.buscarDiarioDoUsuario(id);
        model.addAttribute("diario", diario);
        model.addAttribute("usuarioId", id);
        return "usuarios/diario";
    }

    // Exibir listas personalizadas de um usuário
    @GetMapping("/{id}/listas")
    public String exibirListas(@PathVariable UUID id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("listas", usuario.getListasPersonalizadas());
        model.addAttribute("usuarioId", id);
        return "usuarios/listas";
    }

    // Deletar um usuário pelo ID
    @PostMapping("/{id}/deletar")
    public String deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}
