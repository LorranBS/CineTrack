package br.edu.iff.cinetrack.controller.view;

import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.service.ListaPersonalizadaService;
import br.edu.iff.cinetrack.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/listas")
public class ListaPersonalizadaViewController {

    private final ListaPersonalizadaService listaPersonalizadaService;
    private final UsuarioService usuarioService;

    @Autowired
    public ListaPersonalizadaViewController(ListaPersonalizadaService listaPersonalizadaService, UsuarioService usuarioService) {
        this.listaPersonalizadaService = listaPersonalizadaService;
        this.usuarioService = usuarioService;
    }

    // Página para listar as listas personalizadas do usuário
    @GetMapping("/usuario/{usuarioId}")
    public String listarListasPorUsuario(@PathVariable("usuarioId") UUID usuarioId, Model model) {
        List<ListaPersonalizada> listas = listaPersonalizadaService.buscarPeloCriador(usuarioId);
        model.addAttribute("listas", listas);
        model.addAttribute("usuario", usuarioService.buscarPorId(usuarioId));
        return "listas/lista-personalizada";
    }

    // Página para exibir detalhes de uma lista personalizada específica
    @GetMapping("/{listaId}")
    public String verListaPersonalizada(@PathVariable("listaId") UUID listaId, Model model) {
        ListaPersonalizada lista = listaPersonalizadaService.buscarPorId(listaId);
        model.addAttribute("lista", lista);
        return "listas/detalhes-lista";
    }

    // Processa o formulário de criação de lista personalizada
    @PostMapping("/criar")
    public String criarListaPersonalizada(@RequestParam("usuarioId") UUID usuarioId, @ModelAttribute ListaPersonalizada listaPersonalizada) {
        listaPersonalizadaService.criarListaPersonalizada(usuarioId, listaPersonalizada);
        return "redirect:/listas/usuario/" + usuarioId;
    }

    // Deletar uma lista personalizada
    @PostMapping("/deletar/{listaId}")
    public String deletarListaPersonalizada(@PathVariable("listaId") UUID listaId, @RequestParam("usuarioId") UUID usuarioId) {
        listaPersonalizadaService.deletarListaPersonalizada(listaId);
        return "redirect:/listas/usuario/" + usuarioId;
    }

    // Adicionar uma obra à lista personalizada
    @PostMapping("/adicionar-obra/{listaId}")
    public String adicionarObraNaLista(@PathVariable("listaId") UUID listaId, @ModelAttribute Obra obra) {
        listaPersonalizadaService.adicionarObraNaLista(listaId, obra);
        return "redirect:/listas/" + listaId;
    }

    // Remover uma obra da lista personalizada
    @PostMapping("/remover-obra/{listaId}/{obraId}")
    public String removerObraDaLista(@PathVariable("listaId") UUID listaId, @PathVariable("obraId") UUID obraId) {
        listaPersonalizadaService.removerObraDaLista(listaId, obraId);
        return "redirect:/listas/" + listaId; // Redireciona para a página da lista personalizada
    }
}
