package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Service.DisciplinaService;
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
@RequestMapping("/disciplina")
@SecurityRequirement(name = "bearerAuth")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastrar disciplina e associar a um docente")
    public ResponseEntity<Disciplina> cadastrarDisciplina(@Valid @RequestBody Disciplina disciplina,
                                                          @RequestParam String docenteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaService.cadastrarDisciplina(disciplina, docenteId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaService.listarDisciplinas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(id));
    }

    @PutMapping("/{disciplinaId}/associar-docente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Disciplina> associarDocente(@PathVariable String disciplinaId,
                                                      @RequestParam String docenteId) {
        return ResponseEntity.ok(disciplinaService.associarDocente(disciplinaId, docenteId));
    }

    @PutMapping("/{disciplinaId}/horas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Disciplina> atribuirHoras(@PathVariable String disciplinaId,
                                                    @RequestParam Integer cargaHoraria) {
        return ResponseEntity.ok(disciplinaService.atribuirHorasAoDocente(disciplinaId, cargaHoraria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable String id) {
        disciplinaService.removerDisciplina(id);
        return ResponseEntity.noContent().build();
    }
}