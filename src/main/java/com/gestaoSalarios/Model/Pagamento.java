package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @Column(name = "referencia_pagamento", nullable = false, unique = true)
    private String referenciaPagamento;

    @Column(name = "mes", nullable = false)
    private String mes;

    @Column(name = "total_horas", nullable = false)
    private Double totalHoras;

    @Column(name = "valor_hora", nullable = false)
    private Double valorHora;

    @Column(name = "salario_bruto", nullable = false)
    private Double salarioBruto;

    @Column(name = "desconto_irps", nullable = false)
    private Double descontoIrps;

    @Column(name = "salario_liquido", nullable = false)
    private Double salarioLiquido;

    @Column(name = "data_processamento", nullable = false)
    private LocalDateTime dataProcessamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pagamento", nullable = false)
    private EstadoPagamento estadoPagamento;

    public static String gerarProximoId(String ultimoId) {
        if (ultimoId == null || ultimoId.isEmpty()) {
            return "PAG001";
        }
        String numeroStr = ultimoId.substring(3);
        int numero = Integer.parseInt(numeroStr);
        int proximoNumero = numero + 1;
        return String.format("PAG%03d", proximoNumero);
    }
}