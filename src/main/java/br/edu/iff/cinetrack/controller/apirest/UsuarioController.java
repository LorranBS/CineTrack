package br.edu.iff.cinetrack.controller.apirest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.cinetrack.entities.DiarioItem;
import br.edu.iff.cinetrack.entities.ListaPersonalizada;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário nulo")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        EntityModel<Usuario> resource = EntityModel.of(novoUsuario);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleta um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        EntityModel<Usuario> resource = EntityModel.of(usuario);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorId(id)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Busca um usuário pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<EntityModel<Usuario>> buscarPorNome(@PathVariable String nome) {
        Usuario usuario = usuarioService.buscarPorNome(nome);
        EntityModel<Usuario> resource = EntityModel.of(usuario);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarPorNome(nome)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @Operation(summary = "Busca o diário do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diário encontrado"),
            @ApiResponse(responseCode = "404", description = "Diário não encontrado")
    })
    @GetMapping("/{id}/diario")
    public ResponseEntity<CollectionModel<EntityModel<DiarioItem>>> buscarDiarioDoUsuario(@PathVariable UUID id) {
        List<DiarioItem> diario = usuarioService.buscarDiarioDoUsuario(id);
        List<EntityModel<DiarioItem>> recursos = diario.stream()
                .map(item -> EntityModel.of(item,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).buscarDiarioDoUsuario(id)).withSelfRel()))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<DiarioItem>> collectionModel = CollectionModel.of(recursos);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @Operation(summary = "Adiciona uma obra ao diário do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra adicionada ao diário"),
            @ApiResponse(responseCode = "404", description = "Usuário ou diário não encontrado")
    })
    @PostMapping("/{id}/diario")
    public ResponseEntity<Void> adicionarObraAoDiario(@PathVariable UUID id, @RequestBody DiarioItem diarioItem) {
        usuarioService.adicionarObraAoDiario(id, diarioItem);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Remove uma obra do diário do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra removida do diário"),
            @ApiResponse(responseCode = "404", description = "Usuário ou diário não encontrado")
    })
    @DeleteMapping("/{id}/diario/{diarioId}")
    public ResponseEntity<Void> removerObraDoDiario(@PathVariable UUID id, @PathVariable UUID diarioId) {
        usuarioService.removerObraDoDiario(id, diarioId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona uma lista personalizada ao usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista personalizada adicionada"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/{id}/lista")
    public ResponseEntity<Void> adicionarListaPersonalizada(@PathVariable UUID id, @RequestBody ListaPersonalizada lista) {
        usuarioService.adicionarListaPersonalizada(id, lista);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Remove uma lista personalizada do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lista personalizada removida"),
            @ApiResponse(responseCode = "404", description = "Usuário ou lista personalizada não encontrado")
    })
    @DeleteMapping("/{id}/lista/{listaId}")
    public ResponseEntity<Void> removerListaPersonalizada(@PathVariable UUID id, @PathVariable UUID listaId) {
        usuarioService.removerListaPersonalizada(id, listaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona uma obra na lista personalizada do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra adicionada na lista personalizada"),
            @ApiResponse(responseCode = "404", description = "Usuário, lista personalizada ou obra não encontrado")
    })
    @PostMapping("/{id}/lista/{listaId}/obra")
    public ResponseEntity<Void> adicionarObraNaListaPersonalizada(@PathVariable UUID id, @PathVariable UUID listaId, @RequestBody Obra obra) {
        usuarioService.adicionarObraNaListaPersonalizada(id, listaId, obra);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Remover uma obra na lista personalizada do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrado")
    })
    @DeleteMapping("/{id}/lista/{listaId}/obra")
    public ResponseEntity<Void> removerObraDaLista(@PathVariable UUID id, @PathVariable UUID listaId, @RequestBody UUID obra) {
        usuarioService.removerObraDaListaPersonalizada(id, listaId, obra);
        return ResponseEntity.noContent().build();
    }
}
