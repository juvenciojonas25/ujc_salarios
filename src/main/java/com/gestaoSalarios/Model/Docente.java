package com.gestaoSalarios.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "docente")
public class Docente {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    private String apelido;

    @NotBlank
    @Column(unique = true)
    private String nuit;

    @NotBlank
    @Pattern(regexp = "M|F")
    private String genero;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @Positive
    private Double valorPagoPorHora;

    private String banco;

    @Column(unique = true)
    private String nib;

    @NotBlank
    private String categoria;
}