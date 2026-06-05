package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.CargaHoraria;
import com.gestaoSalarios.Service.CargaHorariaService;
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
@RequestMapping("/carga-horaria")
@SecurityRequirement(name = "bearerAuth")
public class CargaHorariaController {

    @Autowired
    private CargaHorariaService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    @Operation(summary = "Registar horas lecionadas")
    public ResponseEntity<CargaHoraria> registrar(@Valid @RequestBody CargaHoraria carga,
                                                  @RequestParam String docenteId,
                                                  @RequestParam String disciplinaId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registrarCargaHoraria(carga, docenteId, disciplinaId));
    }

    @GetMapping("/docente/{docenteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    public ResponseEntity<List<CargaHoraria>> listarPorDocente(@PathVariable String docenteId) {
        return ResponseEntity.ok(service.listarPorDocente(docenteId));
    }

    @GetMapping("/docente/{docenteId}/mes")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    public ResponseEntity<Double> getHorasNoMes(@PathVariable String docenteId,
                                                @RequestParam int ano,
                                                @RequestParam int mes) {
        return ResponseEntity.ok(service.getTotalHorasPorDocenteEMes(docenteId, ano, mes));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable String id) {
        service.removerCargaHoraria(id);
        return ResponseEntity.noContent().build();
    }
}