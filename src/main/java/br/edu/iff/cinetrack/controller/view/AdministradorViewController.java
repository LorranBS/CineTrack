package br.edu.iff.cinetrack.controller.view;

import br.edu.iff.cinetrack.entities.Administrador;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdministradorViewController {

    private final AdministradorService administradorService;

    @Autowired
    public AdministradorViewController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    // Página para criar um novo administrador
    @GetMapping("/criar")
    public String criarAdministradorForm(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "admin/criar-administrador";
    }

    // Processa o formulário de criação de administrador
    @PostMapping("/criar")
    public String criarAdministrador(@ModelAttribute Administrador administrador) {
        administradorService.criarAdministrador(administrador);
        return "redirect:/admin/listar";
    }

    // Deletar um administrador
    @PostMapping("/deletar/{id}")
    public String deletarAdministrador(@PathVariable("id") UUID id) {
        administradorService.deletarAdministrador(id);
        return "redirect:/admin/listar";
    }

    // Página para criar um novo usuário
    @GetMapping("/usuario/criar")
    public String criarUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/criar-usuario";
    }

    // Processa o formulário de criação de usuário
    @PostMapping("/usuario/criar")
    public String criarUsuario(@ModelAttribute Usuario usuario) {
        administradorService.criarUsuario(usuario);
        return "redirect:/admin/usuario/listar";
    }

    // Deletar um usuário
    @PostMapping("/usuario/deletar/{id}")
    public String deletarUsuario(@PathVariable("id") UUID id) {
        administradorService.deletarUsuario(id);
        return "redirect:/admin/usuario/listar";
    }

    // Página para criar uma nova obra
    @GetMapping("/obra/criar")
    public String criarObraForm(Model model) {
        model.addAttribute("obra", new Obra());
        return "admin/criar-obra";
    }

    // Processa o formulário de criação de obra
    @PostMapping("/obra/criar")
    public String criarObra(@ModelAttribute Obra obra) {
        administradorService.criarObra(obra);
        return "redirect:/admin/obra/listar";
    }

    // Deletar uma obra
    @PostMapping("/obra/deletar/{id}")
    public String deletarObra(@PathVariable("id") UUID id) {
        administradorService.removerObra(id);
        return "redirect:/admin/obra/listar";
    }
}
