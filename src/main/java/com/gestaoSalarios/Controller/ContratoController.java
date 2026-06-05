package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.Contrato;
import com.gestaoSalarios.Service.ContratoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrato")
@SecurityRequirement(name = "bearerAuth")
public class ContratoController {

    @Autowired
    private ContratoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Contrato> registrar(@Valid @RequestBody Contrato contrato,
                                              @RequestParam String docenteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarContrato(contrato, docenteId));
    }

    @GetMapping("/docente/{docenteId}/ativos")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    public ResponseEntity<List<Contrato>> listarAtivos(@PathVariable String docenteId) {
        return ResponseEntity.ok(service.listarContratosAtivosPorDocente(docenteId));
    }

    @GetMapping("/docente/{docenteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO')")
    public ResponseEntity<List<Contrato>> listarTodos(@PathVariable String docenteId) {
        return ResponseEntity.ok(service.listarContratosPorDocente(docenteId));
    }
}