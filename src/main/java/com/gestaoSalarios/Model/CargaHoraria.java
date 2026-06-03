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
@Table(name = "carga_horaria")
public class CargaHoraria {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;  // formato CH001, CH002...

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(name = "data_aula", nullable = false)
    private LocalDate dataAula;

    @Column(name = "horas_lecionadas", nullable = false)
    private Double horasLecionadas;  // pode ser fracionado (2.5 horas)

    public static String gerarProximoId(String ultimoId) {
        if (ultimoId == null || ultimoId.isEmpty()) {
            return "CH001";
        }
        String numeroStr = ultimoId.substring(2);
        int numero = Integer.parseInt(numeroStr);
        int proximoNumero = numero + 1;
        return String.format("CH%03d", proximoNumero);
    }
}