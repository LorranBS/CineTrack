package br.edu.iff.cinetrack.controller.view;

import br.edu.iff.cinetrack.entities.DiarioItem;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.ObraService;
import br.edu.iff.cinetrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    private final UsuarioService usuarioService;
    private final ObraService obraService; // Adiciona ObraService

    @Autowired
    public UsuarioViewController(UsuarioService usuarioService, ObraService obraService) {
        this.usuarioService = usuarioService;
        this.obraService = obraService;
    }

    // Listar todos os usuários
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        model.addAttribute("usuarios", usuarios);

        model.addAttribute("usuario", new Usuario());

        return "usuarios/listarUsuarios";
    }

    // Criar um novo usuário
    @PostMapping("/criar")
    public String criarUsuario(@ModelAttribute Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Usuario> usuarios = usuarioService.buscarTodos();
            model.addAttribute("usuarios", usuarios);
            return "usuarios/listarUsuarios";
        }

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
        List<Obra> obras = obraService.listarObras();
        model.addAttribute("diario", diario);
        model.addAttribute("obras", obras);
        model.addAttribute("usuarioId", id);
        model.addAttribute("diarioItem", new DiarioItem());
        return "usuarios/diario";
    }

    @PostMapping("/{id}/diario")
    public String adicionarDiarioItem(@PathVariable UUID id, @ModelAttribute("diarioItem") DiarioItem diarioItem, BindingResult result) {

        // Busca a obra selecionada pelo ID e define no diarioItem
        Obra obra = obraService.buscarObraPorId(diarioItem.getObra().getId());
        diarioItem.setObra(obra);

        // Setando o usuário que está adicionando o diário
        Usuario usuario = usuarioService.buscarPorId(id);
        diarioItem.setUsuario(usuario);

        // Adiciona o item ao diário
        usuarioService.adicionarObraAoDiario(id, diarioItem);

        return "redirect:/usuarios/" + id + "/diario"; // Redireciona para a página do diário
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
