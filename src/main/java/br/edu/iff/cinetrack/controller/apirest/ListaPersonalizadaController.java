package br.edu.iff.cinetrack.controller.apirest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.service.ListaPersonalizadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lista")
public class ListaPersonalizadaController {

    private final ListaPersonalizadaService listaPersonalizadaService;

    @Autowired
    public ListaPersonalizadaController(ListaPersonalizadaService listaPersonalizadaService) {
        this.listaPersonalizadaService = listaPersonalizadaService;
    }

    @Operation(summary = "Cria uma nova lista personalizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista personalizada criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou lista personalizada nula")
    })
    @PostMapping("/{criadorId}")
    public ResponseEntity<EntityModel<ListaPersonalizada>> criarListaPersonalizada(@PathVariable UUID criadorId, @RequestBody ListaPersonalizada lista) {
        ListaPersonalizada novaLista = listaPersonalizadaService.criarListaPersonalizada(criadorId, lista);
        EntityModel<ListaPersonalizada> resource = EntityModel.of(novaLista);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleta uma lista personalizada pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lista personalizada deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista personalizada não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarListaPersonalizada(@PathVariable UUID id) {
        listaPersonalizadaService.deletarListaPersonalizada(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca listas personalizadas pelo criador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listas personalizadas encontradas"),
            @ApiResponse(responseCode = "404", description = "Listas personalizadas não encontradas para o criador")
    })
    @GetMapping("/criador/{criadorId}")
    public ResponseEntity<CollectionModel<EntityModel<ListaPersonalizada>>> buscarPeloCriador(@PathVariable UUID criadorId) {
        List<ListaPersonalizada> listas = listaPersonalizadaService.buscarPeloCriador(criadorId);
        List<EntityModel<ListaPersonalizada>> recursos = listas.stream()
                .map(lista -> EntityModel.of(lista,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ListaPersonalizadaController.class).buscarPeloCriador(criadorId)).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<ListaPersonalizada>> collectionModel = CollectionModel.of(recursos);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @Operation(summary = "Adiciona uma obra à lista personalizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra adicionada à lista"),
            @ApiResponse(responseCode = "404", description = "Lista personalizada ou obra não encontrado")
    })
    @PostMapping("/{idLista}/obra")
    public ResponseEntity<Void> adicionarObraNaLista(@PathVariable UUID idLista, @RequestBody Obra obra) {
        listaPersonalizadaService.adicionarObraNaLista(idLista, obra);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Remove uma obra da lista personalizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra removida da lista"),
            @ApiResponse(responseCode = "404", description = "Lista personalizada ou obra não encontrado")
    })
    @DeleteMapping("/{idLista}/obra/{obraId}")
    public ResponseEntity<Void> removerObraDaLista(@PathVariable UUID idLista, @PathVariable UUID obraId) {
        listaPersonalizadaService.removerObraDaLista(idLista, obraId);
        return ResponseEntity.noContent().build();
    }
}

