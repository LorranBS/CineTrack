package br.edu.iff.cinetrack.controller.apirest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.iff.cinetrack.entities.Administrador;
import br.edu.iff.cinetrack.entities.Obra;
import br.edu.iff.cinetrack.entities.Usuario;
import br.edu.iff.cinetrack.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/administrador")
public class AdministradorController {

    private final AdministradorService administradorService;

    @Autowired
    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @Operation(summary = "Cria um novo administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou administrador nulo")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Administrador>> criarAdministrador(@RequestBody Administrador administrador) {
        Administrador novoAdministrador = administradorService.criarAdministrador(administrador);
        EntityModel<Administrador> resource = EntityModel.of(novoAdministrador);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleta um administrador pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Administrador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdministrador(@PathVariable UUID id) {
        administradorService.deletarAdministrador(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário nulo")
    })
    @PostMapping("/usuario")
    public ResponseEntity<EntityModel<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = administradorService.criarUsuario(usuario);
        EntityModel<Usuario> resource = EntityModel.of(novoUsuario);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Deleta um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        administradorService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cria uma nova obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Obra criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou obra nula")
    })
    @PostMapping("/obra")
    public ResponseEntity<EntityModel<Obra>> criarObra(@RequestBody Obra obra) {
        Obra novaObra = administradorService.criarObra(obra);
        EntityModel<Obra> resource = EntityModel.of(novaObra);
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Remover uma obra pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Obra removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada")
    })
    @DeleteMapping("/obra/{id}")
    public ResponseEntity<Void> removerObra(@PathVariable UUID id) {
        administradorService.removerObra(id);
        return ResponseEntity.noContent().build();
    }
}

