package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;  // formato CON001, CON002...

    @Column(name = "numero", nullable = false, unique = true)
    private String numero;  // número do contrato

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;  // pode ser null se contrato vigente

    @Column(name = "valor_pago_por_hora", nullable = false)
    private Double valorPagoPorHora;

    @Column(name = "carga_horaria_prevista", nullable = false)
    private Integer cargaHorariaPrevista;  // total de horas previstas no contrato

    public static String gerarProximoId(String ultimoId) {
        if (ultimoId == null || ultimoId.isEmpty()) {
            return "CON001";
        }
        String numeroStr = ultimoId.substring(3);
        int numero = Integer.parseInt(numeroStr);
        int proximoNumero = numero + 1;
        return String.format("CON%03d", proximoNumero);
    }
}