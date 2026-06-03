package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Service.DocenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docente")
@SecurityRequirement(name = "bearerAuth") // para Swagger
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registar docente", description = "Apenas ADMIN pode cadastrar")
    public ResponseEntity<Docente> registrarDocente(@RequestBody Docente docente) {
        Docente novo = docenteService.registrarDocente(docente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    @Operation(summary = "Listar todos os docentes")
    public ResponseEntity<List<Docente>> listarDocentes() {
        return ResponseEntity.ok(docenteService.listarDocentes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    @Operation(summary = "Consultar docente por ID")
    public ResponseEntity<Docente> consultarPorId(@PathVariable String id) {
        return ResponseEntity.ok(docenteService.consultarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    @Operation(summary = "Consultar docente por nome")
    public ResponseEntity<List<Docente>> consultarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(docenteService.consultarPorNome(nome));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar dados de docente")
    public ResponseEntity<Docente> atualizarDocente(@PathVariable String id, @RequestBody Docente docente) {
        return ResponseEntity.ok(docenteService.atualizarDocente(id, docente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remover docente")
    public ResponseEntity<Void> removerDocente(@PathVariable String id) {
        docenteService.removerDocente(id);
        return ResponseEntity.noContent().build();
    }
}