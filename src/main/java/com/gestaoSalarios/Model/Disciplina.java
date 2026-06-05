package com.gestaoSalarios.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(unique = true)
    private String codigo;

    @NotBlank
    private String curso;

    @Positive
    private Integer cargaHoraria;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;
}