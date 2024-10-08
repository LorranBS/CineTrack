package br.edu.iff.cinetrack.controller.apirest;

import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.service.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/obra")
public class ObraController {

    private final ObraService obraService;

    @Autowired
    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }

    @Operation(summary = "Cria uma nova obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou obra nula")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Obra>> criarObra(@RequestBody Obra obra) {
        Obra novaObra = obraService.criarObra(obra);
        EntityModel<Obra> resource = EntityModel.of(novaObra);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(novaObra.getTitulo())).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Remove uma obra pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    })
    @DeleteMapping("/{obraId}")
    public ResponseEntity<Void> removerObra(@PathVariable UUID obraId) {
        obraService.removerObra(obraId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza uma obra existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou obra nula")
    })
    @PutMapping("/{obraId}")
    public ResponseEntity<EntityModel<Obra>> atualizarObra(@PathVariable UUID obraId, @RequestBody Obra obraAtualizada) {
        Obra obra = obraService.atualizarObra(obraId, obraAtualizada);

        EntityModel<Obra> resource = EntityModel.of(obra);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(obra.getTitulo())).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).listarObras()).withRel("all"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    @Operation(summary = "Lista todas as obras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Obra>>> listarObras() {
        List<Obra> obras = obraService.listarObras();
        List<EntityModel<Obra>> recursos = obras.stream()
                .map(obra -> EntityModel.of(obra,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(obra.getTitulo())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).listarObras()).withRel("all")))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Obra>> collectionModel = CollectionModel.of(recursos);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).listarObras()).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @Operation(summary = "Busca obras por gênero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada para o gênero")
    })
    @GetMapping("/genero/{genero}")
    public ResponseEntity<CollectionModel<EntityModel<Obra>>> buscarPorGenero(@PathVariable String genero) {
        List<Obra> obras = obraService.buscarPorGenero(genero);
        List<EntityModel<Obra>> recursos = obras.stream()
                .map(obra -> EntityModel.of(obra,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(obra.getTitulo())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorGenero(genero)).withRel("all")))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Obra>> collectionModel = CollectionModel.of(recursos);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorGenero(genero)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @Operation(summary = "Busca obra por título")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra encontrada"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada para o título")
    })
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<EntityModel<Obra>> buscarPorTitulo(@PathVariable String titulo) {
        Obra obra = obraService.buscarPorTitulo(titulo);
        EntityModel<Obra> resource = EntityModel.of(obra);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(titulo)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Busca obras por diretor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada para o diretor")
    })
    @GetMapping("/diretor/{diretor}")
    public ResponseEntity<CollectionModel<EntityModel<Obra>>> buscarPorDiretor(@PathVariable String diretor) {
        List<Obra> obras = obraService.buscarPorDiretor(diretor);
        List<EntityModel<Obra>> recursos = obras.stream()
                .map(obra -> EntityModel.of(obra,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorTitulo(obra.getTitulo())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorDiretor(diretor)).withRel("all")))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Obra>> collectionModel = CollectionModel.of(recursos);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ObraController.class).buscarPorDiretor(diretor)).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
}

