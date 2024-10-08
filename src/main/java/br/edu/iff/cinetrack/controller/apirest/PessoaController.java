package br.edu.iff.cinetrack.controller.apirest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.cinetrack.entities.Pessoa;
import br.edu.iff.cinetrack.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Busca uma pessoa pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<EntityModel<Pessoa>> buscarPorNome(@PathVariable String nome) {
        Pessoa pessoa = pessoaService.buscarPorNome(nome);
        EntityModel<Pessoa> resource = EntityModel.of(pessoa);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PessoaController.class).buscarPorNome(nome)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
