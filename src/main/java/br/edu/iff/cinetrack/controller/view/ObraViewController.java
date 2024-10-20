package br.edu.iff.cinetrack.controller.view;

import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.service.ObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/obras")
public class ObraViewController {

    private final ObraService obraService;

    @Autowired
    public ObraViewController(ObraService obraService) {
        this.obraService = obraService;
    }

    @GetMapping
    public String listarObras(Model model) {
        List<Obra> obras = obraService.listarObras();
        model.addAttribute("obras", obras);
        return "/obras/listarObras";
    }

    @GetMapping("/{id}")
    public String exibirObra(@PathVariable UUID id, Model model) {
        Obra obra = obraService.buscarPorTitulo(id.toString());
        model.addAttribute("obra", obra);
        return "exibirObra";
    }

    @PostMapping
    public String criarObra(@ModelAttribute Obra obra) {
        obraService.criarObra(obra);
        return "redirect:/obras";
    }

    @PostMapping("/{id}/atualizar")
    public String atualizarObra(@PathVariable UUID id, @ModelAttribute Obra obra) {
        obraService.atualizarObra(id, obra);
        return "redirect:/obras";
    }

    @PostMapping("/{id}/remover")
    public String removerObra(@PathVariable UUID id) {
        obraService.removerObra(id);
        return "redirect:/obras";
    }
}