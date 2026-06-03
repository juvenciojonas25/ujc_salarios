package com.gestaoSalarios.Controller;

import com.gestaoSalarios.Model.EstadoPagamento;
import com.gestaoSalarios.Model.Pagamento;
import com.gestaoSalarios.Service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/processar")
    @PreAuthorize("hasRole('FINANCEIRO')")
    @Operation(summary = "Processar pagamento de um docente num determinado mês (formato yyyy-MM)")
    public ResponseEntity<Pagamento> processarPagamento(@RequestParam String docenteId,
                                                        @RequestParam String mes) {
        Pagamento pagamento = pagamentoService.processarPagamento(docenteId, mes);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os pagamentos (apenas ADMIN)")
    public ResponseEntity<List<Pagamento>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    @GetMapping("/docente/{docenteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCEIRO', 'DOCENTE')")
    @Operation(summary = "Consultar pagamentos de um docente")
    public ResponseEntity<List<Pagamento>> listarPagamentosPorDocente(@PathVariable String docenteId) {
        return ResponseEntity.ok(pagamentoService.listarPagamentosPorDocente(docenteId));
    }

    @PatchMapping("/{pagamentoId}/estado")
    @PreAuthorize("hasRole('FINANCEIRO')")
    @Operation(summary = "Alterar estado do pagamento (PROCESSADO, PAGO, PENDENTE)")
    public ResponseEntity<Pagamento> alterarEstado(@PathVariable String pagamentoId,
                                                   @RequestParam EstadoPagamento novoEstado) {
        return ResponseEntity.ok(pagamentoService.alterarEstadoPagamento(pagamentoId, novoEstado));
    }
}