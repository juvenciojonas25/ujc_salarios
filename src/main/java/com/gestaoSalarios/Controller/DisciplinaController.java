package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Disciplina;
import com.gestaoSalarios.Service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity<Disciplina> cadastrarDisciplina(@RequestBody Disciplina disciplina,
                                                          @RequestParam String docenteId) {
        Disciplina nova = disciplinaService.cadastrarDisciplina(disciplina, docenteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    @Operation(summary = "Listar todas as disciplinas")
    public ResponseEntity<List<Disciplina>> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaService.listarDisciplinas());
    }

    @PutMapping("/{disciplinaId}/associar-docente")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Associar docente a uma disciplina (ou alterar responsável)")
    public ResponseEntity<Disciplina> associarDocente(@PathVariable String disciplinaId,
                                                      @RequestParam String docenteId) {
        return ResponseEntity.ok(disciplinaService.associarDocente(disciplinaId, docenteId));
    }

    @PutMapping("/{disciplinaId}/horas")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atribuir carga horária a uma disciplina (horas ao docente)")
    public ResponseEntity<Disciplina> atribuirHoras(@PathVariable String disciplinaId,
                                                    @RequestParam Integer cargaHoraria) {
        return ResponseEntity.ok(disciplinaService.atribuirHorasAoDocente(disciplinaId, cargaHoraria));
    }
}