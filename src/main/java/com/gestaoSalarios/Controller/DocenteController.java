package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Docente;
import com.gestaoSalarios.Service.DocenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docente")
@SecurityRequirement(name = "bearerAuth")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Registar docente")
    public ResponseEntity<Docente> registrarDocente(@Valid @RequestBody Docente docente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docenteService.registrarDocente(docente));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    public ResponseEntity<List<Docente>> listarDocentes() {
        return ResponseEntity.ok(docenteService.listarDocentes());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    public ResponseEntity<Docente> consultarPorId(@PathVariable String id) {
        return ResponseEntity.ok(docenteService.consultarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    public ResponseEntity<List<Docente>> consultarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(docenteService.consultarPorNome(nome));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Docente> atualizarDocente(@PathVariable String id, @Valid @RequestBody Docente docente) {
        return ResponseEntity.ok(docenteService.atualizarDocente(id, docente));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removerDocente(@PathVariable String id) {
        docenteService.removerDocente(id);
        return ResponseEntity.noContent().build();
    }
}