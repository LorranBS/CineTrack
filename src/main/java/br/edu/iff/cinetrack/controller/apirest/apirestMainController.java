package br.edu.iff.cinetrack.controller.apirest;

import org.springframework.web.bind.annotation.RestController;

import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/api/v1")
public class apirestMainController {

    private UsuarioService usuarioService;

    @GetMapping("/usuario/{nome}")
    @ResponseBody
    public Usuario getMethodName(@PathVariable String nome) {
        return usuarioService.buscarPorNome(nome);
    }
    
    
}
