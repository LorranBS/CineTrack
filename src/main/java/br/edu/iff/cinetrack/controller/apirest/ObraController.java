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
    public ResponseEntity<Obra> criarObra(@RequestBody Obra obra) {
        Obra novaObra = obraService.criarObra(obra);
        return new ResponseEntity<>(novaObra, HttpStatus.CREATED);
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

    @Operation(summary = "Lista todas as obras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras listadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada")
    })
    @GetMapping
    public ResponseEntity<List<Obra>> listarObras() {
        List<Obra> obras = obraService.listarObras();
        return new ResponseEntity<>(obras, HttpStatus.OK);
    }

    @Operation(summary = "Busca obras por gênero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada para o gênero")
    })
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Obra>> buscarPorGenero(@PathVariable String genero) {
        List<Obra> obras = obraService.buscarPorGenero(genero);
        return new ResponseEntity<>(obras, HttpStatus.OK);
    }

    @Operation(summary = "Busca obra por título")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obra encontrada"),
            @ApiResponse(responseCode = "404", description = "Obra não encontrada para o título")
    })
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Obra> buscarPorTitulo(@PathVariable String titulo) {
        Obra obra = obraService.buscarPorTitulo(titulo);
        return new ResponseEntity<>(obra, HttpStatus.OK);
    }

    @Operation(summary = "Busca obras por diretor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obras encontradas"),
            @ApiResponse(responseCode = "404", description = "Nenhuma obra encontrada para o diretor")
    })
    @GetMapping("/diretor/{diretor}")
    public ResponseEntity<List<Obra>> buscarPorDiretor(@PathVariable String diretor) {
        List<Obra> obras = obraService.buscarPorDiretor(diretor);
        return new ResponseEntity<>(obras, HttpStatus.OK);
    }
}
