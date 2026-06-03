package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;  // formato DISC001, DISC002...

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "curso", nullable = false)
    private String curso;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;  // carga horária total da disciplina

    // Relacionamento: muitas disciplinas pertencem a um docente (responsável)
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    // Método para gerar ID sequencial
    public static String gerarProximoId(String ultimoId) {
        if (ultimoId == null || ultimoId.isEmpty()) {
            return "DISC001";
        }
        String numeroStr = ultimoId.substring(4);
        int numero = Integer.parseInt(numeroStr);
        int proximoNumero = numero + 1;
        return String.format("DISC%03d", proximoNumero);
    }
}